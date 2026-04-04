package com.example.guildapi.dto;

import com.example.guildapi.model.enums.ClasseEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AventureiroResumoDto {
    private Long id;
    private String nome;
    private ClasseEnum classe;
    private Integer nivel;
    private boolean ativo;
}
