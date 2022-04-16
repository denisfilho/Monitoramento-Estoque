package projeto.monitoramentoestoque.activity;

import static projeto.monitoramentoestoque.activity.InsumoActivityConstantes.CHAVE_INSUMO;
import static projeto.monitoramentoestoque.activity.InsumoActivityConstantes.CODIGO_REQUISICAO_INSERE_INSUMO;
import static projeto.monitoramentoestoque.activity.InsumoActivityConstantes.CODIGO_RESULTADO_INSUMO_CRIADO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import projeto.monitoramentoestoque.R;
import projeto.monitoramentoestoque.dao.RoomInsumoDAO;
import projeto.monitoramentoestoque.database.InsumoDatabase;
import projeto.monitoramentoestoque.model.Insumo;
import projeto.monitoramentoestoque.recyclerview.adapter.ListaInsumosAdapter;
import projeto.monitoramentoestoque.recyclerview.adapter.listener.OnItemClickListener;
import projeto.monitoramentoestoque.recyclerview.adapter.listener.OnItemLongClickListener;

public class ListaInsumosActivity extends AppCompatActivity {

    public static final String MENSAGEM_INSUMO_REMOVIDO = "Insumo Removido!";
    private ListaInsumosAdapter listaInsumosAdapter;
    private RoomInsumoDAO listaInsumosDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_insumos);

        listaInsumosDAO = InsumoDatabase.getInstance(getApplicationContext()).getRoomInsumoGeralDAO();

        List<Insumo> todosInsumos = pegaTodosInsumos();
        configuraRecyclerView(todosInsumos);

        configuraBotaoInsereInsumo();

    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizaInsumos();
    }

    private List<Insumo> pegaTodosInsumos() {

        List<Insumo> todosInsumos = listaInsumosDAO.todos();
        return todosInsumos;
    }

    private void configuraRecyclerView(List<Insumo> todosInsumos) {
        RecyclerView listaInsumos = findViewById(R.id.lista_insumos_recyclerview);
        configuraAdapter(todosInsumos, listaInsumos);
        configuraLayoutManager(listaInsumos);
        registerForContextMenu(listaInsumos);
    }

    private void configuraAdapter(List<Insumo> todosInsumos, RecyclerView listaInsumos) {
        listaInsumosAdapter = new ListaInsumosAdapter(this, todosInsumos);
        listaInsumos.setAdapter(listaInsumosAdapter);
        configuraExibicaoInsumo();
        configuraRemocaoInsumo();
    }

    private void configuraExibicaoInsumo() {
        listaInsumosAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(Insumo insumo) {
                Intent abreActivityInformacaoInsumo = new Intent(ListaInsumosActivity.this,InformacaoInsumoActivity.class);
                abreActivityInformacaoInsumo.putExtra(CHAVE_INSUMO, insumo);
                startActivity(abreActivityInformacaoInsumo);
            }
        });
    }

    private void configuraRemocaoInsumo() {
        listaInsumosAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void OnItemLongClick(int posicao) {
                Insumo insumo = listaInsumosDAO.todos().get(posicao);
                remove(insumo, posicao);
                atualizaInsumos();
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
        listaInsumosDAO.salvaInsumo(insumo);
        listaInsumosAdapter.adiciona(insumo);

    }

    public void atualizaInsumos(){
        listaInsumosAdapter.atualiza(listaInsumosDAO.todos());
    }

    public void remove(Insumo insumo, int posicao){
        listaInsumosDAO.remove(insumo);
        listaInsumosAdapter.remove(insumo, posicao);
        Toast.makeText(getApplicationContext(), MENSAGEM_INSUMO_REMOVIDO, Toast.LENGTH_LONG).show();
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



}