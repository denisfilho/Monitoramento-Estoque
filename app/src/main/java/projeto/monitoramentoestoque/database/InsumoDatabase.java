package projeto.monitoramentoestoque.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import projeto.monitoramentoestoque.dao.RoomConsumoDAO;
import projeto.monitoramentoestoque.dao.RoomEntradaDAO;
import projeto.monitoramentoestoque.dao.RoomInsumoDAO;
import projeto.monitoramentoestoque.model.Consumo;
import projeto.monitoramentoestoque.model.Entrada;
import projeto.monitoramentoestoque.model.Insumo;

@Database(entities = {Insumo.class, Entrada.class, Consumo.class}, version = 1, exportSchema = false)
public abstract class InsumoDatabase extends RoomDatabase {

    public static final String NOME_BANCO_DE_DADOS = "insumo.bd";

    public abstract RoomInsumoDAO getRoomInsumoGeralDAO();
    public abstract RoomEntradaDAO getRoomHistoricoEntradaDAO();
    public abstract RoomConsumoDAO getRoomHistoricoConsumoDAO();

    public static InsumoDatabase getInstance(Context context){
        return Room.databaseBuilder(context, InsumoDatabase.class, NOME_BANCO_DE_DADOS).allowMainThreadQueries().build();
    }

}
