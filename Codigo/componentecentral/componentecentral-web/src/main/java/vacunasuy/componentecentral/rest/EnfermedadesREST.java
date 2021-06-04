package vacunasuy.componentecentral.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import vacunasuy.componentecentral.business.IEnfermedadService;
import vacunasuy.componentecentral.dto.EnfermedadCrearDTO;
import vacunasuy.componentecentral.dto.EnfermedadDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@RequestScoped
@Path("/enfermedades")
@Consumes("application/json")
@Produces("application/json")
public class EnfermedadesREST {

	@EJB
	IEnfermedadService eService;
	
	@GET
	//@RecursoProtegidoJWT
	public Response listar() {
		RespuestaREST<List<EnfermedadDTO>> respuesta = null;
		try {
			List<EnfermedadDTO> enfermedades = eService.listar();
			respuesta = new RespuestaREST<List<EnfermedadDTO>>(true, "Enfermedades listadas con éxito.", enfermedades);
			return Response.ok(respuesta).build();
		} catch (VacunasUyException e) {
			respuesta = new RespuestaREST<>(false, e.getLocalizedMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
		}
	}
	
	@GET
	@Path("/listarEnfermedadesPorUsuario/{id}")
	//@RecursoProtegidoJWT
	public Response listar(@PathParam("id") Long id) {
		RespuestaREST<List<EnfermedadDTO>> respuesta = null;
		try {
			List<EnfermedadDTO> enfermedades = eService.listarEnfermedadesPorUsuario(id);
			respuesta = new RespuestaREST<List<EnfermedadDTO>>(true, "Enfermedades listadas con éxito.", enfermedades);
			return Response.ok(respuesta).build();
		} catch (VacunasUyException e) {
			respuesta = new RespuestaREST<>(false, e.getLocalizedMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
		}
	}
	
	@POST
	public Response crear(EnfermedadCrearDTO request) {
		RespuestaREST<EnfermedadDTO> respuesta = null;
		try {
			EnfermedadDTO enfermedad = eService.crear(request);
			respuesta = new RespuestaREST<EnfermedadDTO>(true, "Enfermedad creada con éxito.", enfermedad);
			return Response.ok(respuesta).build();
		} catch (VacunasUyException e) {
			respuesta = new RespuestaREST<EnfermedadDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}

	@DELETE
	@Path("/eliminar/{id}")
	//@RecursoProtegidoJWT
	public Response eliminar(@PathParam("id") Long id) {
		RespuestaREST<EnfermedadDTO> respuesta = null;
		try {
			eService.eliminar(id);
			respuesta = new RespuestaREST<EnfermedadDTO>(true, "Enfermedad eliminada con éxito.");
			return Response.ok(respuesta).build();
		} catch (VacunasUyException e) {
			respuesta = new RespuestaREST<EnfermedadDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}

}


