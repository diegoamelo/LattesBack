package org.prog.lattes.controller;
/*
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.prog.lattes.service.AutorService;
import org.prog.lattes.view.AutorView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/autor")
@Tag(name = "Rotas de Autores")
public class AutorController {
    
    @Autowired
    private AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @Operation(summary = "Verfica a existência de Autor", description = "Retorna um valor Boolean")
    @GetMapping("/autorExiste/{nome}")
    public boolean autorExiste(@PathVariable("nome") String nome) {
        return autorService.autorExiste(nome);
    }

    @Operation(summary = "Busca um autor pelo o nome")
    @GetMapping("/nome/{nome}")
    public List<AutorView> listAutorPeloNome(@PathVariable("nome") String nome) {
        return autorService.listAutorPeloNome(nome);
    }
}
*/