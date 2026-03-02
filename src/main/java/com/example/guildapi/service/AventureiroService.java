package com.example.guildapi.service;

import com.example.guildapi.model.Aventureiro;
import com.example.guildapi.model.Companheiro;
import com.example.guildapi.repository.IAventureiroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AventureiroService {
    private final IAventureiroRepository aventureiroRepository;

    public AventureiroService(IAventureiroRepository aventureiroRepository) {
        this.aventureiroRepository = aventureiroRepository;
    }

    public List<Aventureiro> listarAventureiros(){
        return aventureiroRepository.findAll();
    }

    public Optional<Aventureiro> listarAventureiroPorId(Integer id) {
        return aventureiroRepository.findById(id);
    }

    public void atualizarAventureiro(Integer id, Aventureiro novoAventureiro){
        Optional<Aventureiro> aventureiro = aventureiroRepository.findById(id);
        if (aventureiro.isPresent()){
            if (novoAventureiro.getNome() != null){
                aventureiro.get().setNome(novoAventureiro.getNome());
            }
            if (novoAventureiro.getClasse() != null){
                aventureiro.get().setClasse(novoAventureiro.getClasse());
            }
            if (novoAventureiro.getNivel() != null){
                aventureiro.get().setNivel(novoAventureiro.getNivel());
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

    public void deletarCompanheiro(Integer id, Companheiro companheiro) {
        Optional<Aventureiro> aventureiro = aventureiroRepository.findById(id);
        aventureiro.get().setCompanheiro(null);
        aventureiroRepository.save(aventureiro.get());
    }




}
