package vacunasuy.componentecentral.dao;

import java.util.List;

import javax.ejb.Local;

import vacunasuy.componentecentral.dto.ReporteVacunaDTO;
import vacunasuy.componentecentral.entity.Stock;

@Local
public interface IStockDAO {

	public Stock actualizar(Stock stock);
	public Stock listarStockPorVacuna(Long idVacunatorio, Long idVacuna);
	public List<ReporteVacunaDTO> listarStockVacunasDisponiblesParaEnviar();
	public List<ReporteVacunaDTO> listarStockVacunaPorVacunatorios(Long idVacuna);
	public List<ReporteVacunaDTO> listarStockVacunasPorVacunatorio(Long idVacunatorio);
	
}
