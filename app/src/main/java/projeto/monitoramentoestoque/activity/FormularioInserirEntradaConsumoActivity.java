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

import java.util.Calendar;

import projeto.monitoramentoestoque.R;
import projeto.monitoramentoestoque.converter.CalendarStringConverter;
import projeto.monitoramentoestoque.dao.RoomConsumoDAO;
import projeto.monitoramentoestoque.dao.RoomEntradaDAO;
import projeto.monitoramentoestoque.dao.RoomInsumoDAO;
import projeto.monitoramentoestoque.database.InsumoDatabase;
import projeto.monitoramentoestoque.model.SolicitacaoNovoConsumoEntrada;
import projeto.monitoramentoestoque.model.entities.Consumo;
import projeto.monitoramentoestoque.model.entities.Entrada;
import projeto.monitoramentoestoque.model.entities.Insumo;
import projeto.monitoramentoestoque.recyclerview.adapter.ListaInsumosAdapter;

public class FormularioInserirEntradaConsumoActivity extends AppCompatActivity {

    public static final String MENSAGEM_ENTRADA_ADICIONADA = "Entrada adicionada!";
    public static final String MENSAGEM_CONSUMO_ADICIONADO = "Consumo adicionado!";

    private String tituloAppBar;
    private EditText data;
    private EditText quantidade;
    private Button botaoInserirNovaEntradaConsumo;
    private Context context;

    private Insumo insumo;

    private RoomEntradaDAO historicoEntradaDAO;
    private RoomInsumoDAO listaInsumosDAO;
    private RoomConsumoDAO historicoConsumoDAO;

    private ListaInsumosAdapter listaInsumosAdapter;
    private double valor;


    private CalendarStringConverter conversorCalendar = new CalendarStringConverter();
    private Calendar dataCalendar;

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

            configuraBotaoInserirEntradaConsumo();
        }
    }

    private void configuraBotaoInserirEntradaConsumo() {
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
                alterarDataUltimaAtualizacao();
                salvaAlteracoes();
                finish();
            }
        });
    }

    private void salvaAlteracoes() {
        listaInsumosDAO.altera(insumo);
        listaInsumosAdapter.atualiza(listaInsumosDAO.todos());
    }

    private void alterarDataUltimaAtualizacao() {
        Calendar dataAtualizada = Calendar.getInstance();
        insumo.setDataUltimaAtualizacao(dataAtualizada);
    }

    private void alterarValorEstoqueAtual() {
        insumo.setEstoqueAtual(insumo.getEstoqueAtual() + valor);
    }

    private void salvaHistoricoConsumo() {
        historicoConsumoDAO = InsumoDatabase.getInstance(context).getRoomHistoricoConsumoDAO();
        Consumo consumo = new Consumo(dataCalendar, valor, insumo.getId());
        historicoConsumoDAO.salvaConsumo(consumo);
    }

    private void salvaHistoricoEntrada() {
        historicoEntradaDAO = InsumoDatabase.getInstance(context).getRoomHistoricoEntradaDAO();
        Entrada entrada = new Entrada(dataCalendar, valor, insumo.getId());
        historicoEntradaDAO.salvaEntrada(entrada);
    }

    private void salvaValoresEnviadosPeloFormulario() {
        valor = Double.parseDouble(quantidade.getText().toString());
        dataCalendar = conversorCalendar.conveterStringParaCalendar(data.getText().toString());
    }

    private void configuraAdapterListaInsumos() {
        listaInsumosAdapter = new ListaInsumosAdapter(context, listaInsumosDAO.todos());
    }

    private void configuraDAOInsumo() {
        listaInsumosDAO = InsumoDatabase.getInstance(context).getRoomInsumoGeralDAO();
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