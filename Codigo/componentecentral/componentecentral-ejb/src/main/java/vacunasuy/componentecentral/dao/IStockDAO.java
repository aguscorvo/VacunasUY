package vacunasuy.componentecentral.dao;

import javax.ejb.Local;
import vacunasuy.componentecentral.entity.Stock;

@Local
public interface IStockDAO {

	public Stock actualizar(Stock stock);
	public Stock listarStockPorVacuna(Long idVacunatorio, Long idVacuna);
	
}
