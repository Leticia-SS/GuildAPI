package com.example.guildapi.model.audit;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles",
        schema = "audit",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "uq_roles_nome_por_org",
                    columnNames = {"organizacao_id", "nome"}
            )
})
public class Role {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;
    @Column(nullable = false, length = 60)
    private String nome;
    @Column(length = 255)
    private String descricao;
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    @OneToMany(mappedBy = "role")
    private List<RolePermission> permissions;
    @OneToMany(mappedBy = "role")
    private List<UserRole> usuarios;
}