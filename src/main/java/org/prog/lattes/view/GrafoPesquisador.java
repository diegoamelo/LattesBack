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
public class GrafoPesquisador {
    private String idPesquisador1;
    private String nomePesquisador1;
    private String idPesquisador2;
    private String nomePesquisador2;
    private String tipoProducao;
    private Integer total;
}