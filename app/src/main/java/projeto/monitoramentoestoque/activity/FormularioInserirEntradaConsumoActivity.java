package projeto.monitoramentoestoque.activity;

import static projeto.monitoramentoestoque.activity.InsumoActivityConstantes.CHAVE_REQUISICAO;
import static projeto.monitoramentoestoque.activity.InsumoActivityConstantes.CHAVE_REQUISICAO_INSERE_NOVA_ENTRADA;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import projeto.monitoramentoestoque.R;
import projeto.monitoramentoestoque.dao.RoomConsumoDAO;
import projeto.monitoramentoestoque.dao.RoomEntradaDAO;
import projeto.monitoramentoestoque.dao.RoomInsumoDAO;
import projeto.monitoramentoestoque.database.InsumoDatabase;
import projeto.monitoramentoestoque.model.Consumo;
import projeto.monitoramentoestoque.model.Entrada;
import projeto.monitoramentoestoque.model.Insumo;
import projeto.monitoramentoestoque.model.SolicitacaoNovoConsumoEntrada;
import projeto.monitoramentoestoque.recyclerview.adapter.ListaInsumosAdapter;

public class FormularioInserirEntradaConsumoActivity extends AppCompatActivity {

    public static final String MENSAGEM_ENTRADA_ADICIONADA = "Entrada adicionada!";
    public static final String MENSAGEM_CONSUMO_ADICIONADO = "Consumo adicionado!";
    private String tituloAppBar;
    private EditText data;
    private EditText quantidade;
    private Context context;

    private Insumo insumo;

    private RoomEntradaDAO daoEntrada;
    private RoomInsumoDAO daoInsumo;
    private RoomConsumoDAO daoConsumo;
    private double valor;

    private ListaInsumosAdapter adapter;
    private Button botaoInserirNovaEntradaConsumo;
    private String dataInformada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_inserir_entrada_consumo);

        context = getApplicationContext();

        Intent insercaoSelecionada = getIntent();

        if (insercaoSelecionada.hasExtra(CHAVE_REQUISICAO)){
            SolicitacaoNovoConsumoEntrada solicitacao = (SolicitacaoNovoConsumoEntrada) insercaoSelecionada.getSerializableExtra(CHAVE_REQUISICAO);

            nomeAppBar(solicitacao);

            insumo = solicitacao.getInsumo();

            vinculaCamposDoFormulario();

            configuraBotaoInserirEntradaConsumo(solicitacao);
        }
    }

    private void configuraBotaoInserirEntradaConsumo(SolicitacaoNovoConsumoEntrada solicitacao) {
        botaoInserirNovaEntradaConsumo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                configuraDAOInsumo();
                configuraAdapterListaInsumos();
                salvaValoresEnviadosPeloFormulario();

                if(tituloAppBar.equals(CHAVE_REQUISICAO_INSERE_NOVA_ENTRADA)) {

                    salvaHistoricoEntrada();
                    Toast.makeText(context, MENSAGEM_ENTRADA_ADICIONADA, Toast.LENGTH_LONG).show();

                }
                else{

                    valor = -valor;
                    salvaHistoricoConsumo();
                    Toast.makeText(context, MENSAGEM_CONSUMO_ADICIONADO, Toast.LENGTH_LONG).show();

                }
                alterarValorEstoqueAtual();
                finish();
            }
        });
    }

    private void alterarValorEstoqueAtual() {
        insumo.setEstoqueAtual(insumo.getEstoqueAtual() + valor);
        daoInsumo.altera(insumo);
        adapter.atualiza(daoInsumo.todos());
    }

    private void salvaHistoricoConsumo() {
        daoConsumo = InsumoDatabase.getInstance(context).getRoomHistoricoConsumoDAO();
        Consumo consumo = new Consumo(data.getText().toString(), Double.parseDouble(quantidade.getText().toString()), insumo.getId());
        daoConsumo.salvaConsumo(consumo);
    }

    private void salvaHistoricoEntrada() {
        daoEntrada = InsumoDatabase.getInstance(context).getRoomHistoricoEntradaDAO();
        Entrada entrada = new Entrada(dataInformada, valor, insumo.getId());
        daoEntrada.salvaEntrada(entrada);
    }

    private void salvaValoresEnviadosPeloFormulario() {
        valor = Double.parseDouble(quantidade.getText().toString());
        dataInformada = data.getText().toString();
    }

    private void configuraAdapterListaInsumos() {
        adapter = new ListaInsumosAdapter(context, daoInsumo.todos());
    }

    private void configuraDAOInsumo() {
        daoInsumo = InsumoDatabase.getInstance(context).getRoomInsumoGeralDAO();
    }

    private void vinculaCamposDoFormulario() {
        data = findViewById(R.id.formulario_inserir_entrada_consumo_data);
        quantidade = findViewById(R.id.formulario_inserir_entrada_consumo_quantidade);
        botaoInserirNovaEntradaConsumo = findViewById(R.id.formulario_inserir_entrada_consumo_botao);
    }

    private void nomeAppBar(SolicitacaoNovoConsumoEntrada solicitacao) {
        tituloAppBar = solicitacao.getTipoDeSolicitacao();
        setTitle(tituloAppBar);
    }
}