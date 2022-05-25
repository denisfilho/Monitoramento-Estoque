package projeto.monitoramentoestoque.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import projeto.monitoramentoestoque.converter.CalendarStringConverter;
import projeto.monitoramentoestoque.dao.RoomConsumoDAO;
import projeto.monitoramentoestoque.dao.RoomEntradaDAO;
import projeto.monitoramentoestoque.dao.RoomInsumoDAO;
import projeto.monitoramentoestoque.model.entities.Consumo;
import projeto.monitoramentoestoque.model.entities.Entrada;
import projeto.monitoramentoestoque.model.entities.Insumo;

@Database(entities = {Insumo.class, Entrada.class, Consumo.class}, version = 1, exportSchema = false)
@TypeConverters(CalendarStringConverter.class)
public abstract class InsumoDatabase extends RoomDatabase {

    public static final String NOME_BANCO_DE_DADOS = "insumo.bd";

    public abstract RoomInsumoDAO getRoomInsumoGeralDAO();
    public abstract RoomEntradaDAO getRoomHistoricoEntradaDAO();
    public abstract RoomConsumoDAO getRoomHistoricoConsumoDAO();

    public static InsumoDatabase getInstance(Context context){
        return Room.databaseBuilder(context, InsumoDatabase.class, NOME_BANCO_DE_DADOS).allowMainThreadQueries().build();
    }

}
