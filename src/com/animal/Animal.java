package com.animal;

public class Animal {

    private String nome;
    private Boolean salta;
    private Boolean nada;
    private Double peso;

    public Animal(String nome, Boolean salta, Boolean nada, Double peso) {
        this.nome = nome;
        this.salta = salta;
        this.nada = nada;
        this.peso = peso;
    }

    public String getNome() {
        return nome;
    }

    public Double getPeso() {
        return peso;
    }

    public boolean podeSalta() {
        return salta;
    }

    public boolean podeNadar() {
        return nada;
    }

    @Override
    public String toString() {
        return  String.format("Animal é %s, peso: %f ", this.nome, this.peso);
    }
}
