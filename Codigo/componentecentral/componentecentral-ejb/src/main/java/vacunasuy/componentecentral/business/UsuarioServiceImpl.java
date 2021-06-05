package vacunasuy.componentecentral.business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import vacunasuy.componentecentral.converter.AgendaConverter;
import vacunasuy.componentecentral.converter.AtiendeConverter;
import vacunasuy.componentecentral.converter.SectorLaboralConverter;
import vacunasuy.componentecentral.converter.UsuarioConverter;
import vacunasuy.componentecentral.dao.IActoVacunalDAO;
import vacunasuy.componentecentral.dao.IAgendaDAO;
import vacunasuy.componentecentral.dao.IPuestoDAO;
import vacunasuy.componentecentral.dao.IRolDAO;
import vacunasuy.componentecentral.dao.ISectorLaboralDAO;
import vacunasuy.componentecentral.dao.IUsuarioDAO;
import vacunasuy.componentecentral.dto.AgendaDTO;
import vacunasuy.componentecentral.dto.AtiendeCrearDTO;
import vacunasuy.componentecentral.dto.AtiendeDTO;
import vacunasuy.componentecentral.dto.RespuestaUserInfoDTO;
import vacunasuy.componentecentral.dto.UsuarioCrearDTO;
import vacunasuy.componentecentral.dto.UsuarioDNICDTO;
import vacunasuy.componentecentral.dto.UsuarioDTO;
import vacunasuy.componentecentral.dto.UsuarioLoginBackofficeDTO;
import vacunasuy.componentecentral.dto.UsuarioLoginExitosoDTO;
import vacunasuy.componentecentral.dto.UsuarioRegistrarTFDTO;
import vacunasuy.componentecentral.entity.ActoVacunal;
import vacunasuy.componentecentral.entity.Agenda;
import vacunasuy.componentecentral.entity.Atiende;
import vacunasuy.componentecentral.entity.Puesto;
import vacunasuy.componentecentral.entity.Rol;
import vacunasuy.componentecentral.entity.SectorLaboral;
import vacunasuy.componentecentral.entity.Usuario;
import vacunasuy.componentecentral.exception.VacunasUyException;
import vacunasuy.componentecentral.util.Constantes;

@Stateless
public class UsuarioServiceImpl implements IUsuarioService {
	
	@EJB
	private IAgendaService agendaService;
	
	@EJB
	private IUsuarioDAO usuarioDAO;
	
	@EJB
	private IRolDAO rolDAO;
	
	@EJB
	private ISectorLaboralDAO sectorLaboralDAO;
	
	@EJB
	private IPuestoDAO puestoDAO;
	
	@EJB
	private IActoVacunalDAO actoVacunalDAO;
	
	@EJB
	private IAgendaDAO agendaDAO;
	
	@EJB
	private UsuarioConverter usuarioConverter;
	
	@EJB
	private AtiendeConverter atiendeConverter;
	
	@EJB
	private AgendaConverter agendaConverter;
	
	@EJB
	private SectorLaboralConverter sectorLaboralConverter;

