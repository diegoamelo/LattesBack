package org.prog.lattes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Citacao {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nomeCitacao;

    // @ManyToMany (mappedBy = "citacoes")
    // private List<Autor> autores;

    // public void addAutor(Autor autor) {
    //     if (this.autores == null) {
    //         this.autores = new ArrayList<>();
    //     }
    //     this.autores.add(autor);
    // }
}