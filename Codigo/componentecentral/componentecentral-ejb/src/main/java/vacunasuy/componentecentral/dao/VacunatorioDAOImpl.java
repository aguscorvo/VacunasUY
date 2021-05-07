package vacunasuy.componentecentral.dao;

import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import vacunasuy.componentecentral.entity.Vacunatorio;

@Singleton
public class VacunatorioDAOImpl implements IVacunatorioDAO {

    @PersistenceContext(name = "LaboratorioTSE")
	private EntityManager em;
    
    @Override
    public List<Vacunatorio> listar(){
    	Query consulta = em.createQuery("SELECT v FROM Vacunatorio v");
    	return consulta.getResultList();
    }
    
    @Override
	public Vacunatorio listarPorId(Long id) {
		return em.find(Vacunatorio.class, id);
	}
    
    @Override
	public Vacunatorio crear(Vacunatorio vacunatorio) {
		em.persist(vacunatorio);
		return vacunatorio;
	}
    
    @Override
	public Vacunatorio editar(Vacunatorio vacunatorio) {
		em.persist(vacunatorio);
		return vacunatorio;
	}
    
    @Override
	public void eliminar(Vacunatorio vacunatorio) {
		em.remove(vacunatorio);
	}

}
