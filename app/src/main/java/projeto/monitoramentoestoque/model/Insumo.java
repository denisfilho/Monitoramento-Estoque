package projeto.monitoramentoestoque.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

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
    private String dataUltimaAtualizacao;

    public Insumo(String nome, String unidade, double estoqueAtual, String dataUltimaAtualizacao) {
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

    public String getDataUltimaAtualizacao() {
        return dataUltimaAtualizacao;
    }

    public void setEstoqueAtual(double estoqueAtual) {
        this.estoqueAtual = estoqueAtual;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Insumo{" +
                "id=" + id +
                ", unidade='" + unidade + '\'' +
                ", nome='" + nome + '\'' +
                ", estoqueAtual=" + estoqueAtual +
                ", dataUltimaAtualizacao=" + dataUltimaAtualizacao +
                '}';
    }
}
