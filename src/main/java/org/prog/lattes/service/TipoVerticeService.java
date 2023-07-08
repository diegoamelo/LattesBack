package org.prog.lattes.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.jvnet.hk2.annotations.Service;
import org.prog.lattes.model.TipoVertice;
import org.springframework.stereotype.Component;

@Service
@Component
public class TipoVerticeService {
    
    public List<String> listTipoVertice(){
        
        List<String> tipoVertices = Arrays.stream(TipoVertice.values()).map(TipoVertice::getNome).collect(Collectors.toList());

        Collections.sort(tipoVertices);
        
        return tipoVertices;
    }
}