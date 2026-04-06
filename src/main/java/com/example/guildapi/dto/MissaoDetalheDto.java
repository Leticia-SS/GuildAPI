package com.example.guildapi.dto;

import com.example.guildapi.model.enums.NivelPerigoEnum;
import com.example.guildapi.model.enums.StatusEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MissaoDetalheDto {
    private Long id;
    private String titulo;
    private StatusEnum status;
    private NivelPerigoEnum nivelPerigo;
    private LocalDateTime createdAt;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String nomeOrganizacao;
    private List<ParticipanteDto> participantes;
}
