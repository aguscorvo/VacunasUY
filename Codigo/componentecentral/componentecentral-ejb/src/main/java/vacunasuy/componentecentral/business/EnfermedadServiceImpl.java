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
	private IEnfermedadDAO enfermedadDAO;
	
	@EJB
	private EnfermedadConverter eConverter;
	
	
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
	public EnfermedadDTO crear(EnfermedadCrearDTO enfermedadCrearDTO) throws VacunasUyException {
	
		Enfermedad enfermedad = eConverter.fromCrearDTO(enfermedadCrearDTO);
		
			try {
				return eConverter.fromEntity(enfermedadDAO.crear(enfermedad));
			} catch (Exception e) {
				throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
			}
		}
	
	

	@Override
	public EnfermedadDTO editar(Long id, EnfermedadCrearDTO enfermedadCrearDTO) throws VacunasUyException {
		return null;
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

}
