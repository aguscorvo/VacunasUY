package vacunasuy.componentecentral.business;

import java.time.LocalDateTime;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import vacunasuy.componentecentral.converter.AgendaConverter;
import vacunasuy.componentecentral.dao.IAgendaDAO;
import vacunasuy.componentecentral.dao.IPlanVacunacionDAO;
import vacunasuy.componentecentral.dao.IPuestoDAO;
import vacunasuy.componentecentral.dao.IUsuarioDAO;
import vacunasuy.componentecentral.dto.AgendaCrearDTO;
import vacunasuy.componentecentral.dto.AgendaDTO;
import vacunasuy.componentecentral.dto.AgendaMinDTO;
import vacunasuy.componentecentral.entity.Agenda;
import vacunasuy.componentecentral.entity.PlanVacunacion;
import vacunasuy.componentecentral.entity.Puesto;
import vacunasuy.componentecentral.entity.Usuario;
import vacunasuy.componentecentral.exception.VacunasUyException;


@Stateless
public class AgendaServiceImpl implements IAgendaService {

	@EJB
	private IAgendaDAO agendaDAO;
	
	@EJB
	private IPuestoDAO puestoDAO;
	
	@EJB
	private IUsuarioDAO usuarioDAO;
	
	@EJB
	private IPlanVacunacionDAO planVacunacionDAO;
	
	@EJB
	private IUsuarioService usuarioService;
	
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
		if(agenda==null) throw new VacunasUyException("La agenda indicada no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		 try {
			return agendaConverter.fromEntity(agendaDAO.listarPorId(id));
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}		
	}
	
	@Override
	public AgendaMinDTO crear(AgendaCrearDTO agendaDTO)  throws VacunasUyException{
		//se valida que ciudadano exista
		Usuario ciudadano = usuarioDAO.listarPorId(agendaDTO.getUsuario());
		if(ciudadano==null)throw new VacunasUyException("El ciudadano indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		//se valida que el puesto exista
		Puesto puesto = puestoDAO.listarPorId(agendaDTO.getPuesto());
		if(puesto==null)throw new VacunasUyException("El puesto indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		//se valida que el plan de vacunacion exista
		PlanVacunacion planVacunacion = planVacunacionDAO.listarPorId(agendaDTO.getPlanVacunacion());
		if(planVacunacion==null)throw new VacunasUyException("El plan de vacunación indicado no existe.", 
				VacunasUyException.NO_EXISTE_REGISTRO);
		Agenda agenda = agendaConverter.fromCrearDTO(agendaDTO);
		try {
			agenda.setPuesto(puesto);
			agenda.setPlanVacunacion(planVacunacion);
			puesto.getAgendas().add(agenda);			
			return agendaConverter.fromEntityToMin(agendaDAO.crear(agenda));
		}catch(Exception e){
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}		
	}
	
	@Override
	public AgendaDTO editar(Long id, AgendaCrearDTO agendaDTO) throws VacunasUyException{
		//se valida que la agenda y el puesto existan
		Agenda agenda = agendaDAO.listarPorId(id);
		if(agenda==null) throw new VacunasUyException("La agenda indicada no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		Puesto puesto = puestoDAO.listarPorId(agendaDTO.getPuesto());
		if(puesto==null) throw new VacunasUyException("El puesto indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		try {
			agenda.setFecha(LocalDateTime.parse(agendaDTO.getFecha()));
			agenda.setPuesto(puesto);
			return agendaConverter.fromEntity(agendaDAO.editar(agenda));
		}catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}		
	}
	
	@Override
	public void eliminar(Long id) throws VacunasUyException{
		// se valida que la agenda exista
		Agenda agenda = agendaDAO.listarPorId(id);
		if(agenda==null) throw new VacunasUyException("La agenda indicada no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		try {
			agendaDAO.eliminar(agenda);
		}catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}	
    

}
