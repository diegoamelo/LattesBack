package org.prog.lattes.convert;

import java.io.File;
import org.jvnet.hk2.annotations.Service;
import org.prog.lattes.model.Instituto;
import org.prog.lattes.model.LeituraXml;
import org.springframework.stereotype.Component;

@Service
@Component
public class ReadXML{

    private final LeituraXml leituraXml;
 
    public ReadXML(LeituraXml leituraXml) {
        this.leituraXml = leituraXml;
    }

    public void start(String identificador, Instituto instituto) throws Exception{

        File diretorio = new File("src\\main\\java\\org\\prog\\lattes\\files");

        File file = new File(diretorio, identificador + ".xml");

        if(file.isFile()) {
            leituraXml.convert(file, instituto);
        } else {
            throw new Exception("Pesquisador não encontrado.");
        }
    }
}