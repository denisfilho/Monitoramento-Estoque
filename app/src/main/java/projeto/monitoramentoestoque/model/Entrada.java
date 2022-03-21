package projeto.monitoramentoestoque.model;

import java.io.Serializable;
import java.util.Date;

public class Entrada implements Serializable {
    private Date data;
    private int quantidade;

    public Date getData() {
        return data;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Entrada(Date dataEntrada, int quantidade) {
        this.data = dataEntrada;
        this.quantidade = quantidade;
    }
}
