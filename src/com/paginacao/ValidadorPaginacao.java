package com.paginacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValidadorPaginacao {

    static int passou = 0;
    static int falhou = 0;

    public static void main(String[] args) {
        List<Livro> livros = Arrays.asList(
            new Livro(0, "Algoritmos", "Cormen", true),
            new Livro(1, "Clean Code", "Martin", true),
            new Livro(2, "Java Efetivo", "Bloch", true),
            new Livro(3, "O Programador Pragmático", "Hunt", true),
            new Livro(4, "Design Patterns", "GoF", true),
            new Livro(5, "Java: como programar", "Deitel", true),
            new Livro(6, "Entendendo Algoritmos", "Bhargava", true),
            new Livro(7, "Código Limpo em Java", "Silva", true),
            new Livro(8, "Estrutura de Dados", "Preiss", true),
            new Livro(9, "Introdução à Computação", "Brookshear", true)
        );

        Paginador<Livro> paginador = new Paginador<>();
        CatalogoBusca busca = new CatalogoBusca();

        System.out.println("=== Exercício 1: paginar ===");
        verificar("p=0, s=3", paginador.paginar(livros, 0, 3), ids(0, 1, 2));
        verificar("p=1, s=3", paginador.paginar(livros, 1, 3), ids(3, 4, 5));
        verificar("p=2, s=3", paginador.paginar(livros, 2, 3), ids(6, 7, 8));
        verificar("p=3, s=3", paginador.paginar(livros, 3, 3), ids(9));
        verificar("p=4, s=3", paginador.paginar(livros, 4, 3), ids());
        verificar("p=0, s=5", paginador.paginar(livros, 0, 5), ids(0, 1, 2, 3, 4));
        verificar("p=1, s=5", paginador.paginar(livros, 1, 5), ids(5, 6, 7, 8, 9));

        System.out.println("\n=== Exercício 2: primeirosN ===");
        verificar("n=3",  paginador.primeirosN(livros, 3),  ids(0, 1, 2));
        verificar("n=0",  paginador.primeirosN(livros, 0),  ids());
        verificar("n=20", paginador.primeirosN(livros, 20), ids(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));

        System.out.println("\n=== Exercício 3: ignorarN ===");
        verificar("n=7",  paginador.ignorarN(livros, 7),  ids(7, 8, 9));
        verificar("n=0",  paginador.ignorarN(livros, 0),  ids(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        verificar("n=10", paginador.ignorarN(livros, 10), ids());
        verificar("n=15", paginador.ignorarN(livros, 15), ids());

        System.out.println("\n=== Exercício 4: totalPaginas ===");
        verificarInt("10 itens, s=3", paginador.totalPaginas(livros, 3), 4);
        verificarInt("9 itens, s=3",  paginador.totalPaginas(livros.subList(0, 9), 3), 3);
        verificarInt("6 itens, s=3",  paginador.totalPaginas(livros.subList(0, 6), 3), 2);
        verificarInt("1 item,  s=5",  paginador.totalPaginas(livros.subList(0, 1), 5), 1);
        verificarInt("0 itens, s=5",  paginador.totalPaginas(new ArrayList<>(), 5), 0);

        System.out.println("\n=== Exercício 5: buscarComPaginacao ===");
        verificar("Java p=0 s=2", busca.buscarComPaginacao(livros, "Java", 0, 2), ids(2, 5));
        verificar("Java p=1 s=2", busca.buscarComPaginacao(livros, "Java", 1, 2), ids(7));
        verificar("java p=0 s=5", busca.buscarComPaginacao(livros, "java", 0, 5), ids(2, 5, 7));
        verificar("Python p=0",   busca.buscarComPaginacao(livros, "Python", 0, 5), ids());

        System.out.println("\n================================");
        System.out.println("PASSOU: " + passou + " | FALHOU: " + falhou);
        System.out.println(falhou == 0 ? "Todos os testes passaram!" : "Revise as implementações acima.");
    }

    static List<Integer> ids(int... valores) {
        List<Integer> lista = new ArrayList<>();
        for (int v : valores) lista.add(v);
        return lista;
    }

    static void verificar(String descricao, List<Livro> resultado, List<Integer> esperado) {
        List<Integer> idsResultado = new ArrayList<>();
        for (Livro l : resultado) idsResultado.add(l.getId());
        boolean ok = idsResultado.equals(esperado);
        System.out.printf("  [%s] %s%n", ok ? "PASS" : "FAIL", descricao);
        if (!ok) {
            System.out.printf("       Esperado: %s%n", esperado);
            System.out.printf("       Obtido:   %s%n", idsResultado);
            falhou++;
        } else {
            passou++;
        }
    }

    static void verificarInt(String descricao, int resultado, int esperado) {
        boolean ok = resultado == esperado;
        System.out.printf("  [%s] %s%n", ok ? "PASS" : "FAIL", descricao);
        if (!ok) {
            System.out.printf("       Esperado: %d%n", esperado);
            System.out.printf("       Obtido:   %d%n", resultado);
            falhou++;
        } else {
            passou++;
        }
    }
}
