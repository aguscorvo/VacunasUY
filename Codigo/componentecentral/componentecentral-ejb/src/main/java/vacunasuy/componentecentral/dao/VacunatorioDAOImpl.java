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
	public List<Vacunatorio> listarCercanos(Ubicacion ubicacion){
    	Point ubicacionAux = Geometries.mkPoint(new G2D(ubicacion.getLongitud(), ubicacion.getLatitud()), CoordinateReferenceSystems.WGS84);
    	Query consulta = em.createQuery("SELECT v "
    			+ "FROM Vacunatorio v "
    			+ "WHERE within(v.geom, buffer( CAST(:ubicacion AS org.geolatte.geom.Point),  :distancia) ) = TRUE" 
    			);
    	consulta.setParameter("distancia", ubicacion.getDistancia());
    	consulta.setParameter("ubicacion", ubicacionAux);
    	return consulta.getResultList();
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
    
    @Override
	public Double distancia(Vacunatorio vacunatorio1, Vacunatorio vacunatorio2) {
    	Point ubicacion1 = Geometries.mkPoint(new G2D(vacunatorio1.getLongitud(), vacunatorio1.getLatitud()), CoordinateReferenceSystems.WGS84);
    	Point ubicacion2 = Geometries.mkPoint(new G2D(vacunatorio2.getLongitud(), vacunatorio2.getLatitud()), CoordinateReferenceSystems.WGS84);
    	    	Query consulta = em.createQuery("SELECT v "
    			+ "FROM Vacunatorio v "
    			+ "WHERE distance( CAST(:ubicacion1 AS org.geolatte.geom.Point), CAST(:ubicacion2 AS org.geolatte.geom.Point) ) " 
    			);
    	consulta.setParameter("ubicacion1", ubicacion1);
    	consulta.setParameter("ubicacion2", ubicacion2);
    	return (double) consulta.getFirstResult();
    }



}
