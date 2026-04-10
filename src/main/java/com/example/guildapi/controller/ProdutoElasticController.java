package com.example.guildapi.controller;

import com.example.guildapi.model.elastic.ProdutoElastic;
import com.example.guildapi.service.ProdutoElasticService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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


}
