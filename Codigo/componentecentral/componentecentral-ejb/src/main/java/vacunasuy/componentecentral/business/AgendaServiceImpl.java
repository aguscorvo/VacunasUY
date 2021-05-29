package vacunasuy.componentecentral.business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
	public List<AgendaMinDTO> crear(AgendaCrearDTO agendaDTO)  throws VacunasUyException{
		
		//se valida que ciudadano exista
		Usuario ciudadano = usuarioDAO.listarPorId(agendaDTO.getUsuario());
		if(ciudadano==null)throw new VacunasUyException("El ciudadano indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		
		//se valida que el puesto exista
		Puesto puesto = puestoDAO.listarPorId(agendaDTO.getPuesto());
		if(puesto==null)throw new VacunasUyException("El puesto indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		
		//se valida que el plan de vacunacion exista
		PlanVacunacion planVacunacion = planVacunacionDAO.listarPorId(agendaDTO.getPlanVacunacion());
		if(planVacunacion==null)throw new VacunasUyException("El plan de vacunación indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		
		boolean hay_agenda = usuarioService.existeAgenda(agendaDTO.getUsuario(), agendaDTO.getPlanVacunacion());
		if(hay_agenda)throw new VacunasUyException("El usuario ya está agendado para este plan de vacunación.", VacunasUyException.EXISTE_REGISTRO);
		
		int cantidad_de_agendas = planVacunacion.getVacuna().getCant_dosis();
		int periodo = planVacunacion.getVacuna().getPeriodo();
		
		int horaTemp = LocalDateTime.now().getHour();
		
		String hora = "";
		
		/* Si la fecha de la agenda es para hoy */
		if(agendaDTO.getFecha().equalsIgnoreCase(LocalDate.now().toString())) {
			/* Si la hora es de 0 a 8 se agenda normal */
			if(horaTemp >= 0 && horaTemp <= 8) {
				hora =  String.valueOf(((int) (Math.random() * 12) + 8));
			/* Si la hora es de 9 a 19 se agenda a partir de esta hora */
			} else if (horaTemp >= 9 && horaTemp <=19 ) {
				hora =  String.valueOf(((int) (Math.random() * (20 - horaTemp)) + horaTemp + 1));
			/* Si la hora es de 20 a 23, se agenda normal para el siguiente día */
			}else if(horaTemp >= 20 && horaTemp <= 23) {
				hora =  String.valueOf(((int) (Math.random() * 12) + 8));
				agendaDTO.setFecha(sumarDias(agendaDTO.getFecha(), 1));
			}
		} else {
			hora =  String.valueOf(((int) (Math.random() * 12) + 8));
		}
		
		if(Integer.parseInt(hora) < 10) {
			hora = "0" + hora;
		}
		
		//Minutos random
		String minutos = String.valueOf(((int) (Math.random() * 4.99) + 1) * 10);		
		String fecha_hora = agendaDTO.getFecha() + " " + hora + ":" + minutos;
		
		agendaDTO.setFecha(fecha_hora);
		Agenda agenda;
		List<AgendaMinDTO> agendas = new ArrayList<AgendaMinDTO>();
		
		try {
			for (int i=0;i<cantidad_de_agendas;i++) {
				agenda = agendaConverter.fromCrearDTO(agendaDTO);
				agenda.setPuesto(puesto);
				agenda.setPlanVacunacion(planVacunacion);
				agenda.setUsuario(ciudadano);
				puesto.getAgendas().add(agenda);	
				AgendaMinDTO a_agregar = agendaConverter.fromEntityToMin(agendaDAO.crear(agenda));
				agendas.add(a_agregar);
				fecha_hora = agendaDTO.getFecha();
				String nueva_fecha_hora = sumarDias(fecha_hora, periodo);
				agendaDTO.setFecha(nueva_fecha_hora);				
			}
			return agendas;
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
    
	public static String sumarDias(String fechaYHora, long dias) {
	    // Crear un formateador como 2018-10-16 15:00
	    DateTimeFormatter formateador = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm");
	    // Lo convertimos a objeto para poder trabajar con él
	    LocalDateTime fechaYHoraLocal = LocalDateTime.parse(fechaYHora, formateador);
	    // Sumar los años indicados
	    fechaYHoraLocal = fechaYHoraLocal.plusDays(dias);
	    //Formatear de nuevo y regresar como cadena
	    return fechaYHoraLocal.format(formateador);
	}
	
}
