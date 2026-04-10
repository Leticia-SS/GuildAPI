package com.example.guildapi.repository;

import com.example.guildapi.model.elastic.ProdutoElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface IProdutoElasticRepository extends ElasticsearchRepository<ProdutoElastic, String> {
}
