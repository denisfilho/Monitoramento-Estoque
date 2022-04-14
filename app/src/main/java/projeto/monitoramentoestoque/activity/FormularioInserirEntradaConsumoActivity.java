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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_inserir_entrada_consumo);

        context = getApplicationContext();
        Intent insercaoSelecionada = getIntent();

        if (insercaoSelecionada.hasExtra(CHAVE_REQUISICAO)){
            SolicitacaoNovoConsumoEntrada solicitacao = (SolicitacaoNovoConsumoEntrada) insercaoSelecionada.getSerializableExtra(CHAVE_REQUISICAO);
            tituloAppBar = solicitacao.getTipoDeSolicitacao();
            setTitle(tituloAppBar);

            insumo = solicitacao.getInsumo();
            data = findViewById(R.id.formulario_inserir_entrada_consumo_data);
            quantidade = findViewById(R.id.formulario_inserir_entrada_consumo_quantidade);
            Button botaoInserirNovaEntradaConsumo = findViewById(R.id.formulario_inserir_entrada_consumo_botao);


            botaoInserirNovaEntradaConsumo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(tituloAppBar.equals(CHAVE_REQUISICAO_INSERE_NOVA_ENTRADA)) {

                        daoInsumo = InsumoDatabase.getInstance(context).getRoomInsumoGeralDAO();
                        adapter = new ListaInsumosAdapter(context, daoInsumo.todos());

                        valor = Double.parseDouble(quantidade.getText().toString());
                        solicitacao.getInsumo().setEstoqueAtual(insumo.getEstoqueAtual() + valor);

                        daoInsumo.altera(insumo);


                        daoEntrada = InsumoDatabase.getInstance(context).getRoomHistoricoEntradaDAO();
                        daoEntrada.salvaEntrada(new Entrada((data.getText().toString()), valor, insumo.getId()));
                        Toast.makeText(context,"Entrada adicionada!", Toast.LENGTH_LONG).show();

                        adapter.atualiza(daoInsumo.todos());

                    }
                    else{
                        daoInsumo = InsumoDatabase.getInstance(context).getRoomInsumoGeralDAO();
                        adapter = new ListaInsumosAdapter(context, daoInsumo.todos());

                        valor = Double.parseDouble(quantidade.getText().toString());
                        solicitacao.getInsumo().setEstoqueAtual(insumo.getEstoqueAtual() - valor);

                        daoInsumo.altera(insumo);

                        daoConsumo = InsumoDatabase.getInstance(context).getRoomHistoricoConsumoDAO();
                        Consumo consumo = new Consumo(data.getText().toString(), Double.parseDouble(quantidade.getText().toString()), solicitacao.getInsumo().getId());
                        daoConsumo.salvaConsumo(consumo);
                        Toast.makeText(context,"Consumo adicionado!", Toast.LENGTH_LONG).show();

                        adapter.atualiza(daoInsumo.todos());
                    }
                    finish();
                }
            });
        }
    }
}