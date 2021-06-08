package vacunasuy.componentecentral.business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Geometries;
import org.geolatte.geom.Point;
import org.geolatte.geom.crs.CoordinateReferenceSystems;
import vacunasuy.componentecentral.converter.AgendaConverter;
import vacunasuy.componentecentral.converter.UbicacionConverter;
import vacunasuy.componentecentral.converter.UsuarioConverter;
import vacunasuy.componentecentral.converter.VacunatorioConverter;
import vacunasuy.componentecentral.dao.IActoVacunalDAO;
import vacunasuy.componentecentral.dao.IDepartamentoDAO;
import vacunasuy.componentecentral.dao.IEventoDAO;
import vacunasuy.componentecentral.dao.ILocalidadDAO;
import vacunasuy.componentecentral.dao.IPlanVacunacionDAO;
import vacunasuy.componentecentral.dao.IStockDAO;
import vacunasuy.componentecentral.dao.IVacunatorioDAO;
import vacunasuy.componentecentral.dto.AgendaDTO;
import vacunasuy.componentecentral.dto.AgendaMinDTO;
import vacunasuy.componentecentral.dto.AgendaVacunatorioDTO;
import vacunasuy.componentecentral.dto.UbicacionDTO;
import vacunasuy.componentecentral.dto.UsuarioMinDTO;
import vacunasuy.componentecentral.dto.VacunatorioCrearDTO;
import vacunasuy.componentecentral.dto.VacunatorioDTO;
import vacunasuy.componentecentral.dto.VacunatorioPerifericoDTO;
import vacunasuy.componentecentral.entity.ActoVacunal;
import vacunasuy.componentecentral.entity.Agenda;
import vacunasuy.componentecentral.entity.Atiende;
import vacunasuy.componentecentral.entity.Departamento;
import vacunasuy.componentecentral.entity.Evento;
import vacunasuy.componentecentral.entity.Localidad;
import vacunasuy.componentecentral.entity.PlanVacunacion;
import vacunasuy.componentecentral.entity.Puesto;
import vacunasuy.componentecentral.entity.Stock;
import vacunasuy.componentecentral.entity.StockID;
import vacunasuy.componentecentral.entity.Vacuna;
import vacunasuy.componentecentral.entity.Vacunatorio;
import vacunasuy.componentecentral.exception.VacunasUyException;
import vacunasuy.componentecentral.util.Constantes;

@Stateless
public class VacunatorioServiceImpl implements IVacunatorioService {

	@EJB
	private IVacunatorioGeoService vacunatorioGeoService;
	
	@EJB
	private IVacunatorioDAO vacunatorioDAO;
	
	@EJB
	private IPlanVacunacionDAO planDAO;
	
	@EJB
	private ILocalidadDAO localidadDAO;
	
	@EJB
	private IDepartamentoDAO departamentoDAO;
	
	@EJB
	private IEventoDAO eventoDAO;
	
	@EJB 
	private IActoVacunalDAO actoVacunalDAO;
	
	@EJB
	private IStockService stockService;
	
	@EJB
	private VacunatorioConverter vacunatorioConverter;
	
	@EJB
	private UsuarioConverter usuarioConverter;
	
	@EJB
	private UbicacionConverter ubicacionConverter;
	
	@EJB
	private AgendaConverter agendaConverter;
	
