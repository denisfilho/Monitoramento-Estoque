package projeto.monitoramentoestoque.activity;

import static projeto.monitoramentoestoque.activity.InsumoActivityConstantes.CHAVE_INSUMO;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import projeto.monitoramentoestoque.R;
import projeto.monitoramentoestoque.dao.RoomEntradaDAO;
import projeto.monitoramentoestoque.dao.RoomInsumoDAO;
import projeto.monitoramentoestoque.database.InsumoDatabase;
import projeto.monitoramentoestoque.model.Entrada;
import projeto.monitoramentoestoque.model.Insumo;
import projeto.monitoramentoestoque.recyclerview.adapter.HistoricoEntradaAdapter;
import projeto.monitoramentoestoque.recyclerview.adapter.ListaInsumosAdapter;
import projeto.monitoramentoestoque.recyclerview.adapter.listener.OnItemLongClickListener;

public class HistoricoEntradaActivity extends AppCompatActivity {

    private HistoricoEntradaAdapter adapter;

    private RoomEntradaDAO dao;
    private RoomInsumoDAO daoInsumo;

    private Insumo insumo;

    private ListaInsumosAdapter adapterInsumo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_entrada);

        Intent intent = getIntent();
        if(intent.hasExtra(CHAVE_INSUMO)){
            insumo = (Insumo) intent.getSerializableExtra(CHAVE_INSUMO);
            dao = InsumoDatabase.getInstance(getApplicationContext()).getRoomHistoricoEntradaDAO();

            List<Entrada> historicoEntrada = pegaHistoricoEntrada();

            configuraRecyclerView(historicoEntrada);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private List<Entrada> pegaHistoricoEntrada() {;

        List<Entrada> historicoEntrada = dao.buscaHistoricoDeEntrada(insumo.getId());
        return historicoEntrada;
    }

    public void atualizaEntradas(){
        adapter.atualiza(dao.todasEntradas());
    }

    private void configuraRecyclerView(List<Entrada> historicoEntrada) {
        RecyclerView listaHistoricoEntrada = findViewById(R.id.historico_entrada_recyclerview);
        configuraAdapter(historicoEntrada, listaHistoricoEntrada);
        configuraLayoutManager(listaHistoricoEntrada);
    }

    private void configuraAdapter(List<Entrada> historicoEntrada, RecyclerView listaHistoricoEntrada) {
        adapter = new HistoricoEntradaAdapter(this, historicoEntrada, insumo);
        listaHistoricoEntrada.setAdapter(adapter);
        adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void OnItemLongClick(int posicao) {
                daoInsumo = InsumoDatabase.getInstance(getApplicationContext()).getRoomInsumoGeralDAO();
                adapterInsumo = new ListaInsumosAdapter(getApplicationContext(), daoInsumo.todos());

                Entrada entrada = dao.buscaHistoricoDeEntrada(insumo.getId()).get(posicao);

                insumo.setEstoqueAtual(insumo.getEstoqueAtual() - entrada.getQuantidade()); //Diminuindo o valor do estoque atual pelo valor da entrada excluída

                remove(entrada, posicao);
                atualizaEntradas();
                daoInsumo.altera(insumo);
                adapterInsumo.atualiza(daoInsumo.todos());
            }
        });
    }

    private void configuraLayoutManager(RecyclerView listaHistoricoEntrada) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listaHistoricoEntrada.setLayoutManager(layoutManager);
    }
    public void remove(Entrada entrada, int posicao){
        dao.remove(entrada);
        adapter.remove(entrada, posicao);
    }
}
