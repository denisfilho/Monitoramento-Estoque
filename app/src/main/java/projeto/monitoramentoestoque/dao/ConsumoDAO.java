package projeto.monitoramentoestoque.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import projeto.monitoramentoestoque.model.Consumo;


public class ConsumoDAO {

    private final static ArrayList<Consumo> consumos = new ArrayList<>();

    public List<Consumo> todos(){
        return (List<Consumo>) consumos.clone();
    }

    public void insere (Consumo... consumos){
        ConsumoDAO.consumos.addAll(Arrays.asList((consumos)));
    }

    public void altera(int posicao, Consumo consumo){
        consumos.set(posicao,consumo);
    }

    public void removeTodos(){
        consumos.clear();
    }
}
