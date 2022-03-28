package projeto.monitoramentoestoque.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Entrada implements Serializable {
    private Calendar data;
    private double quantidade;

    public Entrada(Calendar dataEntrada, double quantidade) {
        this.data = dataEntrada;
        this.quantidade = quantidade;
    }

    public Calendar getData() {
        return data;
    }

    public double getQuantidade() {
        return quantidade;
    }
}
