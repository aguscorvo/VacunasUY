package vacunasuy.componentecentral.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.geolatte.geom.G2D;
import org.geolatte.geom.Geometries;
import org.geolatte.geom.Point;
import org.geolatte.geom.crs.CoordinateReferenceSystems;

import vacunasuy.componentecentral.converter.UbicacionConverter;
import vacunasuy.componentecentral.converter.VacunatorioConverter;
import vacunasuy.componentecentral.converter.VacunatorioGeoConverter;
import vacunasuy.componentecentral.dao.IVacunatorioDAO;
import vacunasuy.componentecentral.dao.IVacunatorioGeoDAO;
import vacunasuy.componentecentral.dto.UbicacionDTO;
import vacunasuy.componentecentral.dto.VacunatorioDTO;
import vacunasuy.componentecentral.dto.VacunatorioGeoDTO;
import vacunasuy.componentecentral.entity.Vacunatorio;
import vacunasuy.componentecentral.entity.VacunatorioGeo;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Stateless
public class VacunatorioGeoServiceImpl implements IVacunatorioGeoService {

	@EJB
	private IVacunatorioGeoDAO vacunatorioGeoDAO;
	
	@EJB
	private IVacunatorioDAO vacunatorioDAO;
	
	@EJB
	private VacunatorioGeoConverter vacunatorioGeoConverter;
	
	@EJB
	private VacunatorioConverter vacunatorioConverter;
	
	@EJB
	private UbicacionConverter ubicacionConverter;
	
	@Override
	public List<VacunatorioGeoDTO> listar() throws VacunasUyException{
		try {
			return vacunatorioGeoConverter.fromEntity(vacunatorioGeoDAO.listar());
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
	@Override
	public VacunatorioGeoDTO listarPorId(Long id) throws VacunasUyException{
		//se valida que el vacunatorio exista
		VacunatorioGeo vacunatorio = vacunatorioGeoDAO.listarPorId(id);
		if (vacunatorio==null) throw new VacunasUyException("El vacunatorio indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		try {
			return vacunatorioGeoConverter.fromEntity(vacunatorio);
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}	
	}
	
	@Override
	public VacunatorioGeoDTO crear(VacunatorioDTO vacunatorioDTO) throws VacunasUyException{
		// se verifica que el vacunatorio exista
		if(vacunatorioDAO.listarPorId(vacunatorioDTO.getId()) == null) 
			throw new VacunasUyException("El vacunatorio indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		try {
			//GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326); // locationtech
			//Point point = factory.createPoint(new Coordinate(v.getLatitud(), v.getLongitud())); // locationtech
			Point point = Geometries.mkPoint(new G2D(vacunatorioDTO.getLongitud(), vacunatorioDTO.getLatitud()), CoordinateReferenceSystems.WGS84); // geolatte
			VacunatorioGeoDTO vacunatorioGeoDTO = new VacunatorioGeoDTO ();
			VacunatorioGeo vacunatorioGeoNuevo = vacunatorioGeoConverter.fromDTO(vacunatorioGeoDTO);
			vacunatorioGeoNuevo.setGeom(point);
		
			//se asocia vacunatorio a vacunatorioGeo
			Vacunatorio vacunatorio = vacunatorioDAO.listarPorId(vacunatorioDTO.getId());
			vacunatorioGeoNuevo.setVacunatorio(vacunatorio);
			
			return vacunatorioGeoConverter.fromEntity(vacunatorioGeoDAO.crear(vacunatorioGeoNuevo));
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
//	@Override
//	public VacunatorioGeoDTO editar(Long id) throws VacunasUyException{
//		
//	}
	
//	@Override
//	public void eliminar(Long id) throws VacunasUyException{
//		
//	}
	
	@Override
	public List<Long> listarCercanos(UbicacionDTO ubicacionDTO) throws VacunasUyException{
		try {
			//kms. a grados aprox.
			ubicacionDTO.setDistancia(ubicacionDTO.getDistancia()/111);
			return vacunatorioGeoDAO.listarCercanos(ubicacionConverter.fromDTO(ubicacionDTO));
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}

}
