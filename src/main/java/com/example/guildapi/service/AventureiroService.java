package com.example.guildapi.service;

import com.example.guildapi.model.aventura.Aventureiro;
import com.example.guildapi.model.aventura.Companheiro;
import com.example.guildapi.model.enums.ClasseEnum;
import com.example.guildapi.repository.IAventureiroRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<Aventureiro> listarAventureirosPaginado(int page, int size) {
        if (size < 1 || size > 50) {
            throw new IllegalArgumentException("size deve estar entre 1 e 50");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return aventureiroRepository.findAll(pageable);
    }

    public Page<Aventureiro> listarAventureirosComFiltro(
            ClasseEnum classe,
            Boolean ativo,
            Integer nivelMinimo,
            int page,
            int size) {
        if (size < 1 || size > 50) {
            throw new IllegalArgumentException("size deve estar entre 1 e 50");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        if (classe != null) {
            return aventureiroRepository.findByClasse(classe, pageable);
        }
        if (ativo != null) {
            return aventureiroRepository.findByAtivo(ativo, pageable);
        }
        if (nivelMinimo != null) {
            return aventureiroRepository.findByNivelGreaterThanEqual(nivelMinimo, pageable);
        }
        return aventureiroRepository.findAll(pageable);
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
