import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Simulador {
    private ArvoreAVL arvore;
    private int[] codigos;
    private int totalCodigos;
    private Random random;

    public Simulador() {
        this.arvore = new ArvoreAVL();
        this.totalCodigos = 0;
        this.random = new Random();
    }

    public void carregarCatalogo(String nomeArquivo) {
        int linhas = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            while (br.readLine() != null) {
                linhas++;
            }
        } catch (IOException e) {
            System.out.println("Erro ao contar linhas: " + e.getMessage());
            return;
        }

        this.codigos = new int[linhas];

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha = br.readLine();

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length >= 4) {
                    try {
                        int codigo = Integer.parseInt(dados[0].trim());
                        String nome = dados[1].trim();
                        int estoque = Integer.parseInt(dados[2].trim());
                        double preco = Double.parseDouble(dados[3].trim());

                        if (estoque > 0) {
                            Eletrodomestico produto = new Eletrodomestico(codigo, nome, estoque, preco);
                            arvore.inserir(produto);
                            codigos[totalCodigos++] = codigo;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Linha invalida ignorada.");
                    }
                }
            }
            System.out.println("Catalogo carregado com " + totalCodigos + " produtos.");
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }
    }

    public void simularConsumoAleatorio() {
        System.out.println("\n=== Iniciando Simulacao de Consumo ===\n");

        while (!arvore.estaVazia()) {
            int indice = random.nextInt(totalCodigos);
            int codigo = codigos[indice];
            int quantidadeConsumida = 1;

            processar(codigo, quantidadeConsumida);

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        arvore.registrarAlerta("Simulacao encerrada: arvore AVL vazia. Todos os produtos foram consumidos.");
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

    public void listarProdutos() {
        arvore.listarEmOrdem();
    }

    public static void main(String[] args) {
        Simulador simulador = new Simulador();

        simulador.carregarCatalogo("catalogo.csv");

        simulador.listarProdutos();

        simulador.simularConsumoAleatorio();

        System.out.println("\n=== Simulacao Finalizada ===");
    }
}