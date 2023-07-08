package org.prog.lattes.repository;

import org.prog.lattes.model.Instituto;
import org.prog.lattes.model.Pesquisador;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.persistence.criteria.Join;

@Repository
public interface PesquisadorRepository extends JpaRepository<Pesquisador, String>, JpaSpecificationExecutor<Pesquisador> {

    public static Specification<Pesquisador> filtrarPorIdentificador(String identificador) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("identificador")), "%" + identificador + "%");
    }
    
    public static Specification<Pesquisador> filtrarPorNome(String nome) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    public static Specification<Pesquisador> filtrarPorInstituto(Long institutoId) {
        return (root, query, builder) -> {
            Join<Pesquisador, Instituto> join = root.join("instituto");
            return builder.equal(join.get("id"), institutoId);
        };
    }

    public static Specification<Pesquisador> filtrarPorInstitutoNome(String institutoNome) {
        return (root, query, builder) -> {
            Join<Pesquisador, Instituto> join = root.join("instituto");
            return builder.equal(join.get("nome"), institutoNome);
        };
    }

    Pesquisador findByIdentificador(String identificador);

    @Query(value = "SELECT p.instituto AS instituto " +
        "FROM pesquisador p " +
        "WHERE p.identificador = :identificador", nativeQuery = true)
    public Long findByInstituto(@Param("identificador") String identificador);
      
    Boolean existsByIdentificador(String identificador);
}