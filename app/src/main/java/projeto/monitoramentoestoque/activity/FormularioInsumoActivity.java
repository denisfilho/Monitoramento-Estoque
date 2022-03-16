package projeto.monitoramentoestoque.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;

import projeto.monitoramentoestoque.R;

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
}