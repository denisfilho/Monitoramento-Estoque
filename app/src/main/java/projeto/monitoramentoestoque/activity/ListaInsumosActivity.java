package projeto.monitoramentoestoque.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;
import java.util.List;

import projeto.monitoramentoestoque.R;
import projeto.monitoramentoestoque.dao.InsumoDAO;
import projeto.monitoramentoestoque.model.Insumo;
import projeto.monitoramentoestoque.recyclerview.adapter.ListaInsumosAdapter;

public class ListaInsumosActivity extends AppCompatActivity {

    private List<Insumo> todosInsumos;
    private ListaInsumosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_insumos);
        todosInsumos = insumosDeExemplo();
        configuraRecyclerView(todosInsumos);

        Button botaoInsereInsumo = findViewById(R.id.lista_insumos_bota_insere_insumo);
        botaoInsereInsumo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iniciaFormularioInsumo = new Intent(ListaInsumosActivity.this, FormularioInsumoActivity.class);
                startActivityForResult(iniciaFormularioInsumo, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1 && resultCode == 2 && data.hasExtra("insumo")){
            Insumo insumoRecebido = (Insumo) data.getSerializableExtra("insumo");
            new InsumoDAO().insere(insumoRecebido);
            adapter.adiciona(insumoRecebido);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private List<Insumo> insumosDeExemplo() {
        InsumoDAO dao = new InsumoDAO();
        for (int i = 1; i <9; i++){
            dao.insere(new Insumo("Insumo " + i, i));
        }
        List<Insumo> todosInsumos = dao.todos();
        return todosInsumos;
    }

    private void configuraRecyclerView(List<Insumo> todosInsumos) {
        RecyclerView listaInsumos = findViewById(R.id.lista_insumos_recyclerview);
        configuraAdapter(todosInsumos, listaInsumos);
        configuraLayoutManager(listaInsumos);
    }

    private void configuraLayoutManager(RecyclerView listaInsumos) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listaInsumos.setLayoutManager(layoutManager);
    }

    private void configuraAdapter(List<Insumo> todosInsumos, RecyclerView listaInsumos) {
        adapter = new ListaInsumosAdapter(this, todosInsumos);
        listaInsumos.setAdapter(adapter);
    }
}