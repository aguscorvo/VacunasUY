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
import vacunasuy.componentecentral.business.ILoteService;
import vacunasuy.componentecentral.dto.LoteCrearDTO;
import vacunasuy.componentecentral.dto.LoteDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;
import vacunasuy.componentecentral.security.RecursoProtegidoJWT;

@RequestScoped
@Path("/lotes")
@Consumes("application/json")
@Produces("application/json")
public class LotesREST {

	@EJB
	private ILoteService loteService;
	
	@GET
	@RecursoProtegidoJWT
	public Response listar() {
		RespuestaREST<List<LoteDTO>> respuesta = null;
		try {
			List<LoteDTO> lotes = loteService.listar();
			respuesta = new RespuestaREST<List<LoteDTO>>(true, "Lotes listados con éxito.", lotes);
			return Response.ok(respuesta).build();
		} catch (Exception e) {
			respuesta = new RespuestaREST<>(false, e.getLocalizedMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
		}
	}
	
	@POST
	@RecursoProtegidoJWT
	public Response crear(LoteCrearDTO request) {
		RespuestaREST<LoteDTO> respuesta = null;
		try {
			LoteDTO lote = loteService.crear(request);
			respuesta = new RespuestaREST<LoteDTO>(true, "Lote creado con éxito.", lote);
			return Response.ok(respuesta).build();
		} catch (VacunasUyException e) {
			respuesta = new RespuestaREST<LoteDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
	@PUT
	@Path("/editar/{id}")
	@RecursoProtegidoJWT
	public Response editar(@PathParam("id") Long id, LoteCrearDTO request) {
		RespuestaREST<LoteDTO> respuesta = null;
		try {
			LoteDTO lote = loteService.editar(id, request);
			respuesta = new RespuestaREST<LoteDTO>(true, "Lote editado con éxito.", lote);
			return Response.ok(respuesta).build();
		} catch (VacunasUyException e) {
			respuesta = new RespuestaREST<LoteDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
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
		RespuestaREST<LoteDTO> respuesta = null;
		try {
			loteService.eliminar(id);
			respuesta = new RespuestaREST<LoteDTO>(true, "Lote eliminado con éxito.");
			return Response.ok(respuesta).build();
		} catch (VacunasUyException e) {
			respuesta = new RespuestaREST<LoteDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
}
