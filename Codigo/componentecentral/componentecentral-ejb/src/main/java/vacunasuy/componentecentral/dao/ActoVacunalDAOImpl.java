package vacunasuy.componentecentral.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import vacunasuy.componentecentral.dto.ActoVacunalCertificadoDTO;
import vacunasuy.componentecentral.entity.ActoVacunal;
import vacunasuy.componentecentral.entity.Vacuna;

@Singleton
public class ActoVacunalDAOImpl implements IActoVacunalDAO {

	@PersistenceContext(name = "LaboratorioTSE")
	private EntityManager em;
	
	@Override
	public List<ActoVacunal> listar(){
		Query consulta = em.createQuery("SELECT a FROM ActoVacunal a");
		return consulta.getResultList();
	}
	
	@Override
	public ActoVacunal listarPorId(Long id) {
		return em.find(ActoVacunal.class, id);
	}
	
	@Override
	public List<ActoVacunalCertificadoDTO> listarActosVacunalesPorUsuarioEnfermedad(Long idUsuario, Long idEnfermedad) {
		TypedQuery<ActoVacunalCertificadoDTO> actosVacunales = (TypedQuery<ActoVacunalCertificadoDTO>) 
				em.createNativeQuery("SELECT a.fecha, v.nombre FROM usuarios_actos_vacunales u "
						+ "INNER JOIN actos_vacunales a ON u.actosvacunales_id = a.id "
						+ "INNER JOIN planes_vacunacion p ON a.fk_plan_vacunacion = p.id "
						+ "INNER JOIN vacunas v ON p.fk_vacuna = v.id "
						+ "INNER JOIN enfermedades e ON v.fk_enfermedad = e.id "
						+ "WHERE u.usuario_id = :idUsuario AND e.id = :idEnfermedad "
						+ "ORDER BY fecha DESC", "ActoVacunalCertificadoDTOMapping");
		actosVacunales.setParameter("idUsuario", idUsuario);
		actosVacunales.setParameter("idEnfermedad", idEnfermedad);
		return actosVacunales.getResultList();
	}
	
	@Override
	public ActoVacunal crear(ActoVacunal actoVacunal) {
		em.persist(actoVacunal);
		return actoVacunal;		
	}
	
	@Override
	public ActoVacunal editar(ActoVacunal actoVacunal) {
		em.persist(actoVacunal);
		return actoVacunal;
	}
	
	@Override
	public void eliminar(ActoVacunal actoVacunal) {
		em.remove(actoVacunal);		
	}
	
}
