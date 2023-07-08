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
public class GrafoInstituto {
    private Long idInstituto1;
    private String nomeInstituto1;
    private Long idInstituto2;
    private String nomeInstituto2;
    private String tipoProducao;
    private Integer total;
}