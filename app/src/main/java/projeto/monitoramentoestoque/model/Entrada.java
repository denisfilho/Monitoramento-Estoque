package projeto.monitoramentoestoque.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(foreignKeys = {@ForeignKey(entity = Insumo.class, parentColumns = "id", childColumns = "insumoId")})
public class Entrada implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Long idEntrada;
    @ColumnInfo(name = "ENTRADA_DATA")
    private String data;
    @ColumnInfo(name = "ENTRADA_QTD")
    private double quantidade;

    private Long insumoId;

    public Entrada(String data, double quantidade, Long insumoId) {
        this.data = data;
        this.quantidade = quantidade;
        this.insumoId = insumoId;
    }

    public Long getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(Long idEntrada) {
        this.idEntrada = idEntrada;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public Long getInsumoId() {
        return insumoId;
    }

    public void setInsumoId(Long insumoId) {
        this.insumoId = insumoId;
    }

    public String getData() {
        return data;
    }

    public double getQuantidade() {
        return quantidade;
    }
}
