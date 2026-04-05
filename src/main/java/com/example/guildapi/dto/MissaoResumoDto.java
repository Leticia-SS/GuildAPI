package com.example.guildapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MissaoResumoDto {
    private Long id;
    private String titulo;
    private String status;
    private String papelNaMissao;
    private Integer recompensaOuro;
    private boolean destaque;
}
