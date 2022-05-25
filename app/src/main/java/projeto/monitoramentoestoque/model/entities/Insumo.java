package projeto.monitoramentoestoque.model.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Calendar;

@Entity
public class Insumo implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "INSUMO_UNIDADE")
    private String unidade;

    @ColumnInfo(name = "INSUMO_NOME")
    private String nome;

    @ColumnInfo(name = "INSUMO_ESOTQUE_ATUAL")
    private double estoqueAtual;

    @ColumnInfo(name = "INSUMO_ULTIMA_ATT")
    private Calendar dataUltimaAtualizacao;

    public Insumo(String nome, String unidade, double estoqueAtual, Calendar dataUltimaAtualizacao) {
        this.nome = nome;
        this.unidade = unidade;
        this.estoqueAtual = estoqueAtual;
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
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

    public Long getId() {
        return id;
    }

    public void setEstoqueAtual(double estoqueAtual) {
        this.estoqueAtual = estoqueAtual;
    }

    public void setDataUltimaAtualizacao(Calendar dataUltimaAtualizacao) {
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
