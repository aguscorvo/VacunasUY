package vacunasuy.componentecentral.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import vacunasuy.componentecentral.dto.ReporteActoVacunalDTO;
import vacunasuy.componentecentral.dto.ReporteEvolucionTiempoDTO;
import vacunasuy.componentecentral.dto.ReporteVacunaDTO;

@Singleton
public class ReporteDAOImpl implements IReporteDAO {

	@PersistenceContext(name = "LaboratorioTSE")
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<ReporteEvolucionTiempoDTO> listarPorEvolucionEnTiempo(String fechaInicio, String fechaFin,
			Long vacuna) {
		/*
		TypedQuery<ReporteEvolucionTiempoDTO> reporte = (TypedQuery<ReporteEvolucionTiempoDTO>) em
				.createNativeQuery("SELECT to_char(av.fecha, 'YY-mm-dd') as fecha, COUNT(av) as cantidad "
						+ "FROM actos_vacunales av " + "INNER JOIN planes_vacunacion pv ON av.fk_plan_vacunacion=pv.id "
						+ "INNER JOIN 	vacunas v ON pv.fk_vacuna= v.id "
						+ "WHERE fecha< :fechaFin AND fecha> :fechaInicio AND v.id= :vacuna " + "GROUP BY fecha "
						+ "ORDER BY fecha;");
		reporte.setParameter("fechaInicio", LocalDate.parse(fechaInicio));
		reporte.setParameter("fechaFin", LocalDate.parse(fechaFin));
		reporte.setParameter("vacuna", vacuna);
		return reporte.getResultList();
		*/
		Query reporte = em
				.createNativeQuery("SELECT to_char(av.fecha, 'YY-mm-dd') as fecha, COUNT(av) as cantidad "
						+ "FROM actos_vacunales av " + "INNER JOIN planes_vacunacion pv ON av.fk_plan_vacunacion=pv.id "
						+ "INNER JOIN 	vacunas v ON pv.fk_vacuna= v.id "
						+ "WHERE fecha< :fechaFin AND fecha> :fechaInicio AND v.id= :vacuna " + "GROUP BY fecha "
						+ "ORDER BY fecha;");
		reporte.setParameter("fechaInicio", LocalDate.parse(fechaInicio));
		reporte.setParameter("fechaFin", LocalDate.parse(fechaFin));
		reporte.setParameter("vacuna", vacuna);
		List<Object[]> datos = reporte.getResultList();

		List<ReporteEvolucionTiempoDTO> reportefinal = new ArrayList<ReporteEvolucionTiempoDTO>();
		Iterator<Object[]> it = datos.iterator();
		while (it.hasNext()) {
			Object[] line = it.next();
			ReporteEvolucionTiempoDTO eq = new ReporteEvolucionTiempoDTO();
			eq.setFecha(line[0].toString());
			eq.setCantidad(Integer.valueOf(line[1].toString()));
			reportefinal.add(eq);
		}
		return reportefinal;
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReporteActoVacunalDTO> listarPorEdad(String fechaInicio, String fechaFin, String edadInicio,
			String edadFin, Long enfermedad) {
		@SuppressWarnings("unchecked")
		/*
		 * TypedQuery<ReporteActoVacunalDTO> reporte =
		 * (TypedQuery<ReporteActoVacunalDTO>)
		 * em.createNativeQuery("SELECT v.nombre AS vacuna, COUNT(av) AS cantidad " +
		 * "FROM actos_vacunales av " +
		 * "INNER JOIN usuarios_actos_vacunales uav ON av.id = uav.actosvacunales_id " +
		 * "INNER JOIN usuarios u ON uav.usuario_id = u.id " +
		 * "INNER JOIN planes_vacunacion pv ON av.fk_plan_vacunacion=pv.id " +
		 * "INNER JOIN 	vacunas v ON pv.fk_vacuna= v.id " +
		 * "INNER JOIN enfermedades e ON v.fk_enfermedad = e.id " +
		 * "WHERE u.fechanacimiento> :edadFin AND u.fechanacimiento< :edadInicio AND e.id= :enfermedad "
		 * + "AND fecha< :fechaFin AND fecha> :fechaInicio " + "GROUP BY vacuna " +
		 * "ORDER BY cantidad;"); reporte.setParameter("fechaInicio",
		 * LocalDate.parse(fechaInicio)); reporte.setParameter("fechaFin",
		 * LocalDate.parse(fechaFin)); reporte.setParameter("edadInicio",
		 * LocalDate.parse(edadInicio)); reporte.setParameter("edadFin",
		 * LocalDate.parse(edadFin)); reporte.setParameter("enfermedad", enfermedad);
		 * return reporte.getResultList();
		 */

		Query reporte = em
				.createNativeQuery("SELECT v.nombre AS vacuna, COUNT(av) AS cantidad " + "FROM actos_vacunales av "
						+ "INNER JOIN usuarios_actos_vacunales uav ON av.id = uav.actosvacunales_id "
						+ "INNER JOIN usuarios u ON uav.usuario_id = u.id "
						+ "INNER JOIN planes_vacunacion pv ON av.fk_plan_vacunacion=pv.id "
						+ "INNER JOIN 	vacunas v ON pv.fk_vacuna= v.id "
						+ "INNER JOIN enfermedades e ON v.fk_enfermedad = e.id "
						+ "WHERE u.fechanacimiento> :edadFin AND u.fechanacimiento< :edadInicio AND e.id= :enfermedad "
						+ "AND fecha< :fechaFin AND fecha> :fechaInicio " + "GROUP BY vacuna " + "ORDER BY cantidad;");
		reporte.setParameter("fechaInicio", LocalDate.parse(fechaInicio));
		reporte.setParameter("fechaFin", LocalDate.parse(fechaFin));
		reporte.setParameter("edadInicio", LocalDate.parse(edadInicio));
		reporte.setParameter("edadFin", LocalDate.parse(edadFin));
		reporte.setParameter("enfermedad", enfermedad);
		List<Object[]> datos = reporte.getResultList();

		List<ReporteActoVacunalDTO> reportefinal = new ArrayList<ReporteActoVacunalDTO>();
		Iterator<Object[]> it = datos.iterator();
		while (it.hasNext()) {
			Object[] line = it.next();
			ReporteActoVacunalDTO eq = new ReporteActoVacunalDTO();
			eq.setVacuna(line[0].toString());
			eq.setCantidad(Integer.valueOf(line[1].toString()));
			reportefinal.add(eq);
		}
		return reportefinal;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReporteActoVacunalDTO> listarPorSectorLaboral(String fechaInicio, String fechaFin, Long sectorLaboral,
			Long enfermedad) {

		/*
		 * TypedQuery<ReporteActoVacunalDTO> reporte =
		 * (TypedQuery<ReporteActoVacunalDTO>)
		 * em.createNativeQuery("SELECT v.nombre AS vacuna, COUNT(av) AS cantidad " +
		 * "FROM actos_vacunales av " +
		 * "INNER JOIN usuarios_actos_vacunales uav ON av.id = uav.actosvacunales_id " +
		 * "INNER JOIN usuarios u ON uav.usuario_id = u.id " +
		 * "INNER JOIN sectores_laborales sl ON U.fk_sector_laboral=sl.id " +
		 * "INNER JOIN planes_vacunacion pv ON av.fk_plan_vacunacion=pv.id " +
		 * "INNER JOIN 	vacunas v ON pv.fk_vacuna= v.id " +
		 * "INNER JOIN enfermedades e ON v.fk_enfermedad = e.id " +
		 * "WHERE sl.id= :sectorLaboral AND e.id= :enfermedad AND fecha< :fechaFin AND fecha> :fechaInicio "
		 * + "GROUP BY vacuna " + "ORDER BY cantidad;");
		 * reporte.setParameter("fechaInicio", LocalDate.parse(fechaInicio));
		 * reporte.setParameter("fechaFin", LocalDate.parse(fechaFin));
		 * reporte.setParameter("sectorLaboral", sectorLaboral);
		 * reporte.setParameter("enfermedad", enfermedad); return
		 * reporte.getResultList();
		 */

		Query reporte = em.createNativeQuery("SELECT v.nombre AS vacuna, COUNT(av) AS cantidad "
				+ "FROM actos_vacunales av "
				+ "INNER JOIN usuarios_actos_vacunales uav ON av.id = uav.actosvacunales_id "
				+ "INNER JOIN usuarios u ON uav.usuario_id = u.id "
				+ "INNER JOIN sectores_laborales sl ON U.fk_sector_laboral=sl.id "
				+ "INNER JOIN planes_vacunacion pv ON av.fk_plan_vacunacion=pv.id "
				+ "INNER JOIN 	vacunas v ON pv.fk_vacuna= v.id "
				+ "INNER JOIN enfermedades e ON v.fk_enfermedad = e.id "
				+ "WHERE sl.id= :sectorLaboral AND e.id= :enfermedad AND fecha< :fechaFin AND fecha> :fechaInicio "
				+ "GROUP BY vacuna " + "ORDER BY cantidad;");
		reporte.setParameter("fechaInicio", LocalDate.parse(fechaInicio));
		reporte.setParameter("fechaFin", LocalDate.parse(fechaFin));
		reporte.setParameter("sectorLaboral", sectorLaboral);
		reporte.setParameter("enfermedad", enfermedad);
		List<Object[]> datos = reporte.getResultList();

		List<ReporteActoVacunalDTO> reportefinal = new ArrayList<ReporteActoVacunalDTO>();
		Iterator<Object[]> it = datos.iterator();
		while (it.hasNext()) {
			Object[] line = it.next();
			ReporteActoVacunalDTO eq = new ReporteActoVacunalDTO();
			eq.setVacuna(line[0].toString());
			eq.setCantidad(Integer.valueOf(line[1].toString()));
			reportefinal.add(eq);
		}
		return reportefinal;
	}

}
