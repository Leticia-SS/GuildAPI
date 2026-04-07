package com.example.guildapi.service;

import com.example.guildapi.dto.AventureiroCadastroRequestDto;
import com.example.guildapi.dto.AventureiroDetalheDto;
import com.example.guildapi.dto.AventureiroResumoDto;
import com.example.guildapi.dto.MissaoResumoDto;
import com.example.guildapi.exceptions.EntityNotFoundException;
import com.example.guildapi.model.audit.Organizacao;
import com.example.guildapi.model.aventura.Aventureiro;
import com.example.guildapi.model.aventura.Companheiro;
import com.example.guildapi.model.enums.ClasseEnum;
import com.example.guildapi.repository.IAventureiroRepository;
import com.example.guildapi.repository.IParticipacaoDeMissaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AventureiroService {
    private final IAventureiroRepository aventureiroRepository;
    private final IParticipacaoDeMissaoRepository participacaoDeMissaoRepository;
    private final OrganizacaoService organizacaoService;


    public AventureiroDetalheDto listarAventureiroPorId(Long id) {
        Aventureiro aventureiro = aventureiroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado"));
        return converterParaDetalheDto(aventureiro);
    }

    public Page<AventureiroResumoDto> listarAventureirosPaginado(int page, int size) {
        if (size < 1 || size > 50) {
            throw new IllegalArgumentException("size deve estar entre 1 e 50");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Aventureiro> pageResult = aventureiroRepository.findAll(pageable);
        return pageResult.map(this::converterParaResumoDto);
    }

    public Page<AventureiroResumoDto> listarAventureirosComFiltro(ClasseEnum classe, Boolean ativo, Integer nivelMinimo, int page, int size) {
        if (size < 1 || size > 50) {
            throw new IllegalArgumentException("size deve estar entre 1 e 50");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Aventureiro> pageResult;
        if (classe != null) {
            pageResult = aventureiroRepository.findByClasse(classe, pageable);
        } else if (ativo != null) {
            pageResult = aventureiroRepository.findByAtivo(ativo, pageable);
        } else if (nivelMinimo != null) {
            pageResult = aventureiroRepository.findByNivelGreaterThanEqual(nivelMinimo, pageable);
        } else {
            pageResult = aventureiroRepository.findAll(pageable);
        }
        return pageResult.map(this::converterParaResumoDto);
    }

    public AventureiroResumoDto adicionarAventureiro(AventureiroCadastroRequestDto request) {
        Organizacao organizacao = organizacaoService.buscarPorId(request.getOrganizacaoId());
        Aventureiro aventureiro = new Aventureiro();
        aventureiro.setNome(request.getNome());
        aventureiro.setClasse(request.getClasse());
        aventureiro.setNivel(request.getNivel());
        aventureiro.setAtivo(true);
        aventureiro.setCompanheiro(null);
        aventureiro.setOrganizacao(organizacao);
        aventureiro.setCreatedAt(LocalDateTime.now());
        aventureiro.setUpdatedAt(LocalDateTime.now());
        Aventureiro saved = aventureiroRepository.save(aventureiro);
        return converterParaResumoDto(saved);
    }

    public AventureiroResumoDto atualizarAventureiro(Long id, String nome, ClasseEnum classe, Integer nivel) {
        Aventureiro aventureiro = aventureiroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado com ID: " + id));
        if (nome != null && !nome.isBlank()) {
            aventureiro.setNome(nome);
        }
        if (classe != null) {
            aventureiro.setClasse(classe);
        }
        if (nivel != null && nivel >= 1) {
            aventureiro.setNivel(nivel);
        }
        aventureiro.setUpdatedAt(LocalDateTime.now());
        Aventureiro updated = aventureiroRepository.save(aventureiro);
        return converterParaResumoDto(updated);
    }

    public void desativarAventureiro(Long id) {
        Aventureiro aventureiro = aventureiroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado com ID: " + id));
        aventureiro.setAtivo(false);
        aventureiroRepository.save(aventureiro);
    }

    public void ativarAventureiro(Long id) {
        Aventureiro aventureiro = aventureiroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado com ID: " + id));
        aventureiro.setAtivo(true);
        aventureiroRepository.save(aventureiro);
    }

    public void adicionarCompanheiro(Long id, Companheiro request) {
        Aventureiro aventureiro = aventureiroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado com ID: " + id));
        Companheiro companheiro = new Companheiro();
        companheiro.setNome(request.getNome());
        companheiro.setEspecie(request.getEspecie());
        companheiro.setLealdade(request.getLealdade());
        aventureiro.setCompanheiro(companheiro);
        aventureiroRepository.save(aventureiro);
    }

    public void deletarCompanheiro(Long id) {
        Aventureiro aventureiro = aventureiroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado com ID: " + id));
        aventureiro.setCompanheiro(null);
        aventureiroRepository.save(aventureiro);
    }
    private AventureiroResumoDto converterParaResumoDto(Aventureiro aventureiro) {
        return new AventureiroResumoDto(
                aventureiro.getId(),
                aventureiro.getNome(),
                aventureiro.getClasse(),
                aventureiro.getNivel(),
                aventureiro.isAtivo()
        );
    }

    private AventureiroDetalheDto converterParaDetalheDto(Aventureiro aventureiro) {
        AventureiroDetalheDto dto = new AventureiroDetalheDto();
        dto.setId(aventureiro.getId());
        dto.setNome(aventureiro.getNome());
        dto.setClasse(aventureiro.getClasse());
        dto.setNivel(aventureiro.getNivel());
        dto.setAtivo(aventureiro.isAtivo());
        dto.setCompanheiro(aventureiro.getCompanheiro());
        if (aventureiro.getOrganizacao() != null) {
            dto.setNomeOrganizacao(aventureiro.getOrganizacao().getNome());
        }
        Long aventureiroId = aventureiro.getId();
        dto.setTotalParticipacoes(participacaoDeMissaoRepository.countByAventureiroId(aventureiroId));
        participacaoDeMissaoRepository.findTopByAventureiroIdOrderByCreatedAtDesc(aventureiroId)
                .ifPresent(participacao -> {
                    MissaoResumoDto missaoDto = new MissaoResumoDto();
                    missaoDto.setTitulo(participacao.getMissao().getTitulo());
                    missaoDto.setStatus(participacao.getMissao().getStatus().name());
                    missaoDto.setPapelNaMissao(participacao.getPapel().name());
                    missaoDto.setRecompensaOuro(participacao.getRecompensa());
                    missaoDto.setDestaque(participacao.isMvp());
                    dto.setUltimaMissao(missaoDto);
                });
        return dto;
    }
}
