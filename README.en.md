# 🏠 Appliances Inventory System

<div align="center">

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Data Structures](https://img.shields.io/badge/Data_Structures-AVL%20Tree-orange?style=for-the-badge)
![Algorithm](https://img.shields.io/badge/Algorithm-Self--Balancing-green?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)

**Inventory management system with AVL and consumption simulation**

[🇧🇷 Versão em Português](README.md) | [📖 About](#-about-the-project) | [🚀 Installation](#-installation) | [💡 Features](#-features)

---

</div>

## 📋 About the Project

The **Appliances Inventory System** is an application developed in Java that simulates product inventory management with automatic consumption. The system uses:

- **AVL Tree**: To keep products sorted by code with automatic balancing
- **Consumption Simulation**: Processes random sales until stock is exhausted
- **Logging System**: Records all operations to file
- **CSV Import**: Catalog loading

### 🎯 Objectives

- Demonstrate **complete AVL Tree in Java**
- Implement **all AVL rotations**
- Create **robust logging system**
- Simulate **realistic consumption scenario**
- Apply **OOP with encapsulation**

### ✨ Key Features

- 🌳 **Complete AVL** - Automatic balancing in all operations
- 📊 **Sorting by Code** - Guaranteed O(log n) search
- 📁 **CSV Import** - Catalog loading
- 🔄 **Automatic Simulation** - Random product consumption
- 📝 **Logging System** - Complete operation tracking
- ⚖️ **4 Rotation Types** - Simple and double

---

## 🏗️ Architecture

### Project Structure

```
appliances-inventory/
├── src/
│   ├── Eletrodomestico.java    # Domain class
│   ├── NoAVL.java               # AVL tree node
│   ├── ArvoreAVL.java           # Complete AVL implementation
│   └── Simulador.java           # Orchestration and simulation
├── catalogo.csv                 # Product catalog
├── log_avl.txt                  # Operation log (generated)
└── README.md
```

### 📊 Classes and Responsibilities

#### `Eletrodomestico` Class

```java
public class Eletrodomestico {
    private int codigo;
    private String nome;
    private int quantidadeEstoque;
    private double precoUnitario;
    
    public void reduzirEstoque(int quantidade);
    public int getQuantidadeEstoque();
    // ... getters and setters
}
```

**Responsibilities**:
- Store product data
- Manage stock
- Provide textual representation

#### `NoAVL` Class

```java
public class NoAVL {
    private Eletrodomestico dado;
    private NoAVL esquerdo;
    private NoAVL direito;
    private int altura;
    
    // ... getters and setters
}
```

**Responsibilities**:
- Represent tree node
- Store child references
- Maintain height for balancing

#### `ArvoreAVL` Class

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

**Responsibilities**:
- Maintain AVL property
- Perform rotations
- Log operations to file
- Manage tree

#### `Simulador` Class

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

**Responsibilities**:
- Load catalog
- Simulate consumption
- Orchestrate operations

---

## 💡 Features

### 🔄 Simulation Flow

```
┌──────────────────────────┐
│   catalogo.csv           │
│  101;Refrigerator;12;4500│
│  102;SmartTV;8;2999      │
└──────────┬───────────────┘
           │
           ▼
┌──────────────────────────────┐
│   Loading and Parse          │
│   - Read CSV lines           │
│   - Create Eletrodomestico   │
│   - Insert into AVL          │
└──────────┬───────────────────┘
           │
           ▼
┌──────────────────────────────┐
│   AVL Tree Built             │
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
│   Simulation Loop            │
│   While tree not empty:      │
│   1. Choose product          │
│   2. Consume 1 unit          │
│   3. If stock = 0, remove    │
│   4. Wait 200ms              │
└──────────┬───────────────────┘
           │
           ▼
┌──────────────────────────────┐
│   Complete Log               │
│   log_avl.txt                │
│   - Insertions               │
│   - Removals                 │
│   - Alerts                   │
└──────────────────────────────┘
```

### ⚖️ AVL Rotations

#### 1. Simple Right Rotation (LL)

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

**When to use**: Balance factor > 1 and insertion in left-left subtree.

**Visualization**:
```
    y                    x
   / \                  / \
  x   C    Right→      A   y
 / \                      / \
A   B                    B   C
```

#### 2. Simple Left Rotation (RR)

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

**When to use**: Balance factor < -1 and insertion in right-right subtree.

**Visualization**:
```
  x                      y
 / \                    / \
A   y    Left→         x   C
   / \                / \
  B   C              A   B
```

#### 3. Double Left-Right Rotation (LR)

```java
private NoAVL rotacaoDuplaEsquerdaDireita(NoAVL no) {
    no.setEsquerdo(rotacaoEsquerda(no.getEsquerdo()));
    return rotacaoDireita(no);
}
```

**When to use**: Balance factor > 1 and insertion in left-right subtree.

**Visualization**:
```
    z                z                  y
   / \              / \                / \
  x   D  1.Left→   y   D    2.Right→ x   z
 / \              / \                / \ / \
A   y            x   C              A  B C  D
   / \          / \
  B   C        A   B
```

#### 4. Double Right-Left Rotation (RL)

```java
private NoAVL rotacaoDuplaDireitaEsquerda(NoAVL no) {
    no.setDireito(rotacaoDireita(no.getDireito()));
    return rotacaoEsquerda(no);
}
```

**When to use**: Balance factor < -1 and insertion in right-left subtree.

**Visualization**:
```
  x                x                    y
 / \              / \                  / \
A   z  1.Right→  A   y    2.Left→    x   z
   / \              / \              / \ / \
  y   D            B   z            A  B C  D
 / \                  / \
B   C                C   D
```

### 🔍 Insertion with Balancing

```java
private NoAVL inserirRecursivo(NoAVL no, Eletrodomestico produto) {
    // 1. Normal BST insertion
    if (no == null) {
        return new NoAVL(produto);
    }
    
    if (produto.getCodigo() < no.getDado().getCodigo()) {
        no.setEsquerdo(inserirRecursivo(no.getEsquerdo(), produto));
    } else if (produto.getCodigo() > no.getDado().getCodigo()) {
        no.setDireito(inserirRecursivo(no.getDireito(), produto));
    } else {
        return no; // Duplicate code
    }
    
    // 2. Update height
    atualizarAltura(no);
    
    // 3. Calculate balance factor
    int fator = obterFatorBalanceamento(no);
    
    // 4. Apply rotations if necessary
    // LL Case
    if (fator > 1 && produto.getCodigo() < no.getEsquerdo().getDado().getCodigo()) {
        return rotacaoDireita(no);
    }
    
    // RR Case
    if (fator < -1 && produto.getCodigo() > no.getDireito().getDado().getCodigo()) {
        return rotacaoEsquerda(no);
    }
    
    // LR Case
    if (fator > 1 && produto.getCodigo() > no.getEsquerdo().getDado().getCodigo()) {
        return rotacaoDuplaEsquerdaDireita(no);
    }
    
    // RL Case
    if (fator < -1 && produto.getCodigo() < no.getDireito().getDado().getCodigo()) {
        return rotacaoDuplaDireitaEsquerda(no);
    }
    
    return no;
}
```

### 🗑️ Removal with Balancing

```java
private NoAVL removerRecursivo(NoAVL no, int codigo) {
    // 1. Normal BST removal
    if (no == null) return no;
    
    if (codigo < no.getDado().getCodigo()) {
        no.setEsquerdo(removerRecursivo(no.getEsquerdo(), codigo));
    } else if (codigo > no.getDado().getCodigo()) {
        no.setDireito(removerRecursivo(no.getDireito(), codigo));
    } else {
        // Node found
        if (no.getEsquerdo() == null || no.getDireito() == null) {
            NoAVL temp = no.getEsquerdo() != null ? no.getEsquerdo() : no.getDireito();
            if (temp == null) {
                no = null;
            } else {
                no = temp;
            }
        } else {
            // Two children: get successor (smallest from right)
            NoAVL temp = obterMenorNo(no.getDireito());
            no.setDado(temp.getDado());
            no.setDireito(removerRecursivo(no.getDireito(), temp.getDado().getCodigo()));
        }
    }
    
    if (no == null) return no;
    
    // 2. Update height
    atualizarAltura(no);
    
    // 3. Balance
    int fator = obterFatorBalanceamento(no);
    
    // LL Case
    if (fator > 1 && obterFatorBalanceamento(no.getEsquerdo()) >= 0) {
        return rotacaoDireita(no);
    }
    
    // LR Case
    if (fator > 1 && obterFatorBalanceamento(no.getEsquerdo()) < 0) {
        return rotacaoDuplaEsquerdaDireita(no);
    }
    
    // RR Case
    if (fator < -1 && obterFatorBalanceamento(no.getDireito()) <= 0) {
        return rotacaoEsquerda(no);
    }
    
    // RL Case
    if (fator < -1 && obterFatorBalanceamento(no.getDireito()) > 0) {
        return rotacaoDuplaDireitaEsquerda(no);
    }
    
    return no;
}
```

---

## 🚀 Installation

### Prerequisites

- **Java JDK 8+**
- **Operating System**: Windows, Linux or macOS

### Compilation and Execution

```bash
# Clone the repository
git clone https://github.com/JoaoGuilhermmy/appliances-inventory-java.git
cd appliances-inventory-java

# Compile
javac src/*.java

# Run
java -cp src Simulador
```

---

## 💻 How to Use

### Catalog Format (catalogo.csv)

```csv
Code; Name; Stock; Price
101; Frost Free Refrigerator; 12; 4500.00
102; 50 Inch Smart TV; 8; 2999.50
103; 12 Service Dishwasher; 3; 1900.00
104; 60L Electric Oven; 25; 850.90
105; High Power Blender; 1; 350.00
```

**Format**: `code; name; stock; price`

### Execution Example

```
Catalog loaded with 5 products.

=== Available Products ===
Code: 101 | Name: Frost Free Refrigerator | Stock: 12 | Price: $ 4500.00
Code: 102 | Name: 50 Inch Smart TV | Stock: 8 | Price: $ 2999.50
Code: 103 | Name: 12 Service Dishwasher | Stock: 3 | Price: $ 1900.00
Code: 104 | Name: 60L Electric Oven | Stock: 25 | Price: $ 850.90
Code: 105 | Name: High Power Blender | Stock: 1 | Price: $ 350.00

=== Starting Consumption Simulation ===

Simulation ended: AVL tree empty. All products were consumed.

=== Simulation Finished ===
```

### Log File (log_avl.txt)

```
Inserting product 101 - Frost Free Refrigerator.
Inserting product 102 - 50 Inch Smart TV.
Inserting product 103 - 12 Service Dishwasher.
Inserting product 104 - 60L Electric Oven.
Inserting product 105 - High Power Blender.
Alert: Product 105 out of stock.
Product 105 removed from inventory.
Remaining products: 4.
Alert: Product 103 out of stock.
Product 103 removed from inventory.
Remaining products: 3.
...
Simulation ended: AVL tree empty. All products were consumed.
```

---

## 🔧 Technical Details

### Balance Factor Calculation

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

**Interpretation**:
- BF > 1: Unbalanced to the left
- BF < -1: Unbalanced to the right
- -1 ≤ BF ≤ 1: Balanced

### Logging System

```java
private FileWriter logWriter;

public ArvoreAVL() {
    try {
        this.logWriter = new FileWriter("log_avl.txt");
    } catch (IOException e) {
        System.out.println("Error creating log file");
    }
}

private void registrarLog(String mensagem) {
    System.out.println(mensagem);
    try {
        logWriter.write(mensagem + "\n");
        logWriter.flush();
    } catch (IOException e) {
        System.out.println("Error writing to log");
    }
}

public void fecharLog() {
    try {
        if (logWriter != null) {
            logWriter.close();
        }
    } catch (IOException e) {
        System.out.println("Error closing log");
    }
}
```

### Consumption Simulation

```java
public void simularConsumoAleatorio() {
    System.out.println("\n=== Starting Consumption Simulation ===\n");
    
    while (!arvore.estaVazia()) {
        // Choose random product
        int indice = random.nextInt(totalCodigos);
        int codigo = codigos[indice];
        int quantidadeConsumida = 1;
        
        processar(codigo, quantidadeConsumida);
        
        // Wait 200ms
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    arvore.registrarAlerta("Simulation ended: AVL tree empty.");
    arvore.fecharLog();
}

private void processar(int codigo, int quantidade) {
    Eletrodomestico produto = arvore.buscar(codigo);
    
    if (produto == null) {
        arvore.registrarAlerta("Alert: Product " + codigo + " out of stock.");
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

## 📊 Computational Complexity

| Operation | Unbalanced BST | AVL | Justification |
|-----------|----------------|-----|---------------|
| Insert | O(n) worst | O(log n) | Balancing guarantees O(log n) height |
| Remove | O(n) worst | O(log n) | Balancing after removal |
| Search | O(n) worst | O(log n) | Height always O(log n) |
| List in order | O(n) | O(n) | Complete traversal |

### AVL Height

```
Maximum height of an AVL with n nodes:
h ≤ 1.44 × log₂(n + 2) - 1.328

Example:
n = 1000 nodes
h ≤ 1.44 × log₂(1002) - 1.328
h ≤ 1.44 × 9.97 - 1.328
h ≤ 12.04

Maximum 12 levels for 1000 nodes
Guaranteeing O(log n) = O(12) operations
```

---

## 🎓 Applied Concepts

### AVL vs BST

| Aspect | Simple BST | AVL |
|--------|------------|-----|
| Insertion | O(n) worst | O(log n) guaranteed |
| Balancing | No | Automatic |
| Complexity | Simple | Moderate |
| Memory usage | Less | More (stores height) |
| Ideal for | Random data | Sorted or not data |

### When to Use AVL

✅ **Use AVL when**:
- Many search operations
- Data may come sorted
- Need to guarantee O(log n)
- Predictable performance is critical

❌ **Don't use AVL when**:
- Few operations (overhead doesn't pay off)
- Data always random (BST is already good)
- Memory is very limited

---

## 🐛 Troubleshooting

### Problem: Log not created

**Cause**: Write permissions

**Solution**:
```bash
# Linux/macOS
chmod +w .

# Windows: check folder permissions
```

### Problem: CSV doesn't load

**Symptoms**: "Invalid line ignored"

**Solutions**:
1. Check delimiter (`;`)
2. Remove blank lines
3. Check data types

### Problem: Simulation too fast/slow

**Adjust delay**:
```java
Thread.sleep(200);  // 200ms between operations
Thread.sleep(1000); // 1s to visualize better
```

---

## 📈 Possible Improvements

### Short Term
- [ ] GUI showing tree
- [ ] Export log to JSON
- [ ] Rotation statistics
- [ ] Unit tests

### Medium Term
- [ ] Animated rotation visualization
- [ ] AVL vs BST comparison
- [ ] Database persistence
- [ ] REST API

### Long Term
- [ ] Recommendation system
- [ ] Demand prediction
- [ ] Analytics dashboard
- [ ] E-commerce integration

---

## 📄 License

This project is under the MIT license. See the [LICENSE](LICENSE) file for more details.

---

## 👨‍💻 Author

**João Guilhermmy**

- 🔗 GitHub: [https://github.com/JoaoGuilhermmy](https://github.com/JoaoGuilhermmy)
- 💼 LinkedIn: [www.linkedin.com/in/joão-guilhermmy-93661b29b](https://www.linkedin.com/in/joão-guilhermmy-93661b29b)
- 📧 Email: joaoguilhermmy2@gmail.com

---

## 🙏 Acknowledgments

- Java community for educational resources
- Developers who contributed with feedback
- Professors and mentors

---

<div align="center">

### ⭐ If this project was useful, consider giving it a star!

**Developed with ❤️ and lots of ☕**

### 💡 Educational project demonstrating complete AVL Tree in Java

</div>
