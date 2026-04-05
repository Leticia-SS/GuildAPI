package com.example.guildapi.repository;

import com.example.guildapi.model.aventura.ParticipacaoMissao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IParticipacaoDeMissaoRepository extends JpaRepository<ParticipacaoMissao, Long> {
    long countByAventureiroId(Long aventureiroId);
    Optional<ParticipacaoMissao> findTopByAventureiroIdOrderByCreated_AtDesc(Long aventureiroId);
}
