package vacunasuy.componentecentral.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import vacunasuy.componentecentral.dao.IStockDAO;
import vacunasuy.componentecentral.entity.Stock;
import vacunasuy.componentecentral.entity.StockID;
import vacunasuy.componentecentral.entity.Vacuna;
import vacunasuy.componentecentral.entity.Vacunatorio;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Stateless
public class StockServiceImpl implements IStockService {

	@EJB
	private IStockDAO stockDAO;
	
	@Override
	public Stock listarStockPorVacuna(Long idVacunatorio, Long idVacuna) throws VacunasUyException {
		try {
			return stockDAO.listarStockPorVacuna(idVacunatorio, idVacuna);
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
	public void sumarStock(Vacunatorio vacunatorio, Vacuna vacuna, Long cantidad) throws VacunasUyException {
		try {
			Stock stock = stockDAO.listarStockPorVacuna(vacunatorio.getId(), vacuna.getId());
			if(stock == null) {
				System.out.println("NO ENCONTRE EL STOCK");
				StockID stockId = StockID.builder()
						.idVacunatorio(vacunatorio.getId())
						.idVacuna(vacuna.getId())
						.build();
				stock = Stock.builder()
						.id(stockId)
						.cantidad(cantidad)
						.build();
			} else {
				System.out.println("SI ENCONTRE EL STOCK");
				stock.setCantidad(stock.getCantidad() + cantidad);
			}
			stockDAO.actualizar(stock);
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
	public void restarStock(Vacunatorio vacunatorio, Vacuna vacuna, Long cantidad) throws VacunasUyException {
		try {
			Stock stock = stockDAO.listarStockPorVacuna(vacunatorio.getId(), vacuna.getId());
			if(stock == null) throw new VacunasUyException("No existe stock para el vacunatorio/vacuna indicados.", VacunasUyException.DATOS_INCORRECTOS);
			stock.setCantidad(stock.getCantidad() - cantidad);
			stockDAO.actualizar(stock);
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
}
