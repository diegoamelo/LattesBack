package org.prog.lattes.model;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Instituto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String acronimo;
    
    @OneToMany(mappedBy = "instituto")
    private List<Pesquisador> pesquisadores;
    
    public Instituto(String nome, String acronimo) {
        this.nome = nome;
        this.acronimo = acronimo;
    }

    @Override
    public String toString() {
        return nome;
    }
}