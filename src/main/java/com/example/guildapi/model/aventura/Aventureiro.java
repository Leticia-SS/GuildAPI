package com.example.guildapi.model.aventura;

import com.example.guildapi.model.audit.Organizacao;
import com.example.guildapi.model.enums.ClasseEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "aventureiros", schema = "aventura")
public class Aventureiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(
            name = "organizacao_id",
            nullable = false
    )
    private Organizacao organizacao;
    @Column(length = 120, nullable = false)
    private String nome;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClasseEnum classe;
    @Min(1)
    private Integer nivel = 1;
    @Column(nullable = false)
    private boolean ativo;
    @Column(updatable = false, nullable = false)
    private LocalDateTime created_at = LocalDateTime.now();
    @Column(nullable = false)
    private LocalDateTime updated_at;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "nome", column = @Column(name = "companheiro_nome")),
            @AttributeOverride(name = "especie", column = @Column(name = "companheiro_especie")),
            @AttributeOverride(name = "lealdade", column = @Column(name = "companheiro_lealdade"))
    })
    private Companheiro companheiro;

}
