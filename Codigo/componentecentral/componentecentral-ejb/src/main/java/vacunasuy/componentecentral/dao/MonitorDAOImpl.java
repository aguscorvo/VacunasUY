package vacunasuy.componentecentral.dao;

import java.math.BigInteger;
import java.util.List;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import vacunasuy.componentecentral.dto.MonitorEnfermedadPlanesDTO;
import vacunasuy.componentecentral.dto.MonitorEnfermedadVacunasDTO;
import vacunasuy.componentecentral.dto.MonitorVacunaDosisDTO;

@Singleton
public class MonitorDAOImpl implements IMonitorDAO {

	@PersistenceContext(name = "LaboratorioTSE")
	private EntityManager em;
	
	/* Filtro por enfermedad */
	/* 1 - Listar agendas para hoy */
	@Override
	public Long listarAgendasPorEnfermedad(Long idEnfermedad) {
		Query consulta = em.createNativeQuery("SELECT COUNT(*) AS cantidad "
				+ "FROM agendas a "
				+ "INNER JOIN planes_vacunacion p ON a.fk_plan_vacunacion = p.id "
				+ "INNER JOIN vacunas v ON p.fk_vacuna = v.id "
				+ "INNER JOIN enfermedades e ON v.fk_enfermedad = e.id "
				+ "WHERE e.id = :idEnfermedad "
				+ "AND a.fecha\\:\\:date = now()\\:\\:date;");
		consulta.setParameter("idEnfermedad", idEnfermedad);
		BigInteger res = (BigInteger) consulta.getSingleResult();
		return res.longValue();
	}

	/* 2 - Listar vacunados para hoy */
	@Override
	public Long listarVacunadosPorEnfermedad(Long idEnfermedad) {
		Query consulta = em.createNativeQuery("SELECT COUNT(*) AS cantidad "
				+ "FROM actos_vacunales a "
				+ "INNER JOIN planes_vacunacion p ON a.fk_plan_vacunacion = p.id "
				+ "INNER JOIN vacunas v ON p.fk_vacuna = v.id "
				+ "INNER JOIN enfermedades e ON v.fk_enfermedad = e.id "
				+ "WHERE e.id = :idEnfermedad "
				+ "AND a.fecha = now()\\:\\:date;");
		consulta.setParameter("idEnfermedad", idEnfermedad);
		BigInteger res = (BigInteger) consulta.getSingleResult();
		return res.longValue();
	}

	/* 3 - Listar vacunas por enfermedad */
	@Override
	public List<MonitorEnfermedadVacunasDTO> listarVacunasPorEnfermedad(Long idEnfermedad) {
		@SuppressWarnings("unchecked")
		TypedQuery<MonitorEnfermedadVacunasDTO> consulta = (TypedQuery<MonitorEnfermedadVacunasDTO>)
				em.createNativeQuery("SELECT v.nombre, COUNT(a.id) as cantidad "
				+ "FROM vacunas v "
				+ "LEFT JOIN planes_vacunacion p ON p.fk_vacuna = v.id "
				+ "LEFT JOIN actos_vacunales a ON a.fk_plan_vacunacion = p.id "
				+ "LEFT JOIN enfermedades e ON v.fk_enfermedad = e.id "
				+ "WHERE e.id = :idEnfermedad "
				+ "GROUP BY v.nombre "
				+ "ORDER BY cantidad DESC;");
		consulta.setParameter("idEnfermedad", idEnfermedad);
		return consulta.getResultList();
	}
	
	/* 4 - Listar planes por enfermedad */
	@Override
	public List<MonitorEnfermedadPlanesDTO> listarPlanesPorEnfermedad(Long idEnfermedad){
		@SuppressWarnings("unchecked")
		TypedQuery<MonitorEnfermedadPlanesDTO> consulta = (TypedQuery<MonitorEnfermedadPlanesDTO>)
				em.createNativeQuery("SELECT p.id, CONCAT(p.fechainicio\\:\\:date, ' - ', p.fechafin\\:\\:date) AS periodo, v.nombre AS vacuna "
						+ "FROM planes_vacunacion p "
						+ "INNER JOIN vacunas v ON p.fk_vacuna = v.id "
						+ "INNER JOIN enfermedades e ON v.fk_enfermedad = e.id "
						+ "WHERE e.id = :idEnfermedad "
						+ "ORDER BY fechainicio DESC;");
		consulta.setParameter("idEnfermedad", idEnfermedad);
		return consulta.getResultList();
	}

