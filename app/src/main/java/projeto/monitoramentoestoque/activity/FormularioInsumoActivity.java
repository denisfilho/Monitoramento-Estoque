package projeto.monitoramentoestoque.activity;

import static projeto.monitoramentoestoque.activity.InsumoActivityConstantes.CHAVE_INSUMO;
import static projeto.monitoramentoestoque.activity.InsumoActivityConstantes.CODIGO_RESULTADO_INSUMO_CRIADO;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import projeto.monitoramentoestoque.R;
import projeto.monitoramentoestoque.converter.CalendarStringConverter;
import projeto.monitoramentoestoque.model.entities.Insumo;

public class FormularioInsumoActivity extends AppCompatActivity {


    public static final String TITULO_APPBAR = "Novo Insumo";
    private EditText nome;
    private EditText unidade;
    private EditText estoque;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_insumo);

        setTitle(TITULO_APPBAR);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_insumo_salva, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(ehMenuSalvaInsumo(item)){
            Insumo insumoCriado = criaInsumo();
            retornaInsumo(insumoCriado);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void retornaInsumo(Insumo insumo) {
        Intent resultadoInsercao = new Intent();
        resultadoInsercao.putExtra(CHAVE_INSUMO, insumo);
        setResult(CODIGO_RESULTADO_INSUMO_CRIADO, resultadoInsercao);
    }

    @NonNull
    private Insumo criaInsumo() {
        CalendarStringConverter conversor = new CalendarStringConverter();

        vinculaCamposDoFormulario();
        Insumo insumo = new Insumo(nome.getText().toString(), unidade.getText().toString(), Double.parseDouble(estoque.getText().toString()), Calendar.getInstance());
        return insumo;
    }

    private void vinculaCamposDoFormulario() {
        nome = findViewById(R.id.formulario_insumo_nome);
        unidade = findViewById(R.id.formulario_insumo_unidade);
        estoque = findViewById(R.id.formulario_insumo_estoque);
    }

    private boolean ehMenuSalvaInsumo(@NonNull MenuItem item) {
        return item.getItemId() == R.id.menu_formulario_insumo_ic_salva;
    }
}