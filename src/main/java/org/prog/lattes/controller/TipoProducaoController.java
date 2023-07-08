package org.prog.lattes.controller;

import java.util.List;
import org.prog.lattes.service.TipoProducaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/tipoProducao")
public class TipoProducaoController {
    
    @Autowired
    private TipoProducaoService tipoProducaoService;

    public TipoProducaoController(TipoProducaoService tipoProducaoService) {
        this.tipoProducaoService = tipoProducaoService;
    }

    @GetMapping
    public List<String> listTipoProducao() {
        return tipoProducaoService.listTipoProducao();
    }
}