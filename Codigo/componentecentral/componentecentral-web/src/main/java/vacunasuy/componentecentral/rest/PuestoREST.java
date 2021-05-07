package vacunasuy.componentecentral.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import vacunasuy.componentecentral.business.IPuestoService;
import vacunasuy.componentecentral.dto.PuestoCrearDTO;
import vacunasuy.componentecentral.dto.PuestoDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;
import vacunasuy.componentecentral.security.RecursoProtegidoJWT;

@RequestScoped
@Path("/puestos")
@Consumes("application/json")
@Produces("application/json")
public class PuestoREST {
	
	@EJB
	IPuestoService puestoService;
	
	@GET
//	@RecursoProtegidoJWT
	public Response listar() {
		RespuestaREST<List<PuestoDTO>> respuesta = null;
		try {
			List<PuestoDTO> puestos = puestoService.listar();
			respuesta = new RespuestaREST<List<PuestoDTO>>(true, "Puestos listados con éxito.", puestos);
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
		RespuestaREST<PuestoDTO> respuesta = null;
		try {
			PuestoDTO puesto = puestoService.listarPorId(id);
			respuesta = new RespuestaREST<PuestoDTO>(true, "Puesto listado con éxito.", puesto);
			return Response.ok(respuesta).build();
		}catch(VacunasUyException e) {
			respuesta = new RespuestaREST<PuestoDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			}else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
	@GET
	@Path("/{numero}")
//	@RecursoProtegidoJWT
	public Response listarPorNumero(@PathParam("numero") Long numero) {
		RespuestaREST<PuestoDTO> respuesta = null;
		try {
			PuestoDTO puesto = puestoService.listarPorId(numero);
			respuesta = new RespuestaREST<PuestoDTO>(true, "Puesto listado con éxito.", puesto);
			return Response.ok(respuesta).build();
		}catch(VacunasUyException e) {
			respuesta = new RespuestaREST<PuestoDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			}else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
	@POST
	public Response crear(PuestoCrearDTO request) {
		RespuestaREST<PuestoDTO> respuesta = null;
		try {
			PuestoDTO puesto = puestoService.crear(request);
			respuesta = new RespuestaREST<PuestoDTO>(true, "Puesto creado con éxito.", puesto);
			return Response.ok(respuesta).build();
		}catch (VacunasUyException e) {
			respuesta = new RespuestaREST<PuestoDTO>(false, e.getLocalizedMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
		}
	}
	
	@PUT
	@Path("/editar/{id}")
	@RecursoProtegidoJWT
	public Response editar(@PathParam("id") Long id, PuestoCrearDTO request) {
		RespuestaREST<PuestoDTO> respuesta = null;
		try {
			PuestoDTO puesto = puestoService.editar(id, request);
			respuesta = new RespuestaREST<PuestoDTO>(true, "Puesto editado con éxito", puesto);
			return Response.ok(respuesta).build();
		}catch (VacunasUyException e) {
			respuesta = new RespuestaREST<PuestoDTO>(false, e.getLocalizedMessage());
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
		RespuestaREST<PuestoDTO> respuesta = null;
		try {
			puestoService.eliminar(id);
			respuesta = new RespuestaREST<PuestoDTO>(true, "Puesto eliminado con éxito.");
			return Response.ok(respuesta).build();			
		}catch (VacunasUyException e) {
			respuesta = new RespuestaREST<PuestoDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}		
	}
	

}
