package com.animal;

public class Animal {

    private String nome;
    private Boolean salta;
    private Boolean nada;

    public Animal(String nome, Boolean salta, Boolean nada) {
        this.nome = nome;
        this.salta = salta;
        this.nada = nada;
    }

    public String getNome() {
        return nome;
    }

    public boolean podeSalta() {
        return salta;
    }

    public boolean podeNadar() {
        return nada;
    }


}
