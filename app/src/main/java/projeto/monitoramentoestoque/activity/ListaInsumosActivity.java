package projeto.monitoramentoestoque.activity;

import static projeto.monitoramentoestoque.activity.InsumoActivityConstantes.CHAVE_INSUMO;
import static projeto.monitoramentoestoque.activity.InsumoActivityConstantes.CODIGO_REQUISICAO_INSERE_INSUMO;
import static projeto.monitoramentoestoque.activity.InsumoActivityConstantes.CODIGO_RESULTADO_INSUMO_CRIADO;

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

    private ListaInsumosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_insumos);
        List<Insumo> todosInsumos = pegaTodosInsumos();
        configuraRecyclerView(todosInsumos);

        configuraBotaoInsereInsumo();
    }

    private void configuraBotaoInsereInsumo() {
        Button botaoInsereInsumo = findViewById(R.id.lista_insumos_bota_insere_insumo);
        botaoInsereInsumo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaiParaFormularioInsumoActivity();
            }
        });
    }

    private void vaiParaFormularioInsumoActivity() {
        Intent iniciaFormularioInsumo = new Intent(ListaInsumosActivity.this, FormularioInsumoActivity.class);
        startActivityForResult(iniciaFormularioInsumo, CODIGO_REQUISICAO_INSERE_INSUMO);
    }

    private List<Insumo> pegaTodosInsumos() {
        InsumoDAO dao = new InsumoDAO();
        List<Insumo> todosInsumos = dao.todos();
        return todosInsumos;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(ehResultadoComInsumo(requestCode, resultCode, data)){
            Insumo insumoRecebido = (Insumo) data.getSerializableExtra(CHAVE_INSUMO);
            adiciona(insumoRecebido);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void adiciona(Insumo insumo) {
        new InsumoDAO().insere(insumo);
        adapter.adiciona(insumo);
    }

    private boolean ehResultadoComInsumo(int requestCode, int resultCode, @Nullable Intent data) {
        return ehCodigoRequisicaoInsereInsumo(requestCode) && ehCodigoResultadoInsumoCriado(resultCode) && temInsumo(data);
    }

    private boolean temInsumo(@Nullable Intent data) {
        return data.hasExtra(CHAVE_INSUMO);
    }

    private boolean ehCodigoResultadoInsumoCriado(int resultCode) {
        return resultCode == CODIGO_RESULTADO_INSUMO_CRIADO;
    }

    private boolean ehCodigoRequisicaoInsereInsumo(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_INSERE_INSUMO;
    }

    @Override
    protected void onResume() {
        super.onResume();
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