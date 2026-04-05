package com.example.guildapi.controller;

import com.example.guildapi.dto.AventureiroCadastroRequestDto;
import com.example.guildapi.dto.AventureiroDetalheDto;
import com.example.guildapi.dto.AventureiroResumoDto;
import com.example.guildapi.model.aventura.Aventureiro;
import com.example.guildapi.model.aventura.Companheiro;
import com.example.guildapi.model.enums.ClasseEnum;
import com.example.guildapi.service.AventureiroService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/aventureiros")
public class AventureiroController {
    private final AventureiroService aventureiroService;

    @GetMapping("/todos")
    public ResponseEntity<Page<AventureiroResumoDto>> listarTodosPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<AventureiroResumoDto> pageResult = aventureiroService.listarAventureirosPaginado(page, size);
        return responderComPaginacao(pageResult);
    }

    @GetMapping
    public ResponseEntity<Page<AventureiroResumoDto>> listarAventureirosComFiltros(
            @RequestParam(required = false) ClasseEnum classe,
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) Integer nivelMinimo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<AventureiroResumoDto> pageResult = aventureiroService.listarAventureirosComFiltro(
                classe, ativo, nivelMinimo, page, size);
        return responderComPaginacao(pageResult);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarAventureiro(@PathVariable Long id) {
        AventureiroDetalheDto dto = aventureiroService.listarAventureiroPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PostMapping()
    public ResponseEntity<?> cadastrarAventureiro(@Valid @RequestBody AventureiroCadastroRequestDto request){
        AventureiroResumoDto dto = aventureiroService.adicionarAventureiro(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AventureiroResumoDto> atualizarAventureiro(@PathVariable Long id, @RequestParam String nome, @RequestParam ClasseEnum classe, @RequestParam Integer nivel) {
        AventureiroResumoDto dto = aventureiroService.atualizarAventureiro(id, nome, classe, nivel);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PatchMapping("/{id}/encerrar")
    public ResponseEntity<?> encerrarAventureiro(@PathVariable Long id) {
        aventureiroService.desativarAventureiro(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/recrutar")
    public ResponseEntity<?> recturarAventureiro(@PathVariable Long id) {
        aventureiroService.ativarAventureiro(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/adicionarCompanheiro")
    public ResponseEntity<?> adicionarCompanheiro(@PathVariable Long id ,@Valid @RequestBody Companheiro companheiro) {
        aventureiroService.adicionarCompanheiro(id, companheiro);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/removerCompanheiro")
    public ResponseEntity<?> removerCompanheiro(@PathVariable Long id){
        aventureiroService.deletarCompanheiro(id);
        return ResponseEntity.ok().build();
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
