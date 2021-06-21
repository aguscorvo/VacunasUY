package vacunasuy.componentecentral.dao;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.TypedQuery;

import vacunasuy.componentecentral.dto.ReporteVacunaDTO;
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

	@Override
	public List<ReporteVacunaDTO> listarStockVacunasDisponiblesParaEnviar() {
		TypedQuery<ReporteVacunaDTO> reporte = (TypedQuery<ReporteVacunaDTO>) 
				em.createNativeQuery("SELECT nombre, SUM(cantidadDisponible) as cantidad "
						+ "FROM vacunas v INNER JOIN lotes l ON v.id = l.fk_vacuna "
						+ "GROUP BY v.nombre");
		return reporte.getResultList();
	}

	
	
	
	@Override
	public List<ReporteVacunaDTO> listarStockVacunaPorVacunatorios(Long idVacuna) {
		TypedQuery<ReporteVacunaDTO> reporte = (TypedQuery<ReporteVacunaDTO>) 
				em.createNativeQuery("SELECT va.nombre, cantidad "
						+ "FROM vacunas v INNER JOIN stock s ON v.id = s.idvacuna INNER JOIN vacunatorios va ON s.idvacunatorio = va.id "
						+ "WHERE v.id = :idVacuna");
		reporte.setParameter("idVacuna", idVacuna);
		return reporte.getResultList();
	}

	@Override
	public List<ReporteVacunaDTO> listarStockVacunasPorVacunatorio(Long idVacunatorio) {
		TypedQuery<ReporteVacunaDTO> reporte = (TypedQuery<ReporteVacunaDTO>) 
				em.createNativeQuery("SELECT v.nombre, cantidad "
						+ "FROM vacunas v INNER JOIN stock s ON v.id = s.idvacuna INNER JOIN vacunatorios va ON s.idvacunatorio = va.id "
						+ "WHERE va.id = :idVacunatorio");
		reporte.setParameter("idVacunatorio", idVacunatorio);
		return reporte.getResultList();
	}
	
	
	
}
