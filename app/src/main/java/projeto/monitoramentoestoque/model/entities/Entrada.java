package projeto.monitoramentoestoque.model.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Calendar;

@Entity(foreignKeys = {@ForeignKey(entity = Insumo.class, parentColumns = "id", childColumns = "insumoId", onDelete = CASCADE)})
public class Entrada implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Long idEntrada;
    @ColumnInfo(name = "ENTRADA_DATA")
    private Calendar data;
    @ColumnInfo(name = "ENTRADA_QTD")
    private double quantidade;

    private Long insumoId;

    public Entrada(Calendar data, double quantidade, Long insumoId) {
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

    public void setData(Calendar data) {
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

    public Calendar getData() {
        return data;
    }

    public double getQuantidade() {
        return quantidade;
    }
}
