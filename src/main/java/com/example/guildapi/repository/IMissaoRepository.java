package com.example.guildapi.repository;

import com.example.guildapi.model.Missao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMissaoRepository extends JpaRepository<Missao, Long> {
}
