package vacunasuy.componentecentral.bean;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.jboss.logging.Logger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vacunasuy.componentecentral.business.IEnfermedadService;
import vacunasuy.componentecentral.business.IPlanVacunacionService;
import vacunasuy.componentecentral.business.IReporteService;
import vacunasuy.componentecentral.business.IRolService;
import vacunasuy.componentecentral.business.ISectorLaboralService;
import vacunasuy.componentecentral.business.IStockService;
import vacunasuy.componentecentral.business.IUsuarioService;
import vacunasuy.componentecentral.business.IVacunaService;
import vacunasuy.componentecentral.business.IVacunatorioService;
import vacunasuy.componentecentral.dto.EnfermedadDTO;
import vacunasuy.componentecentral.dto.PlanVacunacionDTO;
import vacunasuy.componentecentral.dto.ReporteEvolucionTiempoDTO;
import vacunasuy.componentecentral.dto.ReporteVacunaDTO;
import vacunasuy.componentecentral.dto.RolDTO;
import vacunasuy.componentecentral.dto.UsuarioDTO;
import vacunasuy.componentecentral.dto.VacunaDTO;
import vacunasuy.componentecentral.dto.VacunatorioDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Named("HomeBean")
@SessionScoped
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomeBean implements Serializable {

	static Logger logger = Logger.getLogger(HomeBean.class);

	private static final long serialVersionUID = 1L;

	private List<ReporteVacunaDTO> stockVacunas;
	private Map<String, List<ReporteVacunaDTO>> stockVacunasVacunatorios;
	private Map<String, List<ReporteEvolucionTiempoDTO>> evolucionVacunas;
	private List<VacunaDTO> vacunas;
	private List<VacunatorioDTO> vacunatorios;
	private List<EnfermedadDTO> enfermedades;
	List<PlanVacunacionDTO> planes;
	List<String> fechas;
	Long vacunatoriosMVD;
	Long vacunatoriosINT;
	Long usuariosCIU;
	Long usuariosVAC;
	Long planesVigentes;
	Long planesProximos;

	@EJB
	IEnfermedadService enfermedadService;

	@EJB
	IUsuarioService usuarioService;

	@EJB
	IRolService rolService;

	@EJB
	ISectorLaboralService sectorService;

	@EJB
	IPlanVacunacionService planVacunacionService;

	@EJB
	IReporteService reporteService;

	@EJB
	IStockService stockService;

	@EJB
	IVacunatorioService vacunatorioService;

	@EJB
	IVacunaService vacunaService;

	@PostConstruct
	public void init() {

		try {
			vacunatorios = vacunatorioService.listar();
			stockVacunas = listarStockVacunasDisponiblesParaEnviar();

			VacunatoriosIntMvd();
			PlanesVigPro();
			UsuariosVacCiud();
			listarVacunasPorEvolucionEnTiempo();

		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
		}

	}

	private List<ReporteVacunaDTO> listarStockVacunasDisponiblesParaEnviar() {
		List<ReporteVacunaDTO> ret = new ArrayList<ReporteVacunaDTO>();
		try {
			ret = stockService.listarStockVacunasDisponiblesParaEnviar();
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		return ret;
	}

	private List<ReporteVacunaDTO> listarStockVacunaPorVacunatorios(Long idVacuna) {
		List<ReporteVacunaDTO> ret = new ArrayList<ReporteVacunaDTO>();
		try {
			ret = stockService.listarStockVacunaPorVacunatorios(idVacuna);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}

		return ret;
	}

	private List<ReporteVacunaDTO> listarStockVacunasPorVacunatorio(Long idVacunatorio) {
		List<ReporteVacunaDTO> ret = new ArrayList<ReporteVacunaDTO>();
		try {
			ret = stockService.listarStockVacunasPorVacunatorio(idVacunatorio);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}

		return ret;
	}

	private void VacunatoriosIntMvd() {
		try {
			vacunatoriosMVD = (long) 0;
			vacunatoriosINT = (long) 0;
			stockVacunasVacunatorios = new HashMap<String, List<ReporteVacunaDTO>>();

			for (VacunatorioDTO vac : vacunatorios) {
				stockVacunasVacunatorios.put(vac.getNombre(), listarStockVacunasPorVacunatorio(vac.getId()));

				if (vac.getDepartamento().getNombre().equalsIgnoreCase("Montevideo")) {
					vacunatoriosMVD = vacunatoriosMVD + 1;
				} else {
					vacunatoriosINT = vacunatoriosINT + 1;
				}
			}
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
	}

	private void UsuariosVacCiud() {
		try {

			List<UsuarioDTO> auxvac = usuarioService.listar();

			usuariosVAC = (long) 0;
			usuariosCIU = (long) 0;

			for (UsuarioDTO vdto : auxvac) {
				logger.info(vdto.getNombre().length());
				for (RolDTO rdto : vdto.getRoles()) {

					if (rdto.getNombre().toUpperCase().contains("VACUNADOR")) {
						usuariosVAC = usuariosVAC + 1;
					} else if (rdto.getNombre().toUpperCase().contains("CIUDADANO")) {
						usuariosCIU = usuariosCIU + 1;
					}
				}
			}

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}

	}

	private void PlanesVigPro() {
		try {
			planesVigentes = (long) 0;
			planesProximos = (long) 0;

			List<PlanVacunacionDTO> auxvac = planVacunacionService.listar();

			for (PlanVacunacionDTO vdto : auxvac) {

				Date dFI = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(vdto.getFechaInicio());
				Date dFF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(vdto.getFechaFin());

				Integer resFi = dFI.compareTo(new Date());
				Integer resFF = dFF.compareTo(new Date());

				if (resFi <= 0 && resFF >= 0) {
					planesVigentes = planesVigentes + 1;

				} else if (resFi > 0 && resFF > 0) {
					planesProximos = planesProximos + 1;
				}
			}

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}

	}

	private void listarVacunasPorEvolucionEnTiempo() {
		try {

			fechas = new ArrayList<String>();
			evolucionVacunas = new HashMap<String, List<ReporteEvolucionTiempoDTO>>();

			Date fechaBase = new Date();
			Calendar cfechaBase = Calendar.getInstance();
			cfechaBase.setTime(fechaBase);

			for (int d = 0; d < 90; d++) {

				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				// Convert calendar back to Date
				cfechaBase.add(Calendar.DAY_OF_YEAR, -1);
				Date newDate = cfechaBase.getTime();
				String strDate = dateFormat.format(newDate);
				fechas.add(strDate);

			}

			String fechaInicio = fechas.get(fechas.size() - 1);
			String fechaFin = fechas.get(0);

			Collections.reverse(fechas);

			vacunas = vacunaService.listar();
			for (VacunaDTO vac : vacunas) {
				List<ReporteEvolucionTiempoDTO> vacf = new ArrayList<ReporteEvolucionTiempoDTO>();

				List<ReporteEvolucionTiempoDTO> aux = reporteService.listarPorEvolucionEnTiempo(fechaInicio, fechaFin,
						vac.getId());

				for (String s : fechas) {
					boolean encontre = false;
					for (ReporteEvolucionTiempoDTO rdto : aux) {
						if (rdto.getFecha().equalsIgnoreCase(s)) {
							vacf.add(rdto);
							encontre = true;
							break;
						}
					}
					if (!encontre) {
						ReporteEvolucionTiempoDTO recdto = ReporteEvolucionTiempoDTO.builder().cantidad(0).fecha(s)
								.build();
						vacf.add(recdto);
					}
				}

				evolucionVacunas.put(vac.getNombre(), vacf);
			}

		} catch (VacunasUyException e) {
			logger.error(e.getLocalizedMessage());
		}

	}

}
