package projeto.monitoramentoestoque;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import projeto.monitoramentoestoque.dao.InsumoDAO;
import projeto.monitoramentoestoque.model.Insumo;
import projeto.monitoramentoestoque.recyclerview.adapter.ListaInsumosAdapter;

public class ListaInsumosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_insumos);

        RecyclerView listaInsumos = findViewById(R.id.lista_insumos_recyclerview);

        InsumoDAO dao = new InsumoDAO();
        for (int i = 1; i <9; i++){
            dao.insere(new Insumo("Insumo " + i, i));
        }

        List<Insumo> todosInsumos = dao.todos();


        listaInsumos.setAdapter(new ListaInsumosAdapter(this, todosInsumos));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listaInsumos.setLayoutManager(layoutManager);
    }
}