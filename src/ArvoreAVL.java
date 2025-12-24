import java.io.FileWriter;
import java.io.IOException;

public class ArvoreAVL {
    private NoAVL raiz;
    private int totalProdutos;
    private FileWriter logWriter;

    public ArvoreAVL() {
        this.raiz = null;
        this.totalProdutos = 0;
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

    private int obterAltura(NoAVL no) {
        return no == null ? 0 : no.getAltura();
    }

    private int obterFatorBalanceamento(NoAVL no) {
        return no == null ? 0 : obterAltura(no.getEsquerdo()) - obterAltura(no.getDireito());
    }

    private void atualizarAltura(NoAVL no) {
        if (no != null) {
            int alturaEsq = obterAltura(no.getEsquerdo());
            int alturaDir = obterAltura(no.getDireito());
            no.setAltura(Math.max(alturaEsq, alturaDir) + 1);
        }
    }

    private NoAVL rotacaoDireita(NoAVL y) {
        NoAVL x = y.getEsquerdo();
        NoAVL T2 = x.getDireito();

        x.setDireito(y);
        y.setEsquerdo(T2);

        atualizarAltura(y);
        atualizarAltura(x);

        return x;
    }

    private NoAVL rotacaoEsquerda(NoAVL x) {
        NoAVL y = x.getDireito();
        NoAVL T2 = y.getEsquerdo();

        y.setEsquerdo(x);
        x.setDireito(T2);

        atualizarAltura(x);
        atualizarAltura(y);

        return y;
    }

    private NoAVL rotacaoDuplaEsquerdaDireita(NoAVL no) {
        no.setEsquerdo(rotacaoEsquerda(no.getEsquerdo()));
        return rotacaoDireita(no);
    }

    private NoAVL rotacaoDuplaDireitaEsquerda(NoAVL no) {
        no.setDireito(rotacaoDireita(no.getDireito()));
        return rotacaoEsquerda(no);
    }

    public void inserir(Eletrodomestico produto) {
        raiz = inserirRecursivo(raiz, produto);
        totalProdutos++;
        registrarLog("Inserindo produto " + produto.getCodigo() + " - " + produto.getNome() + ".");
    }

    private NoAVL inserirRecursivo(NoAVL no, Eletrodomestico produto) {
        if (no == null) {
            return new NoAVL(produto);
        }

        if (produto.getCodigo() < no.getDado().getCodigo()) {
            no.setEsquerdo(inserirRecursivo(no.getEsquerdo(), produto));
        } else if (produto.getCodigo() > no.getDado().getCodigo()) {
            no.setDireito(inserirRecursivo(no.getDireito(), produto));
        } else {
            return no;
        }

        atualizarAltura(no);
        int fator = obterFatorBalanceamento(no);

        if (fator > 1 && produto.getCodigo() < no.getEsquerdo().getDado().getCodigo()) {
            return rotacaoDireita(no);
        }

        if (fator < -1 && produto.getCodigo() > no.getDireito().getDado().getCodigo()) {
            return rotacaoEsquerda(no);
        }

        if (fator > 1 && produto.getCodigo() > no.getEsquerdo().getDado().getCodigo()) {
            return rotacaoDuplaEsquerdaDireita(no);
        }

        if (fator < -1 && produto.getCodigo() < no.getDireito().getDado().getCodigo()) {
            return rotacaoDuplaDireitaEsquerda(no);
        }

        return no;
    }

    public void remover(int codigo) {
        raiz = removerRecursivo(raiz, codigo);
        totalProdutos--;
        registrarLog("Produto " + codigo + " removido do inventario.");
        registrarLog("Total de produtos restantes: " + totalProdutos + ".");
    }

    private NoAVL removerRecursivo(NoAVL no, int codigo) {
        if (no == null) {
            return no;
        }

        if (codigo < no.getDado().getCodigo()) {
            no.setEsquerdo(removerRecursivo(no.getEsquerdo(), codigo));
        } else if (codigo > no.getDado().getCodigo()) {
            no.setDireito(removerRecursivo(no.getDireito(), codigo));
        } else {
            if (no.getEsquerdo() == null || no.getDireito() == null) {
                NoAVL temp = no.getEsquerdo() != null ? no.getEsquerdo() : no.getDireito();
                if (temp == null) {
                    no = null;
                } else {
                    no = temp;
                }
            } else {
                NoAVL temp = obterMenorNo(no.getDireito());
                no.setDado(temp.getDado());
                no.setDireito(removerRecursivo(no.getDireito(), temp.getDado().getCodigo()));
            }
        }

        if (no == null) {
            return no;
        }

        atualizarAltura(no);
        int fator = obterFatorBalanceamento(no);

        if (fator > 1 && obterFatorBalanceamento(no.getEsquerdo()) >= 0) {
            return rotacaoDireita(no);
        }

        if (fator > 1 && obterFatorBalanceamento(no.getEsquerdo()) < 0) {
            return rotacaoDuplaEsquerdaDireita(no);
        }

        if (fator < -1 && obterFatorBalanceamento(no.getDireito()) <= 0) {
            return rotacaoEsquerda(no);
        }

        if (fator < -1 && obterFatorBalanceamento(no.getDireito()) > 0) {
            return rotacaoDuplaDireitaEsquerda(no);
        }

        return no;
    }

    private NoAVL obterMenorNo(NoAVL no) {
        NoAVL atual = no;
        while (atual.getEsquerdo() != null) {
            atual = atual.getEsquerdo();
        }
        return atual;
    }

    public Eletrodomestico buscar(int codigo) {
        return buscarRecursivo(raiz, codigo);
    }

    private Eletrodomestico buscarRecursivo(NoAVL no, int codigo) {
        if (no == null) {
            return null;
        }

        if (codigo == no.getDado().getCodigo()) {
            return no.getDado();
        }

        if (codigo < no.getDado().getCodigo()) {
            return buscarRecursivo(no.getEsquerdo(), codigo);
        } else {
            return buscarRecursivo(no.getDireito(), codigo);
        }
    }

    public void listarEmOrdem() {
        System.out.println("\n=== Produtos Disponiveis ===");
        listarEmOrdemRecursivo(raiz);
        System.out.println();
    }

    private void listarEmOrdemRecursivo(NoAVL no) {
        if (no != null) {
            listarEmOrdemRecursivo(no.getEsquerdo());
            System.out.println(no.getDado());
            listarEmOrdemRecursivo(no.getDireito());
        }
    }   

    public boolean estaVazia() {
        return raiz == null;
    }

    public int getTotalProdutos() {
        return totalProdutos;
    }

    public void registrarAlerta(String mensagem) {
        registrarLog(mensagem);
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
}