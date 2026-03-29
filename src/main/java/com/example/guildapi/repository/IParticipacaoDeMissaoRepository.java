package com.example.guildapi.repository;

import com.example.guildapi.model.aventura.ParticipacaoMissao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IParticipacaoDeMissaoRepository extends JpaRepository<ParticipacaoMissao, Long> {
}
