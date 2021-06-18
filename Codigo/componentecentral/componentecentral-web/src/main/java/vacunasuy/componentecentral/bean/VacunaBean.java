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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vacunasuy.componentecentral.business.IEnfermedadService;
import vacunasuy.componentecentral.business.IVacunaService;
import vacunasuy.componentecentral.dto.EnfermedadDTO;
import vacunasuy.componentecentral.dto.VacunaCrearDTO;
import vacunasuy.componentecentral.dto.VacunaDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;



@Named("VacunaBean")
@RequestScoped
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VacunaBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(VacunaBean.class);
	
	private Long id;
	private String nombre;
	private int cant_dosis;
	private int periodo;
	private int inmunidad;
	private Long idEnfermedad;
	private List<VacunaDTO> vacunas;
	private List<EnfermedadDTO> enfermedades;
	String strbuscar;
	
	@EJB
	private IVacunaService vacunaService;
	
	@EJB
	private IEnfermedadService enfermedadService;
	
	@PostConstruct
	public void init() {
		try {
			vacunas = vacunaService.listar();
			enfermedades = enfermedadService.listar();
		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
		}
	}
	
	public void srchVacuna() {

		logger.info("srchVacuna 'strbuscar': " + strbuscar);

		try {
			vacunas = vacunaService.listar();

		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			vacunas = new ArrayList<VacunaDTO>();
		}

		if (!strbuscar.equals("")) {
			List<VacunaDTO> auxvac = new ArrayList<VacunaDTO>();

			strbuscar = strbuscar.toUpperCase();

			for (VacunaDTO tdto : vacunas) {
				if (tdto.getNombre().toUpperCase().contains(strbuscar) ||
						tdto.getEnfermedad().getNombre().toUpperCase().contains(strbuscar))
					auxvac.add(tdto);
			}
			vacunas = auxvac;
		}

	}

	
	public void addVacuna() {
		try {
			VacunaCrearDTO vacuna = VacunaCrearDTO.builder()
					.nombre(nombre)
					.cant_dosis(cant_dosis)
					.periodo(periodo)
					.inmunidad(inmunidad)
					.id_enfermedad(idEnfermedad)
					.build();		
			vacunaService.crear(vacuna);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Vacuna " + vacuna.getNombre() + " creada con éxito.", null));
		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
		} finally {
			clearParam();
			try {
				vacunas = vacunaService.listar();
				enfermedades = enfermedadService.listar();
			} catch (VacunasUyException e) {
				logger.info(e.getMessage().trim());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			}
		}
	}
	
	public void updVacuna() {
		try {
			VacunaCrearDTO vacuna = VacunaCrearDTO.builder()
					.nombre(nombre)
					.cant_dosis(cant_dosis)
					.periodo(periodo)
					.inmunidad(inmunidad)
					.id_enfermedad(idEnfermedad)
					.build();	
			vacunaService.editar(id, vacuna);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Vacuna " + vacuna.getNombre() + " editada con éxito.", null));
		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
		} finally {
			clearParam();
			try {
				vacunas = vacunaService.listar();
				enfermedades = enfermedadService.listar();
			} catch (VacunasUyException e) {
				logger.info(e.getMessage().trim());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			}
		}
	}
	
	public void delVacuna() {
		try {
			vacunaService.eliminar(id);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Vacuna eliminada con éxito.", null));
		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
		} finally {
			clearParam();
			try {
				vacunas = vacunaService.listar();
				enfermedades = enfermedadService.listar();
			} catch (VacunasUyException e) {
				logger.info(e.getMessage().trim());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			}
		}
	}
	
	public String getEnfermedad() {
		String ret = "";
		if(idEnfermedad!=null) {
			for(EnfermedadDTO enf : enfermedades) {
				if(enf.getId().equals(idEnfermedad))
					ret = enf.getNombre();
			}
			
		}
		
		return ret;
	}
	
	private void clearParam() {
		this.id = null;
		this.nombre = null;
		this.cant_dosis = 0;
		this.periodo = 0;
		this.inmunidad = 0;
		this.vacunas = null;
		this.enfermedades = null;
	}
	
}
