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
import projeto.monitoramentoestoque.database.InsumoDatabase;
import projeto.monitoramentoestoque.model.Consumo;
import projeto.monitoramentoestoque.model.Insumo;
import projeto.monitoramentoestoque.recyclerview.adapter.HistoricoConsumoAdapter;

public class HistoricoConsumoActivity extends AppCompatActivity {

    private HistoricoConsumoAdapter adapter;
    private RoomConsumoDAO dao;
    private Insumo insumo;

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

    private void configuraRecyclerView(List<Consumo> historicoConsumo) {
        RecyclerView listaHistoricoConsumo = findViewById(R.id.historico_consumo_recyclerview);
        configuraAdapter(historicoConsumo, listaHistoricoConsumo);
        configuraLayoutManager(listaHistoricoConsumo);
    }

    private void configuraAdapter(List<Consumo> historicoConsumo, RecyclerView listaHistoricoConsumo) {
        adapter = new HistoricoConsumoAdapter(this, historicoConsumo, insumo);
        listaHistoricoConsumo.setAdapter(adapter);
    }

    private void configuraLayoutManager(RecyclerView listaHistoricoConsumo) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listaHistoricoConsumo.setLayoutManager(layoutManager);
    }
}