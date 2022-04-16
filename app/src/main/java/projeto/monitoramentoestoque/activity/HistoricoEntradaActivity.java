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
import projeto.monitoramentoestoque.dao.RoomEntradaDAO;
import projeto.monitoramentoestoque.dao.RoomInsumoDAO;
import projeto.monitoramentoestoque.database.InsumoDatabase;
import projeto.monitoramentoestoque.model.Entrada;
import projeto.monitoramentoestoque.model.Insumo;
import projeto.monitoramentoestoque.recyclerview.adapter.HistoricoEntradaAdapter;
import projeto.monitoramentoestoque.recyclerview.adapter.ListaInsumosAdapter;
import projeto.monitoramentoestoque.recyclerview.adapter.listener.OnItemLongClickListener;

public class HistoricoEntradaActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Histórico de Entrada";
    public static final String MENSAGEM_ENTRADA_REMOVIDA = "Entrada Removida!";

    private HistoricoEntradaAdapter historicoEntradaAdapter;
    private ListaInsumosAdapter listaInsumosAdapter;

    private RoomEntradaDAO historicoEntradaDAO;
    private RoomInsumoDAO listaInsumosDAO;

    private Insumo insumo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_entrada);
        setTitle(TITULO_APPBAR);

        Intent intent = getIntent();
        if(intent.hasExtra(CHAVE_INSUMO)){
            insumo = (Insumo) intent.getSerializableExtra(CHAVE_INSUMO);
            historicoEntradaDAO = InsumoDatabase.getInstance(getApplicationContext()).getRoomHistoricoEntradaDAO();

            List<Entrada> historicoEntrada = pegaHistoricoEntrada();

            configuraRecyclerViewHistoricoEntrada(historicoEntrada);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private List<Entrada> pegaHistoricoEntrada() {;

        List<Entrada> historicoEntrada = historicoEntradaDAO.buscaHistoricoDeEntrada(insumo.getId());
        return historicoEntrada;
    }

    public void atualizaEntradas(){
        historicoEntradaAdapter.atualiza(historicoEntradaDAO.todasEntradas());
    }

    private void configuraRecyclerViewHistoricoEntrada(List<Entrada> historicoEntrada) {
        RecyclerView listaHistoricoEntrada = findViewById(R.id.historico_entrada_recyclerview);
        configuraAdapterHistoricoEntrada(historicoEntrada, listaHistoricoEntrada);
        configuraLayoutManager(listaHistoricoEntrada);
    }

    private void configuraAdapterHistoricoEntrada(List<Entrada> historicoEntrada, RecyclerView listaHistoricoEntrada) {
        historicoEntradaAdapter = new HistoricoEntradaAdapter(this, historicoEntrada, insumo);
        listaHistoricoEntrada.setAdapter(historicoEntradaAdapter);
        configuraRemocaoEntrada();
    }

    private void configuraRemocaoEntrada() {
        historicoEntradaAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void OnItemLongClick(int posicao) {
                configuraDAOListaInsumos();
                configuraAdapterListaInsumos();
                Entrada entrada = retornaEntradaRemovida(posicao);
                alteraEstoqueAtualDoInsumo(entrada);
                atualizaHistoricoDeEntradaEListaInsumos();
            }
        });
    }

    private void atualizaHistoricoDeEntradaEListaInsumos() {
        atualizaEntradas();
        listaInsumosDAO.altera(insumo);
        listaInsumosAdapter.atualiza(listaInsumosDAO.todos());
    }

    private void alteraEstoqueAtualDoInsumo(Entrada entrada) {
        insumo.setEstoqueAtual(insumo.getEstoqueAtual() - entrada.getQuantidade());
    }

    private Entrada retornaEntradaRemovida(int posicao) {
        Entrada entrada = historicoEntradaDAO.buscaHistoricoDeEntrada(insumo.getId()).get(posicao);
        remove(entrada, posicao);
        Toast.makeText(getApplicationContext(), MENSAGEM_ENTRADA_REMOVIDA, Toast.LENGTH_LONG).show();
        return entrada;
    }

    private void configuraAdapterListaInsumos() {
        listaInsumosAdapter = new ListaInsumosAdapter(getApplicationContext(), listaInsumosDAO.todos());
    }

    private void configuraDAOListaInsumos() {
        listaInsumosDAO = InsumoDatabase.getInstance(getApplicationContext()).getRoomInsumoGeralDAO();
    }

    private void configuraLayoutManager(RecyclerView listaHistoricoEntrada) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listaHistoricoEntrada.setLayoutManager(layoutManager);
    }
    public void remove(Entrada entrada, int posicao){
        historicoEntradaDAO.remove(entrada);
        historicoEntradaAdapter.remove(entrada, posicao);
    }
}
