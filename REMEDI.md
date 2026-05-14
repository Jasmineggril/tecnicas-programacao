# Respostas — Questões 3c e 3d para Remedi

---

## 3c) Top 5 Livros Mais Emprestados

### Código

```java
public List<Livro> top5LivrosMaisEmprestados() {
    return emprestimoRepo.buscarTodos().stream()
            .collect(Collectors.groupingBy(
                    Emprestimo::getLivroId,
                    Collectors.counting()
            ))
            .entrySet()
            .stream()
            .sorted(Map.Entry.<Long, Long>comparingByValue(Comparator.reverseOrder()))
            .limit(5)
            .map(entry -> livroRepo.buscarPorId(entry.getKey()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
}
```

### Explicação

**Conceito:** Conta quantas vezes cada livro foi emprestado, ordena do mais emprestado para o menos e retorna os 5 primeiros.

**Como funciona:**
1. `.stream()` — Transforma a lista em um fluxo de empréstimos
2. `.collect(Collectors.groupingBy(..., Collectors.counting()))` — Agrupa por livroId e conta as ocorrências
   - Retorna: `Map<Long, Long>` (livroId → quantidade)
3. `.entrySet().stream()` — Transforma o Map em um stream de entradas
4. `.sorted(Map.Entry.<Long, Long>comparingByValue(Comparator.reverseOrder()))` — Ordena decrescente por quantidade
5. `.limit(5)` — Pega apenas os 5 primeiros
6. `.map(entry -> livroRepo.buscarPorId(entry.getKey()))` — Busca o objeto Livro pelo ID
7. `.filter(Optional::isPresent).map(Optional::get)` — Remove opcionais vazios
8. `.collect(Collectors.toList())` — Coleta em uma lista

**Retorno:** `List<Livro>` com até 5 livros mais emprestados (ordenado descendente)

---

## 3d) Multas Pendentes por Usuário

### Código

```java
public Map<Long, BigDecimal> multasPendentesPorUsuario() {
    return emprestimoRepo.buscarTodos().stream()
            .filter(Emprestimo::estaAtrasado)
            .collect(Collectors.toMap(
                    Emprestimo::getUsuarioId,
                    Emprestimo::calcularMulta,
                    BigDecimal::add
            ));
}
```

### Explicação

**Conceito:** Filtra somente os empréstimos atrasados e soma o valor das multas de cada usuário usando `BigDecimal::add`.

**Como funciona:**
1. `.stream()` — Transforma a lista em um fluxo de empréstimos
2. `.filter(Emprestimo::estaAtrasado)` — Mantém apenas empréstimos atrasados
3. `.collect(Collectors.toMap(...))` — Coleta em um Map com:
   - **Chave:** `Emprestimo::getUsuarioId` (ID do usuário)
   - **Valor:** `Emprestimo::calcularMulta()` (multa do empréstimo)
   - **Merge:** `BigDecimal::add` — Quando o mesmo usuário aparece várias vezes, soma as multas

**Retorno:** `Map<Long, BigDecimal>` (usuarioId → soma total de multas)

---

## Resumo para Apresentação

### 3c) Top 5 Livros Mais Emprestados
> Conta quantas vezes cada livro foi emprestado, ordena do mais emprestado para o menos e retorna os 5 primeiros.

### 3d) Multas Pendentes por Usuário
> Filtra somente os empréstimos atrasados e soma o valor das multas de cada usuário usando `BigDecimal::add`.

---

## Importações Necessárias

```java
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
```

---

## Localização no Código

- **Arquivo:** `src/biblioteca/servico/RelatorioServico.java`
- **Métodos:** 
  - `top5LivrosMaisEmprestados()` (por volta da linha 45)
  - `multasPendentesPorUsuario()` (por volta da linha 85)

---
