package com.example.guildapi.controller;

import com.example.guildapi.dto.MissaoDetalheDto;
import com.example.guildapi.dto.MissaoListagemDto;
import com.example.guildapi.model.enums.NivelPerigoEnum;
import com.example.guildapi.model.enums.StatusEnum;
import com.example.guildapi.service.MissaoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
@RequestMapping("/missoes")
public class MissaoController {
    private final MissaoService missaoService;

    @GetMapping
    public ResponseEntity<Page<MissaoListagemDto>> listarMissoes(@RequestParam(required = false) StatusEnum status,
                                                                 @RequestParam(required = false) NivelPerigoEnum nivelPerigo,
                                                                 @RequestParam(required = false) LocalDateTime startDate,
                                                                 @RequestParam(required = false) LocalDateTime endDate,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        Page<MissaoListagemDto> pageResult = missaoService.listarMissoes(status, nivelPerigo, startDate, endDate, page, size);
        return responderComPaginacao(pageResult);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MissaoDetalheDto> buscarMissaoPorId(@PathVariable Long id) {
        MissaoDetalheDto dto = missaoService.buscarMissaoPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
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
