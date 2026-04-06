package com.example.guildapi.dto;

import lombok.Data;

@Data
public class ParticipanteDto {
    private Long aventureiroId;
    private String nomeAventureiro;
    private String papel;
    private Integer recompensa;
    private boolean destaque;
}
