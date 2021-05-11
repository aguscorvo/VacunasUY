package vacunasuy.componentecentral.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import vacunasuy.componentecentral.converter.LocalidadConverter;
import vacunasuy.componentecentral.dao.ILocalidadDAO;
import vacunasuy.componentecentral.dto.LocalidadCrearDTO;
import vacunasuy.componentecentral.dto.LocalidadDTO;
import vacunasuy.componentecentral.entity.Localidad;
import vacunasuy.componentecentral.exception.VacunasUyException;


@Stateless
public class LocalidadServiceImpl implements ILocalidadService {

	@EJB 
	private ILocalidadDAO localidadDAO;
			
	@EJB
	private LocalidadConverter localidadConverter;
	
	@Override
	public List<LocalidadDTO> listar() throws VacunasUyException{
		try {
			return localidadConverter.fromEntity(localidadDAO.listar()); 
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
	@Override
	public LocalidadDTO listarPorId(Long id) throws VacunasUyException{
		//se valida que la localidad exista
		Localidad localidad = localidadDAO.listarPorId(id);
		if (localidad==null) throw new VacunasUyException("La localidad indicada no existe", VacunasUyException.NO_EXISTE_REGISTRO);
		try {
			return localidadConverter.fromEntity(localidad);
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}				
	}
	
	@Override
	public LocalidadDTO crear(LocalidadCrearDTO localidadDTO) throws VacunasUyException{
		Localidad localidad = localidadConverter.fromCrearDTO(localidadDTO);
		try {
			return localidadConverter.fromEntity(localidadDAO.crear(localidad));
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}					
	}
	
	@Override
	public LocalidadDTO editar(Long id, LocalidadCrearDTO localidadDTO) throws VacunasUyException{
		//se valida que la localidad exista
		Localidad localidad = localidadDAO.listarPorId(id);
		if(localidad==null) throw new VacunasUyException("La localidad indicada no existe", VacunasUyException.NO_EXISTE_REGISTRO);
		try {
			localidad.setNombre(localidadDTO.getNombre());
			return localidadConverter.fromEntity(localidadDAO.editar(localidad));
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
		
	}
	
	@Override
	public void eliminar (Long id) throws VacunasUyException{
		//se valida que la localidad exista
		Localidad localidad = localidadDAO.listarPorId(id);
		if(localidad==null) throw new VacunasUyException("La localidad indicada no existe", VacunasUyException.NO_EXISTE_REGISTRO);
		try {
			localidadDAO.eliminar(localidad);
		}catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
  
}
