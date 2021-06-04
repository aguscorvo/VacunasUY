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

import vacunasuy.componentecentral.business.IActoVacunalService;
import vacunasuy.componentecentral.dto.ActoVacunalCertificadoDTO;
import vacunasuy.componentecentral.dto.ActoVacunalCrearDTO;
import vacunasuy.componentecentral.dto.ActoVacunalDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;
import vacunasuy.componentecentral.security.RecursoProtegidoJWT;

@RequestScoped
@Path("/actosVacunales")
@Consumes("application/json")
@Produces("application/json")
public class ActoVacunalREST {
	
	@EJB
	IActoVacunalService actoVacunalService;
	
	@GET
//	@RecursoProtegidoJWT
	public Response listar() {
		RespuestaREST<List<ActoVacunalDTO>> respuesta =null;
		try {
			List<ActoVacunalDTO> actosVacunales = actoVacunalService.listar();
			respuesta = new RespuestaREST<List<ActoVacunalDTO>>(true, "Actos vacunales listados con éxito.", actosVacunales);
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
		RespuestaREST<ActoVacunalDTO> respuesta = null;
		try {
			ActoVacunalDTO actoVacunal = actoVacunalService.listarPorId(id);
			respuesta = new RespuestaREST<ActoVacunalDTO>(true, "Acto vacunal listado con éxito.", actoVacunal);
			return Response.ok(respuesta).build();
		}catch(VacunasUyException e) {
			respuesta = new RespuestaREST<ActoVacunalDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			}else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
	@GET
	@Path("/listarActosVacunalesPorUsuarioEnfermedad/{idUsuario}/{idEnfermedad}")
//	@RecursoProtegidoJWT
	public Response listarActosVacunalesPorUsuarioEnfermedad(@PathParam("idUsuario") Long idUsuario, @PathParam("idEnfermedad") Long idEnfermedad) {
		RespuestaREST<List<ActoVacunalCertificadoDTO>> respuesta =null;
		try {
			List<ActoVacunalCertificadoDTO> actosVacunales = actoVacunalService.listarActosVacunalesPorUsuarioEnfermedad(idUsuario, idEnfermedad);
			respuesta = new RespuestaREST<List<ActoVacunalCertificadoDTO>>(true, "Actos vacunales listados con éxito.", actosVacunales);
			return Response.ok(respuesta).build();
		}catch (VacunasUyException e) {
			respuesta = new RespuestaREST<List<ActoVacunalCertificadoDTO>>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			}else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
	@POST
	public Response crear(ActoVacunalCrearDTO request) {
		RespuestaREST<ActoVacunalDTO> respuesta = null;
		try {
			ActoVacunalDTO actoVacunal = actoVacunalService.crear(request);
			respuesta = new RespuestaREST<ActoVacunalDTO>(true, "Acto vacunal creado con éxito.", actoVacunal);
			return Response.ok(respuesta).build();
		}catch (VacunasUyException e) {
			respuesta = new RespuestaREST<ActoVacunalDTO>(false, e.getLocalizedMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
		}
	}
	
	@PUT
	@Path("/editar/{id}")
	@RecursoProtegidoJWT
	public Response editar(@PathParam("id") Long id, ActoVacunalCrearDTO request) {
		RespuestaREST<ActoVacunalDTO> respuesta = null;
		try {
			ActoVacunalDTO actoVacunal = actoVacunalService.editar(id, request);
			respuesta = new RespuestaREST<ActoVacunalDTO>(true, "Acto vacunal editado con éxito", actoVacunal);
			return Response.ok(respuesta).build();
		}catch (VacunasUyException e) {
			respuesta = new RespuestaREST<ActoVacunalDTO>(false, e.getLocalizedMessage());
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
		RespuestaREST<ActoVacunalDTO> respuesta = null;
		try {
			actoVacunalService.eliminar(id);
			respuesta = new RespuestaREST<ActoVacunalDTO>(true, "Acto vacunal eliminado con éxito.");
			return Response.ok(respuesta).build();			
		}catch (VacunasUyException e) {
			respuesta = new RespuestaREST<ActoVacunalDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}		
	}	

}
