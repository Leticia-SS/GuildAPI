package com.example.guildapi.service;

import com.example.guildapi.dto.MissaoDetalheDto;
import com.example.guildapi.dto.MissaoListagemDto;
import com.example.guildapi.dto.ParticipanteDto;
import com.example.guildapi.exceptions.EntityNotFoundException;
import com.example.guildapi.model.aventura.Missao;
import com.example.guildapi.model.aventura.ParticipacaoMissao;
import com.example.guildapi.model.enums.NivelPerigoEnum;
import com.example.guildapi.model.enums.StatusEnum;
import com.example.guildapi.repository.IMissaoRepository;
import com.example.guildapi.repository.IParticipacaoDeMissaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MissaoService {
    private final IMissaoRepository missaoRepository;
    private final IParticipacaoDeMissaoRepository participacaoDeMissaoRepository;

    public Page<MissaoListagemDto> listarMissoes(StatusEnum status, NivelPerigoEnum nivelPerigo, LocalDateTime startDate, LocalDateTime endDate, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Missao> pageResult;
        if (status != null && nivelPerigo != null && startDate != null && endDate != null) {
            pageResult = missaoRepository.findByStatusAndNivelPerigoAndCreatedAtBetween(status, nivelPerigo, startDate, endDate, pageable);
        } else if (status != null && nivelPerigo != null) {
            pageResult = missaoRepository.findByStatusAndNivelPerigo(status, nivelPerigo, pageable);
        } else if (status != null && startDate != null && endDate != null) {
            pageResult = missaoRepository.findByStatusAndCreatedAtBetween(status, startDate, endDate, pageable);
        } else if (nivelPerigo != null && startDate != null && endDate != null) {
            pageResult = missaoRepository.findByNivelPerigoAndCreatedAtBetween(nivelPerigo, startDate, endDate, pageable);
        } else if (status != null) {
            pageResult = missaoRepository.findByStatus(status, pageable);
        } else if (nivelPerigo != null) {
            pageResult = missaoRepository.findByNivelPerigo(nivelPerigo, pageable);
        } else if (startDate != null && endDate != null) {
            pageResult = missaoRepository.findByCreatedAtBetween(startDate, endDate, pageable);
        } else {
            pageResult = missaoRepository.findAll(pageable);
        }
        return pageResult.map(this::toListagemDto);
    }

    public MissaoDetalheDto buscarMissaoPorId(Long id) {
        Missao missao = missaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Missão não encontrada"));
        List<ParticipacaoMissao> participacoes = participacaoDeMissaoRepository.findByMissaoId(id);
        List<ParticipanteDto> participantes = new ArrayList<>();
        participacoes.forEach(p -> {
            ParticipanteDto dto = new ParticipanteDto();
            dto.setAventureiroId(p.getAventureiro().getId());
            dto.setNomeAventureiro(p.getAventureiro().getNome());
            dto.setPapel(p.getPapel().name());
            dto.setRecompensa(p.getRecompensa());
            dto.setDestaque(p.isMvp());
            participantes.add(dto);
        });
        MissaoDetalheDto dto = new MissaoDetalheDto();
        dto.setId(missao.getId());
        dto.setTitulo(missao.getTitulo());
        dto.setStatus(missao.getStatus());
        dto.setNivelPerigo(missao.getNivelPerigo());
        dto.setCreatedAt(missao.getCreated_at());
        dto.setStartTime(missao.getStart_time());
        dto.setEndTime(missao.getEnd_time());
        if (missao.getOrganizacao() != null) {
            dto.setNomeOrganizacao(missao.getOrganizacao().getNome());
        }
        dto.setParticipantes(participantes);
        return dto;
    }

    private MissaoListagemDto toListagemDto(Missao m) {
        MissaoListagemDto dto = new MissaoListagemDto();
        dto.setId(m.getId());
        dto.setTitulo(m.getTitulo());
        dto.setStatus(m.getStatus());
        dto.setNivelPerigo(m.getNivelPerigo());
        dto.setCreatedAt(m.getCreated_at());
        dto.setStartTime(m.getStart_time());
        dto.setEndTime(m.getEnd_time());
        return dto;
    }

}
