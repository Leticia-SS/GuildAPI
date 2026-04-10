package com.example.guildapi.service;

import com.example.guildapi.model.operacoes.PainelTaticoMissao;
import com.example.guildapi.repository.PainelTaticoMissaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PainelTaticoMissaoService {
    private final PainelTaticoMissaoRepository painelTaticoMissaoRepository;

    public List<PainelTaticoMissao> listarTopMissoesUltimos15Dias() {
        LocalDateTime dataLimite = LocalDateTime.now().minusDays(15);
        return painelTaticoMissaoRepository.findTop10ByUltimaAtualizacaoAfterOrderByIndiceProntidaoDesc(dataLimite);
    }
}
