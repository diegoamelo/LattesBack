package org.prog.lattes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Producao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(columnDefinition = "text", length = 10485760)
    private String nome;
    
    @Column(nullable = false)
    private Integer ano;

    @Column(nullable = false)
    private String tipoProducao;
    
    @ManyToOne
    @JoinColumn(name = "pesquisador", nullable = false)
    private Pesquisador pesquisador;
    
    // @ManyToMany (cascade = CascadeType.ALL)
    // @JoinTable(name = "producao_autor", 
    //     joinColumns =  @JoinColumn(name = "producao_id"),
    //     inverseJoinColumns = @JoinColumn(name = "autor_id"))
    // private List<Autor> autores;

    // public void addAutor(Autor autor) {
    //     if (this.autores == null) {
    //         this.autores = new ArrayList<>();
    //     }
    //     this.autores.add(autor);
    // }

    @Override
    public String toString() {
        return nome;
    }
} 