package com.example.guildapi.dto;

import com.example.guildapi.model.enums.NivelPerigoEnum;
import com.example.guildapi.model.enums.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MissaoCadastroDto {
    @NotBlank(message = "Título é obrigatório")
    private String titulo;
    @NotNull(message = "Nível de perigo é obrigatório")
    private NivelPerigoEnum nivelPerigo;
    @NotNull(message = "Status é obrigatório")
    private StatusEnum status;
    @NotNull(message = "ID da organização é obrigatório")
    private Long organizacaoId;
}
