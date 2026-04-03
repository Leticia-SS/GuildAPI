package com.example.guildapi.controller;

import com.example.guildapi.model.aventura.Aventureiro;
import com.example.guildapi.model.aventura.Companheiro;
import com.example.guildapi.model.enums.ClasseEnum;
import com.example.guildapi.service.AventureiroService;
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
    public ResponseEntity<Page<Aventureiro>> listarTodosPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Aventureiro> pageResult = aventureiroService.listarAventureirosPaginado(page, size);

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(pageResult.getTotalElements()))
                .header("X-Page", String.valueOf(pageResult.getNumber()))
                .header("X-Size", String.valueOf(pageResult.getSize()))
                .header("X-Total-Pages", String.valueOf(pageResult.getTotalPages()))
                .body(pageResult);
    }

    @GetMapping
    public ResponseEntity<Page<Aventureiro>> listarAventureirosComFiltros(
            @RequestParam(required = false) ClasseEnum classe,
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) Integer nivelMinimo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Aventureiro> pageResult = aventureiroService.listarAventureirosComFiltro(
                classe, ativo, nivelMinimo, page, size);

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(pageResult.getTotalElements()))
                .header("X-Page", String.valueOf(pageResult.getNumber()))
                .header("X-Size", String.valueOf(pageResult.getSize()))
                .header("X-Total-Pages", String.valueOf(pageResult.getTotalPages()))
                .body(pageResult);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarAventureiro(@PathVariable Long id) {
        Optional<Aventureiro> aventureiro = aventureiroService.listarAventureiroPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(aventureiro.get());
    }

    @PostMapping()
    public ResponseEntity<?> cadastrarAventureiro(@RequestBody Aventureiro aventureiro){
        aventureiroService.adicionarAventureiro(aventureiro);
        return ResponseEntity.ok("Aventureiro cadastrado");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarAventureiro(@PathVariable Long id, @RequestBody Aventureiro aventureiroDados) {
        Optional<Aventureiro> aventureiro = aventureiroService.listarAventureiroPorId(id);
        if (aventureiro.isEmpty()) {
            return new ResponseEntity<>("ERRO: Aventureiro não encontrado", HttpStatus.NOT_FOUND);
        }
        aventureiroService.atualizarAventureiro(id, aventureiroDados.getNome(), aventureiroDados.getClasse(), aventureiroDados.getNivel());
        return new ResponseEntity<>(aventureiroService.listarAventureiroPorId(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}/encerrar")
    public ResponseEntity<?> encerrarAventureiro(@PathVariable Long id) {
        Optional<Aventureiro> aventureiro = aventureiroService.listarAventureiroPorId(id);
        if (aventureiro.isEmpty()) {
            return new ResponseEntity<>("ERRO: Aventureiro não encontrado", HttpStatus.NOT_FOUND);
        }
        aventureiroService.desativarAventureiro(id);
        return new ResponseEntity<>("Vinculo Encerrado com a Guilda ", HttpStatus.OK);
    }

    @PatchMapping("/{id}/recrutar")
    public ResponseEntity<?> recturarAventureiro(@PathVariable Long id) {
        Optional<Aventureiro> aventureiro = aventureiroService.listarAventureiroPorId(id);
        if (aventureiro.isEmpty()) {
            return new ResponseEntity<>("ERRO: Aventureiro não encontrado", HttpStatus.NOT_FOUND);
        }
        aventureiroService.ativarAventureiro(id);
        return new ResponseEntity<>("Vinculo iniciado com a Guilda ", HttpStatus.OK);
    }

    // Requests Companheiro
    @PatchMapping("/{id}/adicionarCompanheiro")
    public ResponseEntity<?> adicionarCompanheiro(@PathVariable Long id ,@RequestBody Companheiro companheiro) {
        Optional<Aventureiro> aventureiro = aventureiroService.listarAventureiroPorId(id);
        if (aventureiro.isEmpty()) {
            return new ResponseEntity<>("ERRO: Aventureiro não encontrado", HttpStatus.FORBIDDEN);
        }
        if (aventureiro.get().getCompanheiro() != null) {
            return new ResponseEntity<>("ERRO: Aventureiro já possui um companheiro", HttpStatus.FORBIDDEN);
        }
        aventureiroService.adicionarCompanheiro(id, companheiro);
        return new ResponseEntity<>("Companheiro criado com sucesso", HttpStatus.OK);
    }

    @PatchMapping("/{id}/removerCompanheiro")
    public ResponseEntity<?> removerCompanheiro(@PathVariable Long id){
        Optional<Aventureiro> aventureiro = aventureiroService.listarAventureiroPorId(id);
        if (aventureiro.isEmpty()) {
            return new ResponseEntity<>("ERRO: Aventureiro não encontrado", HttpStatus.FORBIDDEN);
        }
        aventureiroService.deletarCompanheiro(id);
        return new ResponseEntity<>("Companheiro removido", HttpStatus.OK);
    }

}
