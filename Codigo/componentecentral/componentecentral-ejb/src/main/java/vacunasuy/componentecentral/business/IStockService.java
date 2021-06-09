package vacunasuy.componentecentral.business;

import java.util.List;

import javax.ejb.Local;

import vacunasuy.componentecentral.dto.ReporteVacunaDTO;
import vacunasuy.componentecentral.entity.Stock;
import vacunasuy.componentecentral.entity.Vacuna;
import vacunasuy.componentecentral.entity.Vacunatorio;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Local
public interface IStockService {

	public Stock listarStockPorVacuna(Long idVacunatorio, Long idVacuna) throws VacunasUyException;
	public void sumarStock(Vacunatorio vacunatorio, Vacuna vacuna, Long cantidad) throws VacunasUyException;
	public void restarStock(Vacunatorio vacunatorio, Vacuna vacuna, Long cantidad) throws VacunasUyException;
	public List<ReporteVacunaDTO> listarStockVacunasDisponiblesParaEnviar() throws VacunasUyException;
	public List<ReporteVacunaDTO> listarStockVacunaPorVacunatorios(Long idVacuna) throws VacunasUyException;
	public List<ReporteVacunaDTO> listarStockVacunasPorVacunatorio(Long idVacunatorio) throws VacunasUyException;
	
}
