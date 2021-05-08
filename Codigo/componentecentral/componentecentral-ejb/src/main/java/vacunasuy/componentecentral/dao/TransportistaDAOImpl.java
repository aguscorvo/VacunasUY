package vacunasuy.componentecentral.dao;

import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import vacunasuy.componentecentral.entity.Transportista;


@Singleton
public class TransportistaDAOImpl implements ITransportistaDAO {

	@PersistenceContext(name = "LaboratorioTSE")
	private EntityManager em;
	
	@Override
	public List<Transportista> listar(){
		Query consulta = em.createQuery("SELECT t FROM Transportista t");
		return consulta.getResultList();
	}
	
	@Override
	public Transportista listarPorId(Long id) {
		return em.find(Transportista.class, id);
	}
	
	@Override
	public Transportista crear(Transportista transportista) {
		em.persist(transportista);
		return transportista;
	}
	
	@Override
	public Transportista editar(Transportista transportista) {
		em.persist(transportista);
		return transportista;
	}
	
	@Override
	public void eliminar(Transportista transportista) {
		em.remove(transportista);
	}

}
