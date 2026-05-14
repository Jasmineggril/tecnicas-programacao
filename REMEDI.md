# Respostas — Questões 3c e 3d para Remedi

---

## 3c) Agrupar Empréstimos por Usuário

### Código

```java
public Map<Usuario, List<Emprestimo>> agruparEmprestimosPorUsuario(List<Emprestimo> emprestimos) {
    return emprestimos.stream()
            .collect(Collectors.groupingBy(emprestimo -> 
                usuarioRepo.buscarPorId(emprestimo.getUsuarioId())
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Usuário não encontrado para empréstimo: " + emprestimo.getId()))
            ));
}
```

### Explicação

**Conceito:** Utiliza `Collectors.groupingBy()` para organizar os empréstimos conforme o usuário responsável.

**Como funciona:**
- `.stream()` — Transforma a lista em um fluxo de elementos
- `.collect(Collectors.groupingBy(...))` — Agrupa os elementos por uma chave
- A função de agrupamento extrai o usuário de cada empréstimo usando `usuarioRepo.buscarPorId()`
- `orElseThrow()` — Lança exceção se o usuário não existir

**Retorno:** `Map<Usuario, List<Emprestimo>>`
- **Chaves:** Objetos Usuario
- **Valores:** Listas de empréstimos desse usuário

**Exemplo de uso:**
```java
List<Emprestimo> todos = emprestimoRepo.buscarTodos();
Map<Usuario, List<Emprestimo>> agrupadosPorUsuario = agruparEmprestimosPorUsuario(todos);

for (Usuario usuario : agrupadosPorUsuario.keySet()) {
    List<Emprestimo> emprestimosDoUsuario = agrupadosPorUsuario.get(usuario);
    System.out.println("Usuário: " + usuario.getNome());
    System.out.println("Empréstimos: " + emprestimosDoUsuario.size());
}
```

---

## 3d) Filtrar Empréstimos Atrasados

### Código

```java
public List<Emprestimo> filtrarEmprestimosAtrasados(List<Emprestimo> emprestimos) {
    return emprestimos.stream()
            .filter(Emprestimo::estaAtrasado)
            .collect(Collectors.toList());
}
```

### Explicação

**Conceito:** Utiliza `filter()` para retornar apenas os empréstimos que estão atrasados (não devolvidos e já passaram do prazo).

**Como funciona:**
- `.stream()` — Transforma a lista em um fluxo de elementos
- `.filter(Emprestimo::estaAtrasado)` — Mantém apenas elementos que atendem à condição
  - `Emprestimo::estaAtrasado` é uma referência de método (equivalente a `e -> e.estaAtrasado()`)
  - Retorna true se o empréstimo não foi devolvido E já passou da data prevista
- `.collect(Collectors.toList())` — Coleta os elementos filtrados e retorna uma List

**Retorno:** `List<Emprestimo>`
- Contém apenas empréstimos não devolvidos que já passaram da data de devolução prevista

**Exemplo de uso:**
```java
List<Emprestimo> abertos = emprestimoRepo.buscarAbertos();
List<Emprestimo> atrasados = filtrarEmprestimosAtrasados(abertos);

System.out.println("Total de empréstimos atrasados: " + atrasados.size());
for (Emprestimo emp : atrasados) {
    BigDecimal multa = emp.calcularMulta();
    System.out.println("Empréstimo #" + emp.getId() + " - Multa: R$ " + multa);
}
```

---

## Resumo para Apresentação

### 3c) Agrupar Empréstimos por Usuário
> Utiliza `Collectors.groupingBy()` para organizar os empréstimos conforme o usuário. Cada usuário é uma chave em um Map, e o valor é a lista de seus empréstimos.

### 3d) Filtrar Empréstimos Atrasados
> Utiliza `filter()` para retornar apenas os empréstimos que estão atrasados. A filtragem é feita através do método `estaAtrasado()`, mantendo apenas aqueles que não foram devolvidos e já passaram da data prevista.

---

## Importações Necessárias

```java
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
```

---

## Localização no Código

- **Arquivo:** `src/biblioteca/servico/BibliotecaServico.java`
- **Métodos:** 
  - `agruparEmprestimosPorUsuario()` (por volta da linha 120)
  - `filtrarEmprestimosAtrasados()` (por volta da linha 145)

---
