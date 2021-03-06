package vacunasuy.componentecentral.rest;

import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import vacunasuy.componentecentral.business.IUsuarioService;
import vacunasuy.componentecentral.dto.AgendaDTO;
import vacunasuy.componentecentral.dto.AtiendeCrearDTO;
import vacunasuy.componentecentral.dto.AtiendeDTO;
import vacunasuy.componentecentral.dto.UsuarioCrearDTO;
import vacunasuy.componentecentral.dto.UsuarioDTO;
import vacunasuy.componentecentral.dto.UsuarioLoginBackofficeDTO;
import vacunasuy.componentecentral.dto.UsuarioLoginExitosoDTO;
import vacunasuy.componentecentral.dto.UsuarioRegistrarTFDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;
import vacunasuy.componentecentral.security.RecursoProtegidoJWT;

@RequestScoped
@Path("/usuarios")
@Consumes("application/json")
@Produces("application/json")
public class UsuariosREST {
	
	@EJB
	IUsuarioService usuarioService;
	
	@GET
	@RecursoProtegidoJWT
	public Response listar() {
		RespuestaREST<List<UsuarioDTO>> respuesta = null;
		try {
			List<UsuarioDTO> usuarios = usuarioService.listar();
			respuesta = new RespuestaREST<List<UsuarioDTO>>(true, "Usuarios listados con éxito.", usuarios);
			return Response.ok(respuesta).build();
		} catch (VacunasUyException e) {
			respuesta = new RespuestaREST<>(false, e.getLocalizedMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
		}
	}
	
	@GET
	@Path("/listar/{id}")
	public Response listarPorId(@PathParam("id") Long id) {
		RespuestaREST<UsuarioDTO> respuesta = null;
		try {
			UsuarioDTO usuario = usuarioService.listarPorId(id);
			respuesta = new RespuestaREST<UsuarioDTO>(true, "Usuario Listado con exito.", usuario);
			return Response.ok(respuesta).build();
		} catch (VacunasUyException e) {
			respuesta = new RespuestaREST<UsuarioDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.DATOS_INCORRECTOS) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
	@GET
	@Path("/token/{token}")
	public Response listarPorToken(@PathParam("token") String token) {
		RespuestaREST<UsuarioDTO> respuesta = null;
		try {
			UsuarioDTO usuario = usuarioService.listarPorToken(token);
			respuesta = new RespuestaREST<UsuarioDTO>(true, "Usuario Listado con exito.", usuario);
			return Response.ok(respuesta).build();
		} catch (VacunasUyException e) {
			respuesta = new RespuestaREST<UsuarioDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.DATOS_INCORRECTOS) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
	@POST
	@RecursoProtegidoJWT
	public Response crear(UsuarioCrearDTO request) {
		RespuestaREST<UsuarioDTO> respuesta = null;
		try {
			UsuarioDTO usuario = usuarioService.crear(request);
			respuesta = new RespuestaREST<UsuarioDTO>(true, "Usuario creado con éxito.", usuario);
			return Response.ok(respuesta).build();
		} catch (VacunasUyException e) {
			respuesta = new RespuestaREST<UsuarioDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
	@PUT
	@Path("/editar/{id}")
	@RecursoProtegidoJWT
	public Response editar(@PathParam("id") Long id, UsuarioCrearDTO request) {
		RespuestaREST<UsuarioDTO> respuesta = null;
		try {
			UsuarioDTO usuario = usuarioService.editar(id, request);
			respuesta = new RespuestaREST<UsuarioDTO>(true, "Usuario editado con éxito.", usuario);
			return Response.ok(respuesta).build();
		} catch (VacunasUyException e) {
			respuesta = new RespuestaREST<UsuarioDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO || e.getCodigo() == VacunasUyException.EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
	@DELETE
	@Path("/eliminar/{id}")
	@RecursoProtegidoJWT
	public Response eliminar(@PathParam("id") Long id) {
		RespuestaREST<UsuarioDTO> respuesta = null;
		try {
			usuarioService.eliminar(id);
			respuesta = new RespuestaREST<UsuarioDTO>(true, "Usuario eliminado con éxito.");
			return Response.ok(respuesta).build();
		} catch (VacunasUyException e) {
			respuesta = new RespuestaREST<UsuarioDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}

	@POST
	@Path("/loginbackoffice")
	public Response loginBackoffice(UsuarioLoginBackofficeDTO request) {
		RespuestaREST<UsuarioLoginExitosoDTO> respuesta = null;
		try {
			UsuarioLoginExitosoDTO usuario = usuarioService.loginBackoffice(request);
			respuesta = new RespuestaREST<UsuarioLoginExitosoDTO>(true, "Inicio de sesión correcto.", usuario);
			return Response.ok(respuesta).build();
		} catch (VacunasUyException e) {
			respuesta = new RespuestaREST<UsuarioLoginExitosoDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.DATOS_INCORRECTOS) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
	@POST
	@Path("/asignarVacunador")
	@RecursoProtegidoJWT
	public Response asignarVacunadorAPuesto(AtiendeCrearDTO atiendeDTO) {
		RespuestaREST<AtiendeCrearDTO> respuesta = null;
		try {
			usuarioService.asignarVacunadorAPuesto(atiendeDTO);
			respuesta = new RespuestaREST<AtiendeCrearDTO>(true, "Vacunador asignado con éxito.");
			return Response.ok(respuesta).build();
		}catch (VacunasUyException e) {
			respuesta = new RespuestaREST<AtiendeCrearDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.DATOS_INCORRECTOS) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
	@POST
	@Path("/registrarTokenFirebase")
	public Response registrarTokenFirebase(UsuarioRegistrarTFDTO request) {
		RespuestaREST<UsuarioDTO> respuesta = null;
		try {
			usuarioService.registrarTokenFirebase(request);
			respuesta = new RespuestaREST<UsuarioDTO>(true, "Token registrado con éxito.");
			return Response.ok(respuesta).build();
		}catch (VacunasUyException e) {
			respuesta = new RespuestaREST<UsuarioDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
	@GET
	@Path("/listarAgendasCiudadano/{id}")
	@RecursoProtegidoJWT
	public Response listarAgendasCiudadano(@PathParam("id") Long id) {
		RespuestaREST<List<AgendaDTO>> respuesta = null;
		try {
			List<AgendaDTO> agendas = usuarioService.listarAgendasCiudadano(id);
			respuesta = new RespuestaREST<List<AgendaDTO>>(true, "Agendas listadas con éxito", agendas);
			return Response.ok(respuesta).build();
		}catch (VacunasUyException e) {
			respuesta = new RespuestaREST<List<AgendaDTO>>(false, e.getLocalizedMessage());
			if((e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) || (e.getCodigo() == VacunasUyException.DATOS_INCORRECTOS)) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
	@GET
	@Path("/listarAtiendeVacunador/{id}")
	@RecursoProtegidoJWT
	public Response listarAtiendeVacunador(@PathParam("id") Long id) {
		RespuestaREST<List<AtiendeDTO>> respuesta = null;
		try {
			List<AtiendeDTO> atiende = usuarioService.listarAtiendeVacunador(id);
			respuesta = new RespuestaREST<List<AtiendeDTO>>(true, "Atiende listados con éxito", atiende);
			return Response.ok(respuesta).build();
		}catch (VacunasUyException e) {
			respuesta = new RespuestaREST<List<AtiendeDTO>>(false, e.getLocalizedMessage());
			if((e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) || (e.getCodigo() == VacunasUyException.DATOS_INCORRECTOS)) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
}
