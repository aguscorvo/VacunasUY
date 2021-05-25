package vacunasuy.componentecentral.dao;

import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.geolatte.geom.G2D;
import org.geolatte.geom.Geometries;
import org.geolatte.geom.Point;
import org.geolatte.geom.crs.CoordinateReferenceSystems;

import vacunasuy.componentecentral.entity.Ubicacion;
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
    
    @Override
	public List<Vacunatorio> listarPorUbicacion(Long localidad, Long departamento){
    	Query consulta = em.createQuery("SELECT v FROM Vacunatorio v "
    			+ "WHERE v.localidad.id = :localidad AND v.departamento.id = :departamento");
		consulta.setParameter("localidad", localidad);
		consulta.setParameter("departamento", departamento);
    	return consulta.getResultList();
    }
    
    @Override
    public List<Vacunatorio> listarPorDepartamento(Long departamento){
    	Query consulta = em.createQuery("SELECT v FROM Vacunatorio v WHERE v.departamento.id = :departamento");
		consulta.setParameter("departamento", departamento);
    	return consulta.getResultList();
    }
    
}
