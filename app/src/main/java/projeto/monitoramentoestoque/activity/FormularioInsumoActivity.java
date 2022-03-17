package projeto.monitoramentoestoque.activity;

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
        if(item.getItemId() == R.id.menu_formulario_insumo_ic_salva){
            EditText nome = findViewById(R.id.formulario_insumo_nome);
            EditText estoque = findViewById(R.id.formulario_insumo_estoque);
            Insumo insumoCriado = new Insumo(nome.getText().toString(), Integer.parseInt(estoque.getText().toString()));
            Intent resultadoInsercao = new Intent();
            resultadoInsercao.putExtra("insumo", insumoCriado);
            setResult(2, resultadoInsercao);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}