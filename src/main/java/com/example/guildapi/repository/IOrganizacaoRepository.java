package com.example.guildapi.repository;

import com.example.guildapi.model.Organizacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrganizacaoRepository extends JpaRepository<Organizacao, Long> {
}
