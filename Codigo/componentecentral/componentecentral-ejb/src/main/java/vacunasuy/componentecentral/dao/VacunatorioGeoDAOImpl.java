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
import vacunasuy.componentecentral.entity.VacunatorioGeo;

@Singleton
@LocalBean
public class VacunatorioGeoDAOImpl implements IVacunatorioGeoDAO {

	@PersistenceContext(name = "LaboratorioTSE")
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
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
  
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> listarCercanos(Ubicacion ubicacion){
		@SuppressWarnings("rawtypes")
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
