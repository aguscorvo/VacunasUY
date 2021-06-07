package vacunasuy.componentecentral.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import vacunasuy.componentecentral.converter.RolConverter;
import vacunasuy.componentecentral.dao.IRolDAO;
import vacunasuy.componentecentral.dto.RolDTO;
import vacunasuy.componentecentral.entity.Rol;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Stateless
public class RolServiceImpl implements IRolService {
	
	@EJB
	private IRolDAO rolDAO;
	@EJB
	private RolConverter rolConverter;

	@Override
	public List<RolDTO> listar() throws VacunasUyException {
		try {
			return rolConverter.fromEntity(rolDAO.listar());
			
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}

	@Override
	public RolDTO listarPorId(Long id) throws VacunasUyException {
		try {
			Rol rol = rolDAO.listarPorId(id);
			if(rol == null)	throw new VacunasUyException("El rol indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);			
			return rolConverter.fromEntity(rol);
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
		

}
