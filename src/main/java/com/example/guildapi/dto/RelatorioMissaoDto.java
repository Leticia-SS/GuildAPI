package com.example.guildapi.dto;

import com.example.guildapi.model.enums.NivelPerigoEnum;
import com.example.guildapi.model.enums.StatusEnum;
import lombok.Data;

@Data
public class RelatorioMissaoDto {
    private Long missaoId;
    private String titulo;
    private StatusEnum status;
    private NivelPerigoEnum nivelPerigo;
    private Long totalParticipantes;
    private Long totalRecompensas;
}
