package org.prog.lattes.model;

public enum TipoProducao {
    ARTIGO("Artigo"),
    CAPITULO_LIVRO("Capitulo de Livro"), 
    LIVRO("Livro"),
    //ORIENTACOES_MESTRADO("Orientações de Mestrado"),
    //ORIENTACOES_TCC("Orientações de Trabalho de Conclusão de Curso"),
    TRABALHO_EVENTO("Trabalho em Evento");

    private String nome;
    
    public String getNome() {
        return nome;
    }
    
    TipoProducao(String nome){
        this.nome = nome;
    }
}