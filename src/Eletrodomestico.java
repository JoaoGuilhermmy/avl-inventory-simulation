public class Eletrodomestico {
    private int codigo;
    private String nome;
    private int quantidadeEstoque;
    private double precoUnitario;

    public Eletrodomestico(int codigo, String nome, int quantidadeEstoque, double precoUnitario) {
        this.codigo = codigo;
        this.nome = nome;
        this.quantidadeEstoque = quantidadeEstoque;
        this.precoUnitario = precoUnitario;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void reduzirEstoque(int quantidade) {
        this.quantidadeEstoque -= quantidade;
    }

    @Override
    public String toString() {
        return String.format("Codigo: %d | Nome: %s | Estoque: %d | Preco: R$ %.2f",
                codigo, nome, quantidadeEstoque, precoUnitario);
    }
}