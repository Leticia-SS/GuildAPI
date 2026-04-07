package com.example.guildapi.dto;

import com.example.guildapi.model.enums.NivelPerigoEnum;
import com.example.guildapi.model.enums.StatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MissaoUpdateRequest {
    private String titulo;
    private NivelPerigoEnum nivelPerigo;
    private StatusEnum status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
