package vacunasuy.componentecentral.dao;


import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import vacunasuy.componentecentral.entity.Pais;

@Singleton
public class PaisDAOImpl implements IPaisDAO {

	@PersistenceContext(name = "LaboratorioTSE")
	private EntityManager em;
		
	@Override
	public List<Pais> listar(){
		Query consulta = em.createQuery("SELECT p FROM Pais p");
    	return consulta.getResultList();
	}
	
	public Pais listarPorId(Long id) {
		return em.find(Pais.class, id);
	}

}
