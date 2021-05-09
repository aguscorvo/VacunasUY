package vacunasuy.componentecentral.dao;

import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import vacunasuy.componentecentral.entity.Proveedor;

@Singleton
public class ProveedorDAOImpl implements IProveedorDAO {

	@PersistenceContext(name = "LaboratorioTSE")
	private EntityManager em;
	
	@Override
	public List<Proveedor> listar(){
		Query consulta = em.createQuery("SELECT p FROM Proveedor p");
    	return consulta.getResultList();
	}
	
	@Override
	public Proveedor listarPorId(Long id) {
		return em.find(Proveedor.class, id);
	}
	
	@Override
	public Proveedor crear(Proveedor proveedor) {
		em.persist(proveedor);
		return proveedor;
	}
	
	@Override
	public Proveedor editar(Proveedor proveedor) {
		em.persist(proveedor);
		return proveedor;
	}
	
	@Override
	public void eliminar(Proveedor proveedor) {
		em.remove(proveedor);
	}
   

}
