package projeto.monitoramentoestoque.model;

import java.util.Date;

public class Consumo {

    private Date data;
    private int quantidade;

    public Consumo(Date data, int quantidade) {
        this.data = data;
        this.quantidade = quantidade;
    }

    public Date getData() {
        return data;
    }

    public int getQuantidade() {
        return quantidade;
    }
}

