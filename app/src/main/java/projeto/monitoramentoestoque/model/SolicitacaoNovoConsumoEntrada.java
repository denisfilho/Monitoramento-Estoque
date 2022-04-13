package projeto.monitoramentoestoque.model;

import java.io.Serializable;

public class SolicitacaoNovoConsumoEntrada implements Serializable {
    private String tipoDeSolicitacao;
    private Insumo insumo;

    public SolicitacaoNovoConsumoEntrada(String tipoDeSolicitacao, Insumo insumo) {
        this.tipoDeSolicitacao = tipoDeSolicitacao;
        this.insumo = insumo;
    }

    public String getTipoDeSolicitacao() {
        return tipoDeSolicitacao;
    }

    public void setTipoDeSolicitacao(String tipoDeSolicitacao) {
        this.tipoDeSolicitacao = tipoDeSolicitacao;
    }

    public Insumo getInsumo() {
        return insumo;
    }

    public void setInsumo(Insumo insumo) {
        this.insumo = insumo;
    }
}
