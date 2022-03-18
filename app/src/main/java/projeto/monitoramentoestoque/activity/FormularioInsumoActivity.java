package projeto.monitoramentoestoque.activity;

import static projeto.monitoramentoestoque.activity.InsumoActivityConstantes.CHAVE_INSUMO;
import static projeto.monitoramentoestoque.activity.InsumoActivityConstantes.CODIGO_RESULTADO_INSUMO_CRIADO;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import projeto.monitoramentoestoque.R;
import projeto.monitoramentoestoque.dao.InsumoDAO;
import projeto.monitoramentoestoque.model.Insumo;

public class FormularioInsumoActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_insumo);

        setTitle("Novo Insumo");
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
        EditText nome = findViewById(R.id.formulario_insumo_nome);
        EditText estoque = findViewById(R.id.formulario_insumo_estoque);
        return new Insumo(nome.getText().toString(), Integer.parseInt(estoque.getText().toString()));
    }

    private boolean ehMenuSalvaInsumo(@NonNull MenuItem item) {
        return item.getItemId() == R.id.menu_formulario_insumo_ic_salva;
    }
}