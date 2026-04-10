package com.example.guildapi.service;

import com.example.guildapi.model.elastic.ProdutoElastic;
import com.example.guildapi.repository.IProdutoElasticRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProdutoElasticService {
    private final IProdutoElasticRepository produtoElasticRepository;
    private final ElasticsearchTemplate elasticsearchTemplate;

    public List<ProdutoElastic> buscarPorNome(String termo) {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.match(m -> m.field("nome").query(termo)))
                .build();
        SearchHits<ProdutoElastic> hits = elasticsearchTemplate.search(query, ProdutoElastic.class);
        return hits.stream().map(hit -> hit.getContent()).collect(Collectors.toList());
    }

    public List<ProdutoElastic> buscarPorDescricao(String termo) {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.match(m -> m.field("descricao").query(termo)))
                .build();
        SearchHits<ProdutoElastic> hits = elasticsearchTemplate.search(query, ProdutoElastic.class);
        return hits.stream().map(hit -> hit.getContent()).collect(Collectors.toList());
    }

    public List<ProdutoElastic> buscarPorFraseExata(String frase) {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.matchPhrase(m -> m.field("descricao").query(frase)))
                .build();
        SearchHits<ProdutoElastic> hits = elasticsearchTemplate.search(query, ProdutoElastic.class);
        return hits.stream().map(hit -> hit.getContent()).collect(Collectors.toList());
    }

    public List<ProdutoElastic> buscarFuzzy(String termo) {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.fuzzy(f -> f.field("nome").value(termo).fuzziness("AUTO")))
                .build();
        SearchHits<ProdutoElastic> hits = elasticsearchTemplate.search(query, ProdutoElastic.class);
        return hits.stream().map(hit -> hit.getContent()).collect(Collectors.toList());
    }

    public List<ProdutoElastic> buscarMultiCampos(String termo) {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.multiMatch(m -> m.fields(List.of("nome", "descricao")).query(termo)))
                .build();
        SearchHits<ProdutoElastic> hits = elasticsearchTemplate.search(query, ProdutoElastic.class);
        return hits.stream().map(hit -> hit.getContent()).collect(Collectors.toList());
    }


}
