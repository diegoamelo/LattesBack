package org.prog.lattes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.prog.lattes.model.Instituto;
import org.prog.lattes.service.InstitutoService;
import org.prog.lattes.view.InstitutoView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/instituto")
@Tag(name = "Rotas de Instituto")
public class InstitutoController {
    
    @Autowired
    private InstitutoService institutoService;

    public InstitutoController(InstitutoService institutoService) {
        this.institutoService = institutoService;
    }

    @GetMapping
    public Page<InstitutoView> buscarComFiltroDinamico(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String acronimo,
            @RequestParam(required = false) String nomeAcronimo,
            Pageable pageable) {        
        return institutoService.buscarComFiltroDinamico(nome, acronimo, nomeAcronimo, pageable);
    }

    @GetMapping("/list")
    public List<InstitutoView> listBuscarComFiltroDinamico(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String acronimo,
            @RequestParam(required = false) String nomeAcronimo) {
        return institutoService.listBuscarComFiltroDinamico(nome, acronimo, nomeAcronimo);
    }

    @Operation(summary = "Busca a quantidade de Institutos Cadastrados")
    @GetMapping("/count")
    public long countInstituto() {
        return institutoService.countInstituto();
    }

    @Operation(summary = "Cadastra um Instituto")
    @PostMapping("/")
    public void gravar(@RequestBody Instituto instituto) {
        institutoService.gravar(instituto);
    }

    @Operation(summary = "Exclui um Instituto")
    @DeleteMapping("/{id}")
    public void remover(@PathVariable("id") Long id) throws Exception {
        institutoService.remover(id);
    }

    @GetMapping("/cytoscapejs")
    public String cytoscapejs() {
        return institutoService.cytoscapejs();
    }
}