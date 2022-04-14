package projeto.monitoramentoestoque.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import projeto.monitoramentoestoque.model.Consumo;
@Dao
public interface RoomConsumoDAO {
    @Transaction
    @Query("SELECT * FROM CONSUMO c WHERE c.insumoId = (:insumoIdEnviado)")
    List<Consumo> buscaHistoricoDeConsumo(Long insumoIdEnviado);

    @Insert
    void salvaConsumo(Consumo consumo);

    @Query("SELECT * FROM CONSUMO")
    List<Consumo> todosConsumos();
}
