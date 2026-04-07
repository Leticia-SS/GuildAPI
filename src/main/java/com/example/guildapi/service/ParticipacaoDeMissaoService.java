package com.example.guildapi.service;

import com.example.guildapi.dto.ParticipacaoRequestDto;
import com.example.guildapi.dto.ParticipacaoResponseDto;
import com.example.guildapi.dto.RankingParticipacaoDto;
import com.example.guildapi.dto.RelatorioMissaoDto;
import com.example.guildapi.exceptions.EntityNotFoundException;
import com.example.guildapi.exceptions.ValidacaoException;
import com.example.guildapi.model.aventura.Aventureiro;
import com.example.guildapi.model.aventura.Missao;
import com.example.guildapi.model.aventura.ParticipacaoMissao;
import com.example.guildapi.model.enums.StatusEnum;
import com.example.guildapi.repository.IAventureiroRepository;
import com.example.guildapi.repository.IMissaoRepository;
import com.example.guildapi.repository.IParticipacaoDeMissaoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@AllArgsConstructor
public class ParticipacaoDeMissaoService {
    private final IParticipacaoDeMissaoRepository participacaoDeMissaoRepository;
    private final IMissaoRepository missaoRepository;
    private final IAventureiroRepository aventureiroRepository;
    private final MissaoService missaoService;

    @Transactional
    public ParticipacaoResponseDto adicionarAventureiroNaMissao(ParticipacaoRequestDto request) {
        Missao missao = missaoRepository.findById(request.getMissaoId())
                .orElseThrow(() -> new EntityNotFoundException("Missão não encontrada com ID: " + request.getMissaoId()));
        Aventureiro aventureiro = aventureiroRepository.findById(request.getAventureiroId())
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado com ID: " + request.getAventureiroId()));
        if (!aventureiro.isAtivo()) {
            throw new ValidacaoException("Aventureiro inativo não pode ser associado a missões");
        }
        if (!missaoService.isMissaoAceitaParticipantes(missao)) {
            throw new ValidacaoException("Missão não aceita novos participantes. Status atual: " + missao.getStatus());
        }
        if (missao.getOrganizacao() == null || aventureiro.getOrganizacao() == null ||
                !missao.getOrganizacao().getId().equals(aventureiro.getOrganizacao().getId())) {
            throw new ValidacaoException("Aventureiro deve pertencer à mesma organização da missão");
        }
        if (participacaoDeMissaoRepository.existsByMissaoIdAndAventureiroId(request.getMissaoId(), request.getAventureiroId())) {
            throw new ValidacaoException("Aventureiro já está participando desta missão");
        }
        ParticipacaoMissao participacao = new ParticipacaoMissao();
        participacao.setMissao(missao);
        participacao.setAventureiro(aventureiro);
        participacao.setPapel(request.getPapel());
        participacao.setRecompensa(request.getRecompensa());
        participacao.setMvp(request.isMvp());
        participacao.setCreatedAt(LocalDateTime.now());
        ParticipacaoMissao saved = participacaoDeMissaoRepository.save(participacao);

        ParticipacaoResponseDto response = new ParticipacaoResponseDto();
        response.setId(saved.getId());
        response.setMissaoId(saved.getMissao().getId());
        response.setMissaoTitulo(saved.getMissao().getTitulo());
        response.setAventureiroId(saved.getAventureiro().getId());
        response.setAventureiroNome(saved.getAventureiro().getNome());
        response.setPapel(saved.getPapel());
        response.setRecompensa(saved.getRecompensa());
        response.setMvp(saved.isMvp());
        return response;
    }
    public Page<RankingParticipacaoDto> gerarRanking(LocalDateTime startDate, LocalDateTime endDate, StatusEnum statusMissao, int page, int size) {
        List<ParticipacaoMissao> missoesTotal = participacaoDeMissaoRepository.findAll();
        List<ParticipacaoMissao> missoesFiltradas = missoesTotal.stream()
                .filter(p -> startDate == null || !p.getCreatedAt().isBefore(startDate))
                .filter(p -> endDate== null || !p.getCreatedAt().isAfter(endDate))
                .filter(p -> statusMissao== null || p.getMissao().getStatus() == statusMissao)
                .toList();
        Map<Long, RankingParticipacaoDto> map = new HashMap<>();
        missoesFiltradas.forEach(p -> {
            Long id = p.getAventureiro().getId();
            RankingParticipacaoDto dto = map.get(id);
            if (dto == null) {
                dto = new RankingParticipacaoDto();
                dto.setAventureiroId(id);
                dto.setNome(p.getAventureiro().getNome());
                dto.setTotalParticipacoes(0L);
                dto.setTotalRecompensas(0L);
                dto.setTotalDestaques(0L);
                map.put(id, dto);
            }
            dto.setTotalParticipacoes(dto.getTotalParticipacoes() + 1);
            dto.setTotalRecompensas(dto.getTotalRecompensas() + p.getRecompensa());
            if (p.isMvp()) {
                dto.setTotalDestaques(dto.getTotalDestaques() + 1);
            }
        });
        List<RankingParticipacaoDto> lista = new ArrayList<>(map.values());
        lista.sort((a, b) -> b.getTotalRecompensas().compareTo(a.getTotalRecompensas()));
        return paginar(lista, page, size);
    }

    public Page<RelatorioMissaoDto> gerarRelatorioMissoes(LocalDateTime startDate, LocalDateTime endDate, int page, int size) {
        List<ParticipacaoMissao> missoesTotal = participacaoDeMissaoRepository.findAll();
        List<ParticipacaoMissao> missoesFiltradas = missoesTotal.stream()
                .filter(p -> startDate == null || !p.getCreatedAt().isBefore(startDate))
                .filter(p -> endDate == null || !p.getCreatedAt().isAfter(endDate))
                .toList();
        Map<Long, RelatorioMissaoDto> map = new HashMap<>();
        missoesFiltradas.forEach(p -> {
            Long id = p.getMissao().getId();
            RelatorioMissaoDto dto = map.get(id);

            if (dto == null) {
                dto = new RelatorioMissaoDto();
                dto.setMissaoId(id);
                dto.setTitulo(p.getMissao().getTitulo());
                dto.setStatus(p.getMissao().getStatus());
                dto.setNivelPerigo(p.getMissao().getNivelPerigo());
                dto.setTotalParticipantes(0L);
                dto.setTotalRecompensas(0L);
                map.put(id, dto);
            }
            dto.setTotalParticipantes(dto.getTotalParticipantes() + 1);
            dto.setTotalRecompensas(dto.getTotalRecompensas() + p.getRecompensa());
        });
        List<RelatorioMissaoDto> lista = new ArrayList<>(map.values());
        return paginar(lista, page, size);
    }

    @Transactional
    public void removerAventureiroDaMissao(Long missaoId, Long aventureiroId) {
        ParticipacaoMissao participacao = participacaoDeMissaoRepository
                .findByMissaoIdAndAventureiroId(missaoId, aventureiroId)
                .orElseThrow(() -> new EntityNotFoundException("Participação não encontrada"));
        participacaoDeMissaoRepository.delete(participacao);
    }

    private <T> Page<T> paginar(List<T> lista, int page, int size) {
        int start = page * size;
        int end = Math.min(start + size, lista.size());
        if (start > lista.size()) {
            return new PageImpl<>(new ArrayList<>(), PageRequest.of(page, size), lista.size());
        }
        return new PageImpl<>(lista.subList(start, end), PageRequest.of(page, size), lista.size());
    }

}
