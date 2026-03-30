package com.example.guildapi.model.audit;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role_permissions", schema = "audit")
public class RolePermission {
    @EmbeddedId
    private RolePermissionId id;
    @ManyToOne
    @MapsId("role_id")
    @JoinColumn(name = "role_id")
    private Role role;
    @ManyToOne
    @MapsId("permission_id")
    @JoinColumn(name = "permission_id")
    private Permission permission;
}
