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
import vacunasuy.componentecentral.business.IPlanVacunacionService;
import vacunasuy.componentecentral.dto.PlanVacunacionCrearDTO;
import vacunasuy.componentecentral.dto.PlanVacunacionDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;
import vacunasuy.componentecentral.security.RecursoProtegidoJWT;

@RequestScoped
@Path("/planesVacunacion")
@Consumes("application/json")
@Produces("application/json")
public class PlanesVacunacionREST {
	
	@EJB
	private IPlanVacunacionService planVacunacionService;
	
	@GET
	public Response listar() {
		RespuestaREST<List<PlanVacunacionDTO>> respuesta = null;
		try {
			List<PlanVacunacionDTO> usuarios = planVacunacionService.listar();
			respuesta = new RespuestaREST<List<PlanVacunacionDTO>>(true, "Planes de vacunación listados con éxito.", usuarios);
			return Response.ok(respuesta).build();
		} catch (VacunasUyException e) {
			respuesta = new RespuestaREST<>(false, e.getLocalizedMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
		}
	}
	
	@GET
	@Path("/listarVigentes")
	public Response listarPlanesVigentes() {
		RespuestaREST<List<PlanVacunacionDTO>> respuesta = null;
		try {
			List<PlanVacunacionDTO> usuarios = planVacunacionService.listarPlanesVigentes();
			respuesta = new RespuestaREST<List<PlanVacunacionDTO>>(true, "Planes de vacunación listados con éxito.", usuarios);
			return Response.ok(respuesta).build();
		} catch (VacunasUyException e) {
			respuesta = new RespuestaREST<>(false, e.getLocalizedMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
		}
	}
	
	@POST
	public Response crear(PlanVacunacionCrearDTO request) {
		RespuestaREST<PlanVacunacionDTO> respuesta = null;
		try {
			PlanVacunacionDTO usuario = planVacunacionService.crear(request);
			respuesta = new RespuestaREST<PlanVacunacionDTO>(true, "Plan de vacunación creado con éxito.", usuario);
			return Response.ok(respuesta).build();
		} catch (VacunasUyException e) {
			respuesta = new RespuestaREST<PlanVacunacionDTO>(false, e.getLocalizedMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
		}
	}
	
	@PUT
	@Path("/editar/{id}")
	@RecursoProtegidoJWT
	public Response editar(@PathParam("id") Long id, PlanVacunacionCrearDTO request) {
		RespuestaREST<PlanVacunacionDTO> respuesta = null;
		try {
			PlanVacunacionDTO usuario = planVacunacionService.editar(id, request);
			respuesta = new RespuestaREST<PlanVacunacionDTO>(true, "Plan de vacunación editado con éxito.", usuario);
			return Response.ok(respuesta).build();
		} catch (VacunasUyException e) {
			respuesta = new RespuestaREST<PlanVacunacionDTO>(false, e.getLocalizedMessage());
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
		RespuestaREST<PlanVacunacionDTO> respuesta = null;
		try {
			planVacunacionService.eliminar(id);
			respuesta = new RespuestaREST<PlanVacunacionDTO>(true, "Plan de vacunación eliminado con éxito.");
			return Response.ok(respuesta).build();
		} catch (VacunasUyException e) {
			respuesta = new RespuestaREST<PlanVacunacionDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}

}
