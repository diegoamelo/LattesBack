package org.prog.lattes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TotalProducoesAno {
    Integer anoProducao;
    Long artigo;
    Long capituloLivro;
    Long livro;
    Long orientacaoMestrado;
    Long orientacaoTCC;
    Long trabalhoEvento;
    Long totalProducao;
}