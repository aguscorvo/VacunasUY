package vacunasuy.componentecentral.business;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import vacunasuy.componentecentral.dao.IStockDAO;
import vacunasuy.componentecentral.dto.ReporteVacunaDTO;
import vacunasuy.componentecentral.entity.Stock;
import vacunasuy.componentecentral.entity.StockID;
import vacunasuy.componentecentral.entity.Vacuna;
import vacunasuy.componentecentral.entity.Vacunatorio;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Stateless
public class StockServiceImpl implements IStockService {

	@EJB
	public IStockDAO stockDAO;
	
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

	/* 1 - Reporte de stock que lista las vacunas disponibles para enviar */
	@Override
	public List<ReporteVacunaDTO> listarStockVacunasDisponiblesParaEnviar() throws VacunasUyException {
		try {
			return stockDAO.listarStockVacunasDisponiblesParaEnviar();
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}

	/* 2 - Reporte de stock que lista la disponibilidad de una vacuna en los distintos vacunatorios */
	@Override
	public List<ReporteVacunaDTO> listarStockVacunaPorVacunatorios(Long idVacuna) throws VacunasUyException {
		try {
			return stockDAO.listarStockVacunaPorVacunatorios(idVacuna);
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}

	/* 3 - Reporte de stock que lista las vacunas disponibles en un vacunatorio */
	@Override
	public List<ReporteVacunaDTO> listarStockVacunasPorVacunatorio(Long idVacunatorio) throws VacunasUyException {
		try {
			return stockDAO.listarStockVacunasPorVacunatorio(idVacunatorio);
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
}
