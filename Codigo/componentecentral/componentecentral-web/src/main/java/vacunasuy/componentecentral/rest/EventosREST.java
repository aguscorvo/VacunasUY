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
import vacunasuy.componentecentral.business.IEventoService;
import vacunasuy.componentecentral.dto.EventoDTO;
import vacunasuy.componentecentral.dto.EventoPerifericoDTO;
import vacunasuy.componentecentral.dto.EventoCrearDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;
import vacunasuy.componentecentral.util.EstadoEvento;

@RequestScoped
@Path("/eventos")
@Consumes("application/json")
@Produces("application/json")
public class EventosREST {
	
	@EJB
	private IEventoService eventoService;
	
	@GET
	public Response listar() {
		RespuestaREST<List<EventoDTO>> respuesta = null;
		try {
			List<EventoDTO> eventos = eventoService.listar();
			respuesta = new RespuestaREST<List<EventoDTO>>(true, "Eventos listados con éxito.", eventos);
			return Response.ok(respuesta).build();
		} catch (Exception e) {
			respuesta = new RespuestaREST<>(false, e.getLocalizedMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
		}
	}
	
	@GET
	@Path("/listarPorEstado/{estado}")
	public Response listar(@PathParam("estado") String estado) {
		RespuestaREST<List<EventoPerifericoDTO>> respuesta = null;
		try {
			EstadoEvento estadoEnum = null;
			if(estado.equalsIgnoreCase("Iniciado")) {
				estadoEnum = EstadoEvento.INICIADO;
			} else if(estado.equalsIgnoreCase("Transito")) {
				estadoEnum = EstadoEvento.TRANSITO;
			} else if(estado.equalsIgnoreCase("Recibido")) {
				estadoEnum = EstadoEvento.RECIBIDO;
			} else {
				respuesta = new RespuestaREST<>(false, "Estado inválido.");
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			}
			List<EventoPerifericoDTO> eventos = eventoService.listarPorEstado(estadoEnum);
			respuesta = new RespuestaREST<List<EventoPerifericoDTO>>(true, "Eventos listados con éxito.", eventos);
			return Response.ok(eventos).build();
		} catch (Exception e) {
			respuesta = new RespuestaREST<>(false, e.getLocalizedMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
		}
	}
	
	@POST
	public Response crear(EventoCrearDTO request) {
		RespuestaREST<EventoDTO> respuesta = null;
		try {
			EventoDTO evento = eventoService.crear(request);
			respuesta = new RespuestaREST<EventoDTO>(true, "Evento creado con éxito.", evento);
			return Response.ok(respuesta).build();
		} catch (VacunasUyException e) {
			respuesta = new RespuestaREST<EventoDTO>(false, e.getLocalizedMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
		}
	}
	
	@PUT
	@Path("/editar/{id}")
	public Response editar(@PathParam("id") Long id, EventoCrearDTO request) {
		RespuestaREST<EventoDTO> respuesta = null;
		try {
			EventoDTO lote = eventoService.editar(id, request);
			respuesta = new RespuestaREST<EventoDTO>(true, "Evento editado con éxito.", lote);
			return Response.ok(respuesta).build();
		} catch (VacunasUyException e) {
			respuesta = new RespuestaREST<EventoDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
	@DELETE
	@Path("/eliminar/{id}")
	public Response eliminar(@PathParam("id") Long id) {
		RespuestaREST<EventoDTO> respuesta = null;
		try {
			eventoService.eliminar(id);
			respuesta = new RespuestaREST<EventoDTO>(true, "Evento eliminado con éxito.");
			return Response.ok(respuesta).build();
		} catch (VacunasUyException e) {
			respuesta = new RespuestaREST<EventoDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}

}
