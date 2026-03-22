package com.example.guildapi.service;

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

    public Optional<Aventureiro> listarAventureiroPorId(Long id) {
        return aventureiroRepository.findById(id);
    }

    public List<Aventureiro> paginar(List<Aventureiro> base, int page, int size) {
        int total = base.size();
        int from = page * size;
        int to = Math.min(from + size, total);
        return base.subList(from,to);
    }

    public void adicionarAventureiro(Aventureiro aventureiro){
        aventureiroRepository.save(aventureiro);
    }

    public void atualizarAventureiro(Long id, String nome, ClasseEnum classe, Integer nivel){
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

    public void desativarAventureiro(Long id) {
        Optional<Aventureiro> aventureiro = aventureiroRepository.findById(id);
        if (aventureiro.isPresent()){
            aventureiro.get().setAtivo(false);
            aventureiroRepository.save(aventureiro.get());
        }
    }

    public void ativarAventureiro(Long id) {
        Optional<Aventureiro> aventureiro = aventureiroRepository.findById(id);
        if (aventureiro.isPresent()){
            aventureiro.get().setAtivo(true);
            aventureiroRepository.save(aventureiro.get());
        }
    }

    // Gerenciamento do Companheiro
    public void adicionarCompanheiro(Long id, Companheiro companheiro) {
        Optional<Aventureiro> aventureiro = aventureiroRepository.findById(id);
        aventureiro.get().setCompanheiro(companheiro);
        aventureiroRepository.save(aventureiro.get());
    }

    public void deletarCompanheiro(Long id) {
        Optional<Aventureiro> aventureiro = aventureiroRepository.findById(id);
        aventureiro.get().setCompanheiro(null);
        aventureiroRepository.save(aventureiro.get());
    }
}
