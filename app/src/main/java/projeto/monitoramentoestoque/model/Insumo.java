package projeto.monitoramentoestoque.model;

import java.io.Serializable;
import java.util.Calendar;

public class Insumo implements Serializable {

    private String unidade;
    private final String nome;
    private double estoqueAtual;
    private Calendar dataUltimaAtualizacao;

    public Insumo(String nome, String unidade, double estoqueAtual, Calendar data) {
        this.nome = nome;
        this.unidade = unidade;
        this.estoqueAtual = estoqueAtual;
        this.dataUltimaAtualizacao = data;

    }

    public String getNome() {
        return nome;
    }

    public double getEstoqueAtual() {
        return estoqueAtual;
    }

    public String getUnidade() {
        return unidade;
    }

    public Calendar getDataUltimaAtualizacao() {
        return dataUltimaAtualizacao;
    }

    public void setEstoqueAtual(double estoqueAtual) {
        this.estoqueAtual = estoqueAtual;
    }

    public void setDataUltimaAtualizacao(Calendar data) {
        this.dataUltimaAtualizacao = data;
    }
}
