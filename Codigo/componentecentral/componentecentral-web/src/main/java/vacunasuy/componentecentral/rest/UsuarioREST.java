package vacunasuy.componentecentral.rest;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import vacunasuy.componentecentral.business.IUsuarioService;
import vacunasuy.componentecentral.dto.UsuarioCrearDTO;
import vacunasuy.componentecentral.dto.UsuarioDTO;
import vacunasuy.componentecentral.dto.UsuarioLoginBackofficeDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@RequestScoped
@Path("/usuarios")
@Consumes("application/json")
@Produces("application/json")
public class UsuarioREST {
	
	@EJB
	IUsuarioService usuarioService;
	
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

	@POST
	@Path("/loginbackoffice")
	public Response loginBackoffice(UsuarioLoginBackofficeDTO request) {
		RespuestaREST<UsuarioDTO> respuesta = null;
		try {
			UsuarioDTO usuario = usuarioService.loginBackoffice(request);
			respuesta = new RespuestaREST<UsuarioDTO>(true, "Inicio de sesión correcto.", usuario);
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
	
}
