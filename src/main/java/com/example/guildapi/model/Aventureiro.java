package com.example.guildapi.model;

import com.example.guildapi.model.Enum.ClasseEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "aventureiros")
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
    @AttributeOverrides({
            @AttributeOverride(name = "nome", column = @Column(name = "companheiro_nome")),
            @AttributeOverride(name = "especie", column = @Column(name = "companheiro_especie")),
            @AttributeOverride(name = "lealdade", column = @Column(name = "companheiro_lealdade"))
    })
    private Companheiro companheiro;

}
