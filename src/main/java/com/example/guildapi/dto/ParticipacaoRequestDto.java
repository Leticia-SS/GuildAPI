package com.example.guildapi.dto;

import com.example.guildapi.model.enums.PapelMisaoEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ParticipacaoRequestDto {
    @NotNull(message = "ID da missão é obrigatório")
    private Long missaoId;
    @NotNull(message = "ID do aventureiro é obrigatório")
    private Long aventureiroId;
    @NotNull(message = "Papel é obrigatório")
    private PapelMisaoEnum papel;
    @Min(value = 0, message = "Recompensa deve ser maior ou igual a 0")
    private Integer recompensa = 0;
    private boolean mvp = false;
}
