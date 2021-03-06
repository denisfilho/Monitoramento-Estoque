package projeto.monitoramentoestoque.model.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Calendar;

@Entity(foreignKeys = {@ForeignKey(entity = Insumo.class, parentColumns = "id", childColumns = "insumoId", onDelete = CASCADE)})
public class Consumo implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Long idConsumo;
    @ColumnInfo(name = "CONSUMO_DATA")
    private Calendar data;
    @ColumnInfo(name = "CONSUMO_QTD")
    private double quantidade;

    private final Long insumoId;

    public Consumo(Calendar data, double quantidade, Long insumoId) {
        this.data = data;
        this.quantidade = quantidade;
        this.insumoId = insumoId;
    }

    public Long getIdConsumo() {
        return idConsumo;
    }

    public void setIdConsumo(Long idConsumo) {
        this.idConsumo = idConsumo;
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

    public Calendar getData() {
        return data;
    }

    public double getQuantidade() {
        return quantidade;
    }
}

