package projeto.monitoramentoestoque.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import projeto.monitoramentoestoque.model.Entrada;

@Dao
public interface RoomEntradaDAO {

    @Transaction
    @Query("SELECT * FROM ENTRADA e WHERE e.insumoId = (:insumoIdEnviado)")
    List<Entrada> buscaHistoricoDeEntrada(Long insumoIdEnviado);

    @Insert
    void salvaEntrada(Entrada entrada);

    @Query("SELECT * FROM ENTRADA")
    List<Entrada> todasEntradas();
}
