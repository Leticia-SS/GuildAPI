package com.example.guildapi.service;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.AggregationRange;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.json.JsonData;
import com.example.guildapi.model.elastic.ProdutoElastic;
import com.example.guildapi.repository.IProdutoElasticRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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

    public List<ProdutoElastic> buscarPorDescricaoECategoria(String termo, String categoria) {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.bool(b -> b
                        .must(m -> m.match(match -> match.field("descricao").query(termo)))
                        .filter(f -> f.term(t -> t.field("categoria").value(categoria)))
                )).build();
        SearchHits<ProdutoElastic> hits = elasticsearchTemplate.search(query, ProdutoElastic.class);
        return hits.stream().map(hit -> hit.getContent()).collect(Collectors.toList());
    }

    public List<ProdutoElastic> buscarPorFaixaPreco(Double min, Double max) {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.range(r -> r.number(n -> n.field("preco").gte(min).lte(max))))
                .build();
        SearchHits<ProdutoElastic> hits = elasticsearchTemplate.search(query, ProdutoElastic.class);
        return hits.stream()
                .map(SearchHit::getContent).collect(Collectors.toList());
    }

    public List<ProdutoElastic> buscarAvancada(String categoria, String raridade, Double min, Double max) {
        NativeQueryBuilder builder = NativeQuery.builder();
        BoolQuery.Builder boolBuilder = new BoolQuery.Builder();
        if (categoria != null) {
            boolBuilder.filter(f ->
                    f.term(t -> t.field("categoria").value(categoria)));
        }
        if (raridade != null) {
            boolBuilder.filter(f ->
                    f.term(t -> t.field("raridade").value(raridade)));
        }
        if (min != null && max != null) {
            boolBuilder.filter(f -> f.range(r ->
                    r.number(n -> n.field("preco").gte(min).lte(max))));
        }
        builder.withQuery(q -> q.bool(boolBuilder.build()));
        NativeQuery query = builder.build();
        SearchHits<ProdutoElastic> hits = elasticsearchTemplate.search(query, ProdutoElastic.class);
        return hits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    public Map<String, Long> contarPorCategoria() {
        NativeQuery query = NativeQuery.builder()
                .withAggregation("por_categoria", Aggregation.of(a -> a.terms(t -> t.field("categoria").size(100))))
                .build();
        SearchHits<ProdutoElastic> hits = elasticsearchTemplate.search(query, ProdutoElastic.class);
        ElasticsearchAggregations aggregations = (ElasticsearchAggregations) hits.getAggregations();
        Aggregate aggregate = aggregations.aggregationsAsMap()
                .get("por_categoria")
                .aggregation()
                .getAggregate();
        return aggregate.sterms().buckets().array().stream().collect(Collectors.toMap(
                bucket -> bucket.key().stringValue(),
                bucket -> bucket.docCount()
                ));
    }

    public Map<String, Long> contarPorRaridade() {
        NativeQuery query = NativeQuery.builder()
                .withAggregation("por_raridade", Aggregation.of(a -> a.terms(t -> t.field("raridade").size(100))))
                .build();
        SearchHits<ProdutoElastic> hits = elasticsearchTemplate.search(query, ProdutoElastic.class);
        ElasticsearchAggregations aggregations = (ElasticsearchAggregations) hits.getAggregations();
        Aggregate aggregate = aggregations.aggregationsAsMap()
                .get("por_raridade")
                .aggregation()
                .getAggregate();
        return aggregate.sterms().buckets().array().stream().collect(Collectors.toMap(
                bucket -> bucket.key().stringValue(),
                bucket -> bucket.docCount()
                ));
    }

    public Double calcularPrecoMedio() {
        NativeQuery query = NativeQuery.builder()
                .withAggregation("preco_medio", Aggregation.of(a -> a.avg(avg -> avg.field("preco"))))
                .build();
        SearchHits<ProdutoElastic> hits = elasticsearchTemplate.search(query, ProdutoElastic.class);
        ElasticsearchAggregations aggregations = (ElasticsearchAggregations) hits.getAggregations();
        Aggregate aggregate = aggregations.aggregationsAsMap()
                .get("preco_medio")
                .aggregation()
                .getAggregate();
        return aggregate.avg().value();
    }

    public Map<String, Long> contarFaixasPreco() {
        NativeQuery query = NativeQuery.builder()
                .withAggregation("faixas_preco", Aggregation.of(a -> a
                        .range(r -> r
                                .field("preco")
                                .ranges(
                                        AggregationRange.of(rr -> rr.to(100.0)),
                                        AggregationRange.of(rr -> rr.from(100.0).to(300.0)),
                                        AggregationRange.of(rr -> rr.from(300.0).to(700.0)),
                                        AggregationRange.of(rr -> rr.from(700.0))))))
                .build();
        SearchHits<ProdutoElastic> hits = elasticsearchTemplate.search(query, ProdutoElastic.class);
        ElasticsearchAggregations aggregations = (ElasticsearchAggregations) hits.getAggregations();
        Aggregate aggregate = aggregations.aggregationsAsMap()
                .get("faixas_preco")
                .aggregation()
                .getAggregate();
        return aggregate.range().buckets().array().stream().collect(Collectors.toMap(
                bucket -> bucket.key(),
                bucket -> bucket.docCount()
                ));
    }


}
