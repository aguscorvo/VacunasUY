package vacunasuy.componentecentral.dao;

import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import vacunasuy.componentecentral.entity.Vacuna;

@Singleton
public class VacunaDAOImpl implements IVacunaDAO{

	@PersistenceContext(name = "LaboratorioTSE")
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Vacuna> listar() {
		Query consulta = em.createQuery("SELECT v FROM Vacuna v");
		return consulta.getResultList();
	}

	@Override
	public Vacuna listarPorId(Long id) {
		return em.find(Vacuna.class, id);
	}

	@Override
	public Vacuna crear(Vacuna vacuna) {
		em.persist(vacuna);
		return vacuna;
	}

	@Override
	public Vacuna editar(Vacuna vacuna) {
		em.persist(vacuna);
		return vacuna;
	}

	@Override
	public void eliminar(Vacuna vacuna) {
		em.remove(vacuna);
		
	}

}
