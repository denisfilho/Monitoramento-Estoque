package projeto.monitoramentoestoque.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import projeto.monitoramentoestoque.model.Entrada;

public class EntradaDAO {
    private final static ArrayList<Entrada> entradas = new ArrayList<>();

    public List<Entrada> todos(){
        return (List<Entrada>) entradas.clone();
    }

    public void insere (Entrada... entradas){
        EntradaDAO.entradas.addAll(Arrays.asList((entradas)));
    }

    public void altera(int posicao, Entrada entrada){
        entradas.set(posicao,entrada);
    }

    public void removeTodos(){
        entradas.clear();
    }

}
