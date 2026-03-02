package com.example.guildapi.model;

import com.example.guildapi.model.Enum.ClasseEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
public class Aventureiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    @Enumerated(EnumType.STRING)
    private ClasseEnum classe;
    private Integer nivel;
    private boolean ativo;
    @Embedded
    private Companheiro companheiro;

}
