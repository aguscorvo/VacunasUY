package vacunasuy.componentecentral.business;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import vacunasuy.componentecentral.converter.EnfermedadConverter;
import vacunasuy.componentecentral.dao.IEnfermedadDAO;
import vacunasuy.componentecentral.dto.EnfermedadCrearDTO;
import vacunasuy.componentecentral.dto.EnfermedadDTO;
import vacunasuy.componentecentral.entity.Enfermedad;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Stateless
public class EnfermedadServiceImpl implements IEnfermedadService{
	
	@EJB
	public IEnfermedadDAO enfermedadDAO;
	
	@EJB
	public EnfermedadConverter eConverter;
	
	
	@Override
	public List<EnfermedadDTO> listar() throws VacunasUyException {
		try {
			return eConverter.fromEntity(enfermedadDAO.listar());
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}

	@Override
	public Enfermedad listarPorId(Long id) {
		return enfermedadDAO.listarPorId(id);
	}
	
	@Override
	public List<EnfermedadDTO> listarEnfermedadesPorUsuario(Long idUsuario) throws VacunasUyException {
		try {
			return eConverter.fromEntity(enfermedadDAO.listarEnfermedadesPorUsuario(idUsuario));
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}

	@Override
	public EnfermedadDTO crear(EnfermedadCrearDTO enfermedadCrearDTO) throws VacunasUyException {
	
		if(existeNombreEnfermedad(enfermedadCrearDTO.getNombre())) {
			throw new VacunasUyException("Ya existe una enfermedad con ese nombre.", VacunasUyException.EXISTE_REGISTRO);
		}else {	
			try {
				Enfermedad enfermedad = eConverter.fromCrearDTO(enfermedadCrearDTO);
				return eConverter.fromEntity(enfermedadDAO.crear(enfermedad));
			} catch (Exception e) {
				throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
			}
		}
	}
	

	@Override
	public EnfermedadDTO editar(Long id, EnfermedadCrearDTO enfermedadCrearDTO) throws VacunasUyException {
		
		if(existeNombreEnfermedadExcluirId (id, enfermedadCrearDTO.getNombre())) {
			throw new VacunasUyException ("Ya existe una enfermedad con ese nombre.", VacunasUyException.EXISTE_REGISTRO);
		}else {	
			try {
				Enfermedad enfermedad = enfermedadDAO.listarPorId(id);
				enfermedad.setNombre(enfermedadCrearDTO.getNombre());
				return eConverter.fromEntity(enfermedadDAO.editar(enfermedad));
			} catch (Exception e) {
				throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
			}
		}
	}

	@Override
	public void eliminar(Long id) throws VacunasUyException {
		/* Se valida que exista la enfermedad */
		Enfermedad enfermedad= enfermedadDAO.listarPorId(id);
		if(enfermedad == null) {
			throw new VacunasUyException("La enfermedad indicada no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		} else {
			try {
				enfermedadDAO.eliminar(enfermedad);
			} catch (Exception e) {
				throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
			}
		}
		
	}
	
	public boolean existeNombreEnfermedad (String nombre) {
		enfermedadDAO.listar();
		List<EnfermedadDTO> enfermedades = eConverter.fromEntity(enfermedadDAO.listar());
		for (EnfermedadDTO e: enfermedades) {
			if (e.getNombre().equals(nombre)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean existeNombreEnfermedadExcluirId (Long id, String nombre) {
		enfermedadDAO.listar();
		List<EnfermedadDTO> enfermedades = eConverter.fromEntity(enfermedadDAO.listar());
		for (EnfermedadDTO e: enfermedades) {
			if (!e.getId().equals(id)) {
				if (e.getNombre().equals(nombre)) {
					return true;
				}
			}
		}
		return false;
	}

}
