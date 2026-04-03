package com.example.guildapi.repository;

import com.example.guildapi.model.audit.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
}
