package com.example.guildapi.model;

import com.example.guildapi.model.Enum.EspecieEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Embeddable
public class Companheiro {
    @Column(length = 120)
    private String nome;
    @Enumerated(EnumType.STRING)
    private EspecieEnum especie;
    @Min(1)@Max(100)
    private int lealdade;
}
