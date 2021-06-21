package vacunasuy.componentecentral.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.jboss.logging.Logger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vacunasuy.componentecentral.business.IPlanVacunacionService;
import vacunasuy.componentecentral.business.ISectorLaboralService;
import vacunasuy.componentecentral.business.IVacunaService;
import vacunasuy.componentecentral.dto.PlanVacunacionCrearDTO;
import vacunasuy.componentecentral.dto.PlanVacunacionDTO;
import vacunasuy.componentecentral.dto.SectorLaboralDTO;
import vacunasuy.componentecentral.dto.VacunaDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Named("PlanesVacunacionBean")
@RequestScoped
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanesVacunacionBean implements Serializable {

	static Logger logger = Logger.getLogger(PlanesVacunacionBean.class);
	private static final long serialVersionUID = 1L;

	private Long id;
	private int edadMinima;
	private int edadMaxima;
	private String fechaInicio;
	private String fechaFin;
	private Long idsector;
	private Long idvacuna;
	private List<Long> sectoresID;
	private List<SectorLaboralDTO> sectores;
	private List<VacunaDTO> vacunas;
	List<PlanVacunacionDTO> planes;
	private String strbuscar;
	
	@EJB
	private IVacunaService vacunaService;
	
	@EJB
	private ISectorLaboralService sectorLaboralService;
	
	@EJB
	private IPlanVacunacionService planVacunacionService;
	

	@PostConstruct
	public void init() {
		try {
			planes = planVacunacionService.listar();
			vacunas = vacunaService.listar();
			sectores = sectorLaboralService.listar();
		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
		}
	}
	
	public void srchPlan() {

		logger.info("srchVacuna 'strbuscar': " + strbuscar);

		try {
			planes = planVacunacionService.listar();

		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			planes = new ArrayList<PlanVacunacionDTO>();
		}

		if (!strbuscar.equals("")) {
			List<PlanVacunacionDTO> auxvac = new ArrayList<PlanVacunacionDTO>();

			strbuscar = strbuscar.toUpperCase();

			for (PlanVacunacionDTO tdto : planes) {
				if (tdto.getVacuna().getNombre().toUpperCase().contains(strbuscar)) {
					auxvac.add(tdto);
				} else {
					for (SectorLaboralDTO sec : tdto.getSectores()) {
						if (sec.getNombre().toUpperCase().contains(strbuscar))
							auxvac.add(tdto);
					}	
				}
			}
			planes = auxvac;
		}

	}

	
	public void addPlan() {
		try {
			
			fechaInicio = fechaInicio.trim().equals("")?null:fechaInicio + " 00:00";
			fechaFin = fechaFin.trim().equals("")?null:fechaFin + " 00:00";
			
			PlanVacunacionCrearDTO plan =  PlanVacunacionCrearDTO.builder()
					.edadMaxima(edadMaxima)
					.edadMinima(edadMinima)
					.fechaInicio(fechaInicio)
					.fechaFin(fechaFin)
					.sectores(sectoresID)
					.idVacuna(idvacuna)
					.build();		
			
			planVacunacionService.crear(plan);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Plan  creada con éxito.", null));
		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
		} finally {
			clearParam();
			try {
				planes = planVacunacionService.listar();
				vacunas = vacunaService.listar();
				sectores = sectorLaboralService.listar();
				
			} catch (VacunasUyException e) {
				logger.info(e.getMessage().trim());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			}
		}
	}
	
	public void updPlan() {
		try {
			fechaInicio = fechaInicio.trim().equals("")?null:fechaInicio + " 00:00";
			fechaFin = fechaFin.trim().equals("")?null:fechaFin + " 00:00";
			
			PlanVacunacionCrearDTO plan =  PlanVacunacionCrearDTO.builder()
					.edadMaxima(edadMaxima)
					.edadMinima(edadMinima)
					.fechaInicio(fechaInicio)
					.fechaFin(fechaFin)
					.sectores(sectoresID)
					.idVacuna(idvacuna)
					.build();		
			
			
			planVacunacionService.editar(id, plan);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Plan " + id.toString() + " editado con éxito.", null));
		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
		} finally {
			clearParam();
			try {
				planes = planVacunacionService.listar();
				vacunas = vacunaService.listar();
				sectores = sectorLaboralService.listar();
				
			} catch (VacunasUyException e) {
				logger.info(e.getMessage().trim());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			}
		}
	}
	
	public void delPlan() {
		try {
			planVacunacionService.eliminar(id);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Plan eliminado con éxito.", null));
		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
		} finally {
			clearParam();
			try {
				planes = planVacunacionService.listar();
				vacunas = vacunaService.listar();
				sectores = sectorLaboralService.listar();
				
			} catch (VacunasUyException e) {
				logger.info(e.getMessage().trim());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			}
		}
	}
	
	
	private void clearParam() {
		this.id = null;
		this.edadMinima = -1;
		this.edadMaxima = -1;
		this.fechaInicio = null;
		this.fechaFin = null;
		this.idsector = null;
		this.idvacuna = null;
		this.sectoresID = null;
		this.sectores = null;
		this.vacunas = null;
		this.planes = null;
		this.strbuscar = null;
	}


}
