package com.example.guildapi.model.audit;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "permissions",
        schema = "audit",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "permissions_code_key",
                    columnNames = {"code"}
            )
})
public class Permission {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 80, unique = true)
    private String code;
    @Column(nullable = false, length = 255)
    private String descricao;
}
