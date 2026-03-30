package com.example.guildapi.model.audit;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionId implements Serializable {
    private Long role_id;
    private Long permission_id;
}