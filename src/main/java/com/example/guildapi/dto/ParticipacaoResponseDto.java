package com.example.guildapi.dto;

import com.example.guildapi.model.enums.PapelMisaoEnum;
import lombok.Data;

@Data
public class ParticipacaoResponseDto {
    private Long id;
    private Long missaoId;
    private String missaoTitulo;
    private Long aventureiroId;
    private String aventureiroNome;
    private PapelMisaoEnum papel;
    private Integer recompensa;
    private boolean mvp;
}
