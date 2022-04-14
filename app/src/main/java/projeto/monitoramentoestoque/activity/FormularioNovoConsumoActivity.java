package projeto.monitoramentoestoque.activity;

import static projeto.monitoramentoestoque.activity.InsumoActivityConstantes.CHAVE_REQUISICAO;
import static projeto.monitoramentoestoque.activity.InsumoActivityConstantes.CHAVE_REQUISICAO_INSERE_NOVO_CONSUMO;

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
import projeto.monitoramentoestoque.dao.RoomInsumoDAO;
import projeto.monitoramentoestoque.database.InsumoDatabase;
import projeto.monitoramentoestoque.model.Consumo;
import projeto.monitoramentoestoque.model.Insumo;
import projeto.monitoramentoestoque.model.SolicitacaoNovoConsumoEntrada;
import projeto.monitoramentoestoque.recyclerview.adapter.ListaInsumosAdapter;

public class FormularioNovoConsumoActivity extends AppCompatActivity {
    private String tituloAppBar;
    private EditText data;
    private EditText quantidade;
    private Context context;

    private Insumo insumo;

    private RoomConsumoDAO dao;
    private RoomInsumoDAO daoInsumo;
    private double valorConsumo;

    private ListaInsumosAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_novo_consumo);

        context = getApplicationContext();
        Intent insercaoSelecionada = getIntent();

        if(insercaoSelecionada.hasExtra(CHAVE_REQUISICAO)){
            SolicitacaoNovoConsumoEntrada solicitacao = (SolicitacaoNovoConsumoEntrada) insercaoSelecionada.getSerializableExtra(CHAVE_REQUISICAO);
            tituloAppBar = solicitacao.getTipoDeSolicitacao();

            setTitle(tituloAppBar);

            insumo = solicitacao.getInsumo();
            data = findViewById(R.id.formulario_novo_consumo_data);
            quantidade = findViewById(R.id.formulario_novo_consumo_quantidade);

            Button botaoInserir = findViewById(R.id.formulario_novo_consumo_botao_inserir);

            botaoInserir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(tituloAppBar.equals(CHAVE_REQUISICAO_INSERE_NOVO_CONSUMO)){

                        daoInsumo = InsumoDatabase.getInstance(context).getRoomInsumoGeralDAO();
                        adapter = new ListaInsumosAdapter(context, daoInsumo.todos());

                        valorConsumo = Double.parseDouble(quantidade.getText().toString());
                        solicitacao.getInsumo().setEstoqueAtual(insumo.getEstoqueAtual() - valorConsumo);

                        daoInsumo.altera(insumo);

                        dao = InsumoDatabase.getInstance(context).getRoomHistoricoConsumoDAO();
                        Consumo consumo = new Consumo(data.getText().toString(), Double.parseDouble(quantidade.getText().toString()), solicitacao.getInsumo().getId());
                        dao.salvaConsumo(consumo);
                        Toast.makeText(context,"Consumo adicionado!", Toast.LENGTH_LONG).show();

                        adapter.atualiza(daoInsumo.todos());
                    }
                    finish();
                }
            });
        }
    }
}