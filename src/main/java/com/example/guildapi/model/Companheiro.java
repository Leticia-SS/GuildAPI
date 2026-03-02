package com.example.guildapi.model;

import com.example.guildapi.model.Enum.EspecieEnum;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Embeddable
public class Companheiro {
    private String nome;
    @Enumerated(EnumType.STRING)
    private EspecieEnum especie;
    private int lealdade;
}
