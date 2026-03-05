package com.example.guildapi.controller;

import com.example.guildapi.model.Aventureiro;
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
    public ResponseEntity<List<Aventureiro>> listarTodos(){
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

    @PostMapping("/{id}")
    public ResponseEntity<?> atualizarAventureiro(@PathVariable Integer id, @RequestBody Aventureiro novoAventureiro) {
        Optional<Aventureiro> aventureiro = aventureiroService.listarAventureiroPorId(id);
        if (aventureiro == null) {
            return new ResponseEntity<>("ERRO: Aventureiro não encontrado", HttpStatus.NOT_FOUND);
        }
        aventureiroService.atualizarAventureiro(id, novoAventureiro);
        return new ResponseEntity<>(novoAventureiro, HttpStatus.OK);
    }


}
