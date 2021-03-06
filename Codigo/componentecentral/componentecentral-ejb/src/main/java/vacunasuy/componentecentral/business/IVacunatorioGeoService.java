package vacunasuy.componentecentral.business;

import java.util.List;

import javax.ejb.Local;

import org.geolatte.geom.Point;

import vacunasuy.componentecentral.dto.UbicacionDTO;
import vacunasuy.componentecentral.dto.VacunatorioDTO;
import vacunasuy.componentecentral.dto.VacunatorioGeoDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Local
public interface IVacunatorioGeoService {

	public List<VacunatorioGeoDTO> listar() throws VacunasUyException;
	public VacunatorioGeoDTO listarPorId(Long id) throws VacunasUyException;
	public VacunatorioGeoDTO crear(VacunatorioDTO vacunatorioDTO) throws VacunasUyException;
	
//  desde backend
	public List<Long> listarCercanos(UbicacionDTO ubicacionDTO) throws VacunasUyException;
	
}
