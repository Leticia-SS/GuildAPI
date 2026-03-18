package com.example.guildapi.service;

import com.example.guildapi.exceptions.EntityNotFoundException;
import com.example.guildapi.model.Aventureiro;
import com.example.guildapi.model.Companheiro;
import com.example.guildapi.model.Enum.ClasseEnum;
import com.example.guildapi.repository.IAventureiroRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AventureiroService {
    private final IAventureiroRepository aventureiroRepository;

    public List<Aventureiro> listarAventureiros(){
        return aventureiroRepository.findAll();
    }

    public Aventureiro listarAventureiroPorId(Integer id) {
        return aventureiroRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Aventureiro não encontrado"));
    }

    public void adicionarAventureiro(Aventureiro aventureiro){
        aventureiroRepository.save(aventureiro);
    }

    public void atualizarAventureiro(Integer id, String nome, ClasseEnum classe, Integer nivel){
        Optional<Aventureiro> aventureiro = aventureiroRepository.findById(id);
        if (aventureiro.isPresent()) {
            if (nome != null) {
                aventureiro.get().setNome(nome);
            }

            if (classe != null) {
                aventureiro.get().setClasse(classe);
            }

            if (nivel != null) {
                aventureiro.get().setNivel(nivel);
            }
            aventureiroRepository.save(aventureiro.get());
        }
    }

    public void desativarAventureiro(Integer id) {
        Optional<Aventureiro> aventureiro = aventureiroRepository.findById(id);
        if (aventureiro.isPresent()){
            aventureiro.get().setAtivo(false);
            aventureiroRepository.save(aventureiro.get());
        }
    }

    public void ativarAventureiro(Integer id) {
        Optional<Aventureiro> aventureiro = aventureiroRepository.findById(id);
        if (aventureiro.isPresent()){
            aventureiro.get().setAtivo(true);
            aventureiroRepository.save(aventureiro.get());
        }
    }

    // Gerenciamento do Companheiro
    public void adicionarCompanheiro(Integer id, Companheiro companheiro) {
        Optional<Aventureiro> aventureiro = aventureiroRepository.findById(id);
        aventureiro.get().setCompanheiro(companheiro);
        aventureiroRepository.save(aventureiro.get());
    }

    public void deletarCompanheiro(Integer id) {
        Optional<Aventureiro> aventureiro = aventureiroRepository.findById(id);
        aventureiro.get().setCompanheiro(null);
        aventureiroRepository.save(aventureiro.get());
    }
}
