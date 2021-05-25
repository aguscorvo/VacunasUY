package vacunasuy.componentecentral.dao;

import java.util.List;

import javax.ejb.LocalBean;
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
import vacunasuy.componentecentral.entity.VacunatorioGeo;

@Singleton
@LocalBean
public class VacunatorioGeoDAOImpl implements IVacunatorioGeoDAO {

	@PersistenceContext(name = "LaboratorioTSE")
	private EntityManager em;
	
	public List<VacunatorioGeo> listar(){
		Query consulta = em.createQuery("SELECT v FROM VacunatorioGeo v");
    	return consulta.getResultList();
	}
	
	@Override
	public VacunatorioGeo listarPorId(Long id) {
		return em.find(VacunatorioGeo.class, id);
	}
	
	@Override
	public VacunatorioGeo crear(VacunatorioGeo vacunatorio) {
		em.persist(vacunatorio);
		return vacunatorio;
	}
	
	@Override
	public VacunatorioGeo editar(VacunatorioGeo vacunatorio) {
		em.persist(vacunatorio);
		return vacunatorio;
	}
	
	@Override
	public void eliminar(VacunatorioGeo vacunatorio) {
		em.remove(vacunatorio);
	}
		
  @Override
	public Double distancia(Vacunatorio vacunatorio1, Vacunatorio vacunatorio2) {
  	Point ubicacion1 = Geometries.mkPoint(new G2D(vacunatorio1.getLongitud(), vacunatorio1.getLatitud()), CoordinateReferenceSystems.WGS84);
  	Point ubicacion2 = Geometries.mkPoint(new G2D(vacunatorio2.getLongitud(), vacunatorio2.getLatitud()), CoordinateReferenceSystems.WGS84);
  	Query consulta = em.createQuery("SELECT distance( CAST(:ubicacion1 AS org.geolatte.geom.Point), "
  			+ "CAST(:ubicacion2 AS org.geolatte.geom.Point) ) ");
  	consulta.setParameter("ubicacion1", ubicacion1);
  	consulta.setParameter("ubicacion2", ubicacion2);
  	return (double) consulta.getFirstResult();
  }
  
	@Override
	public List<Long> listarCercanos(Ubicacion ubicacion){
		Point ubicacionAux = Geometries.mkPoint(new G2D(ubicacion.getLongitud(), ubicacion.getLatitud()), CoordinateReferenceSystems.WGS84);
		Query consulta = em.createQuery("SELECT v.id "
				+ "FROM VacunatorioGeo v "
				+ "WHERE within(v.geom, buffer( CAST(:ubicacion AS org.geolatte.geom.Point),  :distancia) ) = TRUE" 
				);
		consulta.setParameter("distancia", ubicacion.getDistancia());
		consulta.setParameter("ubicacion", ubicacionAux);
		return consulta.getResultList();
	}

}
