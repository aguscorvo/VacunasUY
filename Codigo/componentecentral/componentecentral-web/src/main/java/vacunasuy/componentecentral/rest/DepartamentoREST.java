package vacunasuy.componentecentral.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import vacunasuy.componentecentral.business.IDepartamentoService;
import vacunasuy.componentecentral.dto.DepartamentoDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;
import vacunasuy.componentecentral.security.RecursoProtegidoJWT;

@RequestScoped
@Path("/departamentos")
@Consumes("application/json")
@Produces("application/json")
public class DepartamentoREST {
	
	@EJB
	IDepartamentoService departamentoService;
	
	@GET
//	@RecursoProtegidoJWT
	public Response listar() {
		RespuestaREST<List<DepartamentoDTO>> respuesta =null;
		try {
			List<DepartamentoDTO> departamentos = departamentoService.listar();
			respuesta = new RespuestaREST<List<DepartamentoDTO>>(true, "Departamentos listados con éxito.", departamentos);
			return Response.ok(respuesta).build();
		}catch(VacunasUyException e) {
			respuesta = new RespuestaREST<>(false, e.getLocalizedMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
		}
	}

	@GET
	@Path("/{id}")
//	@RecursoProtegidoJWT
	public Response listarPorId(@PathParam("id") Long id) {
		RespuestaREST<DepartamentoDTO> respuesta = null;
		try {
			DepartamentoDTO departamento = departamentoService.listarPorId(id);
			respuesta = new RespuestaREST<DepartamentoDTO>(true, "Departamento listado con éxito.", departamento);
			return Response.ok(respuesta).build();
		}catch(VacunasUyException e) {
			respuesta = new RespuestaREST<DepartamentoDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			}else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
	

}
