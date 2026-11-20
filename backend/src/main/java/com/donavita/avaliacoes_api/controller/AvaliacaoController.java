package com.donavita.avaliacoes_api.controller;

import com.donavita.avaliacoes_api.model.Avaliacao;
import com.donavita.avaliacoes_api.service.AvaliacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Avaliações", description = "Operações CRUD para avaliações da plataforma DonaVita")
@RestController
@RequestMapping("/avaliacoes")
@CrossOrigin(origins = "*")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @Operation(summary = "Lista todas as avaliações", description = "Retorna todas as avaliações cadastradas no banco de dados")
    @ApiResponse(responseCode = "200", description = "Lista de avaliações retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<Avaliacao>> listarTodos() {
        return ResponseEntity.ok(avaliacaoService.listarTodos());
    }

    @Operation(summary = "Busca uma avaliação pelo ID", description = "Retorna os detalhes de uma avaliação específica")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Avaliação encontrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Avaliação não encontrada para o ID informado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Avaliacao> findById(@PathVariable Long id) {
        return avaliacaoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cadastra uma nova avaliação", description = "Insere uma nova avaliação com nome, nota (1 a 5) e comentário")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Avaliação cadastrada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição")
    })
    @PostMapping
    public ResponseEntity<Avaliacao> salvar(@Valid @RequestBody Avaliacao avaliacao) {
        Avaliacao salva = avaliacaoService.salvar(avaliacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @Operation(summary = "Atualiza uma avaliação pelo ID", description = "Atualiza nome, nota e comentário de uma avaliação existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Avaliação atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição"),
        @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Avaliacao> atualizar(@PathVariable Long id, @Valid @RequestBody Avaliacao avaliacao) {
        return avaliacaoService.atualizar(id, avaliacao)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Exclui uma avaliação pelo ID", description = "Remove permanentemente uma avaliação pelo seu identificador")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Avaliação excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (avaliacaoService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}