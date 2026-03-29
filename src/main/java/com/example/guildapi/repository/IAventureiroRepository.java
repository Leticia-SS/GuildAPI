package com.example.guildapi.repository;

import com.example.guildapi.model.aventura.Aventureiro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAventureiroRepository extends JpaRepository<Aventureiro, Long> {
}
