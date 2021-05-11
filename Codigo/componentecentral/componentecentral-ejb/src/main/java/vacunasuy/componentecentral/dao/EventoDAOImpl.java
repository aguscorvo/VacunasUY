package vacunasuy.componentecentral.dao;

import java.util.List;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import vacunasuy.componentecentral.entity.Evento;

@Singleton
public class EventoDAOImpl implements IEventoDAO {

	@PersistenceContext(name = "LaboratorioTSE")
	private EntityManager em;
	
	@Override
	public List<Evento> listar() {
		Query consulta = em.createQuery("SELECT e FROM Evento e");
		return consulta.getResultList();
	}

	@Override
	public Evento listarPorId(Long id) {
		return em.find(Evento.class, id);
	}

	@Override
	public Evento crear(Evento evento) {
		em.persist(evento);
		return evento;
	}

	@Override
	public Evento editar(Evento evento) {
		em.persist(evento);
		return evento;
	}

	@Override
	public void eliminar(Evento evento) {
		em.remove(evento);
	}

}
