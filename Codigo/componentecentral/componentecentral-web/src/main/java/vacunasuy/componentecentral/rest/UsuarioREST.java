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
import vacunasuy.componentecentral.dto.UsuarioCrearDTO;
import vacunasuy.componentecentral.dto.UsuarioDTO;
import vacunasuy.componentecentral.dto.UsuarioLoginBackofficeDTO;
import vacunasuy.componentecentral.dto.UsuarioLoginExitosoDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@RequestScoped
@Path("/usuarios")
@Consumes("application/json")
@Produces("application/json")
public class UsuarioREST {
	
	@EJB
	IUsuarioService usuarioService;
	
	@GET
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
	
	@POST
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
	
}
