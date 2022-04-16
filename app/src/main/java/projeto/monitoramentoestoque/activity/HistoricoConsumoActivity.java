package projeto.monitoramentoestoque.activity;

import static projeto.monitoramentoestoque.activity.InsumoActivityConstantes.CHAVE_INSUMO;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

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

    public static final String MENSAGEM_CONSUMO_REMOVIDO = "Consumo Removido!";
    public static final String TITULO_APPBAR = "Histórico de Consumo";

    private HistoricoConsumoAdapter historicoConsumoAdapter;
    private ListaInsumosAdapter listaInsumosAdapter;

    private RoomConsumoDAO historicoConsumoDAO;
    private RoomInsumoDAO listaInsumosDAO;

    private Insumo insumo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_consumo);
        setTitle(TITULO_APPBAR);

        Intent intent = getIntent();

        if(intent.hasExtra(CHAVE_INSUMO)){
            insumo = (Insumo) intent.getSerializableExtra(CHAVE_INSUMO);
            historicoConsumoDAO = InsumoDatabase.getInstance(getApplicationContext()).getRoomHistoricoConsumoDAO();

            List<Consumo> historico = pegaHistoricoConsumo();

            configuraRecyclerView(historico);
        }
    }

    private List<Consumo> pegaHistoricoConsumo() {
        List<Consumo> historicoConsumo = historicoConsumoDAO.buscaHistoricoDeConsumo(insumo.getId());
        return historicoConsumo;
    }

    public void atualizaConsumos(){
        historicoConsumoAdapter.atualiza(historicoConsumoDAO.todosConsumos());
    }

    private void configuraRecyclerView(List<Consumo> historicoConsumo) {
        RecyclerView listaHistoricoConsumo = findViewById(R.id.historico_consumo_recyclerview);
        configuraAdapter(historicoConsumo, listaHistoricoConsumo);
        configuraLayoutManager(listaHistoricoConsumo);
    }

    private void configuraAdapter(List<Consumo> historicoConsumo, RecyclerView listaHistoricoConsumo) {
        historicoConsumoAdapter = new HistoricoConsumoAdapter(this, historicoConsumo, insumo);
        listaHistoricoConsumo.setAdapter(historicoConsumoAdapter);
        historicoConsumoAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void OnItemLongClick(int posicao) {
                configuraDAOListaInsumos();
                configuraAdapterListaInsumos();
                Consumo consumo = retornaConsumoRemovido(posicao);
                alteraEstoqueAtualDoInsumo(consumo);
                atualizaHistoricoDeConsumoEListaInsumo();
            }
        });
    }

    private void atualizaHistoricoDeConsumoEListaInsumo() {
        atualizaConsumos();
        listaInsumosDAO.altera(insumo);
        listaInsumosAdapter.atualiza(listaInsumosDAO.todos());
    }

    private void alteraEstoqueAtualDoInsumo(Consumo consumo) {
        insumo.setEstoqueAtual(insumo.getEstoqueAtual() + consumo.getQuantidade());
    }

    private Consumo retornaConsumoRemovido(int posicao) {
        Consumo consumo = historicoConsumoDAO.buscaHistoricoDeConsumo(insumo.getId()).get(posicao);
        remove(consumo, posicao);
        Toast.makeText(getApplicationContext(), MENSAGEM_CONSUMO_REMOVIDO, Toast.LENGTH_LONG).show();
        return consumo;
    }

    private void configuraAdapterListaInsumos() {
        listaInsumosAdapter = new ListaInsumosAdapter(getApplicationContext(), listaInsumosDAO.todos());
    }

    private void configuraDAOListaInsumos() {
        listaInsumosDAO = InsumoDatabase.getInstance(getApplicationContext()).getRoomInsumoGeralDAO();
    }

    private void configuraLayoutManager(RecyclerView listaHistoricoConsumo) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listaHistoricoConsumo.setLayoutManager(layoutManager);
    }

    public void remove(Consumo consumo, int posicao){
        historicoConsumoDAO.removeConsumo(consumo);
        historicoConsumoAdapter.remove(consumo, posicao);
    }
}