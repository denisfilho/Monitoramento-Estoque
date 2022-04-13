package projeto.monitoramentoestoque.activity;

import static projeto.monitoramentoestoque.activity.InsumoActivityConstantes.CHAVE_INSUMO;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

import projeto.monitoramentoestoque.R;
import projeto.monitoramentoestoque.dao.RoomEntradaDAO;
import projeto.monitoramentoestoque.database.InsumoDatabase;
import projeto.monitoramentoestoque.model.Entrada;
import projeto.monitoramentoestoque.model.Insumo;
import projeto.monitoramentoestoque.recyclerview.adapter.HistoricoEntradaAdapter;

public class HistoricoEntradaActivity extends AppCompatActivity {

    private HistoricoEntradaAdapter adapter;

    private RoomEntradaDAO dao;
    private Insumo insumo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_entrada);

        Intent intent = getIntent();
        if(intent.hasExtra(CHAVE_INSUMO)){
            insumo = (Insumo) intent.getSerializableExtra(CHAVE_INSUMO);
            dao = Room.databaseBuilder(getApplicationContext(), InsumoDatabase.class, "insumo.bd").allowMainThreadQueries().build().getRoomHistoricoEntradaDAO();

            List<Entrada> historicoEntrada = pegaHistoricoEntrada();

            configuraRecyclerView(historicoEntrada);
        }

    }

    private List<Entrada> pegaHistoricoEntrada() {;

        //List<Entrada> historicoEntrada = dao.todasEntradas() /*dao.buscaHistoricoDeEntrada(insumo.getId())*/;
        List<Entrada> historicoEntrada = dao.buscaHistoricoDeEntrada(insumo.getId());
        return historicoEntrada;
    }

    private void configuraRecyclerView(List<Entrada> historicoEntrada) {
        RecyclerView listaHistoricoEntrada = findViewById(R.id.historico_entrada_recyclerview);
        configuraAdapter(historicoEntrada, listaHistoricoEntrada);
        configuraLayoutManager(listaHistoricoEntrada);
    }

    private void configuraAdapter(List<Entrada> historicoEntrada, RecyclerView listaHistoricoEntrada) {
        adapter = new HistoricoEntradaAdapter(this, historicoEntrada, insumo);
        listaHistoricoEntrada.setAdapter(adapter);
    }

    private void configuraLayoutManager(RecyclerView listaHistoricoEntrada) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listaHistoricoEntrada.setLayoutManager(layoutManager);
    }
}
