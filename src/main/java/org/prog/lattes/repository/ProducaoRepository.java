package org.prog.lattes.repository;

import java.util.List;
import org.prog.lattes.model.Producao;
import org.prog.lattes.model.TotalProducoesTipo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducaoRepository extends JpaRepository<Producao, Long>, JpaSpecificationExecutor<Producao>{
    
    public static Specification<Producao> filtrarPorAnoInicio(Integer anoInicio) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("ano"), anoInicio);
    }
    
    public static Specification<Producao> filtrarPorAnoFim(Integer anoFim) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("ano"), anoFim);
    }

    public static Specification<Producao> filtrarPorInstituto(String instituto) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("pesquisador").get("instituto").get("nome")),
            "%" + instituto.toLowerCase() + "%");
    }

    public static Specification<Producao> filtrarPorPesquisador(String pesquisador) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("pesquisador").get("nome")), 
            "%" + pesquisador.toLowerCase() + "%");
    }

    public static Specification<Producao> filtrarPorTipoProducao(String tipoProducao) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("tipoProducao")), "%" + tipoProducao.toLowerCase() + "%");
    }

    public static Specification<Producao> filtrarPorAno(Integer ano) {
        return (root, query, builder) -> builder.equal(root.get("ano"), ano);
    }

    public Page<Producao> findByAno(Integer ano, Pageable pageable);

    @Query(value = "SELECT p.tipo_producao AS tipoProducao, COUNT(p.*) AS totalProducao "
         + "FROM producao AS p GROUP BY p.tipo_producao ORDER BY p.tipo_producao", nativeQuery = true)
    public List<TotalProducoesTipo> countTotalProducoesPorTipo();

    @Query(value = "SELECT DISTINCT p1.nome AS producao, " + 
            "pe1.identificador AS idPesquisador1, pe1.nome AS nomePesquisador1, " +
            "pe2.identificador AS idPesquisador2, pe2.nome AS pesquisador2, " +
            "p1.tipo_producao AS tipoProducao " + 
            "FROM producao p1 " + 
            "JOIN producao p2 ON upper(p1.nome) = upper(p2.nome) " +
            "JOIN pesquisador pe1 ON p1.pesquisador = pe1.identificador " +
            "JOIN pesquisador pe2 ON p2.pesquisador = pe2.identificador " + 
            "WHERE p1.pesquisador < p2.pesquisador;", nativeQuery = true)
    List<Object[]> findGrafoPesquisador();


    @Query(value = "SELECT p.instituto FROM pesquisador p WHERE p.identificador = :idPesquisador", nativeQuery = true)
    Long buscarIdInstituto(@Param("idPesquisador") String idPesquisador);

    @Query(value = "SELECT i.nome FROM instituto i WHERE i.id = :idInstituto", nativeQuery = true)
    String buscarNomeInstituto(@Param("idInstituto") Long idInstituto);
}