package vacunasuy.componentecentral.bean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.jboss.logging.Logger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vacunasuy.componentecentral.business.IStockService;
import vacunasuy.componentecentral.business.IVacunaService;
import vacunasuy.componentecentral.business.IVacunatorioService;
import vacunasuy.componentecentral.dto.ReporteVacunaDTO;
import vacunasuy.componentecentral.dto.VacunaDTO;
import vacunasuy.componentecentral.dto.VacunatorioDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Named("StockBean")
@RequestScoped
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockBean implements Serializable {

	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(StockBean.class);
	
	private Long idVacuna;
	private Long idVacunatorio;
	private String nombre;
	private Long cantidad;
	private List<ReporteVacunaDTO> reporte;
	private List<VacunaDTO> vacunas;
	private List<VacunatorioDTO> vacunatorios;
	
	@EJB
	private IStockService stockService;
	
	@EJB
	private IVacunatorioService vacunatorioService;
	
	@EJB
	private IVacunaService vacunaService;
	
	@PostConstruct
	public void init() {
		try {
			vacunas = vacunaService.listar();
			vacunatorios = vacunatorioService.listar();
		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
		}
	}
	
	public void listarStockVacunasDisponiblesParaEnviar() {
		try {
			reporte = stockService.listarStockVacunasDisponiblesParaEnviar();
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
	}
	 
	public void listarStockVacunaPorVacunatorios() {
		try {
			reporte = stockService.listarStockVacunaPorVacunatorios(idVacuna);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
	}
	
	public void listarStockVacunasPorVacunatorio() {
		try {
			reporte = stockService.listarStockVacunasPorVacunatorio(idVacunatorio);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
	}
	
	private void clearParam() {
		this.idVacuna = null;
		this.idVacunatorio = null;
		this.nombre = null;
		this.nombre = null;
		this.cantidad = null;
		this.reporte = null;
		this.vacunas = null;
		this.vacunatorios = null;
	}
	
}
