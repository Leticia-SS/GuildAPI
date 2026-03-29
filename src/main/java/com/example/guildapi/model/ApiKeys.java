package com.example.guildapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Entity
@Getter@Setter
@Table(name = "api_keys", schema = "audit")
public class ApiKeys {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;
    @Column(nullable = false, length = 120)
    private String nome;
    @Column(name = "key_hash", nullable = false, length = 255)
    private String keyHash;
    @Column(nullable = false)
    private boolean ativo = true;
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(name = "last_used_at")
    private LocalDateTime lastUsedAt;
}
