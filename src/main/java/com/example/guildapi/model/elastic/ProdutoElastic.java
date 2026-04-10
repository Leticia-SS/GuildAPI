package com.example.guildapi.model.elastic;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "guilda_loja")
public class ProdutoElastic {
    @Id
    private String id;
    @Field(type = FieldType.Text)
    private String nome;
    @Field(type = FieldType.Text)
    private String descricao;
    @Field(type = FieldType.Keyword)
    private String categoria;
    @Field(type = FieldType.Keyword)
    private String raridade;
    @Field(type = FieldType.Double)
    private Double preco;

}
