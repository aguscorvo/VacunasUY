package vacunasuy.componentecentral.rest;

import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import vacunasuy.componentecentral.business.ISectorLaboralService;
import vacunasuy.componentecentral.dto.SectorLaboralDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@RequestScoped
@Path("/sectoreslaborales")
@Consumes("application/json")
@Produces("application/json")
public class SectoresLaboralesREST {

	@EJB
	private ISectorLaboralService sectorLaboralService;
	
	@GET
	public Response listar() {
		RespuestaREST<List<SectorLaboralDTO>> respuesta = null;
		try {
			List<SectorLaboralDTO> sectores = sectorLaboralService.listar();
			respuesta = new RespuestaREST<List<SectorLaboralDTO>>(true, "Sectores laborales listados con éxito.", sectores);
			return Response.ok(respuesta).build();
		} catch (VacunasUyException e) {
			respuesta = new RespuestaREST<>(false, e.getLocalizedMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
		}
	}
	
}
