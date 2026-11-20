package com.donavita.avaliacoes_api.repository;

import com.donavita.avaliacoes_api.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
}