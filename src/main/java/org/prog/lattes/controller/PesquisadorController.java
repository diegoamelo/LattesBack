package org.prog.lattes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.prog.lattes.model.Instituto;
import org.prog.lattes.service.PesquisadorService;
import org.prog.lattes.view.PesquisadorView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/pesquisador")
@Tag(name = "Rotas Pesquisadores")
public class PesquisadorController {

    @Autowired
    private PesquisadorService pesquisadorService;

    public PesquisadorController(PesquisadorService pesquisadorService) {
        this.pesquisadorService = pesquisadorService;
    }

    @GetMapping
    public Page<PesquisadorView> buscarComFiltroDinamico(
            @RequestParam(required = false) String identificador,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Long instituto,
            @RequestParam(required = false) String institutoNome,
            Pageable pageable) {        
        return pesquisadorService.buscarComFiltroDinamico(identificador, nome, instituto, institutoNome, pageable);
    }

    @GetMapping("/list")
    public List<PesquisadorView> buscarComFiltroDinamico(
            @RequestParam(required = false) String identificador,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Long instituto,
            @RequestParam(required = false) String institutoNome) {
        return pesquisadorService.listBuscarComFiltroDinamico(identificador, nome, instituto, institutoNome);
    }

    @Operation(summary = "Busca a quantidade de Pesquisadores Cadastrados")
    @GetMapping("/count")
    public long countPesquisador() {
        return pesquisadorService.countPesquisador();
    }

    @Operation(summary = "Cadastra um novo pesquisador")
    @PostMapping("/add/{identificador}/instituto/{instituto}")
    public void addPesquisador(@PathVariable("identificador") String identificador, @PathVariable("instituto") Instituto instituto) throws Exception {
        pesquisadorService.addPesquisador(identificador, instituto);
    }

    @Operation(summary = "Deleta um Pesquisador cadastrado")
    @DeleteMapping("/excluir/{identificador}")
    public void excluir(@PathVariable("identificador") String identificador) throws Exception {
        pesquisadorService.excluir(identificador);
    }

    @GetMapping("/cytoscapejs")
    public String cytoscapejs() {
        return pesquisadorService.cytoscapejs();
    }
}