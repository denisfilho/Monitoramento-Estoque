package projeto.monitoramentoestoque.activity;

import static projeto.monitoramentoestoque.activity.InsumoActivityConstantes.CHAVE_INSUMO;
import static projeto.monitoramentoestoque.activity.InsumoActivityConstantes.CHAVE_REQUISICAO;
import static projeto.monitoramentoestoque.activity.InsumoActivityConstantes.CHAVE_REQUISICAO_INSERE_NOVA_ENTRADA;
import static projeto.monitoramentoestoque.activity.InsumoActivityConstantes.CHAVE_REQUISICAO_INSERE_NOVO_CONSUMO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import projeto.monitoramentoestoque.R;
import projeto.monitoramentoestoque.model.Insumo;
import projeto.monitoramentoestoque.model.SolicitacaoNovoConsumoEntrada;

public class InformacaoInsumoActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Informação Detalhada";
    private SolicitacaoNovoConsumoEntrada solicitacaoNovoConsumoEntrada;

    private TextView nomeInsumoRecebido;
    private TextView unidadeInsumoRecebido;
    private TextView estoqueAtualInsumoRecebido;
    private TextView ultimaAtualizacaoInsumoRecebido;
    private TextView botaoInserirNovaEntrada;
    private TextView botaoInserirNovoConsumo;
    private TextView botaoHistoricoEntradas;
    private TextView botaoHistoricoConsumo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacao_insumo);
        setTitle(TITULO_APPBAR);

        Intent insumoSelecionado = getIntent();
        if(insumoSelecionado.hasExtra(CHAVE_INSUMO)){
            Insumo insumoRecebido = (Insumo) insumoSelecionado.getSerializableExtra(CHAVE_INSUMO);

            vinculaCampos();
            preencheCampos(insumoRecebido);
            configuraBotoes(insumoRecebido);

        }
    }

    private void configuraBotoes(Insumo insumoRecebido) {
        configuraBotaoInserirEntrada(insumoRecebido);
        configuraBotaoInserirConsumo(insumoRecebido);
        configuraBotaoExibirHistoricoEntrada(insumoRecebido);
        comfiguraBotaoExibirHistoricoConsumo(insumoRecebido);
    }

    private void configuraBotaoExibirHistoricoEntrada(Insumo insumoRecebido) {
        botaoHistoricoEntradas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abreHistoricoDeEntrada = new Intent(InformacaoInsumoActivity.this, HistoricoEntradaActivity.class);
                abreHistoricoDeEntrada.putExtra(CHAVE_INSUMO, insumoRecebido);
                startActivity(abreHistoricoDeEntrada);
            }
        });
    }

    private void comfiguraBotaoExibirHistoricoConsumo(Insumo insumoRecebido) {
        botaoHistoricoConsumo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abreHistoricoDeConsumo = new Intent(InformacaoInsumoActivity.this, HistoricoConsumoActivity.class);
                abreHistoricoDeConsumo.putExtra(CHAVE_INSUMO, insumoRecebido);
                startActivity(abreHistoricoDeConsumo);
            }
        });
    }

    private void configuraBotaoInserirConsumo(Insumo insumoRecebido) {
        botaoInserirNovoConsumo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solicitacaoNovoConsumoEntrada = new SolicitacaoNovoConsumoEntrada(CHAVE_REQUISICAO_INSERE_NOVO_CONSUMO,insumoRecebido);
                InserirEntradaOuConsumo(solicitacaoNovoConsumoEntrada, FormularioInserirEntradaConsumoActivity.class);
            }
        });
    }

    private void configuraBotaoInserirEntrada(Insumo insumoRecebido) {
        botaoInserirNovaEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solicitacaoNovoConsumoEntrada = new SolicitacaoNovoConsumoEntrada(CHAVE_REQUISICAO_INSERE_NOVA_ENTRADA,insumoRecebido);
                InserirEntradaOuConsumo(solicitacaoNovoConsumoEntrada, FormularioInserirEntradaConsumoActivity.class);
            }
        });
    }

    private void preencheCampos(Insumo insumoRecebido) {
        nomeInsumoRecebido.setText(insumoRecebido.getNome());
        unidadeInsumoRecebido.setText("Unidade: " + insumoRecebido.getUnidade());
        estoqueAtualInsumoRecebido.setText("Estoque Atual: " + Double.toString(insumoRecebido.getEstoqueAtual()));
        ultimaAtualizacaoInsumoRecebido.setText("Última Atualização: " + insumoRecebido.getDataUltimaAtualizacao());
    }

    private void vinculaCampos() {
        nomeInsumoRecebido = findViewById(R.id.informacao_insumo_nome);
        unidadeInsumoRecebido = findViewById(R.id.informacao_insumo_unidade);
        estoqueAtualInsumoRecebido = findViewById(R.id.informacao_insumo_estoque_atual);
        ultimaAtualizacaoInsumoRecebido = findViewById(R.id.informacao_insumo_data_ultima_atualizacao);
        botaoInserirNovaEntrada = findViewById(R.id.informacao_insumo_inserir_nova_entrada);
        botaoInserirNovoConsumo = findViewById(R.id.informacao_insumo_inserir_novo_consumo);
        botaoHistoricoEntradas = findViewById(R.id.informacao_insumo_historico_entradas);
        botaoHistoricoConsumo = findViewById(R.id.informacao_insumo_historico_consumo);
    }

    private void InserirEntradaOuConsumo(SolicitacaoNovoConsumoEntrada solicitacaoNovoConsumoEntrada, Class tipoFormulario) {
        Intent iniciaFormulario = new Intent(InformacaoInsumoActivity.this, tipoFormulario);
        iniciaFormulario.putExtra(CHAVE_REQUISICAO,  solicitacaoNovoConsumoEntrada);
        startActivity(iniciaFormulario);
    }

}