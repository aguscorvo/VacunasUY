package vacunasuy.componentecentral.dao;

import java.util.List;

import javax.ejb.Local;

import vacunasuy.componentecentral.dto.ReporteActoVacunalDTO;
import vacunasuy.componentecentral.dto.ReporteEvolucionTiempoDTO;

@Local
public interface IReporteDAO {

	public List<ReporteEvolucionTiempoDTO> listarPorEvolucionEnTiempo(String fechaInicio, String fechaFin, 
			Long vacuna);
	public List<ReporteActoVacunalDTO> listarPorEdad(String fechaInicio, String fechaFin, String edadInicio,
			String edadFin, Long enfermedad);
	public List<ReporteActoVacunalDTO> listarPorSectorLaboral(String fechaInicio, String fechaFin,
			Long sectorLaboral, Long enfermedad);

}
