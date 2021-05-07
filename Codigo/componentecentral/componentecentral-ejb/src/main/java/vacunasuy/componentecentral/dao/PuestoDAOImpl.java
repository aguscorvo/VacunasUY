package vacunasuy.componentecentral.dao;

import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import vacunasuy.componentecentral.entity.Puesto;

@Singleton
public class PuestoDAOImpl implements IPuestoDAO {

	@PersistenceContext(name = "LaboratorioTSE")
	private EntityManager em;
	
	@Override
	public List<Puesto> listar(){
		Query consulta = em.createQuery("SELECT p FROM Puesto p");
		return consulta.getResultList();
	}
		
	@Override
	public Puesto listarPorId(Long id) {
		return em.find(Puesto.class, id);
	}
	
	@Override
	public Puesto listarPorNumero(int numero) {
		Query consulta = em.createQuery("SELECT p FROM Puesto p WHERE p.numero = :numero");
		consulta.setParameter("numero", numero);
		return (Puesto) consulta.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public Puesto crear(Puesto puesto) {
		em.persist(puesto);
		return puesto;
	}
	
	@Override
	public Puesto editar(Puesto puesto) {
		em.persist(puesto);
		return puesto;
	}
	
	@Override
	public void eliminar(Puesto puesto) {
		em.remove(puesto);
	}

}
