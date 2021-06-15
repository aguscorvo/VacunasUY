package vacunasuy.componentecentral.business;

import java.util.List;

import javax.ejb.Local;

import vacunasuy.componentecentral.dto.ReporteActoVacunalDTO;
import vacunasuy.componentecentral.dto.ReporteEvolucionTiempoDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Local
public interface IReporteService {
	
	public List<ReporteEvolucionTiempoDTO> listarPorEvolucionEnTiempo(String fechaInicio, String fechaFin, 
			Long vacuna) throws VacunasUyException;
	public List<ReporteActoVacunalDTO> listarPorEdad(String fechaInicio, String fechaFin, int edadInicio,
			int edadFin, Long enfermedad) throws VacunasUyException;
	public List<ReporteActoVacunalDTO> listarPorSectorLaboral(String fechaInicio, String fechaFin,
			Long sectorLaboral, Long enfermedad) throws VacunasUyException;

}
