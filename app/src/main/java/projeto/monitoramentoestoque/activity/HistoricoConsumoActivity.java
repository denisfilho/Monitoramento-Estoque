package projeto.monitoramentoestoque.activity;

import static projeto.monitoramentoestoque.activity.InsumoActivityConstantes.CHAVE_INSUMO;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import projeto.monitoramentoestoque.R;
import projeto.monitoramentoestoque.dao.RoomConsumoDAO;
import projeto.monitoramentoestoque.dao.RoomInsumoDAO;
import projeto.monitoramentoestoque.database.InsumoDatabase;
import projeto.monitoramentoestoque.model.Consumo;
import projeto.monitoramentoestoque.model.Insumo;
import projeto.monitoramentoestoque.recyclerview.adapter.HistoricoConsumoAdapter;
import projeto.monitoramentoestoque.recyclerview.adapter.ListaInsumosAdapter;
import projeto.monitoramentoestoque.recyclerview.adapter.listener.OnItemLongClickListener;

public class HistoricoConsumoActivity extends AppCompatActivity {

    private HistoricoConsumoAdapter adapter;

    private RoomConsumoDAO dao;
    private RoomInsumoDAO daoInsumo;

    private Insumo insumo;

    private ListaInsumosAdapter adapterInsumo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_consumo);

        Intent intent = getIntent();

        if(intent.hasExtra(CHAVE_INSUMO)){
            insumo = (Insumo) intent.getSerializableExtra(CHAVE_INSUMO);
            dao = InsumoDatabase.getInstance(getApplicationContext()).getRoomHistoricoConsumoDAO();

            List<Consumo> historico = pegaHistoricoConsumo();

            configuraRecyclerView(historico);
        }
    }

    private List<Consumo> pegaHistoricoConsumo() {
        List<Consumo> historicoConsumo = dao.buscaHistoricoDeConsumo(insumo.getId());
        return historicoConsumo;
    }

    public void atualizaConsumos(){
        adapter.atualiza(dao.todosConsumos());
    }

    private void configuraRecyclerView(List<Consumo> historicoConsumo) {
        RecyclerView listaHistoricoConsumo = findViewById(R.id.historico_consumo_recyclerview);
        configuraAdapter(historicoConsumo, listaHistoricoConsumo);
        configuraLayoutManager(listaHistoricoConsumo);
    }

    private void configuraAdapter(List<Consumo> historicoConsumo, RecyclerView listaHistoricoConsumo) {
        adapter = new HistoricoConsumoAdapter(this, historicoConsumo, insumo);
        listaHistoricoConsumo.setAdapter(adapter);
        adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void OnItemLongClick(int posicao) {
                daoInsumo = InsumoDatabase.getInstance(getApplicationContext()).getRoomInsumoGeralDAO();
                adapterInsumo = new ListaInsumosAdapter(getApplicationContext(), daoInsumo.todos());

                Consumo consumo = dao.buscaHistoricoDeConsumo(insumo.getId()).get(posicao);

                insumo.setEstoqueAtual(insumo.getEstoqueAtual() + consumo.getQuantidade()); //Aumentando o valor do estoque atual pelo valor do consumo excluída

                remove(consumo, posicao);
                atualizaConsumos();

                daoInsumo.altera(insumo);
                adapterInsumo.atualiza(daoInsumo.todos());
            }
        });
    }

    private void configuraLayoutManager(RecyclerView listaHistoricoConsumo) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listaHistoricoConsumo.setLayoutManager(layoutManager);
    }

    public void remove(Consumo consumo, int posicao){
        dao.removeConsumo(consumo);
        adapter.remove(consumo, posicao);
    }
}