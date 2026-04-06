package com.example.guildapi.repository;

import com.example.guildapi.model.aventura.Missao;
import com.example.guildapi.model.enums.NivelPerigoEnum;
import com.example.guildapi.model.enums.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface IMissaoRepository extends JpaRepository<Missao, Long> {
    Page<Missao> findByStatus(StatusEnum status, Pageable pageable);
    Page<Missao> findByNivelPerigo(NivelPerigoEnum nivelPerigo, Pageable pageable);
    Page<Missao> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    Page<Missao> findByStatusAndNivelPerigo(StatusEnum status, NivelPerigoEnum nivelPerigo, Pageable pageable);
    Page<Missao> findByStatusAndCreatedAtBetween(StatusEnum status, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    Page<Missao> findByNivelPerigoAndCreatedAtBetween(NivelPerigoEnum nivelPerigo, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    Page<Missao> findByStatusAndNivelPerigoAndCreatedAtBetween(StatusEnum status, NivelPerigoEnum nivelPerigo, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);


}
