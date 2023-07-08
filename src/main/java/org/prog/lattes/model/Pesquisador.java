package org.prog.lattes.model;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pesquisador{    
    
    @Id
    @Column(unique = true, nullable = false)
    private String identificador;

    @Column(unique = true, nullable = false, length = 100)
    private String nome;
    
    @Column(name = "uf", nullable = true, length = 5)
    private String ufNascimento;
    
    @ManyToOne
    @JoinColumn(name = "instituto", nullable = false)
    private Instituto instituto;

    @OneToMany(mappedBy = "pesquisador")
    private List<Producao> producoes;
    
    @Override
    public String toString() {
        return nome;
    }
}