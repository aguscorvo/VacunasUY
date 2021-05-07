package vacunasuy.componentecentral.dao;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import vacunasuy.componentecentral.entity.Rol;

@Singleton
public class RolDAOImpl implements IRolDAO{

	@PersistenceContext(name = "LaboratorioTSE")
	private EntityManager em;
	
	@Override
	public Rol listarPorId(Long id) {
		return em.find(Rol.class, id);
	}

}
