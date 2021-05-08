package vacunasuy.componentecentral.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import vacunasuy.componentecentral.entity.ActoVacunal;

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
