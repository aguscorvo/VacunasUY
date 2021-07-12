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

import vacunasuy.componentecentral.business.ILocalidadService;
import vacunasuy.componentecentral.dto.LocalidadDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;
import vacunasuy.componentecentral.security.RecursoProtegidoJWT;

@RequestScoped
@Path("/localidades")
@Consumes("application/json")
@Produces("application/json")
public class LocalidadREST {
	
	@EJB
	ILocalidadService localidadService;

	@GET
	@RecursoProtegidoJWT
	public Response listar() {
		RespuestaREST<List<LocalidadDTO>> respuesta =null;
		try {
			List<LocalidadDTO> localidades = localidadService.listar();
			respuesta = new RespuestaREST<List<LocalidadDTO>>(true, "Localidades listadas con éxito.", localidades);
			return Response.ok(respuesta).build();
		}catch(VacunasUyException e) {
			respuesta = new RespuestaREST<>(false, e.getLocalizedMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
		}
	}

	@GET
	@Path("/{id}")
	@RecursoProtegidoJWT
	public Response listarPorId(@PathParam("id") Long id) {
		RespuestaREST<LocalidadDTO> respuesta = null;
		try {
			LocalidadDTO localidad = localidadService.listarPorId(id);
			respuesta = new RespuestaREST<LocalidadDTO>(true, "Localidad listada con éxito.", localidad);
			return Response.ok(respuesta).build();
		}catch(VacunasUyException e) {
			respuesta = new RespuestaREST<LocalidadDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			}else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
}
