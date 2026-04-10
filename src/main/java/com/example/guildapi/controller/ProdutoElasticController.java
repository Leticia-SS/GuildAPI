package com.example.guildapi.controller;

import com.example.guildapi.model.elastic.ProdutoElastic;
import com.example.guildapi.service.ProdutoElasticService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/produtos")
@AllArgsConstructor
public class ProdutoElasticController {
    private final ProdutoElasticService produtoElasticService;

    @GetMapping("/busca/nome")
    public List<ProdutoElastic> buscarPorNome(@RequestParam String termo) {
        return produtoElasticService.buscarPorNome(termo);
    }

    @GetMapping("/busca/descricao")
    public List<ProdutoElastic> buscarPorDescricao(@RequestParam String termo) {
        return produtoElasticService.buscarPorDescricao(termo);
    }

    @GetMapping("/busca/frase")
    public List<ProdutoElastic> buscarPorFraseExata(@RequestParam String termo) {
        return produtoElasticService.buscarPorFraseExata(termo);
    }

    @GetMapping("/busca/fuzzy")
    public List<ProdutoElastic> buscarFuzzy(@RequestParam String termo) {
        return produtoElasticService.buscarFuzzy(termo);
    }

    @GetMapping("/busca/multicampos")
    public List<ProdutoElastic> buscarMultiCampos(@RequestParam String termo) {
        return produtoElasticService.buscarMultiCampos(termo);
    }

    @GetMapping("/busca/com-filtro")
    public List<ProdutoElastic> buscarComFiltro(@RequestParam String termo, @RequestParam String categoria) {
        return produtoElasticService.buscarPorDescricaoECategoria(termo, categoria);
    }

    @GetMapping("/busca/faixa-preco")
    public List<ProdutoElastic> buscarPorFaixaPreco(@RequestParam Double min, @RequestParam Double max) {
        return produtoElasticService.buscarPorFaixaPreco(min, max);
    }

    @GetMapping("/busca/avancada")
    public List<ProdutoElastic> buscarAvancada(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String raridade,
            @RequestParam(required = false) Double min,
            @RequestParam(required = false) Double max) {
        return produtoElasticService.buscarAvancada(categoria, raridade, min, max);
    }

    @GetMapping("/agregacoes/por-categoria")
    public Map<String, Long> contarPorCategoria() {
        return produtoElasticService.contarPorCategoria();
    }

    @GetMapping("/agregacoes/por-raridade")
    public Map<String, Long> contarPorRaridade() {
        return produtoElasticService.contarPorRaridade();
    }

    @GetMapping("/agregacoes/preco-medio")
    public Double precoMedio() {
        return produtoElasticService.calcularPrecoMedio();
    }

    @GetMapping("/agregacoes/faixas-preco")
    public Map<String, Long> faixasPreco() {
        return produtoElasticService.contarFaixasPreco();
    }
}
