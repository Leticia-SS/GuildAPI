package com.example.guildapi.model.audit;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter@Setter
public class Permissions {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 80, unique = true)
    private String code;
    @Column(nullable = false, length = 255)
    private String descricao;
}
