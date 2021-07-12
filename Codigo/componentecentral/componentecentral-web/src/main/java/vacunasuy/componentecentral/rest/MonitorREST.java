package vacunasuy.componentecentral.rest;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import vacunasuy.componentecentral.business.IMonitorService;
import vacunasuy.componentecentral.dto.MonitorEnfermedadDTO;
import vacunasuy.componentecentral.dto.MonitorPlanDTO;
import vacunasuy.componentecentral.dto.MonitorVacunaDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@RequestScoped
@Path("/monitor")
@Consumes("application/json")
@Produces("application/json")
public class MonitorREST {
	
	@EJB
	private IMonitorService monitorService;
	
	@GET
	@Path("/enfermedad/{id}")
	public Response listarPorEnfermedad(@PathParam("id") Long id) {
		RespuestaREST<MonitorEnfermedadDTO> respuesta = null;
		try {
			MonitorEnfermedadDTO datos = monitorService.listarDatosPorEnfermedad(id);
			respuesta = new RespuestaREST<MonitorEnfermedadDTO>(true, "Datos listados con éxito.", datos);
			return Response.ok(respuesta).build();
		} catch (VacunasUyException e) {
			respuesta = new RespuestaREST<>(false, e.getLocalizedMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
		}
	}
	
	@GET
	@Path("/vacuna/{id}")
	public Response listarPorVacuna(@PathParam("id") Long id) {
		RespuestaREST<MonitorVacunaDTO> respuesta = null;
		try {
			MonitorVacunaDTO datos = monitorService.listarDatosPorVacuna(id);
			respuesta = new RespuestaREST<MonitorVacunaDTO>(true, "Datos listados con éxito.", datos);
			return Response.ok(respuesta).build();
		} catch (VacunasUyException e) {
			respuesta = new RespuestaREST<>(false, e.getLocalizedMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
		}
	}
	
	@GET
	@Path("/plan/{id}")
	public Response listarPorPlan(@PathParam("id") Long id) {
		RespuestaREST<MonitorPlanDTO> respuesta = null;
		try {
			MonitorPlanDTO datos = monitorService.listarDatosPorPlan(id);
			respuesta = new RespuestaREST<MonitorPlanDTO>(true, "Datos listados con éxito.", datos);
			return Response.ok(respuesta).build();
		} catch (VacunasUyException e) {
			respuesta = new RespuestaREST<>(false, e.getLocalizedMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
		}
	}
	
}
