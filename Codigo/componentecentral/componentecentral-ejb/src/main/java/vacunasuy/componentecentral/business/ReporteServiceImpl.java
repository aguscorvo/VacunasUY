package vacunasuy.componentecentral.business;

import java.time.LocalDate;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import vacunasuy.componentecentral.dao.IEnfermedadDAO;
import vacunasuy.componentecentral.dao.IReporteDAO;
import vacunasuy.componentecentral.dao.ISectorLaboralDAO;
import vacunasuy.componentecentral.dao.IVacunaDAO;
import vacunasuy.componentecentral.dto.ReporteActoVacunalDTO;
import vacunasuy.componentecentral.dto.ReporteEvolucionTiempoDTO;
import vacunasuy.componentecentral.entity.Enfermedad;
import vacunasuy.componentecentral.entity.SectorLaboral;
import vacunasuy.componentecentral.entity.Vacuna;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Stateless
public class ReporteServiceImpl implements IReporteService {

	@EJB
	public IReporteDAO reporteDAO;
	
	@EJB
	public IVacunaDAO vacunaDAO;
	
	@EJB
	public ISectorLaboralDAO sectorLaboralDAO;
	
	@EJB
	public IEnfermedadDAO enfermedadDAO;
	
	@Override	
	public List<ReporteEvolucionTiempoDTO> listarPorEvolucionEnTiempo(String fechaInicio, String fechaFin, 
			Long vacuna) throws VacunasUyException{
		try {
			Vacuna vacunaAux = vacunaDAO.listarPorId(vacuna);
			if (vacunaAux == null) throw new VacunasUyException("La vacuna indicada no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
			return reporteDAO.listarPorEvolucionEnTiempo(fechaInicio, fechaFin, vacuna);
		}catch(Exception e){
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}	
	}
	
	@Override	
	public List<ReporteActoVacunalDTO> listarPorEdad(String fechaInicio, String fechaFin, int edadInicio,
			int edadFin, Long enfermedad) throws VacunasUyException{
		try {
			Enfermedad enfermedadAux = enfermedadDAO.listarPorId(enfermedad);
			if(enfermedadAux == null) throw new VacunasUyException("La enfermedad indicada no existe.", 
					VacunasUyException.NO_EXISTE_REGISTRO);
			LocalDate hoy = LocalDate.now();
			LocalDate fechaEdadInicio = hoy.minusYears(edadInicio);
			LocalDate fechaEdadFin = hoy.minusYears(edadFin);			
			return reporteDAO.listarPorEdad(fechaInicio, fechaFin, fechaEdadInicio.toString(), fechaEdadFin.toString(), enfermedad);
		}catch(Exception e){
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}		
	}
	
	@Override	
	public List<ReporteActoVacunalDTO> listarPorSectorLaboral(String fechaInicio, String fechaFin,
			Long sectorLaboral, Long enfermedad) throws VacunasUyException{
		try {
			SectorLaboral sectorAux = sectorLaboralDAO.listarPorId(sectorLaboral);
			if (sectorAux == null) throw new VacunasUyException("El sector laboral indicado no existe.",
					VacunasUyException.NO_EXISTE_REGISTRO);
			Enfermedad enfermedadAux = enfermedadDAO.listarPorId(enfermedad);
			if(enfermedadAux == null) throw new VacunasUyException("La enfermedad indicada no existe.",
					VacunasUyException.NO_EXISTE_REGISTRO);
			return reporteDAO.listarPorSectorLaboral(fechaInicio, fechaFin, sectorLaboral, enfermedad);
		}catch(Exception e){
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}	

}
