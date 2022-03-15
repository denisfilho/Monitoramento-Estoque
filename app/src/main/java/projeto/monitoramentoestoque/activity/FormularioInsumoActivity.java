package projeto.monitoramentoestoque.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import projeto.monitoramentoestoque.R;

public class FormularioInsumoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_insumo);

        setTitle("Novo Insumo");
    }
}