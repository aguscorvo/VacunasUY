package vacunasuy.componentecentral.business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.sound.midi.Soundbank;

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
import vacunasuy.componentecentral.entity.Rol;
import vacunasuy.componentecentral.entity.SectorLaboral;
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
	private IVacunatorioService vacunatorioService;

	@EJB
	private INotificacionService notificacionService;
	
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
		
		//se valida que el puesto esté en un vacunatorio apropiado
		boolean puesto_valido = vacunatorioService.vacunatorioTienePlan(puesto.getVacunatorio().getId(), agendaDTO.getPlanVacunacion());
		if (!puesto_valido)throw new VacunasUyException("El vacunatorio asociado al puesto ingresado no tiene vacunas para el plan ingresado.", VacunasUyException.NO_EXISTE_REGISTRO);
		
		//se valida que el plan de vacunacion exista
		PlanVacunacion planVacunacion = planVacunacionDAO.listarPorId(agendaDTO.getPlanVacunacion());
		if(planVacunacion==null)throw new VacunasUyException("El plan de vacunación indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		
		//se valida que el usuario no esté agendado para el plan
		boolean hay_agenda = usuarioService.existeAgenda(agendaDTO.getUsuario(), agendaDTO.getPlanVacunacion());
		if(hay_agenda)throw new VacunasUyException("El usuario ya está agendado para este plan de vacunación.", VacunasUyException.EXISTE_REGISTRO);
		
		//se valida que el usuario esté habilitado al plan
		//se valida la edad
		LocalDate hoy = LocalDate.now();
		Period diferencia= Period.between(ciudadano.getFechaNacimiento(), hoy);
		int edad= diferencia.getYears();
		if(edad>planVacunacion.getEdadMaxima() || edad<planVacunacion.getEdadMinima()) throw new 
				VacunasUyException("El usuario indicado no se encuentra habilitado para este plan. No cumple el requerimiento de edad.", 
				VacunasUyException.DATOS_INCORRECTOS);		
		//se valida el sector laboral			
		List<SectorLaboral> sectoresLaborales = planVacunacion.getSectores();
		SectorLaboral sectorLaboral = sectoresLaborales.stream()
				.filter(s -> s == ciudadano.getSectorLaboral()).findFirst().orElse(null);
		if(sectorLaboral==null) throw new VacunasUyException("El usuario indicado no se encuentra habilitado para este plan. "
				+ "No cumple el requerimiento de sector laboral.", VacunasUyException.DATOS_INCORRECTOS);
				
		int cantidad_de_agendas = planVacunacion.getVacuna().getCant_dosis();
		int periodo = planVacunacion.getVacuna().getPeriodo();
		
		int horaTemp = LocalDateTime.now().getHour();
		
		String hora = "";
		
		/* Si la fecha de la agenda es para hoy */
		hora =  String.valueOf(((int) (Math.random() * 12) + 8));
		
		if(Integer.parseInt(hora) < 10) {
			hora = "0" + hora;
		}
		
		//Minutos random
		String minutos = String.valueOf(((int) (Math.random() * 4.99) + 1) * 10);		
		String fecha_hora = agendaDTO.getFecha() + " " + hora + ":" + minutos;
		
		agendaDTO.setFecha(fecha_hora);
		Agenda agenda = null;
		List<AgendaMinDTO> agendas = new ArrayList<AgendaMinDTO>();		
		try {
			for (int i=0;i<cantidad_de_agendas;i++) {
				agenda = agendaConverter.fromCrearDTO(agendaDTO);
				agenda.setPuesto(puesto);
				agenda.setPlanVacunacion(planVacunacion);
				agenda.setUsuario(ciudadano);
				agenda.setNroDosis(i+1);
				puesto.getAgendas().add(agenda);				
				AgendaMinDTO a_agregar = agendaConverter.fromEntityToMin(agendaDAO.crear(agenda));
				agendas.add(a_agregar);
				fecha_hora = agendaDTO.getFecha();
				String nueva_fecha_hora = sumarDias(fecha_hora, periodo);
				agendaDTO.setFecha(nueva_fecha_hora);				
			}		
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
			DateTimeFormatter formato1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			/* Si tiene un token de firebase definido, se le envía la notificación */
			if(ciudadano.getTokenFirebase() != null) {
				notificacionService.enviarNotificacionFirebase(ciudadano.getTokenFirebase(), "Agenda registrada con éxito.", "Documento: " + ciudadano.getDocumento() + " - Fecha/hora: " + LocalDateTime.parse(agendas.get(0).getFecha(), formato).format(formato1) + " - Vacunatorio: " + puesto.getVacunatorio().getNombre() + " - Dirección: " + puesto.getVacunatorio().getDireccion());
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
	
//	//desde backend
//	@Override
//	public void eliminar(Long id) throws VacunasUyException{
//		try {
//			Agenda agenda = agendaDAO.listarPorId(id);
//			System.out.println("Entre al eliminar, elimina agenda con id: " +id+" del usuario: " + agenda.getUsuario().getApellido());			
//			agendaDAO.eliminar(agenda);
//		}catch (Exception e) {
//			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
//		}
//	}
	
	@Override
	public void cancelarAgenda(Long usuario, Long agenda) throws VacunasUyException{
		try {
			usuarioService.cancelarAgenda(usuario, agenda);
		}catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
    
	public static String sumarDias(String fechaYHora, long dias) {
	    // Crear un formateador como 2018-10-16 15:00
	    DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	    // Lo convertimos a objeto para poder trabajar con él
	    LocalDateTime fechaYHoraLocal = LocalDateTime.parse(fechaYHora, formateador);
	    // Sumar los años indicados
	    fechaYHoraLocal = fechaYHoraLocal.plusDays(dias);
	    //Formatear de nuevo y regresar como cadena
	    return fechaYHoraLocal.format(formateador);
	}
	
}
