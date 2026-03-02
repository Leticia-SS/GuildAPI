package com.example.guildapi.repository;

import com.example.guildapi.model.Aventureiro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAventureiroRepository extends JpaRepository<Aventureiro, Integer> {
}
