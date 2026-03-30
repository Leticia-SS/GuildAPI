package com.example.guildapi.model.audit;

import com.example.guildapi.model.enums.StatusUsuarioEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios", schema = "audit")
public class Usuario {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;
    @Column(nullable = false, length = 120)
    private String nome;
    @Column(nullable = false, length = 180)
    private String email;
    @Column(name = "senha_hash", nullable = false, length = 255)
    private String senhaHash;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusUsuarioEnum status;
    @Column(name = "ultimo_login_em")
    private LocalDateTime ultimoLoginEm;
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
    @OneToMany(mappedBy = "usuario")
    private List<UserRole> roles;

}