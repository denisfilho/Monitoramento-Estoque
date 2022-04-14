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

    private SolicitacaoNovoConsumoEntrada solicitacaoNovoConsumoEntrada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacao_insumo);

        Intent insumoSelecionado = getIntent();
        if(insumoSelecionado.hasExtra(CHAVE_INSUMO)){
            Insumo insumoRecebido = (Insumo) insumoSelecionado.getSerializableExtra(CHAVE_INSUMO);

            TextView nomeInsumoRecebido = findViewById(R.id.informacao_insumo_nome);
            nomeInsumoRecebido.setText(insumoRecebido.getNome());

            TextView unidadeInsumoRecebido = findViewById(R.id.informacao_insumo_unidade);
            unidadeInsumoRecebido.setText("Unidade: " + insumoRecebido.getUnidade());

            TextView estoqueAtualInsumoRecebido = findViewById(R.id.informacao_insumo_estoque_atual);
            estoqueAtualInsumoRecebido.setText("Estoque Atual: " + Double.toString(insumoRecebido.getEstoqueAtual()));

            TextView ultimaAtualizacaoInsumoRecebido = findViewById(R.id.informacao_insumo_data_ultima_atualizacao);
            ultimaAtualizacaoInsumoRecebido.setText("Última Atualização: " + insumoRecebido.getDataUltimaAtualizacao());

            TextView botaoInserirNovaEntrada = findViewById(R.id.informacao_insumo_inserir_nova_entrada);
            botaoInserirNovaEntrada.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    solicitacaoNovoConsumoEntrada = new SolicitacaoNovoConsumoEntrada(CHAVE_REQUISICAO_INSERE_NOVA_ENTRADA,insumoRecebido);
                    InserirEntradaOuConsumo(solicitacaoNovoConsumoEntrada, FormularioNovaEntradaActivity.class);
                }
            });

            TextView botaoInserirNovoConsumo = findViewById(R.id.informacao_insumo_inserir_novo_consumo);
            botaoInserirNovoConsumo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    solicitacaoNovoConsumoEntrada = new SolicitacaoNovoConsumoEntrada(CHAVE_REQUISICAO_INSERE_NOVO_CONSUMO,insumoRecebido);
                    InserirEntradaOuConsumo(solicitacaoNovoConsumoEntrada, FormularioNovoConsumoActivity.class);
                }
            });
            TextView botaoHistoricoEntradas = findViewById(R.id.informacao_insumo_historico_entradas);
            botaoHistoricoEntradas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent abreHistoricoDeEntrada = new Intent(InformacaoInsumoActivity.this, HistoricoEntradaActivity.class);
                    abreHistoricoDeEntrada.putExtra(CHAVE_INSUMO, insumoRecebido);
                    startActivity(abreHistoricoDeEntrada);
                }
            });

            TextView botaoHistoricoConsumo = findViewById(R.id.informacao_insumo_historico_consumo);
            botaoHistoricoConsumo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent abreHistoricoDeConsumo = new Intent(InformacaoInsumoActivity.this, HistoricoConsumoActivity.class);
                    abreHistoricoDeConsumo.putExtra(CHAVE_INSUMO, insumoRecebido);
                    startActivity(abreHistoricoDeConsumo);
                }
            });
        }
    }

    private void InserirEntradaOuConsumo(SolicitacaoNovoConsumoEntrada solicitacaoNovoConsumoEntrada, Class tipoFormulario) {
        Intent iniciaFormulario = new Intent(InformacaoInsumoActivity.this, tipoFormulario);
        iniciaFormulario.putExtra(CHAVE_REQUISICAO,  solicitacaoNovoConsumoEntrada);
        startActivity(iniciaFormulario);
    }

}