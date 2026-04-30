package com.paginacao;

public class Livro {
    private int id;
    private String titulo;
    private String autor;
    private boolean disponivel;

    public Livro(int id, String titulo, String autor, boolean disponivel) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.disponivel = disponivel;
    }

    public int getId()            { return id; }
    public String getTitulo()     { return titulo; }
    public String getAutor()      { return autor; }
    public boolean isDisponivel() { return disponivel; }

    @Override
    public String toString() {
        return "[" + id + "] " + titulo + " - " + autor;
    }
}
