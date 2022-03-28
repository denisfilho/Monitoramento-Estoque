package projeto.monitoramentoestoque.activity;

import static projeto.monitoramentoestoque.activity.InsumoActivityConstantes.CHAVE_INSUMO;
import static projeto.monitoramentoestoque.activity.InsumoActivityConstantes.CHAVE_REQUISICAO;
import static projeto.monitoramentoestoque.activity.InsumoActivityConstantes.CHAVE_REQUISICAO_INSERE_NOVA_ENTRADA;
import static projeto.monitoramentoestoque.activity.InsumoActivityConstantes.CHAVE_REQUISICAO_INSERE_NOVO_CONSUMO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;

import projeto.monitoramentoestoque.R;
import projeto.monitoramentoestoque.model.Insumo;

public class InformacaoInsumoActivity extends AppCompatActivity {

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
            ultimaAtualizacaoInsumoRecebido.setText("Última Atualização: " + converterCalendarParaString(insumoRecebido));

            TextView botaoInserirNovaEntrada = findViewById(R.id.informacao_insumo_inserir_nova_entrada);
            botaoInserirNovaEntrada.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent iniciaFormularioNovaEntrada = new Intent(InformacaoInsumoActivity.this, FormularioNovaEntradaConsumoActivity.class);
                    iniciaFormularioNovaEntrada.putExtra(CHAVE_REQUISICAO,CHAVE_REQUISICAO_INSERE_NOVA_ENTRADA);
                    startActivity(iniciaFormularioNovaEntrada);
                }
            });

            TextView botaoInserirNovoConsumo = findViewById(R.id.informacao_insumo_inserir_novo_consumo);
            botaoInserirNovoConsumo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent iniciaFormularioNovoConsumo = new Intent(InformacaoInsumoActivity.this, FormularioNovaEntradaConsumoActivity.class);
                    iniciaFormularioNovoConsumo.putExtra(CHAVE_REQUISICAO, CHAVE_REQUISICAO_INSERE_NOVO_CONSUMO);
                    startActivity(iniciaFormularioNovoConsumo);
                }
            });
            TextView botaoHistoricoEntradas = findViewById(R.id.informacao_insumo_historico_entradas);

            TextView botaoHistoricoConsumo = findViewById(R.id.informacao_insumo_historico_consumo);
        }
    }

    @NonNull
    private String converterCalendarParaString(Insumo insumoRecebido) {
        return new SimpleDateFormat("dd/MM/yyyy").format(insumoRecebido.getDataUltimaAtualizacao().getTime());
    }
}