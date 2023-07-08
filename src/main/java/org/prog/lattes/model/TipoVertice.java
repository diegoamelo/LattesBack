package org.prog.lattes.model;

public enum TipoVertice {
    //AUTOR("Autor"),
    INSTITUTO("Instituto"),
    PESQUISADOR("Pesquisador");

    private String nome;
    
    public String getNome() {
        return nome;
    }
    
    TipoVertice(String nome){
        this.nome = nome;
    }
}
