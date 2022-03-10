package projeto.monitoramentoestoque.model;

import java.io.Serializable;

public class Insumo implements Serializable {

    private final String nome;
    private int estoqueAtual;

    public Insumo(String nome, int estoqueAtual) {
        this.nome = nome;
        this.estoqueAtual = estoqueAtual;
    }

    public String getNome() {
        return nome;
    }

    public int getEstoqueAtual() {
        return estoqueAtual;
    }

}
