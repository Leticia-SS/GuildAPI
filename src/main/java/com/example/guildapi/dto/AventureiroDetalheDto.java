package com.example.guildapi.dto;

import com.example.guildapi.model.aventura.Companheiro;
import com.example.guildapi.model.enums.ClasseEnum;
import lombok.Data;

@Data
public class AventureiroDetalheDto {
    private Long id;
    private String nome;
    private ClasseEnum classe;
    private Integer nivel;
    private boolean ativo;
    private Companheiro companheiro;
    private String nomeOrganizacao;
    private Long totalParticipacoes;
    private MissaoResumoDto ultimaMissao;
}
