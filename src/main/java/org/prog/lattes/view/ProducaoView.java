package org.prog.lattes.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProducaoView {
    private Long id;
    private String nome;
    private Integer ano;
    private String tipoProducao;
    private String pesquisador;
}
