package com.example.guildapi.model.audit;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Entity
@Getter@Setter
@Table(name = "audit_entries", schema = "audit")
public class AuditEntries {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;
    @ManyToOne
    @JoinColumn(name = "actor_user_id")
    private Usuario actorUser;
    @ManyToOne
    @JoinColumn(name = "actor_api_key_id")
    private ApiKeys actorApiKey;
    @Column(nullable = false, length = 30)
    private String action;
    @Column(name = "entity_schema", nullable = false, length = 60)
    private String entitySchema;
    @Column(name = "entity_name", nullable = false, length = 80)
    private String entityName;
    @Column(name = "entity_id", length = 80)
    private String entityId;
    @Column(name = "occurred_at", nullable = false, updatable = false)
    private LocalDateTime occurredAt = LocalDateTime.now();
    private String ip;
    @Column(name = "user_agent", length = 255)
    private String userAgent;
    private String diff;
    private String metadata;
    @Column(nullable = false)
    private boolean success = true;
}
