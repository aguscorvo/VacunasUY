package vacunasuy.componentecentral.dao;

import java.util.List;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import vacunasuy.componentecentral.entity.Enfermedad;

@Singleton
public class EnfermedadDAOImpl implements IEnfermedadDAO{
	
	@PersistenceContext(name = "LaboratorioTSE")
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Enfermedad> listar() {
		Query consulta = em.createQuery("SELECT e FROM Enfermedad e");
		return consulta.getResultList();
	}

	@Override
	public Enfermedad listarPorId(Long id) {
		return em.find(Enfermedad.class, id);
	}
	
	@Override
	public List<Enfermedad> listarEnfermedadesPorUsuario(Long idUsuario) {
		TypedQuery<Enfermedad> enfermedades = em.createNamedQuery("listarEnfermedadesPorUsuario", Enfermedad.class);
		enfermedades.setParameter("idUsuario", idUsuario);
		return enfermedades.getResultList();
	}

	@Override
	public Enfermedad crear(Enfermedad enfermedad) {
		em.persist(enfermedad);
		return enfermedad;
	}

	@Override
	public Enfermedad editar(Enfermedad enfermedad) {
		em.persist(enfermedad);
		return enfermedad;
	}

	@Override
	public void eliminar(Enfermedad enfermedad) {
		em.remove(enfermedad);
		
	}

}
