package com.example.guildapi.repository;

import com.example.guildapi.model.aventura.Aventureiro;
import com.example.guildapi.model.enums.ClasseEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAventureiroRepository extends JpaRepository<Aventureiro, Long> {
    Page<Aventureiro> findByClasse(ClasseEnum classe, Pageable pageable);
    Page<Aventureiro> findByAtivo(boolean ativo, Pageable pageable);
    Page<Aventureiro> findByNivelGreaterThanEqual(Integer nivelMinimo, Pageable pageable);
}
