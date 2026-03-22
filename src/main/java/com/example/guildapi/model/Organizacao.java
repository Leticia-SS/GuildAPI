package com.example.guildapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "organizacoes") //schema = "audit"
public class Organizacao {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private boolean ativo;
    @Column(updatable = false)
    private LocalDateTime created_at = LocalDateTime.now();

}