	@Override
	public List<VacunatorioDTO> listar() throws VacunasUyException{
		try {
			return vacunatorioConverter.fromEntity(vacunatorioDAO.listar());
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
	@Override
	public VacunatorioDTO listarPorId(Long id) throws VacunasUyException{
		//se valida que el vacunatorio exista
		Vacunatorio vacunatorio = vacunatorioDAO.listarPorId(id);
		if (vacunatorio==null) throw new VacunasUyException("El vacunatorio indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		try {
			return vacunatorioConverter.fromEntity(vacunatorio);
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}	
	}
	
	@Override
	public VacunatorioDTO crear(VacunatorioCrearDTO vacunatorioDTO) throws VacunasUyException{
		Vacunatorio vacunatorio = vacunatorioConverter.fromCrearDTO(vacunatorioDTO);
		//se verifica que la localidad y el departamento existan
		Localidad localidad = localidadDAO.listarPorId(vacunatorioDTO.getLocalidad());
		if (localidad == null) throw new VacunasUyException("La localidad indicada no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		Departamento departamento = departamentoDAO.listarPorId(vacunatorioDTO.getDepartamento());
		if(departamento==null) throw new VacunasUyException("El departamento indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		//se crea el vacunatorio
		try {
			vacunatorio.setLocalidad(localidad);
			vacunatorio.setDepartamento(departamento);
			String clave = UUID.randomUUID().toString();
			vacunatorio.setClave(clave);
			vacunatorioDAO.crear(vacunatorio);
			registrarVacunatorioPeriferico(vacunatorio);
			// se crea la geometría
			VacunatorioDTO vacunatorioCreado = vacunatorioConverter.fromEntity(vacunatorio);
			vacunatorioGeoService.crear(vacunatorioCreado);
			return vacunatorioCreado;
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}	
	}
	
	@Override
	public VacunatorioDTO editar(Long id, VacunatorioCrearDTO vacunatorioDTO) throws VacunasUyException{
		//se verifica que el vacunatorio exista
		Vacunatorio vacunatorio = vacunatorioDAO.listarPorId(id);
		if(vacunatorio==null) throw new VacunasUyException("El vacunatorio indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		//se verifica que la localidad y departamentos existan
		Localidad localidad = localidadDAO.listarPorId(vacunatorioDTO.getLocalidad());
		if (localidad == null) 	throw new VacunasUyException("La localidad indicada no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		Departamento departamento = departamentoDAO.listarPorId(vacunatorioDTO.getDepartamento());
		if(departamento==null) throw new VacunasUyException("El departamento indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);				
		//se edita el vacunatorio
		try {
			vacunatorio.setNombre(vacunatorioDTO.getNombre());
			vacunatorio.setLatitud(vacunatorioDTO.getLatitud());
			vacunatorio.setLongitud(vacunatorioDTO.getLongitud());
			vacunatorio.setDireccion(vacunatorioDTO.getDireccion());
			vacunatorio.setDepartamento(departamento);
			vacunatorio.setLocalidad(localidad);
			return vacunatorioConverter.fromEntity(vacunatorioDAO.editar(vacunatorio));
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}		
		
	@Override
	public void eliminar(Long id) throws VacunasUyException{
		//se valida que el vacunatorio exista
		Vacunatorio vacunatorio = vacunatorioDAO.listarPorId(id);
		if (vacunatorio==null) throw new VacunasUyException("El vacunatorio indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		try {
			vacunatorioDAO.eliminar(vacunatorio);
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}		
	}
	
	@Override
	public List<VacunatorioDTO> listarCercanos(UbicacionDTO ubicacionDTO) throws VacunasUyException{
		try {
			List<Long> vacunatoriosId = vacunatorioGeoService.listarCercanos(ubicacionDTO);
			List<Vacunatorio> vacunatorios = new ArrayList<Vacunatorio>();
			for(Long id: vacunatoriosId) {
				vacunatorios.add(vacunatorioDAO.listarPorId(id));
			}
			return vacunatorioConverter.fromEntity(vacunatorios);
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
	@Override
	public VacunatorioDTO agregarEvento(Long vacunatorio, Long evento) throws VacunasUyException{
		try {
			//se valida que el vacunatorio y el evento existan
			Vacunatorio vacunatorioAux = vacunatorioDAO.listarPorId(vacunatorio);
			if(vacunatorioAux==null) throw new VacunasUyException("El vacunatorio indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
			Evento eventoAux = eventoDAO.listarPorId(evento);
			if(eventoAux==null) throw new VacunasUyException("El evento indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
			//se valida que el evento no esté asociado a vacunatorio
			for(Evento e: vacunatorioAux.getEventos()) {
				if(e.getId()==eventoAux.getId()) throw new VacunasUyException("El evento indicado ya se "
						+ "encuentra asociado al vacunatorio.", VacunasUyException.EXISTE_REGISTRO);
			}
			vacunatorioAux.getEventos().add(eventoAux);
			return vacunatorioConverter.fromEntity(vacunatorioDAO.editar(vacunatorioAux));
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
	@Override
	public VacunatorioDTO agregarActoVacunal(Long vacunatorio, Long actoVacunal) throws VacunasUyException{
		try {
			//se valida que el vacunatorio y el actoVacunal existan
			Vacunatorio vacunatorioAux = vacunatorioDAO.listarPorId(vacunatorio);
			if(vacunatorioAux==null) throw new VacunasUyException("El vacunatorio indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
			ActoVacunal actoVacunalAux = actoVacunalDAO.listarPorId(actoVacunal);
			if(actoVacunalAux==null) throw new VacunasUyException("El acto vacunal indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
			//se valida que el acto vacunal no esté asociado a vacunatorio
			for(ActoVacunal a: vacunatorioAux.getActosVacunales()) {
				if(a.getId()==actoVacunalAux.getId()) throw new VacunasUyException("El acto vacunal indicado ya se "
						+ "encuentra asociado al vacunatorio.", VacunasUyException.EXISTE_REGISTRO); 
			}
			vacunatorioAux.getActosVacunales().add(actoVacunalAux);
			return vacunatorioConverter.fromEntity(vacunatorioDAO.editar(vacunatorioAux));
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
	@Override
	public List<UsuarioMinDTO> obtenerAsignacionVacunadores(Long idVacunatorio, String clave, String fecha) throws VacunasUyException{
		try {
			/* Se valida que exista el vacunatorio */
			Vacunatorio vacunatorio = vacunatorioDAO.listarPorId(idVacunatorio);
			if(vacunatorio==null) throw new VacunasUyException("El vacunatorio indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
			/* Se valida la clave */
			if(!vacunatorio.getClave().equalsIgnoreCase(clave)) throw new VacunasUyException("ID o clave de vacunatorio incorrectos.", VacunasUyException.DATOS_INCORRECTOS);
			List<UsuarioMinDTO> vacunadores = new ArrayList();
			for(Puesto p: vacunatorio.getPuestos()) {
				for(Atiende a: p.getAtiende()) {
					if(a.getFecha().toString().equals(fecha)) 
						vacunadores.add(usuarioConverter.fromEntityToMin(a.getUsuario()));
				}
			}
			return vacunadores;
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
	@Override
	public List<VacunatorioDTO> listarPorUbicacion(Long localidad, Long departamento) throws VacunasUyException{
		try {
			//se valida que localidad y departamento existan
			Localidad localidadAux = localidadDAO.listarPorId(localidad);
			if (localidadAux == null) 	throw new VacunasUyException("La localidad indicada no existe.", 
					VacunasUyException.NO_EXISTE_REGISTRO);
			Departamento departamentoAux = departamentoDAO.listarPorId(departamento);
			if(departamentoAux==null) throw new VacunasUyException("El departamento indicado no existe.", 
					VacunasUyException.NO_EXISTE_REGISTRO);		
			//se valida que la localidad se encuentre dentro del departamento
			if(!departamentoAux.getLocalidades().contains(localidadAux)) throw new VacunasUyException("La localidad no se encuentra "
					+ "en el departamento indicado.", VacunasUyException.DATOS_INCORRECTOS);
			return vacunatorioConverter.fromEntity(vacunatorioDAO.listarPorUbicacion(localidad, departamento));
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}		
	}
	
	@Override
	public List<VacunatorioDTO> listarPorDepartamento(Long departamento) throws VacunasUyException{
		try {
			Departamento departamentoAux = departamentoDAO.listarPorId(departamento);
			if(departamentoAux==null) throw new VacunasUyException("El departamento indicado no existe.", 
					VacunasUyException.NO_EXISTE_REGISTRO);
			return vacunatorioConverter.fromEntity(vacunatorioDAO.listarPorDepartamento(departamento));
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}		
	}

	@Override
	public List<VacunatorioDTO> listarVacunatoriosDadoPlan(Long id_plan) throws VacunasUyException {
		
		try {
			List<VacunatorioDTO> vacunatoriosReturn = new ArrayList<VacunatorioDTO>();
			PlanVacunacion plan = planDAO.listarPorId(id_plan);
			if(plan==null) throw new VacunasUyException("El plan de vacunación indicado no existe.", 
					VacunasUyException.NO_EXISTE_REGISTRO);
			else {
				Long id_vacuna = planDAO.listarPorId(id_plan).getVacuna().getId();
				List<Vacunatorio> vacunatorios = vacunatorioDAO.listar();
				for (Vacunatorio v: vacunatorios) {
					boolean hay_vacuna = false;
					List<Evento> eventos = v.getEventos();
					for (Evento e: eventos) {
						if (e.getLote().getVacuna().getId() == id_vacuna) {
							hay_vacuna = true;
							break;
						}
					}
					if (hay_vacuna) {
						vacunatoriosReturn.add(vacunatorioConverter.fromEntity(v));
					}
							
				}
				return vacunatoriosReturn;
			}
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}		
	}	
	
	@Override
	public void crearGeometrias() throws VacunasUyException{
		try {
			for(Vacunatorio v: vacunatorioDAO.listar()) {
				vacunatorioGeoService.crear(vacunatorioConverter.fromEntity(v));
			}
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
	public List<AgendaVacunatorioDTO> listarAgendasPorVacunatorio(Long id, String fecha) throws VacunasUyException {
		try {
			/* Se valida que el vacunatorio exista */
			Vacunatorio vacunatorio = vacunatorioDAO.listarPorId(id);
			if(vacunatorio==null) throw new VacunasUyException("El vacunatorio indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
			List<AgendaVacunatorioDTO> agendas = new ArrayList<AgendaVacunatorioDTO>();
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyy-MM-dd'T'hh:MM");
			/* Itero cada puesto */
			for (Puesto puesto : vacunatorio.getPuestos()) {
				/* Itero cada agenda del puesto */
				for (Agenda agenda : puesto.getAgendas()) {
					/* Verifico si la fecha de la agenda es la solicitada */
					if(agenda.getFecha().toLocalDate().isEqual(LocalDateTime.parse(fecha).toLocalDate())  && (agenda.getFecha().isEqual(LocalDateTime.parse(fecha)) || agenda.getFecha().isAfter(LocalDateTime.parse(fecha)))) {
						/* Construyo el DTO y lo agrego a la lista */
						AgendaVacunatorioDTO agendaDTO = agendaConverter.fromEntityToAgendaVacunatorio(agenda);
						agendas.add(agendaDTO);
					}
				}
			}
			return agendas;
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
	/* Registra al vacunatorio en el nodo periférico */
	private void registrarVacunatorioPeriferico(Vacunatorio vacunatorio) {
		Client cliente = ClientBuilder.newClient();
		WebTarget target = cliente.target(Constantes.NODOS_PERIFERICOS_REST_URL+"/vacunatorios");
		VacunatorioPerifericoDTO vacunatorioPeriferico = VacunatorioPerifericoDTO.builder()
				.id(vacunatorio.getId())
				.nombre(vacunatorio.getNombre())
				.direccion(vacunatorio.getDireccion())
				.clave(vacunatorio.getClave())
				.build();
		String response = target.request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.json(vacunatorioPeriferico), String.class);
		System.out.println(response);
	}

	@Override
	public boolean vacunatorioTienePlan(Long idVacunatorio, Long idPlan) throws VacunasUyException {
		
		Vacunatorio vacunatorio = vacunatorioDAO.listarPorId(idVacunatorio);
		
		PlanVacunacion plan = planDAO.listarPorId(idPlan);
		
		List<Evento> eventos = vacunatorio.getEventos();
		
		for (Evento e: eventos) {
			if(e.getLote().getVacuna().getId() == plan.getVacuna().getId()) {
				return true;
			}
		}
		
		return false;
	}
	
}
