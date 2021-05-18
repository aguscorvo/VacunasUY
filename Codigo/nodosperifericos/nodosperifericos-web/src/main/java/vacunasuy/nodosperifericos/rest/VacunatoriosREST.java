package vacunasuy.nodosperifericos.rest;

import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import vacunasuy.nodosperifericos.business.IVacunatorioService;
import vacunasuy.nodosperifericos.dto.VacunatorioDTO;
import vacunasuy.nodosperifericos.exception.NodosPerifericosException;

@RequestScoped
@Path("/vacunatorios")
@Consumes("application/json")
@Produces("application/json")
public class VacunatoriosREST {
	
	@EJB
	IVacunatorioService vacunatorioService;
	
	@GET
	public Response listar() {
		RespuestaREST<List<VacunatorioDTO>> respuesta = null;
		try {
			List<VacunatorioDTO> vacunatorios = vacunatorioService.listar();
			respuesta = new RespuestaREST<List<VacunatorioDTO>>(true, "Vacunatorios listados con éxito.", vacunatorios);
			return Response.ok(respuesta).build();
		}catch (NodosPerifericosException e) {
			respuesta = new RespuestaREST<>(false, e.getLocalizedMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
		}		
	}
	
	@POST
	public Response crear(VacunatorioDTO request) {
		RespuestaREST<VacunatorioDTO> respuesta = null;
		try {
			VacunatorioDTO vacunatorio = vacunatorioService.crear(request);
			respuesta = new RespuestaREST<VacunatorioDTO>(true, "Vacunatorio creado con éxito.", vacunatorio);
			return Response.ok(respuesta).build();
		}catch (NodosPerifericosException e) {
			respuesta = new RespuestaREST<VacunatorioDTO>(false, e.getLocalizedMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
		}
	}

}
