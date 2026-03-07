package com.example.guildapi.controller;

import com.example.guildapi.model.Aventureiro;
import com.example.guildapi.model.Companheiro;
import com.example.guildapi.service.AventureiroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/aventureiros")
public class AventureiroController {
    private final AventureiroService aventureiroService;

    public AventureiroController(AventureiroService aventureiroService) {
        this.aventureiroService = aventureiroService;
    }

    @GetMapping
    public ResponseEntity<List<Aventureiro>> listarTodos(@RequestHeader(value = "X-Page", defaultValue = "0") int page, @RequestHeader(value = "X-Size" , defaultValue = "10") int size){
        List<Aventureiro> aventureiros = aventureiroService.listarAventureiros();
        return ResponseEntity.ok(aventureiros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarAventureiro(@PathVariable Integer id) {
        Optional<Aventureiro> aventureiro = aventureiroService.listarAventureiroPorId(id);
        if (aventureiro.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: aventureiro não encontrado");
        }
        return ResponseEntity.ok(aventureiro.get());
    }

    @PostMapping()
    public String cadastrarAventureiro(@RequestBody Aventureiro aventureiro){
        aventureiroService.adicionarAventureiro(aventureiro);
        return "Aventureiro cadastrado";
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarAventureiro(@PathVariable Integer id, @RequestBody Aventureiro aventureiroDados) {
        Optional<Aventureiro> aventureiro = aventureiroService.listarAventureiroPorId(id);
        if (aventureiro.isEmpty()) {
            return new ResponseEntity<>("ERRO: Aventureiro não encontrado", HttpStatus.NOT_FOUND);
        }
        aventureiroService.atualizarAventureiro(id, aventureiroDados.getNome(), aventureiroDados.getClasse(), aventureiroDados.getNivel());
        return new ResponseEntity<>(aventureiroService.listarAventureiroPorId(id).get(), HttpStatus.OK);
    }

    @PatchMapping("/{id}/encerrar")
    public ResponseEntity<?> encerrarAventureiro(@PathVariable Integer id) {
        Optional<Aventureiro> aventureiro = aventureiroService.listarAventureiroPorId(id);
        if (aventureiro.isEmpty()) {
            return new ResponseEntity<>("ERRO: Aventureiro não encontrado", HttpStatus.NOT_FOUND);
        }
        aventureiroService.desativarAventureiro(id);
        return new ResponseEntity<>("Vinculo Encerrado com a Guilda ", HttpStatus.OK);
    }

    @PatchMapping("/{id}/recrutar")
    public ResponseEntity<?> recturarAventureiro(@PathVariable Integer id) {
        Optional<Aventureiro> aventureiro = aventureiroService.listarAventureiroPorId(id);
        if (aventureiro.isEmpty()) {
            return new ResponseEntity<>("ERRO: Aventureiro não encontrado", HttpStatus.NOT_FOUND);
        }
        aventureiroService.ativarAventureiro(id);
        return new ResponseEntity<>("Vinculo iniciado com a Guilda ", HttpStatus.OK);
    }

    // Requests Companheiro
    @PatchMapping("/{id}/companheiro")
    public ResponseEntity<?> adicionarCompanheiro(@PathVariable Integer id ,@RequestBody Companheiro companheiro) {
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
    public ResponseEntity<?> removerCompanheiro(@PathVariable Integer id){
        Optional<Aventureiro> aventureiro = aventureiroService.listarAventureiroPorId(id);
        if (aventureiro.isEmpty()) {
            return new ResponseEntity<>("ERRO: Aventureiro não encontrado", HttpStatus.FORBIDDEN);
        }
        aventureiroService.deletarCompanheiro(id);
        return new ResponseEntity<>("Companheiro removido", HttpStatus.OK);
    }

}
