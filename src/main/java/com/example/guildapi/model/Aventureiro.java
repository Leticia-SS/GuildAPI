package com.example.guildapi.model;

import com.example.guildapi.model.Enum.ClasseEnum;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Aventureiro {
    private Integer id;
    private String nome;
    @Enumerated(EnumType.STRING)
    private ClasseEnum classe;
    private int nivel;
    private boolean ativo;
    @Embedded
    private Companheiro companheiro;

}
