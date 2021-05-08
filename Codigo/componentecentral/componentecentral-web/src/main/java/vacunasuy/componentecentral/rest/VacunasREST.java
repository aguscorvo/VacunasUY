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

import vacunasuy.componentecentral.business.IVacunaService;
import vacunasuy.componentecentral.dto.VacunaCrearDTO;
import vacunasuy.componentecentral.dto.VacunaDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;


@RequestScoped
@Path("/vacunas")
@Consumes("application/json")
@Produces("application/json")
public class VacunasREST {
	
	@EJB
	IVacunaService vacunaService;
	
	@GET
	//@RecursoProtegidoJWT
	public Response listar() {
		RespuestaREST<List<VacunaDTO>> respuesta = null;
		try {
			List<VacunaDTO> vacunas = vacunaService.listar();
			respuesta = new RespuestaREST<List<VacunaDTO>>(true, "Vacunas listadas con éxito.", vacunas);
			return Response.ok(respuesta).build();
		} catch (VacunasUyException e) {
			respuesta = new RespuestaREST<>(false, e.getLocalizedMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
		}
	}
	
	@POST
	public Response crear(VacunaCrearDTO request) {
		RespuestaREST<VacunaDTO> respuesta = null;
		try {
			VacunaDTO vacuna = vacunaService.crear(request);
			respuesta = new RespuestaREST<VacunaDTO>(true, "Vacuna creada con éxito.", vacuna);
			return Response.ok(respuesta).build();
		} catch (VacunasUyException e) {
			respuesta = new RespuestaREST<VacunaDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
	@PUT
	@Path("/editar/{id}")
	//@RecursoProtegidoJWT
	public Response editar(@PathParam("id") Long id, VacunaCrearDTO request) {
		RespuestaREST<VacunaDTO> respuesta = null;
		try {
			VacunaDTO vacuna = vacunaService.editar(id, request);
			respuesta = new RespuestaREST<VacunaDTO>(true, "Vacuna editada con éxito.", vacuna);
			return Response.ok(respuesta).build();
		} catch (VacunasUyException e) {
			respuesta = new RespuestaREST<VacunaDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO || e.getCodigo() == VacunasUyException.EXISTE_REGISTRO) {
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
		RespuestaREST<VacunaDTO> respuesta = null;
		try {
			vacunaService.eliminar(id);
			respuesta = new RespuestaREST<VacunaDTO>(true, "Vacuna eliminada con éxito.");
			return Response.ok(respuesta).build();
		} catch (VacunasUyException e) {
			respuesta = new RespuestaREST<VacunaDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
}

