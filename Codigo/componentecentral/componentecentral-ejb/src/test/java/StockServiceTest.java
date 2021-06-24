import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import vacunasuy.componentecentral.business.StockServiceImpl;
import vacunasuy.componentecentral.dao.StockDAOImpl;
import vacunasuy.componentecentral.dto.ReporteVacunaDTO;
import vacunasuy.componentecentral.entity.Stock;
import vacunasuy.componentecentral.entity.StockID;
import vacunasuy.componentecentral.entity.Vacuna;
import vacunasuy.componentecentral.entity.Vacunatorio;
import vacunasuy.componentecentral.exception.VacunasUyException;

@RunWith(MockitoJUnitRunner.class)
public class StockServiceTest {

	/* Servicio que se quiere probar */
	@InjectMocks
	private StockServiceImpl stockService;
	
	/* 
	 * Mocks que se desean realizar, serían los @EJB que se necesiten en los métodos a probar
	 * Deben declararse como public en el service 
	*/
	
	@Mock
	private StockDAOImpl stockDAO;
	
	@Before
	public void init() {
		stockService = new StockServiceImpl();
		stockService.stockDAO = this.stockDAO;
	}
	
	@Test
	public void listarStockPorVacuna () {
		Stock stock = new Stock(null, 10L);	
		Mockito.when(stockService.stockDAO.listarStockPorVacuna(1L, 1L)).thenReturn(stock);
		try {
			Stock stockEsperado = stockService.listarStockPorVacuna(1L, 1L);
			assertEquals(stockEsperado.getCantidad(), 10L);
		}catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void sumarStock () {
		Vacunatorio vacunatorio = new Vacunatorio (1L, null, null, null, null, null, null, null, null, null, null);
		Vacuna vacuna = new Vacuna(1L, null, 1, 1, 1, null);
		StockID stockID = new StockID(1L, 1L);
		Stock stock = new Stock(stockID, 10L);	
		Stock stock_actualizado = new Stock(stockID, 20L);
		Mockito.when(stockService.stockDAO.listarStockPorVacuna(1L, 1L)).thenReturn(stock);
		Mockito.when(stockService.stockDAO.actualizar(stock)).thenReturn(stock_actualizado);
		try {
			stockService.sumarStock(vacunatorio, vacuna, 10L);
			assertEquals(stock.getCantidad(), 20L);
		}catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void sumarStock_null () {
		Vacunatorio vacunatorio = new Vacunatorio (1L, null, null, null, null, null, null, null, null, null, null);
		Vacuna vacuna = new Vacuna(1L, null, 1, 1, 1, null);
		Stock stock = null;
		Mockito.when(stockService.stockDAO.listarStockPorVacuna(1L, 1L)).thenReturn(stock);
		try {
			stockService.sumarStock(vacunatorio, vacuna, 10L);
		}catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void restarStock () {
		Vacunatorio vacunatorio = new Vacunatorio (1L, null, null, null, null, null, null, null, null, null, null);
		Vacuna vacuna = new Vacuna(1L, null, 1, 1, 1, null);
		StockID stockID = new StockID(1L, 1L);
		Stock stock = new Stock(stockID, 10L);	
		Stock stock_actualizado = new Stock(stockID, 5L);
		Mockito.when(stockService.stockDAO.listarStockPorVacuna(1L, 1L)).thenReturn(stock);
		Mockito.when(stockService.stockDAO.actualizar(stock)).thenReturn(stock_actualizado);
		try {
			stockService.restarStock(vacunatorio, vacuna, 5L);
			assertEquals(stock.getCantidad(), 5L);
		}catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void resrtarStock_null () throws VacunasUyException {
		Vacunatorio vacunatorio = new Vacunatorio (1L, null, null, null, null, null, null, null, null, null, null);
		Vacuna vacuna = new Vacuna(1L, null, 1, 1, 1, null);
		Stock stock = null;
		Mockito.when(stockService.stockDAO.listarStockPorVacuna(1L, 1L)).thenReturn(stock);
		stockService.restarStock(vacunatorio, vacuna, 5L);
	}
	
	@Test
	public void listarStockVacunasDisponiblesParaEnviar() {
		ReporteVacunaDTO rvDTO = new ReporteVacunaDTO("nombre", 10L);
		List<ReporteVacunaDTO> rvDTOs = new ArrayList<ReporteVacunaDTO>();
		rvDTOs.add(rvDTO);
		Mockito.when(stockService.stockDAO.listarStockVacunasDisponiblesParaEnviar()).thenReturn(rvDTOs);
		try {
			List<ReporteVacunaDTO> reportesEsperados = stockService.listarStockVacunasDisponiblesParaEnviar();
			assertEquals(1, reportesEsperados.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void listarStockVacunaPorVacunatorios() {
		ReporteVacunaDTO rvDTO = new ReporteVacunaDTO("nombre", 10L);
		List<ReporteVacunaDTO> rvDTOs = new ArrayList<ReporteVacunaDTO>();
		rvDTOs.add(rvDTO);
		Mockito.when(stockService.stockDAO.listarStockVacunaPorVacunatorios(1L)).thenReturn(rvDTOs);
		try {
			List<ReporteVacunaDTO> reportesEsperados = stockService.listarStockVacunaPorVacunatorios(1L);
			assertEquals(1, reportesEsperados.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void listarStockVacunasPorVacunatorios() {
		ReporteVacunaDTO rvDTO = new ReporteVacunaDTO("nombre", 10L);
		List<ReporteVacunaDTO> rvDTOs = new ArrayList<ReporteVacunaDTO>();
		rvDTOs.add(rvDTO);
		Mockito.when(stockService.stockDAO.listarStockVacunasPorVacunatorio(1L)).thenReturn(rvDTOs);
		try {
			List<ReporteVacunaDTO> reportesEsperados = stockService.listarStockVacunasPorVacunatorio(1L);
			assertEquals(1, reportesEsperados.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
		
	}
}