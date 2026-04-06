package com.example.guildapi.controller;

import com.example.guildapi.dto.RankingParticipacaoDto;
import com.example.guildapi.dto.RelatorioMissaoDto;
import com.example.guildapi.model.enums.StatusEnum;
import com.example.guildapi.service.ParticipacaoDeMissaoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
@RequestMapping("/relatorios")
public class ParticipacaoMissaoController {
    private final ParticipacaoDeMissaoService participacaoDeMissaoService;

    @GetMapping("/ranking")
    public ResponseEntity<Page<RankingParticipacaoDto>> gerarRanking(@RequestParam(required = false) LocalDateTime startDate,
                                                                     @RequestParam(required = false) LocalDateTime endDate,
                                                                     @RequestParam(required = false) StatusEnum statusMissao,
                                                                     @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size) {
        Page<RankingParticipacaoDto> pageResult = participacaoDeMissaoService.gerarRanking(startDate, endDate, statusMissao, page, size);
        return responderComPaginacao(pageResult);
    }

    @GetMapping("/missoes")
    public ResponseEntity<Page<RelatorioMissaoDto>> gerarRelatorioMissoes(@RequestParam(required = false) LocalDateTime startDate,
                                                                          @RequestParam(required = false) LocalDateTime endDate,
                                                                          @RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "10") int size) {

        Page<RelatorioMissaoDto> pageResult = participacaoDeMissaoService.gerarRelatorioMissoes(startDate, endDate, page, size);
        return responderComPaginacao(pageResult);
    }

    private <T> ResponseEntity<Page<T>> responderComPaginacao(Page<T> pageResult) {
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(pageResult.getTotalElements()))
                .header("X-Page", String.valueOf(pageResult.getNumber()))
                .header("X-Size", String.valueOf(pageResult.getSize()))
                .header("X-Total-Pages", String.valueOf(pageResult.getTotalPages()))
                .body(pageResult);
    }


}
