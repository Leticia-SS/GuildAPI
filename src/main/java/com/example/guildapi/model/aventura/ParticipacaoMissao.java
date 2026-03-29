package com.example.guildapi.model.aventura;

import com.example.guildapi.model.enums.PapelMisaoEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "participacao_missao") //schema = "aventura"
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
