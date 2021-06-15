package vacunasuy.componentecentral.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import vacunasuy.componentecentral.business.IReporteService;
import vacunasuy.componentecentral.dto.ReporteActoVacunalDTO;
import vacunasuy.componentecentral.dto.ReporteEvolucionTiempoDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@RequestScoped
@Path("/reportes")
@Consumes("application/json")
@Produces("application/json")
public class ReporteREST {
	
	@EJB
	IReporteService reporteService;
	
	@GET
	@Path("/listarEvolucion/{fechaInicio}/{fechaFin}/{vacuna}")
	public Response listarPorEvolucionEnTiempo(@PathParam("fechaInicio")String fechaInicio, @PathParam("fechaFin") String fechaFin, 
			@PathParam("vacuna") Long vacuna) {
		RespuestaREST<List<ReporteEvolucionTiempoDTO>> respuesta = null;
		try {
			List<ReporteEvolucionTiempoDTO> reporte = reporteService.listarPorEvolucionEnTiempo(fechaInicio, fechaFin, vacuna);
			respuesta = new RespuestaREST<List<ReporteEvolucionTiempoDTO>>(true, "Reporte listado con éxito", reporte);
			return Response.ok(respuesta).build();
		}catch(VacunasUyException e) {
			respuesta = new RespuestaREST<List<ReporteEvolucionTiempoDTO>>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			}else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
	@GET
	@Path("/listarPorEdad/{fechaInicio}/{fechaFin}/{edadInicio}/{edadFin}/{enfermedad}")
	public Response listarPorEdad(@PathParam("fechaInicio")String fechaInicio, @PathParam("fechaFin") String fechaFin, 
			@PathParam("edadInicio") int edadInicio, @PathParam("edadFin") int edadFin, @PathParam("enfermedad") Long enfermedad) {
		RespuestaREST<List<ReporteActoVacunalDTO>> respuesta = null;
		try {
			List<ReporteActoVacunalDTO> reporte = reporteService.listarPorEdad(fechaInicio, fechaFin, edadInicio, edadFin, enfermedad);
			respuesta = new RespuestaREST<List<ReporteActoVacunalDTO>>(true, "Reporte listado con éxito", reporte);
			return Response.ok(respuesta).build();
		}catch(VacunasUyException e) {
			respuesta = new RespuestaREST<List<ReporteActoVacunalDTO>>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			}else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
	@GET
	@Path("/listarPorSector/{fechaInicio}/{fechaFin}/{sectorLaboral}/{enfermedad}")
	public Response listarPorSectorLaboral(@PathParam("fechaInicio")String fechaInicio, @PathParam("fechaFin") String fechaFin, 
			@PathParam("sectorLaboral") Long sectorLaboral, @PathParam("enfermedad") Long enfermedad) {
		RespuestaREST<List<ReporteActoVacunalDTO>> respuesta = null;
		try {
			List<ReporteActoVacunalDTO> reporte = reporteService.listarPorSectorLaboral(fechaInicio, fechaFin, 
					sectorLaboral, enfermedad);
			respuesta = new RespuestaREST<List<ReporteActoVacunalDTO>>(true, "Reporte listado con éxito", reporte);
			return Response.ok(respuesta).build();
		}catch(VacunasUyException e) {
			respuesta = new RespuestaREST<List<ReporteActoVacunalDTO>>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			}else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}

}
