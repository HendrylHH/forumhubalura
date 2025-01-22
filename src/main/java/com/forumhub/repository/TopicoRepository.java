package com.forumhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.forumhub.model.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    // Métodos adicionais de consulta podem ser definidos aqui
}
