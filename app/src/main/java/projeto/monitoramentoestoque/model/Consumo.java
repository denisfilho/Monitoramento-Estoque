package projeto.monitoramentoestoque.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Consumo implements Serializable {

    private Calendar data;
    private double quantidade;

    public Consumo(Calendar data, double quantidade) {
        this.data = data;
        this.quantidade = quantidade;
    }

    public Calendar getData() {
        return data;
    }

    public double getQuantidade() {
        return quantidade;
    }
}

