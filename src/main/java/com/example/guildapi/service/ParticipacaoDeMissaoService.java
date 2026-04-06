package com.example.guildapi.service;

import com.example.guildapi.dto.RankingParticipacaoDto;
import com.example.guildapi.dto.RelatorioMissaoDto;
import com.example.guildapi.model.aventura.ParticipacaoMissao;
import com.example.guildapi.model.enums.StatusEnum;
import com.example.guildapi.repository.IParticipacaoDeMissaoRepository;
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

    public Page<RankingParticipacaoDto> gerarRanking(LocalDateTime startDate, LocalDateTime endDate, StatusEnum statusMissao, int page, int size) {
        List<ParticipacaoMissao> missoesTotal = participacaoDeMissaoRepository.findAll();
        List<ParticipacaoMissao> missoesFiltradas = missoesTotal.stream()
                .filter(p -> startDate == null || !p.getCreated_At().isBefore(startDate))
                .filter(p -> endDate== null || !p.getCreated_At().isAfter(endDate))
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
                .filter(p -> startDate == null || !p.getCreated_At().isBefore(startDate))
                .filter(p -> endDate == null || !p.getCreated_At().isAfter(endDate))
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

    private <T> Page<T> paginar(List<T> lista, int page, int size) {
        int start = page * size;
        int end = Math.min(start + size, lista.size());
        if (start > lista.size()) {
            return new PageImpl<>(new ArrayList<>(), PageRequest.of(page, size), lista.size());
        }
        return new PageImpl<>(lista.subList(start, end), PageRequest.of(page, size), lista.size());
    }

}
