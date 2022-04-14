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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import projeto.monitoramentoestoque.R;
import projeto.monitoramentoestoque.dao.ConsumoDAO;
import projeto.monitoramentoestoque.dao.RoomEntradaDAO;
import projeto.monitoramentoestoque.database.InsumoDatabase;
import projeto.monitoramentoestoque.model.Consumo;
import projeto.monitoramentoestoque.model.Entrada;
import projeto.monitoramentoestoque.model.SolicitacaoNovoConsumoEntrada;
import projeto.monitoramentoestoque.recyclerview.adapter.ListaInsumosAdapter;

public class FormularioNovaEntradaConsumoActivity extends AppCompatActivity {

    private String tituloAppBar;
    private EditText data;
    private EditText quantidade;
    private Context context;

    private RoomEntradaDAO dao;

    private ListaInsumosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nova_entrada_consumo);
        context = getApplicationContext();
        Intent insercaoSelecionada = getIntent();
        if (insercaoSelecionada.hasExtra(CHAVE_REQUISICAO)){
            SolicitacaoNovoConsumoEntrada solicitacao = (SolicitacaoNovoConsumoEntrada) insercaoSelecionada.getSerializableExtra(CHAVE_REQUISICAO);
            tituloAppBar = solicitacao.getTipoDeSolicitacao();
            setTitle(tituloAppBar);

            data = findViewById(R.id.formulario_nova_entrada_consumo_data);
            quantidade = findViewById(R.id.formulario_nova_entrada_consumo_quantidade);
            Button botaoInserirNovaEntradaConsumo = findViewById(R.id.formulario_nova_entrada_consumo_botao_inserir);
            Toast.makeText(getApplicationContext(), "ID: " + solicitacao.getInsumo().getId(), Toast.LENGTH_LONG).show();

            botaoInserirNovaEntradaConsumo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(tituloAppBar.equals(CHAVE_REQUISICAO_INSERE_NOVA_ENTRADA)) {
                        dao = InsumoDatabase.getInstance(context).getRoomHistoricoEntradaDAO();
                        dao.salvaEntrada(new Entrada((data.getText().toString()), Double.parseDouble(quantidade.getText().toString()), solicitacao.getInsumo().getId()));
                        Toast.makeText(context,"Entrada adicionada!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        ConsumoDAO dao = new ConsumoDAO();
                        try {
                            Calendar dataConvertida = ConverterStringParaCalendar();
                            dao.insere(new Consumo(dataConvertida, Double.parseDouble(quantidade.getText().toString())));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(context,"Consumo adicionado!", Toast.LENGTH_LONG).show();
                    }
                    finish();
                }
            });
        }
    }

    @NonNull
    private Calendar ConverterStringParaCalendar() throws ParseException {
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        String dataString = data.getText().toString();
        Calendar dataConvertida = Calendar.getInstance();
        dataConvertida.setTime(formatoData.parse(dataString));
        return dataConvertida;
    }
}