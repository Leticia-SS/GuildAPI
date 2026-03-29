package com.example.guildapi.model.aventura;

import com.example.guildapi.model.audit.Organizacao;
import com.example.guildapi.model.enums.NivelPerigoEnum;
import com.example.guildapi.model.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "missoes") //schema = "aventura"
public class Missao {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "organizacao_id")
    private Organizacao organizacao;
    @Column(length = 150, nullable = false)
    private String titulo;
    @Enumerated(EnumType.STRING)
    private NivelPerigoEnum nivelPerigo;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    @Column(updatable = false)
    private LocalDateTime created_at = LocalDateTime.now();
    private LocalDateTime start_time;
    private LocalDateTime end_time;
}
