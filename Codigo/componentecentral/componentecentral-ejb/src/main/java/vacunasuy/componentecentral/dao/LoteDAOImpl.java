package vacunasuy.componentecentral.dao;

import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import vacunasuy.componentecentral.entity.Lote;

@Singleton
public class LoteDAOImpl implements ILoteDAO {

	@PersistenceContext(name = "LaboratorioTSE")
	private EntityManager em;
	
	@Override
	public List<Lote> listar() {
		Query consulta = em.createQuery("SELECT l FROM Lote l");
		return consulta.getResultList();
	}

	@Override
	public Lote listarPorId(Long id) {
		return em.find(Lote.class, id);
	}

	@Override
	public Lote crear(Lote lote) {
		em.persist(lote);
		return lote;
	}

	@Override
	public Lote editar(Lote lote) {
		em.persist(lote);
		return lote;
	}

	@Override
	public void eliminar(Lote lote) {
		em.remove(lote);
	}
	
}
