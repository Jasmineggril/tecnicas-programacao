# Soluções — Questões 3c e 3d

## 📋 Visão Geral

As questões 3c e 3d exploram o uso de **Streams e Collectors** em Java para manipulação de coleções de forma funcional e elegante.

---

## ❓ 3c) Agrupar Empréstimos por Usuário

### 🎯 Objetivo
Organizar todos os empréstimos da biblioteca conforme o usuário responsável, retornando um mapa que facilita consultas por usuário.

### 💡 Solução

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

### 📝 Explicação Detalhada

| Parte | Explicação |
|-------|-----------|
| `.stream()` | Transforma a lista em um fluxo (stream) de elementos |
| `.collect()` | Coleta os elementos do stream e os organiza |
| `Collectors.groupingBy()` | **Agrupador**: organiza os elementos conforme uma chave |
| `emprestimo -> usuarioRepo.buscarPorId(...)` | **Função de agrupamento**: extrai o usuário de cada empréstimo |
| **Retorno** | `Map<Usuario, List<Emprestimo>>` com usuários e suas listas de empréstimos |

### 🔍 Exemplo de Uso

```java
List<Emprestimo> todos = emprestimoRepo.buscarTodos();
Map<Usuario, List<Emprestimo>> agrupadosPorUsuario = 
    agruparEmprestimosPorUsuario(todos);

// Acessando os dados:
for (Usuario usuario : agrupadosPorUsuario.keySet()) {
    List<Emprestimo> emprestimosDoUsuario = agrupadosPorUsuario.get(usuario);
    System.out.println("Usuário: " + usuario.getNome());
    System.out.println("Empréstimos: " + emprestimosDoUsuario.size());
}
```

### ✅ Benefícios

- ✓ Código conciso e legível
- ✓ Sem loops manuais
- ✓ Operação funcional e imutável
- ✓ Fácil de manter e entender

---

## ❓ 3d) Filtrar Empréstimos Atrasados

### 🎯 Objetivo
Retornar apenas os empréstimos que estão com atraso na devolução (não foram devolvidos e já passaram da data prevista).

### 💡 Solução

```java
public List<Emprestimo> filtrarEmprestimosAtrasados(List<Emprestimo> emprestimos) {
    return emprestimos.stream()
            .filter(Emprestimo::estaAtrasado)
            .collect(Collectors.toList());
}
```

### 📝 Explicação Detalhada

| Parte | Explicação |
|-------|-----------|
| `.stream()` | Transforma a lista em um fluxo de elementos |
| `.filter()` | **Filtrador**: mantém apenas elementos que atendem a uma condição |
| `Emprestimo::estaAtrasado` | **Predicate** (referência de método): testa se o empréstimo está atrasado |
| `.collect(Collectors.toList())` | Coleta os elementos filtrados e retorna uma nova lista |
| **Retorno** | `List<Emprestimo>` contendo apenas empréstimos atrasados |

### 🔍 Exemplo de Uso

```java
List<Emprestimo> abertos = emprestimoRepo.buscarAbertos();
List<Emprestimo> atrasados = filtrarEmprestimosAtrasados(abertos);

// Exibindo os atrasados:
System.out.println("Total de empréstimos atrasados: " + atrasados.size());
for (Emprestimo emp : atrasados) {
    BigDecimal multa = emp.calcularMulta();
    System.out.println("Empréstimo #" + emp.getId() + " - Multa: R$ " + multa);
}
```

### ✅ Benefícios

- ✓ Código muito conciso (3 linhas!)
- ✓ Sem declaração de loop ou variáveis intermediárias
- ✓ Operação de filtragem declarativa
- ✓ Reutilizável para outras listas

---

## 📊 Comparação: Antes vs Depois

### ❌ Abordagem Tradicional (com loops)

```java
// 3c) Agrupamento manual
public Map<Usuario, List<Emprestimo>> agruparEmprestimosPorUsuario(List<Emprestimo> emprestimos) {
    Map<Usuario, List<Emprestimo>> resultado = new HashMap<>();
    
    for (Emprestimo emp : emprestimos) {
        Usuario usuario = usuarioRepo.buscarPorId(emp.getUsuarioId()).orElse(null);
        if (usuario != null) {
            resultado.computeIfAbsent(usuario, k -> new ArrayList<>()).add(emp);
        }
    }
    return resultado;
}

// 3d) Filtragem manual
public List<Emprestimo> filtrarEmprestimosAtrasados(List<Emprestimo> emprestimos) {
    List<Emprestimo> resultado = new ArrayList<>();
    
    for (Emprestimo emp : emprestimos) {
        if (emp.estaAtrasado()) {
            resultado.add(emp);
        }
    }
    return resultado;
}
```

### ✅ Abordagem Funcional (com Streams)

```java
// 3c) Agrupamento com Stream
public Map<Usuario, List<Emprestimo>> agruparEmprestimosPorUsuario(List<Emprestimo> emprestimos) {
    return emprestimos.stream()
            .collect(Collectors.groupingBy(e -> 
                usuarioRepo.buscarPorId(e.getUsuarioId()).orElseThrow(...)));
}

// 3d) Filtragem com Stream
public List<Emprestimo> filtrarEmprestimosAtrasados(List<Emprestimo> emprestimos) {
    return emprestimos.stream()
            .filter(Emprestimo::estaAtrasado)
            .collect(Collectors.toList());
}
```

---

## 🎓 Conceitos-Chave Aplicados

### 1️⃣ Stream API
- Processamento funcional de coleções
- Operações encadeadas (pipeline)
- Lazy evaluation (avaliação preguiçosa)

### 2️⃣ Collectors
- `groupingBy()`: agrupa elementos por uma chave
- `toList()`: coleta elementos em uma List

### 3️⃣ Filter
- Operação intermediária
- Retorna um novo stream com elementos filtrados
- Utiliza um **predicado** (condição booleana)

### 4️⃣ Method References
- `Emprestimo::estaAtrasado` substitui `e -> e.estaAtrasado()`
- Sintaxe mais limpa e legível

---

## 🚀 Vantagens da Solução Proposta

| Aspecto | Benefício |
|--------|----------|
| **Clareza** | Código legível e fácil de entender |
| **Concisão** | Menos linhas de código |
| **Manutenibilidade** | Fácil de modificar e estender |
| **Performance** | Lazy evaluation (processamento sob demanda) |
| **Segurança** | Reduz erros de loop manual |
| **Testabilidade** | Métodos pequenos e focados |

---

## 📌 Resumo para Apresentação

### **3c) Agrupar Empréstimos por Usuário**
> Utiliza `Collectors.groupingBy()` para organizar os empréstimos conforme o usuário. Cada usuário é uma chave em um Map, e o valor é a lista de seus empréstimos.

### **3d) Filtrar Empréstimos Atrasados**
> Utiliza `filter()` para retornar apenas os empréstimos que estão atrasados. A filtragem é feita através do método `estaAtrasado()`, mantendo apenas aqueles que não foram devolvidos e já passaram da data prevista.

---

## 🔗 Localização no Código

- **Arquivo**: [src/biblioteca/servico/BibliotecaServico.java](src/biblioteca/servico/BibliotecaServico.java)
- **Métodos implementados**:
  - `agruparEmprestimosPorUsuario()` (linhas ~120-135)
  - `filtrarEmprestimosAtrasados()` (linhas ~145-160)

---

