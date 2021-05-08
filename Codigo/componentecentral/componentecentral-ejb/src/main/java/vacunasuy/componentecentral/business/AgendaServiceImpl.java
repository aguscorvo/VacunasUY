package vacunasuy.componentecentral.business;

import java.time.LocalDate;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import vacunasuy.componentecentral.converter.AgendaConverter;
import vacunasuy.componentecentral.dao.IAgendaDAO;
import vacunasuy.componentecentral.dao.IPuestoDAO;
import vacunasuy.componentecentral.dao.UsuarioDAOImpl;
import vacunasuy.componentecentral.dto.AgendaCrearDTO;
import vacunasuy.componentecentral.dto.AgendaDTO;
import vacunasuy.componentecentral.entity.Agenda;
import vacunasuy.componentecentral.entity.Puesto;
import vacunasuy.componentecentral.exception.VacunasUyException;


@Stateless
public class AgendaServiceImpl implements IAgendaService {

	@EJB
	private IAgendaDAO agendaDAO;
	
	@EJB
	private IPuestoDAO puestoDAO;
	
	@EJB
	private AgendaConverter agendaConverter;
	
	@Override
	public List<AgendaDTO> listar() throws VacunasUyException{
		try {
			return agendaConverter.fromEntity(agendaDAO.listar());
		}catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
	@Override
	public AgendaDTO listarPorId(Long id) throws VacunasUyException{
		//se valida que la agenda exista 
		Agenda agenda = agendaDAO.listarPorId(id);
		if(agenda==null) {
			throw new VacunasUyException("La agenda indicada no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		}else {
			try {
				return agendaConverter.fromEntity(agendaDAO.listarPorId(id));
			}catch(Exception e) {
				throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
			}
		}
	}
	
	@Override
	public AgendaDTO crear(AgendaCrearDTO agendaDTO)  throws VacunasUyException{
		Agenda agenda = agendaConverter.fromCrearDTO(agendaDTO);
		//se valida que el puesto exista
		Puesto puesto = puestoDAO.listarPorId(agendaDTO.getPuesto());
		if(puesto==null) {
			throw new VacunasUyException("El puesto indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		}else {
			try {
				agenda.setPuesto(puesto);
				return agendaConverter.fromEntity(agenda);
			}catch(Exception e){
				throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
			}
		}
	}
	
	@Override
	public AgendaDTO editar(Long id, AgendaCrearDTO agendaDTO) throws VacunasUyException{
		//se valida que la agenda y el puesto existan
		Agenda agenda = agendaDAO.listarPorId(id);
		Puesto puesto = puestoDAO.listarPorId(agendaDTO.getPuesto());
		if(agenda==null) {
			throw new VacunasUyException("La agenda indicada no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		}else if(puesto==null) {
			throw new VacunasUyException("El puesto indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		}else {
			try {
				agenda.setFecha(LocalDate.parse(agendaDTO.getFecha()));
				agenda.setPuesto(puesto);
				return agendaConverter.fromEntity(agendaDAO.editar(agenda));
			}catch (Exception e) {
				throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
			}		
		}		
	}
	
	@Override
	public void eliminar(Long id) throws VacunasUyException{
		// se valida que la agenda exista
		Agenda agenda = agendaDAO.listarPorId(id);
		if(agenda==null) {
			throw new VacunasUyException("La agenda indicada no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		}else {
			try {
				agendaDAO.eliminar(agenda);
			}catch (Exception e) {
				throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
			}
		}
	}	
    

}
