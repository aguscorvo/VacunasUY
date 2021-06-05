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

import vacunasuy.componentecentral.business.IAgendaService;
import vacunasuy.componentecentral.dto.AgendaCrearDTO;
import vacunasuy.componentecentral.dto.AgendaDTO;
import vacunasuy.componentecentral.dto.AgendaMinDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;
import vacunasuy.componentecentral.security.RecursoProtegidoJWT;

@RequestScoped
@Path("/agendas")
@Consumes("application/json")
@Produces("application/json")
public class AgendaREST {

	@EJB
	IAgendaService agendaService;
	
	@GET
//	@RecursoProtegidoJWT
	public Response listar() {
		RespuestaREST<List<AgendaDTO>> respuesta = null;
		try {
			List<AgendaDTO> agendas = agendaService.listar();
			respuesta = new RespuestaREST<List<AgendaDTO>>(true, "Agendas listadas con éxito", agendas);
			return Response.ok(respuesta).build();
		}catch (VacunasUyException e) {
			respuesta = new RespuestaREST<>(false, e.getLocalizedMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
		}
	}
	
	@GET
	@Path("/{id}")
//	@RecursoProtegidoJWT
	public Response listarPorId(@PathParam("id") Long id) {
		RespuestaREST<AgendaDTO> respuesta = null;
		try {
			AgendaDTO agenda = agendaService.listarPorId(id);
			respuesta = new RespuestaREST<AgendaDTO>(true, "Agenda listada con éxito.", agenda);
			return Response.ok(respuesta).build();
		}catch(VacunasUyException e) {
			respuesta = new RespuestaREST<AgendaDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			}else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
	@POST
	public Response crear(AgendaCrearDTO request) {
		RespuestaREST <List<AgendaMinDTO>> respuesta = null;
		try {
			List<AgendaMinDTO> agendas = agendaService.crear(request);
			respuesta = new RespuestaREST<List<AgendaMinDTO>>(true, "Agenda creada con éxito.", agendas);
			return Response.ok(respuesta).build();
		}catch (VacunasUyException e) {
			respuesta = new RespuestaREST<List<AgendaMinDTO>>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
	@PUT
	@Path("/editar/{id}")
//	@RecursoProtegidoJWT
	public Response editar(@PathParam("id") Long id, AgendaCrearDTO request) {
		RespuestaREST<AgendaDTO> respuesta = null;
		try{
			AgendaDTO agenda = agendaService.editar(id, request);
			respuesta = new RespuestaREST<AgendaDTO>(true, "Agenda editada con éxito.", agenda);
			return Response.ok(respuesta).build();
		}catch (VacunasUyException e) {
			respuesta = new RespuestaREST<AgendaDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO || e.getCodigo() == VacunasUyException.EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
//	@DELETE
//	@Path("/eliminar/{id}")
//	@RecursoProtegidoJWT
//	public Response eliminar(@PathParam("id") Long id) {
//		RespuestaREST<AgendaDTO> respuesta = null;
//		try {
//			agendaService.eliminar(id);
//			respuesta = new RespuestaREST<AgendaDTO>(true, "Agenda eliminada con éxito.");
//			return Response.ok(respuesta).build();
//		}catch (VacunasUyException e) {
//			respuesta = new RespuestaREST<AgendaDTO>(false, e.getLocalizedMessage());
//			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
//				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
//			} else {
//				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
//			}
//		}
//	}
	
	@DELETE
	@Path("/cancelarAgenda/{usuario}/{agenda}")
	public Response cancelarAgenda(@PathParam("usuario") Long usuario, @PathParam("agenda")Long agenda) {
		RespuestaREST<AgendaDTO> respuesta = null;
		try {
			agendaService.cancelarAgenda(usuario, agenda);
			respuesta = new RespuestaREST<AgendaDTO>(true, "Agenda cancelada con éxito");
			return Response.ok(respuesta).build();
		}catch (VacunasUyException e) {
			respuesta = new RespuestaREST<AgendaDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}	
	
	
}