	@Override
	public List<UsuarioDTO> listar() throws VacunasUyException {
		try {
			return usuarioConverter.fromEntity(usuarioDAO.listar());
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}

	@Override
	public UsuarioDTO listarPorId(Long id) throws VacunasUyException {
		try {
			Usuario usuario = usuarioDAO.listarPorId(id);
			if(usuario == null)	throw new VacunasUyException("El usuario indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);			
			return usuarioConverter.fromEntity(usuario);
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}

	@Override
	public UsuarioDTO listarPorCorreo(String correo) throws VacunasUyException {
		try {
			Usuario usuario = usuarioDAO.listarPorCorreo(correo);
			if(usuario == null)	throw new VacunasUyException("El usuario indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);			
			return usuarioConverter.fromEntity(usuario);
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}

	@Override
	public UsuarioDTO crear(UsuarioCrearDTO usuarioDTO) throws VacunasUyException {
		/* Se valida que el correo sea único */
		Usuario usuario = usuarioConverter.fromCrearDTO(usuarioDTO);
		if(usuarioDAO.listarPorCorreo(usuario.getCorreo()) != null) {
			throw new VacunasUyException("El correo electrónico ya se encuentra en uso.", VacunasUyException.EXISTE_REGISTRO);
		}else {
			try {
				/* Se encripta la contraseña */
				usuario.setPassword(BCrypt.withDefaults().hashToString(12, usuario.getPassword().toCharArray()));
				/* Se agregan los roles */
				List<Rol> roles = new ArrayList<>();
				for (Long idRol : usuarioDTO.getRoles()) {
					Rol rol = rolDAO.listarPorId(idRol);
					if(rol != null) {
						roles.add(rol);
					}
				}
				usuario.setRoles(roles);
				/* Se agrega el sector laboral */
				SectorLaboral sector = sectorLaboralDAO.listarPorId(usuarioDTO.getSectorLaboral());
				usuario.setSectorLaboral(sector);
				return usuarioConverter.fromEntity(usuarioDAO.crear(usuario));
			} catch (Exception e) {
				throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
			}
		}
	}
	
	/* Este método editar no es para cambiar la contraseña, se debe implementar un servicio particular para ello. */
	@Override
	public UsuarioDTO editar(Long id, UsuarioCrearDTO usuarioDTO) throws VacunasUyException {
		/* Se valida que exista el usuario */
		Usuario usuario = usuarioDAO.listarPorId(id);
		if(usuario == null) {
			throw new VacunasUyException("El usuario indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		} else if(!usuario.getCorreo().equalsIgnoreCase(usuarioDTO.getCorreo())) {
			/* Usuario desea actualizar su correo electrónico, se debe verificar que no esté registrado */
			if(usuarioDAO.listarPorCorreo(usuarioDTO.getCorreo()) != null) {
				throw new VacunasUyException("El correo electrónico ya se encuentra en uso.", VacunasUyException.EXISTE_REGISTRO);
			}
		}
		try {
			/* Se busca el sector laboral */
			SectorLaboral sector = sectorLaboralDAO.listarPorId(usuarioDTO.getSectorLaboral());
			if(sector == null) throw new VacunasUyException("El sector laboral indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
			/* Se editar los atributos */
			usuario.setNombre(usuarioDTO.getNombre());
			usuario.setApellido(usuarioDTO.getApellido());
			usuario.setCorreo(usuarioDTO.getCorreo());
			usuario.setFechaNacimiento(LocalDate.parse(usuarioDTO.getFechaNacimiento()));
			usuario.setSectorLaboral(sector);
			return usuarioConverter.fromEntity(usuarioDAO.editar(usuario));
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
	@Override
	public void eliminar(Long id) throws VacunasUyException {
		try {
			/* Se valida que exista el usuario */
			Usuario usuario = usuarioDAO.listarPorId(id);
			if(usuario == null) throw new VacunasUyException("El usuario indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
			usuarioDAO.eliminar(usuario);
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
	@Override
	public UsuarioLoginExitosoDTO loginBackoffice(UsuarioLoginBackofficeDTO usuarioDTO) throws VacunasUyException {
		/* Se valida que exista el correo electrónico */
		Usuario usuario = usuarioDAO.listarPorCorreo(usuarioDTO.getCorreo());
		if (usuario == null) {
			throw new VacunasUyException("Usuario o contraseña incorrectos.", VacunasUyException.DATOS_INCORRECTOS);
		} else {
			/* Se verifica que la contraseña sea válida */
			BCrypt.Result resultado = null;
			resultado = BCrypt.verifyer().verify(usuarioDTO.getPassword().toCharArray(), usuario.getPassword());
			if(resultado.verified) {
				String token = crearJsonWebToken(usuario);
				return usuarioConverter.fromLogin(usuario, token);
			} else {
				throw new VacunasUyException("Usuario o contraseña incorrectos.", VacunasUyException.DATOS_INCORRECTOS);
			}
		}
	}

	@Override
	public UsuarioLoginExitosoDTO loginGubUy(RespuestaUserInfoDTO usuarioDTO) throws VacunasUyException {
		/* Verifico si el usuario se encuentra registrado */
		Usuario usuario = usuarioDAO.listarPorDocumento(usuarioDTO.getNumero_documento());
		if(usuario == null) {
			/* Debo registrarlo */
			usuario = new Usuario();
			usuario.setNombre(usuarioDTO.getPrimer_nombre());
			usuario.setApellido(usuarioDTO.getPrimer_apellido());
			usuario.setDocumento(usuarioDTO.getNumero_documento());
			usuario.setCorreo(usuarioDTO.getEmail());
			/* Le agrego el rol de ciudadano */
			Rol rol = rolDAO.listarPorId(4L);
			usuario.getRoles().add(rol);
			/* Le agrego fecha de nacimiento*/
			UsuarioDNICDTO usuarioDNIC = getDatosDNIC(usuarioDTO.getNumero_documento());
			usuario.setFechaNacimiento(LocalDate.parse(usuarioDNIC.getFechaDeNacimiento()));
			/* Le agrego un sector laboral aleatorio */
			List<SectorLaboral> sectoresLaborales = sectorLaboralDAO.listar();
			SectorLaboral sectorLaboral = sectoresLaborales.stream().filter(sector -> 
				sector.getNombre().equals(usuarioDNIC.getSectorLaboral())).findFirst().orElse(null);
			usuario.setSectorLaboral(sectorLaboral);
			usuario = usuarioDAO.crear(usuario);
		}else {
			/* Verifico si el usuario tiene rol ciudadano */
			boolean tieneRol = false;
			for (Rol rol : usuario.getRoles()) {
				if(rol.getNombre().equalsIgnoreCase("Ciudadano")) {
					tieneRol = true;
				}
			}
			if(!tieneRol) {
				/* Le agrego el rol de ciudadano */
				Rol rol = rolDAO.listarPorId(4L);
				usuario.getRoles().add(rol);
				usuario = usuarioDAO.editar(usuario);
			}
		}
		/* Creo un nuevo inicio de sesión */
		String token = crearJsonWebToken(usuario);
		return usuarioConverter.fromLogin(usuario, token);
	}
	
	/* Función auxiliar para generar un JWT */
	private String crearJsonWebToken(Usuario usuario) {
		Date ahora = new Date();
		/* 1 horas de validez */
		Date expiracion = new Date(ahora.getTime() + (1000*60*60));
		return Jwts.builder()
				.setSubject(Long.toString(usuario.getId()))
				.claim("roles", usuario.getRoles())
				.setIssuedAt(ahora)
				.setExpiration(expiracion)
				.signWith(SignatureAlgorithm.HS512, Constantes.JWT_KEY)
				.compact();
	}
		
	@Override
	public void asignarVacunadorAPuesto(AtiendeCrearDTO atiendeDTO) throws VacunasUyException{
		try {
			Usuario vacunadorAux = usuarioDAO.listarPorId(atiendeDTO.getIdUsuario());
			if(vacunadorAux==null) throw new VacunasUyException("El usuario indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
			Puesto puestoAux = puestoDAO.listarPorId(atiendeDTO.getIdPuesto());
			if(puestoAux==null) throw new VacunasUyException("El puesto indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
			Atiende atiende = atiendeConverter.fromCrearDTO(atiendeDTO);
			atiende.setUsuario(vacunadorAux);
			atiende.setPuesto(puestoAux);
			
			vacunadorAux.getAtiende().add(atiende);
//			puestoAux.getAtiende().add(atiende);
			usuarioDAO.editar(vacunadorAux);
//			puestoDAO.editar(puestoAux);
		}catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
	//desde backend
	@Override
	public void agregarActoVacunal(Long usuario, Long actoVacunal) throws VacunasUyException{
		ActoVacunal actoVacunalAux = actoVacunalDAO.listarPorId(actoVacunal);
		if(actoVacunalAux==null) throw new VacunasUyException("El acto vacunal indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		Usuario usuarioAux = usuarioDAO.listarPorId(usuario);
		usuarioAux.getActosVacunales().add(actoVacunalAux);
		usuarioDAO.editar(usuarioAux);
	}
	
	//desde backend
	@Override
	public void agregarAgenda(Long usuario, Long agenda) throws VacunasUyException{
		Agenda agendaAux = agendaDAO.listarPorId(agenda);
		if(agendaAux==null) throw new VacunasUyException("La agenda indicada no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		Usuario usuarioAux = usuarioDAO.listarPorId(usuario);
		usuarioAux.getAgendas().add(agendaAux);
		usuarioDAO.editar(usuarioAux);
	}
	
	@Override
	public void cancelarAgenda(Long usuario, Long agenda) throws VacunasUyException{
		try {
			Usuario usuarioAux = usuarioDAO.listarPorId(usuario);
			if(usuarioAux==null) throw new VacunasUyException("El usuario indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
			Agenda agendaAux = agendaDAO.listarPorId(agenda);
			if(agendaAux==null) throw new VacunasUyException("La agenda indicada no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
			// se obtienen las agendas posteriores del mismo plan
//			List<Agenda> agendas = usuarioAux.getAgendas();
//			List<Agenda> agendasAEliminar = agendas.stream()
//					.filter(a -> a.getPlanVacunacion()==agendaAux.getPlanVacunacion() && 
//						(a.getFecha().compareTo(agendaAux.getFecha()) > 0))
//					.collect(Collectors.toList());	
			
//			List<Agenda> agendasAEliminar = new ArrayList<Agenda>();			segunda prueba
//			agendasAEliminar.add(agendaAux);
//			for(Agenda a: agendas) {
//				if((a.getPlanVacunacion()==agendaAux.getPlanVacunacion() ) &&  (a.getFecha().compareTo(agendaAux.getFecha()) > 0)) {
//					agendasAEliminar.add(a);
//				}
//			}
			//se borran las agendas
//			for(Agenda a: agendasAEliminar) {
//				agendaService.eliminar(a.getId());
//			}	
			agendaService.eliminar(agenda);

		}catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}


	@Override
	public boolean existeAgenda(Long id_usuario, Long id_plan) throws VacunasUyException {
		Usuario u = usuarioDAO.listarPorId(id_usuario);
		List<Agenda> agendas = u.getAgendas();
		for (Agenda a: agendas) {
			if (a.getPlanVacunacion().getId()==id_plan) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void registrarTokenFirebase(UsuarioRegistrarTFDTO usuarioDTO) throws VacunasUyException {
		try {
			/* Se valida que exista el usuario */
			Usuario usuario = usuarioDAO.listarPorId(usuarioDTO.getId());
			if(usuario == null) throw new VacunasUyException("El usuario indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
			usuario.setTokenFirebase(usuarioDTO.getToken());
			usuarioDAO.editar(usuario);
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
	private UsuarioDNICDTO getDatosDNIC(String cedula) {
		Client cliente = ClientBuilder.newClient();
		WebTarget target = cliente.target(Constantes.NODOS_EXTERNOS_REST_URL+"/personas/"+cedula);
		UsuarioDNICDTO response = target.request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .get(UsuarioDNICDTO.class);
		return response;
	}
	
	@Override
	public List<AgendaDTO> listarAgendasCiudadano(Long id) throws VacunasUyException{
		try{
			Usuario usuario = usuarioDAO.listarPorId(id);
			if(usuario == null) throw new VacunasUyException("El usuario indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
			// se valida que sea ciudadano
			List<Rol> roles = usuario.getRoles();
			Rol rol = roles.stream().filter(r -> r.getNombre().equals("Ciudadano")).findFirst().orElse(null);
			if(rol == null) throw new VacunasUyException("El usuario indicado no es un ciudadano.", VacunasUyException.DATOS_INCORRECTOS);
			return agendaConverter.fromEntity(usuario.getAgendas());
		}catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}		
	}
	
	@Override
	public List<AtiendeDTO> listarAtiendeVacunador(Long id) throws VacunasUyException{
		try{
			Usuario usuario = usuarioDAO.listarPorId(id);
			if(usuario == null) throw new VacunasUyException("El usuario indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
			// se valida que sea vacunador
			List<Rol> roles = usuario.getRoles();
			Rol rol = roles.stream().filter(r -> r.getNombre().equals("Vacunador")).findFirst().orElse(null);
			if(rol == null) throw new VacunasUyException("El usuario indicado no es un vacunador.", VacunasUyException.DATOS_INCORRECTOS);
			return atiendeConverter.fromEntity(usuario.getAtiende());
		}catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}	
	

}
