package vacunasuy.componentecentral.business;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import vacunasuy.componentecentral.converter.DepartamentoConverter;
import vacunasuy.componentecentral.dao.IDepartamentoDAO;
import vacunasuy.componentecentral.dao.ILocalidadDAO;
import vacunasuy.componentecentral.dto.DepartamentoCrearDTO;
import vacunasuy.componentecentral.dto.DepartamentoDTO;
import vacunasuy.componentecentral.entity.Departamento;
import vacunasuy.componentecentral.entity.Localidad;
import vacunasuy.componentecentral.exception.VacunasUyException;


@Stateless
public class DepartamentoServiceImpl implements IDepartamentoService {

	@EJB
	public IDepartamentoDAO departamentoDAO;
	
	@EJB
	public ILocalidadDAO localidadDAO;
	
	@EJB
	public DepartamentoConverter departamentoConverter;
	
	@Override
	public List<DepartamentoDTO> listar() throws VacunasUyException{
		try {
			return departamentoConverter.fromEntity(departamentoDAO.listar());
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
	@Override
	public DepartamentoDTO listarPorId(Long id) throws VacunasUyException{
		//se valida que el departamento exista
		Departamento departamento = departamentoDAO.listarPorId(id);
		if (departamento==null) throw new VacunasUyException("El departamento indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		try {
			return departamentoConverter.fromEntity(departamento);
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}		
	}
	
	@Override
	public DepartamentoDTO crear(DepartamentoCrearDTO departamentoDTO) throws VacunasUyException{
		//se valida que las localidades existan
		List<Localidad>  localidades = new ArrayList<Localidad>();
		for (Long id: departamentoDTO.getLocalidades()) {
			if(localidadDAO.listarPorId(id)==null) throw new VacunasUyException("La localidad indicada no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
			localidades.add(localidadDAO.listarPorId(id));					
		}
		Departamento departamento = departamentoConverter.fromCrearDTO(departamentoDTO);
		try {
			departamento.setLocalidades(localidades);
			return departamentoConverter.fromEntity(departamentoDAO.crear(departamento));
		}catch(Exception e){
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}		
	
	@Override
	public DepartamentoDTO editar(Long id, DepartamentoCrearDTO departamentoDTO) throws VacunasUyException{
		//se valida que el departamento exista
		Departamento departamento = departamentoDAO.listarPorId(id);
		if (departamento==null) throw new VacunasUyException("El departamento indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		List<Localidad>  localidades = new ArrayList<Localidad>();
		for (Long idLocalidad: departamentoDTO.getLocalidades()) {
				if(localidadDAO.listarPorId(idLocalidad)==null) throw new VacunasUyException("La localidad indicada no existe.", VacunasUyException.NO_EXISTE_REGISTRO);				
				localidades.add(localidadDAO.listarPorId(id));		
		}
		try {
			departamento.setNombre(departamentoDTO.getNombre());
			departamento.setLocalidades(localidades);
			return departamentoConverter.fromEntity(departamentoDAO.editar(departamento));
		}catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
	@Override
	public void eliminar(Long id) throws VacunasUyException{
		//se valida que el departamento exista
		Departamento departamento = departamentoDAO.listarPorId(id);
		if (departamento==null) throw new VacunasUyException("El departamento indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		try {
			departamentoDAO.eliminar(departamento);
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}				
	}
   

}
