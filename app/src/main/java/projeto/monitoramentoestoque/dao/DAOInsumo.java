package projeto.monitoramentoestoque.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import projeto.monitoramentoestoque.model.Insumo;

@Repository
public interface DAOInsumo extends JpaRepository<Insumo, Long> {
}
