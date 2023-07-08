package org.prog.lattes.controller;

import java.util.List;
import org.prog.lattes.service.TipoVerticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/tipoVertice")
public class TipoVerticeController {
    
    @Autowired
    private TipoVerticeService tipoVerticeService;

    public TipoVerticeController(TipoVerticeService tipoVerticeService) {
        this.tipoVerticeService = tipoVerticeService;
    }

    @GetMapping
    public List<String> listTipoVertice() {
        return tipoVerticeService.listTipoVertice();
    }
}