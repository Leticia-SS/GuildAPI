package com.example.guildapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankingParticipacaoDto {
    private Long aventureiroId;
    private String nome;
    private Long totalParticipacoes;
    private Long totalRecompensas;
    private Long totalDestaques;
}
