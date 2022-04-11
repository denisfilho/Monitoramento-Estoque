package projeto.monitoramentoestoque.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import projeto.monitoramentoestoque.dao.RoomInsumoDAO;
import projeto.monitoramentoestoque.model.Insumo;

@Database(entities = {Insumo.class}, version = 1, exportSchema = false)
public abstract class InsumoDatabase extends RoomDatabase {
    public abstract RoomInsumoDAO getRoomInsumoDAO();

//    public abstract RoomInsumoDAO getInsumoDAO();
//    private static  InsumoDatabase insumoDB;
//
//    public static InsumoDatabase getInstance(Context context){
//        if(insumoDB == null){
//            insumoDB = buildDatabaseInstance(context);
//        }
//        return insumoDB;
//    }
//
//    @NonNull
//    private static InsumoDatabase buildDatabaseInstance(Context context) {
//        return Room.databaseBuilder(context,
//                InsumoDatabase.class, "insumo.bd").allowMainThreadQueries().fallbackToDestructiveMigration().build();
//    }
//
//    public static void cleanUp(){
//        insumoDB = null;
//    }

}
