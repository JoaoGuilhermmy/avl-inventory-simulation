# 🏠 Sistema de Inventário de Eletrodomésticos

<div align="center">

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Data Structures](https://img.shields.io/badge/Data_Structures-AVL%20Tree-orange?style=for-the-badge)
![Algorithm](https://img.shields.io/badge/Algorithm-Self--Balancing-green?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)

**Sistema de gerenciamento de inventário com AVL e simulação de consumo**

[🇺🇸 English Version](README.en.md) | [📖 Sobre](#-sobre-o-projeto) | [🚀 Instalação](#-instalação) | [💡 Funcionalidades](#-funcionalidades)

---

</div>

## 📋 Sobre o Projeto

O **Sistema de Inventário de Eletrodomésticos** é uma aplicação desenvolvida em Java que simula o gerenciamento de estoque de produtos com consumo automático. O sistema utiliza:

- **Árvore AVL**: Para manter produtos ordenados por código com balanceamento automático
- **Simulação de Consumo**: Processa vendas aleatórias até esgotar estoque
- **Sistema de Logs**: Registra todas operações em arquivo
- **Importação CSV**: Carregamento de catálogo

### 🎯 Objetivos

- Demonstrar **Árvore AVL completa em Java**
- Implementar **todas as rotações AVL**
- Criar **sistema de logging robusto**
- Simular **cenário de consumo realista**
- Aplicar **POO com encapsulamento**

### ✨ Características Principais

- 🌳 **AVL Completa** - Balanceamento automático em todas operações
- 📊 **Ordenação por Código** - Busca O(log n) garantida
- 📁 **Importação CSV** - Carregamento de catálogo
- 🔄 **Simulação Automática** - Consumo aleatório de produtos
- 📝 **Sistema de Logs** - Rastreamento completo de operações
- ⚖️ **4 Tipos de Rotação** - Simples e duplas

---

## 🏗️ Arquitetura

### Estrutura do Projeto

```
inventario-eletrodomesticos/
├── src/
│   ├── Eletrodomestico.java    # Classe de domínio
│   ├── NoAVL.java               # Nó da árvore AVL
│   ├── ArvoreAVL.java           # Implementação AVL completa
│   └── Simulador.java           # Orquestração e simulação
├── catalogo.csv                 # Catálogo de produtos
├── log_avl.txt                  # Log de operações (gerado)
└── README.md
```

### 📊 Classes e Responsabilidades

#### Classe `Eletrodomestico`

```java
public class Eletrodomestico {
    private int codigo;
    private String nome;
    private int quantidadeEstoque;
    private double precoUnitario;
    
    public void reduzirEstoque(int quantidade);
    public int getQuantidadeEstoque();
    // ... getters e setters
}
```

**Responsabilidades**:
- Armazenar dados do produto
- Gerenciar estoque
- Fornecer representação textual

#### Classe `NoAVL`

```java
public class NoAVL {
    private Eletrodomestico dado;
    private NoAVL esquerdo;
    private NoAVL direito;
    private int altura;
    
    // ... getters e setters
}
```

**Responsabilidades**:
- Representar nó da árvore
- Armazenar referências aos filhos
- Manter altura para balanceamento

#### Classe `ArvoreAVL`

```java
public class ArvoreAVL {
    private NoAVL raiz;
    private int totalProdutos;
    private FileWriter logWriter;
    
    public void inserir(Eletrodomestico produto);
    public void remover(int codigo);
    public Eletrodomestico buscar(int codigo);
    public void listarEmOrdem();
    private NoAVL rotacaoDireita(NoAVL no);
    private NoAVL rotacaoEsquerda(NoAVL no);
    private NoAVL rotacaoDuplaEsquerdaDireita(NoAVL no);
    private NoAVL rotacaoDuplaDireitaEsquerda(NoAVL no);
}
```

**Responsabilidades**:
- Manter propriedade AVL
- Realizar rotações
- Registrar operações em log
- Gerenciar árvore

#### Classe `Simulador`

```java
public class Simulador {
    private ArvoreAVL arvore;
    private int[] codigos;
    private int totalCodigos;
    private Random random;
    
    public void carregarCatalogo(String nomeArquivo);
    public void simularConsumoAleatorio();
    private void processar(int codigo, int quantidade);
}
```

**Responsabilidades**:
- Carregar catálogo
- Simular consumo
- Orquestrar operações

---

## 💡 Funcionalidades

### 🔄 Fluxo de Simulação

```
┌──────────────────────────┐
│   catalogo.csv           │
│  101;Geladeira;12;4500   │
│  102;SmartTV;8;2999      │
└──────────┬───────────────┘
           │
           ▼
┌──────────────────────────────┐
│   Carregamento e Parse       │
│   - Ler linhas CSV           │
│   - Criar Eletrodoméstico    │
│   - Inserir na AVL           │
└──────────┬───────────────────┘
           │
           ▼
┌──────────────────────────────┐
│   Árvore AVL Construída      │
│                               │
│        [102]                  │
│        /    \                 │
│    [101]    [104]             │
│              /   \            │
│          [103]   [105]        │
└──────────┬───────────────────┘
           │
           ▼
┌──────────────────────────────┐
│   Loop de Simulação          │
│   Enquanto árvore não vazia: │
│   1. Escolher produto        │
│   2. Consumir 1 unidade      │
│   3. Se estoque = 0, remover │
│   4. Aguardar 200ms          │
└──────────┬───────────────────┘
           │
           ▼
┌──────────────────────────────┐
│   Log Completo               │
│   log_avl.txt                │
│   - Inserções                │
│   - Remoções                 │
│   - Alertas                  │
└──────────────────────────────┘
```

### ⚖️ Rotações AVL

#### 1. Rotação Simples à Direita (LL)

```java
private NoAVL rotacaoDireita(NoAVL y) {
    NoAVL x = y.getEsquerdo();
    NoAVL T2 = x.getDireito();
    
    x.setDireito(y);
    y.setEsquerdo(T2);
    
    atualizarAltura(y);
    atualizarAltura(x);
    
    return x;
}
```

**Quando usar**: Fator de balanceamento > 1 e inserção na subárvore esquerda-esquerda.

**Visualização**:
```
    y                    x
   / \                  / \
  x   C    Direita→   A   y
 / \                     / \
A   B                   B   C
```

#### 2. Rotação Simples à Esquerda (RR)

```java
private NoAVL rotacaoEsquerda(NoAVL x) {
    NoAVL y = x.getDireito();
    NoAVL T2 = y.getEsquerdo();
    
    y.setEsquerdo(x);
    x.setDireito(T2);
    
    atualizarAltura(x);
    atualizarAltura(y);
    
    return y;
}
```

**Quando usar**: Fator de balanceamento < -1 e inserção na subárvore direita-direita.

**Visualização**:
```
  x                      y
 / \                    / \
A   y    Esquerda→    x   C
   / \               / \
  B   C             A   B
```

#### 3. Rotação Dupla Esquerda-Direita (LR)

```java
private NoAVL rotacaoDuplaEsquerdaDireita(NoAVL no) {
    no.setEsquerdo(rotacaoEsquerda(no.getEsquerdo()));
    return rotacaoDireita(no);
}
```

**Quando usar**: Fator de balanceamento > 1 e inserção na subárvore esquerda-direita.

**Visualização**:
```
    z                z                  y
   / \              / \                / \
  x   D  1.Esq→    y   D    2.Dir→   x   z
 / \              / \                / \ / \
A   y            x   C              A  B C  D
   / \          / \
  B   C        A   B
```

#### 4. Rotação Dupla Direita-Esquerda (RL)

```java
private NoAVL rotacaoDuplaDireitaEsquerda(NoAVL no) {
    no.setDireito(rotacaoDireita(no.getDireito()));
    return rotacaoEsquerda(no);
}
```

**Quando usar**: Fator de balanceamento < -1 e inserção na subárvore direita-esquerda.

**Visualização**:
```
  x                x                    y
 / \              / \                  / \
A   z  1.Dir→    A   y    2.Esq→     x   z
   / \              / \              / \ / \
  y   D            B   z            A  B C  D
 / \                  / \
B   C                C   D
```

### 🔍 Inserção com Balanceamento

```java
private NoAVL inserirRecursivo(NoAVL no, Eletrodomestico produto) {
    // 1. Inserção BST normal
    if (no == null) {
        return new NoAVL(produto);
    }
    
    if (produto.getCodigo() < no.getDado().getCodigo()) {
        no.setEsquerdo(inserirRecursivo(no.getEsquerdo(), produto));
    } else if (produto.getCodigo() > no.getDado().getCodigo()) {
        no.setDireito(inserirRecursivo(no.getDireito(), produto));
    } else {
        return no; // Código duplicado
    }
    
    // 2. Atualizar altura
    atualizarAltura(no);
    
    // 3. Calcular fator de balanceamento
    int fator = obterFatorBalanceamento(no);
    
    // 4. Aplicar rotações se necessário
    // Caso LL
    if (fator > 1 && produto.getCodigo() < no.getEsquerdo().getDado().getCodigo()) {
        return rotacaoDireita(no);
    }
    
    // Caso RR
    if (fator < -1 && produto.getCodigo() > no.getDireito().getDado().getCodigo()) {
        return rotacaoEsquerda(no);
    }
    
    // Caso LR
    if (fator > 1 && produto.getCodigo() > no.getEsquerdo().getDado().getCodigo()) {
        return rotacaoDuplaEsquerdaDireita(no);
    }
    
    // Caso RL
    if (fator < -1 && produto.getCodigo() < no.getDireito().getDado().getCodigo()) {
        return rotacaoDuplaDireitaEsquerda(no);
    }
    
    return no;
}
```

### 🗑️ Remoção com Balanceamento

```java
private NoAVL removerRecursivo(NoAVL no, int codigo) {
    // 1. Remoção BST normal
    if (no == null) return no;
    
    if (codigo < no.getDado().getCodigo()) {
        no.setEsquerdo(removerRecursivo(no.getEsquerdo(), codigo));
    } else if (codigo > no.getDado().getCodigo()) {
        no.setDireito(removerRecursivo(no.getDireito(), codigo));
    } else {
        // Nó encontrado
        if (no.getEsquerdo() == null || no.getDireito() == null) {
            NoAVL temp = no.getEsquerdo() != null ? no.getEsquerdo() : no.getDireito();
            if (temp == null) {
                no = null;
            } else {
                no = temp;
            }
        } else {
            // Dois filhos: pegar sucessor (menor da direita)
            NoAVL temp = obterMenorNo(no.getDireito());
            no.setDado(temp.getDado());
            no.setDireito(removerRecursivo(no.getDireito(), temp.getDado().getCodigo()));
        }
    }
    
    if (no == null) return no;
    
    // 2. Atualizar altura
    atualizarAltura(no);
    
    // 3. Balancear
    int fator = obterFatorBalanceamento(no);
    
    // Caso LL
    if (fator > 1 && obterFatorBalanceamento(no.getEsquerdo()) >= 0) {
        return rotacaoDireita(no);
    }
    
    // Caso LR
    if (fator > 1 && obterFatorBalanceamento(no.getEsquerdo()) < 0) {
        return rotacaoDuplaEsquerdaDireita(no);
    }
    
    // Caso RR
    if (fator < -1 && obterFatorBalanceamento(no.getDireito()) <= 0) {
        return rotacaoEsquerda(no);
    }
    
    // Caso RL
    if (fator < -1 && obterFatorBalanceamento(no.getDireito()) > 0) {
        return rotacaoDuplaDireitaEsquerda(no);
    }
    
    return no;
}
```

---

## 🚀 Instalação

### Pré-requisitos

- **Java JDK 8+**
- **Sistema Operacional**: Windows, Linux ou macOS

### Compilação e Execução

```bash
# Clone o repositório
git clone https://github.com/JoaoGuilhermmy/inventario-eletrodomesticos-java.git
cd inventario-eletrodomesticos-java

# Compile
javac src/*.java

# Execute
java -cp src Simulador
```

---

## 💻 Como Usar

### Formato do Catálogo (catalogo.csv)

```csv
Código; Nome; Estoque; Preço
101; Geladeira Frost Free; 12; 4500.00
102; Smart TV 50 Polegadas; 8; 2999.50
103; Lava-Louças 12 Serviços; 3; 1900.00
104; Forno Elétrico 60L; 25; 850.90
105; Liquidificador Alta Potência; 1; 350.00
```

**Formato**: `codigo; nome; estoque; preco`

### Exemplo de Execução

```
Catalogo carregado com 5 produtos.

=== Produtos Disponiveis ===
Codigo: 101 | Nome: Geladeira Frost Free | Estoque: 12 | Preco: R$ 4500.00
Codigo: 102 | Nome: Smart TV 50 Polegadas | Estoque: 8 | Preco: R$ 2999.50
Codigo: 103 | Nome: Lava-Louças 12 Serviços | Estoque: 3 | Preco: R$ 1900.00
Codigo: 104 | Nome: Forno Elétrico 60L | Estoque: 25 | Preco: R$ 850.90
Codigo: 105 | Nome: Liquidificador Alta Potência | Estoque: 1 | Preco: R$ 350.00

=== Iniciando Simulacao de Consumo ===

Simulacao encerrada: arvore AVL vazia. Todos os produtos foram consumidos.

=== Simulacao Finalizada ===
```

### Arquivo de Log (log_avl.txt)

```
Inserindo produto 101 - Geladeira Frost Free.
Inserindo produto 102 - Smart TV 50 Polegadas.
Inserindo produto 103 - Lava-Louças 12 Serviços.
Inserindo produto 104 - Forno Elétrico 60L.
Inserindo produto 105 - Liquidificador Alta Potência.
Alerta: Produto 105 fora de estoque.
Produto 105 removido do inventario.
Total de produtos restantes: 4.
Alerta: Produto 103 fora de estoque.
Produto 103 removido do inventario.
Total de produtos restantes: 3.
...
Simulacao encerrada: arvore AVL vazia. Todos os produtos foram consumidos.
```

---

## 🔧 Detalhes Técnicos

### Cálculo do Fator de Balanceamento

```java
private int altura(NoAVL no) {
    return no == null ? 0 : no.getAltura();
}

private int obterFatorBalanceamento(NoAVL no) {
    return no == null ? 0 : altura(no.getEsquerdo()) - altura(no.getDireito());
}

private void atualizarAltura(NoAVL no) {
    if (no != null) {
        int alturaEsq = altura(no.getEsquerdo());
        int alturaDir = altura(no.getDireito());
        no.setAltura(Math.max(alturaEsq, alturaDir) + 1);
    }
}
```

**Interpretação**:
- FB > 1: Desbalanceado à esquerda
- FB < -1: Desbalanceado à direita
- -1 ≤ FB ≤ 1: Balanceado

### Sistema de Logging

```java
private FileWriter logWriter;

public ArvoreAVL() {
    try {
        this.logWriter = new FileWriter("log_avl.txt");
    } catch (IOException e) {
        System.out.println("Erro ao criar arquivo de log");
    }
}

private void registrarLog(String mensagem) {
    System.out.println(mensagem);
    try {
        logWriter.write(mensagem + "\n");
        logWriter.flush();
    } catch (IOException e) {
        System.out.println("Erro ao escrever no log");
    }
}

public void fecharLog() {
    try {
        if (logWriter != null) {
            logWriter.close();
        }
    } catch (IOException e) {
        System.out.println("Erro ao fechar log");
    }
}
```

### Simulação de Consumo

```java
public void simularConsumoAleatorio() {
    System.out.println("\n=== Iniciando Simulacao de Consumo ===\n");
    
    while (!arvore.estaVazia()) {
        // Escolher produto aleatório
        int indice = random.nextInt(totalCodigos);
        int codigo = codigos[indice];
        int quantidadeConsumida = 1;
        
        processar(codigo, quantidadeConsumida);
        
        // Aguardar 200ms
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    arvore.registrarAlerta("Simulacao encerrada: arvore AVL vazia.");
    arvore.fecharLog();
}

private void processar(int codigo, int quantidade) {
    Eletrodomestico produto = arvore.buscar(codigo);
    
    if (produto == null) {
        arvore.registrarAlerta("Alerta: Produto " + codigo + " fora de estoque.");
        return;
    }
    
    if (produto.getQuantidadeEstoque() > quantidade) {
        produto.reduzirEstoque(quantidade);
    } else {
        produto.setQuantidadeEstoque(0);
        arvore.remover(codigo);
    }
}
```

---

## 📊 Complexidade Computacional

| Operação | BST Não Balanceada | AVL | Justificativa |
|----------|-------------------|-----|---------------|
| Inserir | O(n) pior | O(log n) | Balanceamento garante altura O(log n) |
| Remover | O(n) pior | O(log n) | Balanceamento após remoção |
| Buscar | O(n) pior | O(log n) | Altura sempre O(log n) |
| Listar em ordem | O(n) | O(n) | Travessia completa |

### Altura da AVL

```
Altura máxima de uma AVL com n nós:
h ≤ 1.44 × log₂(n + 2) - 1.328

Exemplo:
n = 1000 nós
h ≤ 1.44 × log₂(1002) - 1.328
h ≤ 1.44 × 9.97 - 1.328
h ≤ 12.04

Máximo 12 níveis para 1000 nós
Garantindo O(log n) = O(12) operações
```

---

## 🎓 Conceitos Aplicados

### Árvore AVL vs BST

| Aspecto | BST Simples | AVL |
|---------|-------------|-----|
| Inserção | O(n) pior | O(log n) garantido |
| Balanceamento | Não | Automático |
| Complexidade | Simples | Moderada |
| Uso de memória | Menor | Maior (armazena altura) |
| Ideal para | Dados aleatórios | Dados ordenados ou não |

### Quando Usar AVL

✅ **Use AVL quando**:
- Muitas operações de busca
- Dados podem vir ordenados
- Precisa garantir O(log n)
- Performance previsível é crítica

❌ **Não use AVL quando**:
- Poucas operações (overhead não compensa)
- Dados sempre aleatórios (BST já é boa)
- Memória é muito limitada

---

## 🐛 Solução de Problemas

### Problema: Log não é criado

**Causa**: Permissões de escrita

**Solução**:
```bash
# Linux/macOS
chmod +w .

# Windows: verificar permissões da pasta
```

### Problema: CSV não carrega

**Sintomas**: "Linha invalida ignorada"

**Soluções**:
1. Verificar delimitador (`;`)
2. Remover linhas em branco
3. Verificar tipos de dados

### Problema: Simulação muito rápida/lenta

**Ajustar delay**:
```java
Thread.sleep(200);  // 200ms entre operações
Thread.sleep(1000); // 1s para visualizar melhor
```

---

## 📈 Possíveis Melhorias

### Curto Prazo
- [ ] Interface gráfica mostrando árvore
- [ ] Exportar log em JSON
- [ ] Estatísticas de rotações
- [ ] Testes unitários

### Médio Prazo
- [ ] Visualização animada de rotações
- [ ] Comparação AVL vs BST
- [ ] Persistência em banco
- [ ] API REST

### Longo Prazo
- [ ] Sistema de recomendação
- [ ] Predição de demanda
- [ ] Dashboard analytics
- [ ] Integração com e-commerce

---

## 📄 Licença

Este projeto está sob a licença MIT. Consulte o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 👨‍💻 Autor

**João Guilhermmy**

- 🔗 GitHub: [https://github.com/JoaoGuilhermmy](https://github.com/JoaoGuilhermmy)
- 💼 LinkedIn: [www.linkedin.com/in/joão-guilhermmy-93661b29b](https://www.linkedin.com/in/joão-guilhermmy-93661b29b)
- 📧 Email: joaoguilhermmy2@gmail.com

---

## 🙏 Agradecimentos

- Comunidade Java pelos recursos educacionais
- Desenvolvedores que contribuíram com feedback
- Professores e mentores

---

<div align="center">

### ⭐ Se este projeto foi útil, considere dar uma estrela!

**Desenvolvido com ❤️ e muito ☕**

### 💡 Projeto educacional demonstrando Árvore AVL completa em Java

</div>
