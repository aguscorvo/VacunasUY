package vacunasuy.componentecentral.dao;

import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import vacunasuy.componentecentral.entity.Departamento;

@Singleton
public class DepartamentoDAOImpl implements IDepartamentoDAO {

	@PersistenceContext(name = "LaboratorioTSE")
	private EntityManager em;	
    
	@Override
	public List<Departamento> listar(){
		Query consulta = em.createQuery("SELECT d FROM Departamento d");
		return consulta.getResultList();
	}
	
	@Override
	public Departamento listarPorId(Long id) {
		return em.find(Departamento.class, id);

	}
	
	@Override
	public Departamento crear(Departamento departamento) {
		em.persist(departamento);
		return departamento;
	}
	
	@Override
	public Departamento editar (Departamento departamento) {
		em.persist(departamento);
		return departamento;		
	}
	
	@Override
	public void eliminar (Departamento departamento) {
		em.remove(departamento);
	}

}
