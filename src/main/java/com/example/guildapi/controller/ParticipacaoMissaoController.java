package com.example.guildapi.controller;

import com.example.guildapi.dto.ParticipacaoRequestDto;
import com.example.guildapi.dto.ParticipacaoResponseDto;
import com.example.guildapi.dto.RankingParticipacaoDto;
import com.example.guildapi.dto.RelatorioMissaoDto;
import com.example.guildapi.model.enums.StatusEnum;
import com.example.guildapi.service.ParticipacaoDeMissaoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
@RequestMapping("/participacoes")
public class ParticipacaoMissaoController {
    private final ParticipacaoDeMissaoService participacaoDeMissaoService;

    @PostMapping
    public ResponseEntity<ParticipacaoResponseDto> adicionarParticipacao(@Valid @RequestBody ParticipacaoRequestDto request) {
        ParticipacaoResponseDto response = participacaoDeMissaoService.adicionarAventureiroNaMissao(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> removerParticipacao(@RequestParam Long missaoId, @RequestParam Long aventureiroId) {
        participacaoDeMissaoService.removerAventureiroDaMissao(missaoId, aventureiroId);
        return ResponseEntity.noContent().build();
    }

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
