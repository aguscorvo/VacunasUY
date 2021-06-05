package vacunasuy.componentecentral.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import vacunasuy.componentecentral.entity.Agenda;


@Singleton
public class AgendaDAOImpl implements IAgendaDAO {

	@PersistenceContext(name = "LaboratorioTSE")
	private EntityManager em;
	
	@Override
	public List<Agenda> listar(){
		Query consulta = em.createQuery("SELECT a FROM Agenda a");
		return consulta.getResultList();
	}
	
	@Override
	public Agenda listarPorId(Long id) {
		return em.find(Agenda.class, id);	
	}
	
	@Override
	public Agenda crear(Agenda agenda) {
		em.persist(agenda);
		return agenda;
	}
	
	@Override
	public Agenda editar(Agenda agenda) {
		em.persist(agenda);
		return agenda;
	}
	
	@Override
	public void eliminar(Agenda agenda) {
		System.out.println("adentro de eliminar en AgendaDAO, agenda con id: " + agenda.getId());
		em.remove(agenda);
	}
	
}
