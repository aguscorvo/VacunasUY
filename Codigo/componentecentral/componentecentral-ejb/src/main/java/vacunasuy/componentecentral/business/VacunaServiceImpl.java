package vacunasuy.componentecentral.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import vacunasuy.componentecentral.converter.VacunaConverter;
import vacunasuy.componentecentral.dao.IEnfermedadDAO;
import vacunasuy.componentecentral.dao.IVacunaDAO;
import vacunasuy.componentecentral.dto.VacunaCrearDTO;
import vacunasuy.componentecentral.dto.VacunaDTO;
import vacunasuy.componentecentral.dto.VacunaMinDTO;
import vacunasuy.componentecentral.entity.Enfermedad;
import vacunasuy.componentecentral.entity.Vacuna;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Stateless
public class VacunaServiceImpl implements IVacunaService{

	@EJB
	public IVacunaDAO vacunaDAO;
	
	@EJB
	public IEnfermedadDAO enfermedadDAO;
	
	@EJB
	public VacunaConverter vacunaConverter;
	
	@Override
	public List<VacunaDTO> listar() throws VacunasUyException {
		try {
			return vacunaConverter.fromEntity(vacunaDAO.listar());
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}

	@Override
	public Vacuna listarPorId(Long id) {
		return vacunaDAO.listarPorId(id);
	}
	
	@Override
	public List<VacunaMinDTO> listarVacunasPorUsuario(Long idUsuario) throws VacunasUyException {
		try {
			return vacunaConverter.fromEntityToMin(vacunaDAO.listarVacunasPorUsuario(idUsuario));
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}

	@Override
	public VacunaDTO crear(VacunaCrearDTO vacunaCrearDTO) throws VacunasUyException {
		if(existeNombreVacuna(vacunaCrearDTO.getNombre())) {
			throw new VacunasUyException("Ya existe una vacuna con ese nombre.", VacunasUyException.EXISTE_REGISTRO);
		}else {	
			try {
				Vacuna vacuna = vacunaConverter.fromCrearDTO(vacunaCrearDTO);
				Enfermedad e = enfermedadDAO.listarPorId(vacunaCrearDTO.getId_enfermedad());
				vacuna.setEnfermedad(e);
				return vacunaConverter.fromEntity(vacunaDAO.crear(vacuna));
			} catch (Exception e) {
				throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
			}
		}
	}
	
	@Override
	public VacunaDTO editar(Long id, VacunaCrearDTO vacunaCrearDTO) throws VacunasUyException {
		if(existeNombreVacunaExcluirId(id, vacunaCrearDTO.getNombre())) {
			throw new VacunasUyException("Ya existe una vacuna con ese nombre.", VacunasUyException.EXISTE_REGISTRO);
		}else {	
			try {
				Vacuna vacuna = vacunaDAO.listarPorId(id);
				Enfermedad e = enfermedadDAO.listarPorId(vacunaCrearDTO.getId_enfermedad());
				vacuna.setEnfermedad(e);
				vacuna.setNombre(vacunaCrearDTO.getNombre());
				vacuna.setPeriodo(vacunaCrearDTO.getPeriodo());
				vacuna.setCant_dosis(vacunaCrearDTO.getCant_dosis());
				vacuna.setInmunidad(vacunaCrearDTO.getInmunidad());
				return vacunaConverter.fromEntity(vacunaDAO.editar(vacuna));
			} catch (Exception e) {
				throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
			}
		}
	}

	@Override
	public void eliminar(Long id) throws VacunasUyException {
		/* Se valida que exista la vacuna */
		Vacuna vacuna= vacunaDAO.listarPorId(id);
		if(vacuna == null) {
			throw new VacunasUyException("La vacuna indicada no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		} else {
			try {
				vacunaDAO.eliminar(vacuna);
			} catch (Exception e) {
				throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
			}
		}
		
	}

	public boolean existeNombreVacuna (String nombre) {
		
		List<VacunaDTO> vacunas = vacunaConverter.fromEntity(vacunaDAO.listar());
		for (VacunaDTO e: vacunas) {
			if (e.getNombre().equals(nombre)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean existeNombreVacunaExcluirId (Long id, String nombre) {
		
		List<VacunaDTO> vacunas = vacunaConverter.fromEntity(vacunaDAO.listar());
		for (VacunaDTO e: vacunas) {
			if (!e.getId().equals(id)) {
				if (e.getNombre().equals(nombre)) {
					return true;
				}
			}
		}
		return false;
	}

}
