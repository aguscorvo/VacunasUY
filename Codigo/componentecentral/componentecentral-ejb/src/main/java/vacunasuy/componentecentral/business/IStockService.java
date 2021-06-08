package vacunasuy.componentecentral.business;

import javax.ejb.Local;

import vacunasuy.componentecentral.entity.Stock;
import vacunasuy.componentecentral.entity.Vacuna;
import vacunasuy.componentecentral.entity.Vacunatorio;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Local
public interface IStockService {

	public Stock listarStockPorVacuna(Long idVacunatorio, Long idVacuna) throws VacunasUyException;
	public void sumarStock(Vacunatorio vacunatorio, Vacuna vacuna, Long cantidad) throws VacunasUyException;
	public void restarStock(Vacunatorio vacunatorio, Vacuna vacuna, Long cantidad) throws VacunasUyException;
	
}
