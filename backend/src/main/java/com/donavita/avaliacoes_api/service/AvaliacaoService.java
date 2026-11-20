package com.donavita.avaliacoes_api.service;

import com.donavita.avaliacoes_api.model.Avaliacao;
import com.donavita.avaliacoes_api.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    public List<Avaliacao> listarTodos() {
        return avaliacaoRepository.findAll();
    }

    public Optional<Avaliacao> findById(Long id) {
        return avaliacaoRepository.findById(id);
    }

    public Avaliacao salvar(Avaliacao avaliacao) {
        return avaliacaoRepository.save(avaliacao);
    }

    public Optional<Avaliacao> atualizar(Long id, Avaliacao dadosAtualizados) {
        return avaliacaoRepository.findById(id).map(existente -> {
            existente.setNome(dadosAtualizados.getNome());
            existente.setNota(dadosAtualizados.getNota());
            existente.setComentario(dadosAtualizados.getComentario());
            return avaliacaoRepository.save(existente);
        });
    }

    public boolean deletar(Long id) {
        if (avaliacaoRepository.existsById(id)) {
            avaliacaoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}