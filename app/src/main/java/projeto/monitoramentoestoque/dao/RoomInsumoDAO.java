package projeto.monitoramentoestoque.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import projeto.monitoramentoestoque.model.entities.Insumo;

@Dao
public interface RoomInsumoDAO {

    @Query("SELECT * FROM insumo")
    List<Insumo> todos();

    @Insert
    void salvaInsumo(Insumo insumo);

    @Delete
    void remove(Insumo insumo);

    @Update
    void altera(Insumo insumo);
}