	/* Filtro por vacuna */
	/* 1 - Listar agendas para hoy por dósis */
	@Override
	public List<MonitorVacunaDosisDTO> listarAgendasPorDosis(Long idVacuna) {
		@SuppressWarnings("unchecked")
		TypedQuery<MonitorVacunaDosisDTO> consulta = (TypedQuery<MonitorVacunaDosisDTO>)
				em.createNativeQuery("SELECT nro_dosis AS nroDosis, COUNT(*) AS cantidad "
						+ "FROM agendas a "
						+ "INNER JOIN planes_vacunacion p ON a.fk_plan_vacunacion = p.id "
						+ "INNER JOIN vacunas v ON p.fk_vacuna = v.id "
						+ "WHERE v.id = :idVacuna AND fecha\\:\\:date = now()\\:\\:date "
						+ "GROUP BY nro_dosis;");
		consulta.setParameter("idVacuna", idVacuna);
		return consulta.getResultList();
	}

	/* 2 - Listar vacunados para hoy por dósis */
	@Override
	public List<MonitorVacunaDosisDTO> listarVacunadosPorDosis(Long idVacuna) {
		@SuppressWarnings("unchecked")
		TypedQuery<MonitorVacunaDosisDTO> consulta = (TypedQuery<MonitorVacunaDosisDTO>)
				em.createNativeQuery("SELECT nro_dosis AS nroDosis, COUNT(*) AS cantidad "
						+ "FROM actos_vacunales a "
						+ "INNER JOIN planes_vacunacion p ON a.fk_plan_vacunacion = p.id "
						+ "INNER JOIN vacunas v ON p.fk_vacuna = v.id "
						+ "WHERE v.id = :idVacuna AND fecha\\:\\:date = now()\\:\\:date "
						+ "GROUP BY nro_dosis;");
		consulta.setParameter("idVacuna", idVacuna);
		return consulta.getResultList();
	}
	
	
	/* Filtro por plan */
	/* 1 - Listar agendas para hoy */
	@Override
	public Long listarAgendasPorPlan(Long idPlan) {
		Query consulta = em.createNativeQuery("SELECT COUNT(*) "
				+ "FROM planes_vacunacion p "
				+ "INNER JOIN agendas a ON a.fk_plan_vacunacion = p.id "
				+ "WHERE p.id = :idPlan AND a.fecha = now()\\:\\:date;");
		consulta.setParameter("idPlan", idPlan);
		BigInteger res = (BigInteger) consulta.getSingleResult();
		return res.longValue();
	}

	/* 2 - Listar vacunados para hoy */
	@Override
	public Long listarVacunadosPorPlan(Long idPlan) {
		Query consulta = em.createNativeQuery("SELECT COUNT(*) "
				+ "FROM planes_vacunacion p "
				+ "INNER JOIN actos_vacunales a ON a.fk_plan_vacunacion = p.id "
				+ "WHERE p.id = :idPlan AND a.fecha = now()\\:\\:date;");
		consulta.setParameter("idPlan", idPlan);
		BigInteger res = (BigInteger) consulta.getSingleResult();
		return res.longValue();
	}

	/* 3 - Listar total de vacunados */
	@Override
	public Long listarTotalVacunadosPorPlan(Long idPlan) {
		Query consulta = em.createNativeQuery("SELECT COUNT(*) "
				+ "FROM planes_vacunacion p "
				+ "INNER JOIN actos_vacunales a ON a.fk_plan_vacunacion = p.id "
				+ "WHERE p.id = :idPlan");
		consulta.setParameter("idPlan", idPlan);
		BigInteger res = (BigInteger) consulta.getSingleResult();
		return res.longValue();
	}
	
}
