package com.example.guildapi.model;

import com.example.guildapi.model.Enum.PapelMisaoEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Entity
@Getter@Setter
public class ParticipacaoMissao {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "missao_id")
    private Missao missao;
    @ManyToOne
    @JoinColumn(name = "aventureiro_id")
    private Aventureiro aventureiro;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PapelMisaoEnum papel;
    @Min(0)
    private Integer recompensa;
    @Column(nullable = false)
    private boolean mvp;
    private LocalDateTime created_At = LocalDateTime.now();

}
