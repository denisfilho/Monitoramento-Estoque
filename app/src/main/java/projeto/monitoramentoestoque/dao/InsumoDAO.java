package projeto.monitoramentoestoque.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import projeto.monitoramentoestoque.model.Insumo;

public class InsumoDAO {

    private final static ArrayList<Insumo> insumos = new ArrayList<>();

    public List<Insumo> todos(){
        return (List<Insumo>) insumos.clone();
    }

    public void insere (Insumo... insumos){
        InsumoDAO.insumos.addAll(Arrays.asList((insumos)));
    }

    public void altera(int posicao, Insumo insumo){
        insumos.set(posicao,insumo);
    }

    public void removeTodos(){
        insumos.clear();
    }
}
