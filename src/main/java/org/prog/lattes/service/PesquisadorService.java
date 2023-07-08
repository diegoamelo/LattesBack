package org.prog.lattes.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.prog.lattes.convert.ReadXML;
import org.prog.lattes.model.Instituto;
import org.prog.lattes.model.Pesquisador;
import org.prog.lattes.repository.PesquisadorRepository;
import org.prog.lattes.view.PesquisadorView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class PesquisadorService {

    private static ReadXML readXML;
    
    PesquisadorRepository pesquisadorRepository;

    @Autowired
    public void setReadXML(ReadXML readXML) {
        PesquisadorService.readXML = readXML;
    }

    public PesquisadorService(PesquisadorRepository pesquisadorRepository){
        this.pesquisadorRepository = pesquisadorRepository;
    }

    public Specification<Pesquisador> querySpecification(String identificador, String nome, Long instituto, String institutoNome){
        Specification<Pesquisador> spec = Specification.where(null);
        
        if (identificador != null) {
            spec = spec.and(PesquisadorRepository.filtrarPorIdentificador(identificador));
        }
        
        if (nome != null) {
            spec = spec.and(PesquisadorRepository.filtrarPorNome(nome));
        }
    
        if (instituto != null) {
            spec = spec.and(PesquisadorRepository.filtrarPorInstituto(instituto));
        }

        if (institutoNome != null) {
            spec = spec.and(PesquisadorRepository.filtrarPorInstitutoNome(institutoNome));
        }

        return spec;
    }

    public Page<PesquisadorView> buscarComFiltroDinamico(String identificador, String nome, Long institutoId, String institutoNome, Pageable pageable) {
        //Usado para ordenar a pagina pelo nome do pesquisador de forma crescente
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("nome"));

        Specification<Pesquisador> spec = querySpecification(identificador, nome, institutoId, institutoNome);

        Page<Pesquisador> pesquisadoresPage = pesquisadorRepository.findAll(spec, pageRequest);

        List<PesquisadorView> pesquisadoresView = pesquisadoresPage
                .stream()
                .map(this::convertToPesquisadorView)
                .collect(Collectors.toList());

        return new PageImpl<>(pesquisadoresView, pageRequest, pesquisadoresPage.getTotalElements());
    }

    public List<PesquisadorView> listBuscarComFiltroDinamico(String identificador, String nome, Long instituto, String institutoNome) {
        Specification<Pesquisador> spec = querySpecification(identificador, nome, instituto, institutoNome);

        List<Pesquisador> listPesquisador = pesquisadorRepository.findAll(spec);

        Collections.sort(listPesquisador, (p1, p2) -> p1.getNome().compareTo(p2.getNome()));
        
        List<PesquisadorView> pesquisadoresView = listPesquisador
                .stream()
                .map(this::convertToPesquisadorView)
                .collect(Collectors.toList());
                
        return pesquisadoresView;
    }

    private PesquisadorView convertToPesquisadorView(Pesquisador pesquisador) {
        PesquisadorView pesquisadorView = new PesquisadorView();
        pesquisadorView.setIdentificador(pesquisador.getIdentificador());
        pesquisadorView.setNome(pesquisador.getNome());
        pesquisadorView.setUfNascimento(pesquisador.getUfNascimento());
        pesquisadorView.setInstituto(pesquisador.getInstituto().getNome());
        return pesquisadorView;
    }

    public long countPesquisador() {
        return pesquisadorRepository.count();
    }

    public void addPesquisador(String identificador, Instituto instituto) throws Exception{
        if(pesquisadorRepository.existsByIdentificador(identificador)){
            throw new Exception("Pesquisador já cadastrado no sistema");
        }else{            
            readXML.start(identificador, instituto);
        }
    }

    public void excluir(String identificador) throws Exception {
        try {
            Pesquisador pesquisador = pesquisadorRepository.findByIdentificador(identificador);
            
            if (pesquisador != null) {
                pesquisadorRepository.delete(pesquisador);
                System.out.println("Pesquisador excluído com sucesso.");
            } else {
                System.out.println("Pesquisador não encontrado com o ID fornecido.");
            }
        } catch (Exception e) {
            System.out.println("Falha ao excluir o pesquisador. Erro: " + e.getMessage());
        }
    }
    
    public void save(Pesquisador pesquisador) {
        pesquisadorRepository.save(pesquisador);
    }

    /************************************************************************************************************************************/
    /*                                     MÉTODOS PARA GERAR O GRAFO COM VERTICE COMO PESQUISADOR                                      */
    /************************************************************************************************************************************/

    public String cytoscapejs() {
        String nodeFormat = "";

        List<Pesquisador> listPesquisador = pesquisadorRepository.findAll();
        
        for (int i = 0; i < listPesquisador.size(); i++) {
            nodeFormat = nodeFormat + "{ data: { id: '" + listPesquisador.get(i).getIdentificador() + "', " +
                    "label: '" + listPesquisador.get(i).getNome() + "'}},\n";
        }
        return nodeFormat;
    }
}