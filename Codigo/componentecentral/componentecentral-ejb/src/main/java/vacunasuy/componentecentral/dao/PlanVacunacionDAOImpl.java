package vacunasuy.componentecentral.dao;

import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import vacunasuy.componentecentral.entity.PlanVacunacion;

@Singleton
public class PlanVacunacionDAOImpl implements IPlanVacunacionDAO {

	@PersistenceContext(name = "LaboratorioTSE")
	private EntityManager em;

	@Override
	public List<PlanVacunacion> listar() {
		Query consulta = em.createQuery("SELECT p FROM PlanVacunacion p");
		return consulta.getResultList();
	}

	@Override
	public PlanVacunacion listarPorId(Long id) {
		return em.find(PlanVacunacion.class, id);
	}

	@Override
	public PlanVacunacion crear(PlanVacunacion plan) {
		em.persist(plan);
		return plan;
	}

	@Override
	public PlanVacunacion editar(PlanVacunacion plan) {
		em.persist(plan);
		return plan;
	}

	@Override
	public void eliminar(PlanVacunacion plan) {
		em.remove(plan);
	}
	
}
