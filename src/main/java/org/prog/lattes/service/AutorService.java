package org.prog.lattes.service;
/*
import java.util.List;
import java.util.stream.Collectors;
import org.prog.lattes.model.Autor;
import org.prog.lattes.model.Producao;
import org.prog.lattes.repository.AutorRepository;
import org.prog.lattes.view.AutorView;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class AutorService {
    
    public AutorRepository autorRepository;
    
    public AutorService(AutorRepository autorRepository){
        this.autorRepository = autorRepository;
    }

    public boolean autorExiste(String nome) {
        return autorRepository.autorExiste(nome);
    }

    public Autor buscarAutorNoBanco(String nome) {
        List<Autor> autoresCadastrados = autorRepository.findAll();

        for (Autor autorCadastrado : autoresCadastrados) {
            if (autorCadastrado.getNome().equals(nome)) {
                return autorCadastrado;
            }
        }
        return null;
    }

    public Autor buscarAutorNaLista(String nome, List<Producao> producoesList) {
        if(producoesList == null) return null;
        
        for (Producao producaoCadastrada : producoesList) {
            List<Autor> autoresList = producaoCadastrada.getAutores();

            if(autoresList == null) return null;
            
            for (Autor autorCadastrado : autoresList) {
                if (autorCadastrado.getNome().equals(nome)) {
                    return autorCadastrado;
                }
            }
        }
        return null;
    }

    public List<AutorView> listAutorPeloNome(String nome) {
        List<Autor> autorList = autorRepository.findByNomeContainingIgnoreCase(nome);

        List<AutorView> autoresView = autorList
                .stream()
                .map(this::convertToAutorView)
                .collect(Collectors.toList());
                
        return autoresView;
    }

    private AutorView convertToAutorView(Autor autor) {
        AutorView autorView = new AutorView();
        autorView.setId(autor.getId());
        autorView.setNome(autor.getNome());
        return autorView;
    }
}
*/