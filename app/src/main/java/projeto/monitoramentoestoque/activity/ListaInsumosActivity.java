package projeto.monitoramentoestoque.activity;

import static projeto.monitoramentoestoque.activity.InsumoActivityConstantes.CHAVE_INSUMO;
import static projeto.monitoramentoestoque.activity.InsumoActivityConstantes.CODIGO_REQUISICAO_EXIBE_INSUMO;
import static projeto.monitoramentoestoque.activity.InsumoActivityConstantes.CODIGO_REQUISICAO_INSERE_INSUMO;
import static projeto.monitoramentoestoque.activity.InsumoActivityConstantes.CODIGO_RESULTADO_INSUMO_CRIADO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import projeto.monitoramentoestoque.R;
import projeto.monitoramentoestoque.dao.DAOInsumo;
import projeto.monitoramentoestoque.model.Insumo;
import projeto.monitoramentoestoque.recyclerview.adapter.ListaInsumosAdapter;
import projeto.monitoramentoestoque.recyclerview.adapter.listener.OnItemClickListener;

public class ListaInsumosActivity extends AppCompatActivity {

    private ListaInsumosAdapter adapter;

    @Autowired
    private DAOInsumo insumoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_insumos);
        List<Insumo> todosInsumos = pegaTodosInsumos();
        configuraRecyclerView(todosInsumos);

        configuraBotaoInsereInsumo();
    }

    private List<Insumo> pegaTodosInsumos() {
        /*
        InsumoDAO dao = new InsumoDAO();
        List<Insumo> todosInsumos = dao.todos();
*/

        List<Insumo> todosInsumos = insumoDAO.findAll();
        return todosInsumos;
    }

    private void configuraRecyclerView(List<Insumo> todosInsumos) {
        RecyclerView listaInsumos = findViewById(R.id.lista_insumos_recyclerview);
        configuraAdapter(todosInsumos, listaInsumos);
        configuraLayoutManager(listaInsumos);
    }

    private void configuraAdapter(List<Insumo> todosInsumos, RecyclerView listaInsumos) {
        adapter = new ListaInsumosAdapter(this, todosInsumos);
        listaInsumos.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(Insumo insumo) {
                Intent abreActivityInformacaoInsumo = new Intent(ListaInsumosActivity.this,InformacaoInsumoActivity.class);
                abreActivityInformacaoInsumo.putExtra(CHAVE_INSUMO, insumo);
                startActivityForResult(abreActivityInformacaoInsumo,CODIGO_REQUISICAO_EXIBE_INSUMO);
            }
        });
    }

    private void configuraLayoutManager(RecyclerView listaInsumos) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listaInsumos.setLayoutManager(layoutManager);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(ehResultadoComInsumo(requestCode, resultCode, data)){
            Insumo insumoRecebido = (Insumo) data.getSerializableExtra(CHAVE_INSUMO);
            adiciona(insumoRecebido);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void adiciona(Insumo insumo) {
        //new InsumoDAO().insere(insumo);
        insumoDAO.save(insumo);
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
}