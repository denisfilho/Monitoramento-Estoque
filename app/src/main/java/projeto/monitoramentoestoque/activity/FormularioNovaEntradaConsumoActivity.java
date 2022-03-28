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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import projeto.monitoramentoestoque.R;
import projeto.monitoramentoestoque.dao.ConsumoDAO;
import projeto.monitoramentoestoque.dao.EntradaDAO;
import projeto.monitoramentoestoque.model.Consumo;
import projeto.monitoramentoestoque.model.Entrada;

public class FormularioNovaEntradaConsumoActivity extends AppCompatActivity {

    private String tituloAppBar;
    private EditText data;
    private EditText quantidade;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nova_entrada_consumo);
        context = getApplicationContext();
        Intent insercaoSelecionada = getIntent();
        if (insercaoSelecionada.hasExtra(CHAVE_REQUISICAO)){
            tituloAppBar = insercaoSelecionada.getStringExtra(CHAVE_REQUISICAO);
            setTitle(tituloAppBar);

            data = findViewById(R.id.formulario_nova_entrada_consumo_data);
            quantidade = findViewById(R.id.formulario_nova_entrada_consumo_quantidade);
            Button botaoInserirNovaEntradaConsumo = findViewById(R.id.formulario_nova_entrada_consumo_botao_inserir);

            botaoInserirNovaEntradaConsumo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(tituloAppBar.equals(CHAVE_REQUISICAO_INSERE_NOVA_ENTRADA)) {
                        EntradaDAO dao = new EntradaDAO();
                        try {
                            SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
                            String dataString = data.getText().toString();
                            Calendar dataConvertida = Calendar.getInstance();
                            dataConvertida.setTime(formatoData.parse(dataString));
                            dao.insere(new Entrada(dataConvertida, Double.parseDouble(quantidade.getText().toString())));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(context,"Entrada adicionada!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        ConsumoDAO dao = new ConsumoDAO();
                        try {
                            SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
                            String dataString = data.getText().toString();
                            Calendar dataConvertida = Calendar.getInstance();
                            dataConvertida.setTime(formatoData.parse(dataString));
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
}