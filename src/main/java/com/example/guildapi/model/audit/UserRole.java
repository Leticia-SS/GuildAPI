package com.example.guildapi.model.audit;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_roles", schema = "audit")
public class UserRole {
    @EmbeddedId
    private UserRoleId id;
    @ManyToOne
    @MapsId("usuario_id")
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @ManyToOne
    @MapsId("role_id")
    @JoinColumn(name = "role_id")
    private Role role;
    @Column(name = "granted_at", nullable = false, updatable = false)
    private LocalDateTime grantedAt = LocalDateTime.now();
}
