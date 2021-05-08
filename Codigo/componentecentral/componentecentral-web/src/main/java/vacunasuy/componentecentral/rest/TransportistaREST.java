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

import vacunasuy.componentecentral.business.ITransportistaService;
import vacunasuy.componentecentral.dto.AgendaCrearDTO;
import vacunasuy.componentecentral.dto.AgendaDTO;
import vacunasuy.componentecentral.dto.TransportistaCrearDTO;
import vacunasuy.componentecentral.dto.TransportistaDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;
import vacunasuy.componentecentral.security.RecursoProtegidoJWT;

@RequestScoped
@Path("/transportistas")
@Consumes("application/json")
@Produces("application/json")
public class TransportistaREST {
	
	@EJB
	ITransportistaService transportistaService;
	
	@GET
//	@RecursoProtegidoJWT
	public Response listar() {
		RespuestaREST<List<TransportistaDTO>> respuesta = null;
		try {
			List<TransportistaDTO> transportistas = transportistaService.listar();
			respuesta = new RespuestaREST<List<TransportistaDTO>>(true, "Transportistas listados con éxito", transportistas);
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
		RespuestaREST<TransportistaDTO> respuesta = null;
		try {
			TransportistaDTO transportista = transportistaService.listarPorId(id);
			respuesta = new RespuestaREST<TransportistaDTO>(true, "Transportista listado con éxito.", transportista);
			return Response.ok(respuesta).build();
		}catch(VacunasUyException e) {
			respuesta = new RespuestaREST<TransportistaDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			}else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
	@POST
	public Response crear(TransportistaCrearDTO request) {
		RespuestaREST<TransportistaDTO> respuesta = null;
		try {
			TransportistaDTO transportista = transportistaService.crear(request);
			respuesta = new RespuestaREST<TransportistaDTO>(true, "Transportista creado con éxito.", transportista);
			return Response.ok(respuesta).build();
		}catch (VacunasUyException e) {
			respuesta = new RespuestaREST<TransportistaDTO>(false, e.getLocalizedMessage());
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
	public Response editar(@PathParam("id") Long id, TransportistaCrearDTO request) {
		RespuestaREST<TransportistaDTO> respuesta = null;
		try{
			TransportistaDTO agenda = transportistaService.editar(id, request);
			respuesta = new RespuestaREST<TransportistaDTO>(true, "Transportista editado con éxito.", agenda);
			return Response.ok(respuesta).build();
		}catch (VacunasUyException e) {
			respuesta = new RespuestaREST<TransportistaDTO>(false, e.getLocalizedMessage());
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
		RespuestaREST<TransportistaDTO> respuesta = null;
		try {
			transportistaService.eliminar(id);
			respuesta = new RespuestaREST<TransportistaDTO>(true, "Transportista eliminado con éxito.");
			return Response.ok(respuesta).build();
		}catch (VacunasUyException e) {
			respuesta = new RespuestaREST<TransportistaDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}

}
