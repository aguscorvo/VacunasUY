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
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import vacunasuy.componentecentral.business.IVacunatorioService;
import vacunasuy.componentecentral.dto.AgendaDTO;
import vacunasuy.componentecentral.dto.AgendaMinDTO;
import vacunasuy.componentecentral.dto.AgendaVacunatorioDTO;
import vacunasuy.componentecentral.dto.UbicacionDTO;
import vacunasuy.componentecentral.dto.UsuarioMinDTO;
import vacunasuy.componentecentral.dto.VacunatorioCrearDTO;
import vacunasuy.componentecentral.dto.VacunatorioDTO;
import vacunasuy.componentecentral.dto.VacunatorioPerifericoDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;
import vacunasuy.componentecentral.security.RecursoProtegidoJWT;
import vacunasuy.componentecentral.util.Constantes;


@RequestScoped
@Path("/vacunatorios")
@Consumes("application/json")
@Produces("application/json")
public class VacunatorioREST {
	
	@EJB
	IVacunatorioService vacunatorioService;
	
	@GET
//	@RecursoProtegidoJWT
	public Response listar() {
		RespuestaREST<List<VacunatorioDTO>> respuesta = null;
		try {
			List<VacunatorioDTO> vacunatorios = vacunatorioService.listar();
			respuesta = new RespuestaREST<List<VacunatorioDTO>>(true, "Vacunatorios listados con éxito.", vacunatorios);
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
		RespuestaREST<VacunatorioDTO> respuesta = null;
		try {
			VacunatorioDTO vacunatorio = vacunatorioService.listarPorId(id);
			respuesta = new RespuestaREST<VacunatorioDTO>(true, "Vacunatorio listado con éxito.", vacunatorio);
			return Response.ok(respuesta).build();
		}catch(VacunasUyException e) {
			respuesta = new RespuestaREST<VacunatorioDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			}else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
	@POST
	public Response crear(VacunatorioCrearDTO request) {
		RespuestaREST<VacunatorioDTO> respuesta = null;
		try {
			VacunatorioDTO vacunatorio = vacunatorioService.crear(request);
			respuesta = new RespuestaREST<VacunatorioDTO>(true, "Vacunatorio creado con éxito.", vacunatorio);
			return Response.ok(respuesta).build();
		}catch (VacunasUyException e) {
			respuesta = new RespuestaREST<VacunatorioDTO>(false, e.getLocalizedMessage());
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
	public Response editar (@PathParam("id") Long id, VacunatorioCrearDTO request) {
		RespuestaREST<VacunatorioDTO> respuesta = null;
		try {
			VacunatorioDTO vacunatorio = vacunatorioService.editar(id, request);
			respuesta = new RespuestaREST<VacunatorioDTO>(true, "Vacunatorio editado con éxito.", vacunatorio);
			return Response.ok(respuesta).build();			
		}catch (VacunasUyException e) {
			respuesta = new RespuestaREST<VacunatorioDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}	
	}
	
	@DELETE
	@Path("/eliminar/{id}")
//	@RecursoProtegidoJWT
	public Response eliminar(@PathParam("id") Long id) {
		RespuestaREST<VacunatorioDTO> respuesta = null;
		try {
			vacunatorioService.eliminar(id);
			respuesta = new RespuestaREST<VacunatorioDTO>(true, "Vacunatorio eliminado con éxito");
			return Response.ok(respuesta).build();			
		}catch (VacunasUyException e) {
			respuesta = new RespuestaREST<VacunatorioDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}	
	}
	
	@GET
//	@RecursoProtegidoJWT
	@Path("/listar/cercanos/{latitud}/{longitud}/{distancia}")
	public Response listarCercanos(
			@PathParam("latitud") Double latitud, 
			@PathParam("longitud")Double longitud,
			@PathParam("distancia")Double distancia){
		RespuestaREST<List<VacunatorioDTO>> respuesta = null;
		try {
			UbicacionDTO ubicacion = new UbicacionDTO(latitud, longitud, distancia);
			List<VacunatorioDTO> vacunatorios = vacunatorioService.listarCercanos(ubicacion);
			respuesta = new RespuestaREST<List<VacunatorioDTO>>(true, "Vacunatorios listados con éxito.", vacunatorios);
			return Response.ok(respuesta).build();
		}catch (VacunasUyException e) {
			respuesta = new RespuestaREST<>(false, e.getLocalizedMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
		}		
	}
	
	@PUT
	@Path("/agregarEvento/{vacunatorio}/{evento}")
//	@RecursoProtegidoJWT
	public Response agregarEvento (@PathParam("vacunatorio") Long vacunatorio, @PathParam("evento") Long evento) {
		RespuestaREST<VacunatorioDTO> respuesta = null;
		try {
			VacunatorioDTO vacunatorioAux = vacunatorioService.agregarEvento(vacunatorio, evento);
			respuesta = new RespuestaREST<VacunatorioDTO>(true, "Evento agregado al vacunatorio con éxito.", vacunatorioAux);
			return Response.ok(respuesta).build();			
		}catch (VacunasUyException e) {
			respuesta = new RespuestaREST<VacunatorioDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO ||  e.getCodigo() ==VacunasUyException.EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}	
	}
	
	@PUT
	@Path("/agregarActoVacunal/{vacunatorio}/{actoVacunal}/")
//	@RecursoProtegidoJWT
	public Response agregarActoVacunal (@PathParam("vacunatorio") Long vacunatorio, @PathParam("actoVacunal") Long actoVacunal) {
		RespuestaREST<VacunatorioDTO> respuesta = null;
		try {
			VacunatorioDTO vacunatorioAux = vacunatorioService.agregarEvento(vacunatorio, actoVacunal);
			respuesta = new RespuestaREST<VacunatorioDTO>(true, "Acto vacunal agregado al vacunatorio con éxito.", vacunatorioAux);
			return Response.ok(respuesta).build();			
		}catch (VacunasUyException e) {
			respuesta = new RespuestaREST<VacunatorioDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO ||  e.getCodigo() ==VacunasUyException.EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}	
	}
	
	@GET
	@Path("/obtenerAsignacionVacunadores/{vacunatorio}/{clave}/{fecha}")
	public Response enviarAsignaciones(@PathParam("vacunatorio") Long vacunatorio, @PathParam("clave") String clave, @PathParam("fecha") String fecha) {
		RespuestaREST<List<UsuarioMinDTO>> respuesta = null;
		try {
			List<UsuarioMinDTO> vacunadores = vacunatorioService.obtenerAsignacionVacunadores(vacunatorio, clave, fecha);
			respuesta = new RespuestaREST<List<UsuarioMinDTO>>(true, "Vacunadores listados con éxito", vacunadores);
			return Response.ok(vacunadores).build();
		}catch (VacunasUyException e) {
			respuesta = new RespuestaREST<List<UsuarioMinDTO>>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO){
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
	@GET
	@Path("/listarPorUbicacion/{localidad}/{departamento}")
	public Response listarPorUbicacion(@PathParam("localidad") Long localidad, @PathParam("departamento") Long departamento) {
		RespuestaREST<List<VacunatorioDTO>> respuesta = null;
		try {
			List<VacunatorioDTO> vacunatorios = vacunatorioService.listarPorUbicacion(localidad, departamento);
			respuesta = new RespuestaREST<List<VacunatorioDTO>>(true, "Vacunatorios listados con éxito", vacunatorios);
			return Response.ok(respuesta).build();
		}catch (VacunasUyException e) {
			respuesta = new RespuestaREST<List<VacunatorioDTO>>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO){
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}	
	}
	
	@GET
	@Path("/listarPorDepartamento/{departamento}")
	public Response listarPorDepartamento(@PathParam("departamento") Long departamento) {
		RespuestaREST<List<VacunatorioDTO>> respuesta = null;
		try {
			List<VacunatorioDTO> vacunatorios = vacunatorioService.listarPorDepartamento(departamento);
			respuesta = new RespuestaREST<List<VacunatorioDTO>>(true, "Vacunatorios listados con éxito", vacunatorios);
			return Response.ok(respuesta).build();
		}catch (VacunasUyException e) {
			respuesta = new RespuestaREST<List<VacunatorioDTO>>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO){
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}	
	}
	
	@GET
	@Path("/listarVacunatoriosDadoPlan/{id}")
//	@RecursoProtegidoJWT
	public Response listarVacunatoriosDadoPlan(@PathParam("id") Long id) {
		RespuestaREST<List<VacunatorioDTO>> respuesta = null;
		try {
			List<VacunatorioDTO> vacunatorios = vacunatorioService.listarVacunatoriosDadoPlan(id);
			respuesta = new RespuestaREST<List<VacunatorioDTO>>(true, "Vacunatorios listados con éxito.", vacunatorios);
			return Response.ok(respuesta).build();
		}catch(VacunasUyException e) {
			respuesta = new RespuestaREST<List<VacunatorioDTO>>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			}else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
	@PUT
	@Path("/crearGeometrias")
//	@RecursoProtegidoJWT
	public Response crearGeometrias() {
		RespuestaREST<VacunatorioDTO> respuesta = null;
		try {
			vacunatorioService.crearGeometrias();
			respuesta = new RespuestaREST<VacunatorioDTO>(true, "Geometrías creadas con éxito.");
			return Response.ok(respuesta).build();				
		}catch(VacunasUyException e) {
			respuesta = new RespuestaREST<VacunatorioDTO>(false, e.getLocalizedMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
		}
	}
		
	@GET
	@Path("/listarAgendasPorVacunatorio/{id}/{fecha}")
	public Response listarAgendasPorVacunatorio(@PathParam("id") Long id, @PathParam("fecha") String fecha) {
		RespuestaREST<List<AgendaVacunatorioDTO>> respuesta = null;
		try {
			List<AgendaVacunatorioDTO> agendas = vacunatorioService.listarAgendasPorVacunatorio(id, fecha);
			return Response.ok(agendas).build();
		}catch(VacunasUyException e) {
			respuesta = new RespuestaREST<List<AgendaVacunatorioDTO>>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			}else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
}
