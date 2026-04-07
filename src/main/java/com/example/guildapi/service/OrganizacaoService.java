package com.example.guildapi.service;

import com.example.guildapi.exceptions.EntityNotFoundException;
import com.example.guildapi.model.audit.Organizacao;
import com.example.guildapi.repository.IOrganizacaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrganizacaoService {
    private final IOrganizacaoRepository organizacaoRepository;

    public Organizacao buscarPorId(Long id) {
        return organizacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Organização não encontrada com ID: " + id));
    }
}
