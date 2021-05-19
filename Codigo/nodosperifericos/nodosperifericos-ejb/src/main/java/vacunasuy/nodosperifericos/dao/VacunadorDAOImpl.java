package vacunasuy.nodosperifericos.dao;

import java.time.LocalDate;
import java.util.List;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import vacunasuy.nodosperifericos.entity.Vacunador;

@Singleton
public class VacunadorDAOImpl implements IVacunadorDAO {

	@PersistenceContext(name = "LaboratorioTSE")
	private EntityManager em;
	
	@Override
	public List<Vacunador> listar() {
		Query consulta = em.createQuery("SELECT v FROM Vacunador v");
    	return consulta.getResultList();
	}
	
	@Override
	public Vacunador crear(Vacunador vacunador) {
		em.persist(vacunador);
		return vacunador;
	}
	
	public void eliminarRegistros(String fecha, Long idVacunatorio) {
		Query consulta = em.createQuery("DELETE FROM Vacunador v WHERE v.fechaAsignacion = :fecha AND v.idVacunatorio = :idVacunatorio");
		consulta.setParameter("fecha", LocalDate.parse(fecha));
		consulta.setParameter("idVacunatorio", idVacunatorio);
		consulta.executeUpdate();
	}

}
