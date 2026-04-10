package com.example.guildapi.model.operacoes;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vw_painel_tatico_missao", schema = "operacoes")
public class PainelTaticoMissao {
    @Id
    @Column(name = "missao_id")
    private Long id;
    private String titulo;
    private String status;
    @Column(name = "nivel_perigo")
    private String nivelPerigo;
    @Column(name = "organizacao_id")
    private Long organizacaoId;
    @Column(name = "total_participantes")
    private Integer totalParticipantes;
    @Column(name = "nivel_medio_equipe")
    private Double nivelMedioEquipe;
    @Column(name = "total_recompensa")
    private Double totalRecompensa;
    @Column(name = "total_mvps")
    private Integer totalMvps;
    @Column(name = "participantes_com_companheiro")
    private Integer participantesComCompanheiro;
    @Column(name = "ultima_atualizacao")
    private LocalDateTime ultimaAtualizacao;
    @Column(name = "indice_prontidao")
    private Double indiceProntidao;

}
