package org.prog.lattes.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.prog.lattes.model.Instituto;
import org.prog.lattes.model.Pesquisador;
import org.prog.lattes.model.Producao;
import org.prog.lattes.model.TipoProducao;
import org.prog.lattes.model.TotalProducoesAno;
import org.prog.lattes.model.TotalProducoesTipo;
import org.prog.lattes.repository.ProducaoRepository;
import org.prog.lattes.view.GrafoInstituto;
import org.prog.lattes.view.GrafoPesquisador;
import org.prog.lattes.view.ProducaoView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class ProducaoService {
    
    public ProducaoRepository producaoRepository;
    
    public ProducaoService(ProducaoRepository producaoRepository){
        this.producaoRepository = producaoRepository;
    }

    public Specification<Producao> querySpecification(Integer anoInicio, Integer anoFim, String instituto, String pesquisador, String tipoProducao, Integer ano){
        Specification<Producao> spec = Specification.where(null);
        if (anoInicio != null) {
            System.out.println("Ano Inicio = " + anoInicio); ////////////////////////////////////////////////////////////////////////////////////////////////
            spec = spec.and(ProducaoRepository.filtrarPorAnoInicio(anoInicio));
        }
        if (anoFim != null) {
            System.out.println("Ano Fim = " + anoFim); ////////////////////////////////////////////////////////////////////////////////////////////////
            spec = spec.and(ProducaoRepository.filtrarPorAnoFim(anoFim));
        }
        if ((instituto != null) && (instituto.trim() != "")){
            System.out.println("Instituto = " + instituto); ////////////////////////////////////////////////////////////////////////////////////////////////
            spec = spec.and(ProducaoRepository.filtrarPorInstituto(instituto));
        }
        if ((pesquisador != null) && (pesquisador.trim() != "")){
            System.out.println("Pesquisador = " + pesquisador); ////////////////////////////////////////////////////////////////////////////////////////////////
            spec = spec.and(ProducaoRepository.filtrarPorPesquisador(pesquisador));
        }
        if ((tipoProducao != null) && (tipoProducao.trim() != "")){
            System.out.println("Tipo Produção = " + tipoProducao); ////////////////////////////////////////////////////////////////////////////////////////////////
            spec = spec.and(ProducaoRepository.filtrarPorTipoProducao(tipoProducao));
        }
        if (ano != null) {
            System.out.println("Ano = " + ano); ////////////////////////////////////////////////////////////////////////////////////////////////
            spec = spec.and(ProducaoRepository.filtrarPorAno(ano));
        }
        return spec;
    }

    public Specification<Producao> querySpecification(Integer anoInicio, Integer anoFim, String instituto, String pesquisador, String tipoProducao){
        Specification<Producao> spec = Specification.where(null);
        if (anoInicio != null) {
            System.out.println("Ano Inicio = " + anoInicio); ////////////////////////////////////////////////////////////////////////////////////////////////
            spec = spec.and(ProducaoRepository.filtrarPorAnoInicio(anoInicio));
        }
        if (anoFim != null) {
            System.out.println("Ano Fim = " + anoFim); ////////////////////////////////////////////////////////////////////////////////////////////////
            spec = spec.and(ProducaoRepository.filtrarPorAnoFim(anoFim));
        }
        if (instituto != null) {
            System.out.println("Instituto = " + instituto); ////////////////////////////////////////////////////////////////////////////////////////////////
            spec = spec.and(ProducaoRepository.filtrarPorInstituto(instituto));
        }
        if (pesquisador != null) {
            System.out.println("Pesquisador = " + pesquisador); ////////////////////////////////////////////////////////////////////////////////////////////////
            spec = spec.and(ProducaoRepository.filtrarPorPesquisador(pesquisador));
        }
        if (tipoProducao != null) {
            System.out.println("Tipo Produção = " + tipoProducao); ////////////////////////////////////////////////////////////////////////////////////////////////
            spec = spec.and(ProducaoRepository.filtrarPorTipoProducao(tipoProducao));
        }
        return spec;
    }

    public Page<ProducaoView> buscarComFiltroDinamico(Integer anoInicio, Integer anoFim, String instituto, String pesquisador, String tipoProducao, Integer ano, Pageable pageable) {
        //Usado para ordenar a pagina pelo nome da produção de forma crescente
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("nome"));

        Specification<Producao> spec = querySpecification(anoInicio, anoFim, instituto, pesquisador, tipoProducao, ano);

        Page<Producao> producoesPage = producaoRepository.findAll(spec, pageRequest);

        List<ProducaoView> producoesView = producoesPage
                .stream()
                .map(this::convertToProducaoView)
                .collect(Collectors.toList());

        return new PageImpl<>(producoesView, pageRequest, producoesPage.getTotalElements());
    }

    private ProducaoView convertToProducaoView(Producao producao) {
        ProducaoView producaoView = new ProducaoView();
        producaoView.setId(producao.getId());
        producaoView.setNome(producao.getNome());
        producaoView.setAno(producao.getAno());
        producaoView.setTipoProducao(producao.getTipoProducao());
        producaoView.setPesquisador(producao.getPesquisador().getNome());
        return producaoView;
    }

    public List<TotalProducoesAno> countTotalProducoesPorAno(Integer anoInicio, Integer anoFim, String instituto, String pesquisador, String tipoProducao) {
        Specification<Producao> spec = querySpecification(anoInicio, anoFim, instituto, pesquisador, tipoProducao);

        List<Producao> producoesFiltradas = producaoRepository.findAll(spec);
        
        List<TotalProducoesAno> totalPorAno = new ArrayList<>();

        Map<Integer, Map<String, Long>> producoesAgrupadas = producoesFiltradas.stream()
            .collect(Collectors.groupingBy(Producao::getAno,
                Collectors.groupingBy(Producao::getTipoProducao,
                Collectors.counting())));

        for (Map.Entry<Integer, Map<String, Long>> entry : producoesAgrupadas.entrySet()) {
            Integer anoProducao = entry.getKey();
            Map<String, Long> producoesPorTipo = entry.getValue();

            TotalProducoesAno totalProducoesAno = new TotalProducoesAno();
            totalProducoesAno.setAnoProducao(anoProducao);

            // Obtenha os totais para cada tipo de produção e defina-os no objeto TotalProducoesAno
            totalProducoesAno.setArtigo(producoesPorTipo.getOrDefault(TipoProducao.ARTIGO.getNome(), 0L));
            totalProducoesAno.setCapituloLivro(producoesPorTipo.getOrDefault(TipoProducao.CAPITULO_LIVRO.getNome(), 0L));
            totalProducoesAno.setLivro(producoesPorTipo.getOrDefault(TipoProducao.LIVRO.getNome(), 0L));
            //totalProducoesAno.setOrientacaoMestrado(producoesPorTipo.getOrDefault(TipoProducao.ORIENTACOES_MESTRADO.getNome(), 0L));
            //totalProducoesAno.setOrientacaoTCC(producoesPorTipo.getOrDefault(TipoProducao.ORIENTACOES_TCC.getNome(), 0L));
            totalProducoesAno.setTrabalhoEvento(producoesPorTipo.getOrDefault(TipoProducao.TRABALHO_EVENTO.getNome(), 0L));

            // Calcule o total de produção para o ano e defina-o no objeto TotalProducoesAno
            long totalProducao = totalProducoesAno.getArtigo() + totalProducoesAno.getCapituloLivro()
                    + totalProducoesAno.getLivro() + /*totalProducoesAno.getOrientacaoMestrado()
                    + totalProducoesAno.getOrientacaoTCC() +*/ totalProducoesAno.getTrabalhoEvento();
            totalProducoesAno.setTotalProducao(totalProducao);

            // Adicione o objeto TotalProducoesAno à lista de resultados
            totalPorAno.add(totalProducoesAno);
        }

        // Ordenar a lista totalPorAno pelo atributo anoProducao
        Collections.sort(totalPorAno, Comparator.comparingInt(TotalProducoesAno::getAnoProducao));

        return totalPorAno;
    }

    public long countProducao() {
        return producaoRepository.count();
    }
    
    public List<TotalProducoesTipo> countTotalProducoesPorTipo() {
        return producaoRepository.countTotalProducoesPorTipo();
    }

    public void saveAll(List<Producao> producaoList){
        producaoRepository.saveAll(producaoList);
    }

    /************************************************************************************************************************************/
    /*                                     MÉTODOS PARA GERAR O GRAFO COM VERTICE COMO PESQUISADOR                                      */
    /************************************************************************************************************************************/
    
    private GrafoPesquisador existeNaLista(List<GrafoPesquisador> list, String idPesquisador1, String idPesquisador2){
        if(!list.isEmpty()){
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).getIdPesquisador1().equals(idPesquisador1) && 
                    list.get(i).getIdPesquisador2().equals(idPesquisador2)){
                    return list.get(i);
                }
            }
        }
        return null;
    }

    public List<GrafoPesquisador> grafoPesquisador(){
        List<Object[]> results = producaoRepository.findGrafoPesquisador();
        List<GrafoPesquisador> list = new ArrayList<>();
        
        for (Object[] row : results) {
            String idPesquisador1 = (String) row[1];
            String idPesquisador2 = (String) row[3];

            GrafoPesquisador pesquisador = existeNaLista(list, idPesquisador1, idPesquisador2);

            if(pesquisador == null){
                GrafoPesquisador grafoPesquisador = new GrafoPesquisador();
                grafoPesquisador.setIdPesquisador1((String) row[1]);
                grafoPesquisador.setNomePesquisador1((String) row[2]);
                grafoPesquisador.setIdPesquisador2((String) row[3]);
                grafoPesquisador.setNomePesquisador2((String) row[4]);
                grafoPesquisador.setTipoProducao((String) row[5]);
                grafoPesquisador.setTotal(1);
                list.add(grafoPesquisador);
            }
            else{
                pesquisador.setTotal(pesquisador.getTotal() + 1);
            }
        }
        return list;
    }

    public String cytoscapejsPesquisador() {
        String edgeFormat = "";

        List<GrafoPesquisador> listGrafoPesquisador = grafoPesquisador();
        
        for (int i = 0; i < listGrafoPesquisador.size(); i++) {
            edgeFormat = edgeFormat + "{ data: { source: '" + listGrafoPesquisador.get(i).getIdPesquisador1() + "', " +
                    "target: '" + listGrafoPesquisador.get(i).getIdPesquisador2() + "', " +
                    "label: '" + listGrafoPesquisador.get(i).getTotal() + "'}},\n";
        }
        return edgeFormat;
    }

    /************************************************************************************************************************************/
    /*                                     MÉTODOS PARA GERAR O GRAFO COM VERTICE COMO INSTITUTO                                        */
    /************************************************************************************************************************************/

    private Long buscarIdInstituto(String idPesquisador){
        return producaoRepository.buscarIdInstituto(idPesquisador);
    }

    private String buscarNomeInstituto(Long idInstituto){
        return producaoRepository.buscarNomeInstituto(idInstituto);
    }

    private GrafoInstituto existeNaLista(List<GrafoInstituto> list, Long idPesquisador1, Long idPesquisador2){
        if(!list.isEmpty()){
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).getIdInstituto1().equals(idPesquisador1) && 
                    list.get(i).getIdInstituto2().equals(idPesquisador2)){
                    return list.get(i);
                }
            }
        }
        return null;
    }

    public List<GrafoInstituto> grafoInstituto(){
        List<GrafoPesquisador> listGrafoPesquisador = grafoPesquisador();
        List<GrafoInstituto> list = new ArrayList<>();
        
        for (int i = 0; i < listGrafoPesquisador.size(); i++) {
            Long idInstituto1 = buscarIdInstituto(listGrafoPesquisador.get(i).getIdPesquisador1());
            Long idInstituto2 = buscarIdInstituto(listGrafoPesquisador.get(i).getIdPesquisador2());

            GrafoInstituto instituto = existeNaLista(list, idInstituto1, idInstituto2);

            if(instituto == null){
                GrafoInstituto grafoInstituto = new GrafoInstituto();
                grafoInstituto.setIdInstituto1(idInstituto1);
                grafoInstituto.setNomeInstituto1(buscarNomeInstituto(idInstituto1));
                grafoInstituto.setIdInstituto2(idInstituto2);
                grafoInstituto.setNomeInstituto2(buscarNomeInstituto(idInstituto2));
                grafoInstituto.setTipoProducao(listGrafoPesquisador.get(i).getTipoProducao());
                grafoInstituto.setTotal(listGrafoPesquisador.get(i).getTotal());
                list.add(grafoInstituto);
            }
            else{
                instituto.setTotal(instituto.getTotal() + listGrafoPesquisador.get(i).getTotal());
            }
        }
        return list;
    }
    
    public String cytoscapejsInstituto() {
        String edgeFormat = "";

        List<GrafoInstituto> listGrafoInstituto = grafoInstituto();
        
        for (int i = 0; i < listGrafoInstituto.size(); i++) {
            edgeFormat = edgeFormat + "{ data: { source: '" + listGrafoInstituto.get(i).getIdInstituto1() + "', " +
                    "target: '" + listGrafoInstituto.get(i).getIdInstituto2() + "', " +
                    "label: '" + listGrafoInstituto.get(i).getTotal() + "'}},\n";
        }
        return edgeFormat;
    }

    public String filtroDinamicoGrafo(List<Pesquisador> pesquisadores, String tipoProducao, String tipoVertice){
        String result = new String();

        List<GrafoPesquisador> listGrafoPesquisador = grafoPesquisador();

        if (tipoVertice.equals("pesquisador")) {
            List<String> idsPesquisadores = pesquisadores.stream()
                    .map(Pesquisador::getIdentificador)
                        .collect(Collectors.toList());

            listGrafoPesquisador = listGrafoPesquisador.stream()
                    .filter(p -> idsPesquisadores.contains(p.getIdPesquisador1()) || idsPesquisadores.contains(p.getIdPesquisador2()))
                    .collect(Collectors.toList());
        } else if (tipoVertice.equals("instituto")) {
            List<Long> idsInstitutos = pesquisadores.stream()
                    .map(Pesquisador::getInstituto)
                    .map(Instituto::getId)
                    .collect(Collectors.toList());

            listGrafoPesquisador = listGrafoPesquisador.stream()
                    .filter(p -> idsInstitutos.contains(buscarIdInstituto(p.getIdPesquisador1())) || idsInstitutos.contains(buscarIdInstituto(p.getIdPesquisador2())))
                    .collect(Collectors.toList());
        }

        if (tipoProducao != null && !tipoProducao.isEmpty()) {
            listGrafoPesquisador = listGrafoPesquisador.stream()
                    .filter(p -> p.getTipoProducao().equals(tipoProducao))
                    .collect(Collectors.toList());
        }

        for (int i = 0; i < listGrafoPesquisador.size(); i++) {
            result = result + "{ data: { source: '" + listGrafoPesquisador.get(i).getIdPesquisador1() + "', " +
                    "target: '" + listGrafoPesquisador.get(i).getIdPesquisador2() + "', " +
                    "label: '" + listGrafoPesquisador.get(i).getTotal() + "'}},\n";
        }
        
        return result;
    }
}