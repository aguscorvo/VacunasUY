package vacunasuy.componentecentral.dao;

import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import vacunasuy.componentecentral.entity.Rol;
import vacunasuy.componentecentral.entity.Usuario;

@Singleton
public class RolDAOImpl implements IRolDAO{

	@PersistenceContext(name = "LaboratorioTSE")
	private EntityManager em;
	
	@Override
	public Rol listarPorId(Long id) {
		return em.find(Rol.class, id);
	}

	@Override
	public List<Rol> listar() {
		Query consulta = em.createQuery("SELECT r FROM Rol r");
		return consulta.getResultList();
	}
}
