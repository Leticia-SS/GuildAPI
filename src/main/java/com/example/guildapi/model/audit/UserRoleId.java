package com.example.guildapi.model.audit;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class UserRoleId implements Serializable {
    private Long usuario_id;
    private Long role_id;
}
