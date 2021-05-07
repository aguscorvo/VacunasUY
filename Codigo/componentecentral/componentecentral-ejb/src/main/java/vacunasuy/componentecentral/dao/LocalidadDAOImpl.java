package vacunasuy.componentecentral.dao;

import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import vacunasuy.componentecentral.entity.Localidad;

@Singleton

public class LocalidadDAOImpl implements ILocalidadDAO {
	
	@PersistenceContext(name = "LaboratorioTSE")
	private EntityManager em;

	@Override
	public List<Localidad> listar(){
		Query consulta = em.createQuery("SELECT l FROM Localidad l");
		return consulta.getResultList();
	}
	
	@Override
	public Localidad listarPorId(Long id) {
		return em.find(Localidad.class, id);

	}
	
	@Override
	public Localidad crear(Localidad localidad) {
		em.persist(localidad);
		return localidad;
	}
	
	@Override
	public Localidad editar(Localidad localidad) {
		em.persist(localidad);
		return localidad;
	}
	
	@Override
	public void eliminar(Localidad localidad) {
		em.remove(localidad);
	}
}
