package projeto.monitoramentoestoque.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import projeto.monitoramentoestoque.dao.RoomInsumoDAO;
import projeto.monitoramentoestoque.model.Insumo;

@Database(entities = {Insumo.class}, version = 1, exportSchema = false)
public abstract class InsumoDatabase extends RoomDatabase {
    public abstract RoomInsumoDAO getRoomInsumoDAO();

}
