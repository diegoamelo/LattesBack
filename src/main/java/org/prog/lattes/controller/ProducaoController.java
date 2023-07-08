package org.prog.lattes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.prog.lattes.model.Pesquisador;
import org.prog.lattes.model.TotalProducoesAno;
import org.prog.lattes.model.TotalProducoesTipo;
import org.prog.lattes.service.ProducaoService;
import org.prog.lattes.view.ProducaoView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/producao")
@Tag(name = "Rotas de Produções")
public class ProducaoController {

    @Autowired
    private ProducaoService producaoService;

    public ProducaoController(ProducaoService producaoService) {
        this.producaoService = producaoService;
    }

    @GetMapping
    public Page<ProducaoView> buscarComFiltroDinamico(
            @RequestParam(required = false) Integer anoInicio,
            @RequestParam(required = false) Integer anoFim,
            @RequestParam(required = false) String instituto,
            @RequestParam(required = false) String pesquisador,
            @RequestParam(required = false) String tipoProducao, 
            @RequestParam(required = false) Integer ano,
            Pageable pageable) {
        return producaoService.buscarComFiltroDinamico(anoInicio, anoFim, instituto, pesquisador, tipoProducao, ano, pageable);
    }

    @Operation(summary = "Busca a quantidades de produções em um determinado ano")
    @GetMapping("/countTotalProducoesPorAno")
    public List<TotalProducoesAno> countTotalProducoesPorAno(
            @RequestParam(required = false) Integer anoInicio,
            @RequestParam(required = false) Integer anoFim,
            @RequestParam(required = false) String instituto,
            @RequestParam(required = false) String pesquisador,
            @RequestParam(required = false) String tipoProducao){
        return producaoService.countTotalProducoesPorAno(anoInicio, anoFim, instituto, pesquisador, tipoProducao);
    }

    @Operation(summary = "Busca a quantidade total de produções")
     @GetMapping("/count")
    public long countProducao() {
        return producaoService.countProducao();
    }

    @Operation(summary = "Busca a quantidade de Produções por tipo")
    @GetMapping("/countTotalProducoesPorTipo")
    public List<TotalProducoesTipo> countTotalProducoesPorTipo() {
        return producaoService.countTotalProducoesPorTipo();
    }
    
    @GetMapping("/cytoscapejsPesquisador")
    public String cytoscapejsPesquisador() {
        return producaoService.cytoscapejsPesquisador();
    }

    @GetMapping("/cytoscapejsInstituto")
    public String cytoscapejsInstituto() {
        return producaoService.cytoscapejsInstituto();
    }

    @GetMapping("/filtroDinamicoGrafo")
    public String filtroDinamicoGrafo(
        @RequestParam(required = false) List<Pesquisador> pesquisadores,
        @RequestParam(required = false) String tipoProducao,
        @RequestParam(required = false) String tipoVertice){
        return producaoService.filtroDinamicoGrafo(pesquisadores, tipoProducao, tipoVertice);
    }
}