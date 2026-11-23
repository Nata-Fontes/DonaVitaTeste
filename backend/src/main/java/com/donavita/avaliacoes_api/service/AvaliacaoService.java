package com.donavita.avaliacoes_api.service;

import com.donavita.avaliacoes_api.model.Avaliacao;
import com.donavita.avaliacoes_api.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Value("${donavita.admin.emails:admin@donavita.com}")
    private String adminEmailsConfig;

    private boolean isEmailAdmin(String email) {
        if (email == null || email.isBlank()) return false;
        return Arrays.stream(adminEmailsConfig.split(","))
                .map(String::trim)
                .anyMatch(adminEmail -> adminEmail.equalsIgnoreCase(email.trim()));
    }

    public List<Avaliacao> listarTodos() {
        return avaliacaoRepository.findAll();
    }

    public Optional<Avaliacao> findById(Long id) {
        return avaliacaoRepository.findById(id);
    }

    public Avaliacao salvar(Avaliacao avaliacao) {
        return avaliacaoRepository.save(avaliacao);
    }

    public Avaliacao atualizar(Long id, Avaliacao dadosAtualizados) {
        Avaliacao existente = avaliacaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avaliação não encontrada"));

        boolean isAutor = existente.getUsuarioId() != null
                && existente.getUsuarioId().equals(dadosAtualizados.getUsuarioId());

        if (!isAutor) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você só pode editar suas próprias avaliações");
        }

        existente.setNota(dadosAtualizados.getNota());
        existente.setComentario(dadosAtualizados.getComentario());
        return avaliacaoRepository.save(existente);
    }

    public void deletar(Long id, String usuarioId, String usuarioEmail, boolean isAdmin) {
        Avaliacao existente = avaliacaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avaliação não encontrada"));

        boolean isAutor = existente.getUsuarioId() != null
                && existente.getUsuarioId().equals(usuarioId);
        boolean temPermissaoAdmin = isAdmin || isEmailAdmin(usuarioEmail);

        if (!isAutor && !temPermissaoAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Apenas o autor ou um ADM pode excluir esta avaliação");
        }

        avaliacaoRepository.delete(existente);
    }
}
