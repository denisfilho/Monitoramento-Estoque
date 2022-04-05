package projeto.monitoramentoestoque.model;

import java.io.Serializable;
import java.util.Calendar;


import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name="INSUMO")
public class Insumo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable = false)
    private Long id;

    @Column(name = "UNIDADE", nullable = false)
    private String unidade;

    @Column(name = "NOME", nullable = false)
    private final String nome;

    @Column(name = "ESTOQUE_ATUAL", nullable = false)
    private double estoqueAtual;

    @Column(name = "ULTIMA_ATT", nullable = false)
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
