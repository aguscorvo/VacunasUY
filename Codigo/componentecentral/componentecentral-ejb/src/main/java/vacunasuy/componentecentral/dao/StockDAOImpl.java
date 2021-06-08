package vacunasuy.componentecentral.dao;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import vacunasuy.componentecentral.entity.Stock;

@Singleton
public class StockDAOImpl implements IStockDAO {

	@PersistenceContext(name = "LaboratorioTSE")
	private EntityManager em;
	
	@Override
	public Stock listarStockPorVacuna(Long idVacunatorio, Long idVacuna) {
		Query consulta = em.createQuery("SELECT s FROM Stock s WHERE s.id.idVacunatorio = :idVacunatorio AND s.id.idVacuna = :idVacuna");
		consulta.setParameter("idVacunatorio", idVacunatorio);
		consulta.setParameter("idVacuna", idVacuna);
		return (Stock) consulta.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public Stock actualizar(Stock stock) {
		em.persist(stock);
		return stock;
	}
	
}
