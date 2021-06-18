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
import vacunasuy.componentecentral.business.IEnfermedadService;
import vacunasuy.componentecentral.dto.EnfermedadCrearDTO;
import vacunasuy.componentecentral.dto.EnfermedadDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Named("EnfermedadBean")
@RequestScoped
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnfermedadBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	static Logger logger = Logger.getLogger(EnfermedadBean.class);
	
	private Long id;
	private String nombre;
	private List<EnfermedadDTO> enfermedades;
	String strbuscar;
	
	
	@EJB
	private IEnfermedadService enfermedadService;
	
	@PostConstruct
	public void init() {
		try {
			enfermedades = enfermedadService.listar();
		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
		}
	}
	
	public void srchEnfermedad() {

		logger.info("srchEnfermedad 'strbuscar': " + strbuscar);

		try {
			enfermedades = enfermedadService.listar();

		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			enfermedades = new ArrayList<EnfermedadDTO>();
		}

		if (!strbuscar.equals("")) {
			List<EnfermedadDTO> auxvac = new ArrayList<EnfermedadDTO>();

			strbuscar = strbuscar.toUpperCase();

			for (EnfermedadDTO tdto : enfermedades) {
				if (tdto.getNombre().toUpperCase().contains(strbuscar))
					auxvac.add(tdto);
			}
			enfermedades = auxvac;
		}

	}

	public void addEnfermedad() {
		try {
			EnfermedadCrearDTO enfermedad = EnfermedadCrearDTO.builder()
					.nombre(nombre)
					.build();
			enfermedadService.crear(enfermedad);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Enfermedad " + enfermedad.getNombre() + " creada con éxito.", null));
		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
		} finally {
			clearParam();
			try {
				enfermedades = enfermedadService.listar();
			} catch (VacunasUyException e) {
				logger.info(e.getMessage().trim());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			}
		}
	}
	
	public void updEnfermedad() {
		try {
			EnfermedadCrearDTO enfermedad = EnfermedadCrearDTO.builder()
					.nombre(nombre)
					.build();
			enfermedadService.editar(id, enfermedad);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Enfermedad " + enfermedad.getNombre() + " editada con éxito.", null));
		} catch (Exception e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
		} finally {
			clearParam();
			try {
				enfermedades = enfermedadService.listar();
			} catch (VacunasUyException e) {
				logger.info(e.getMessage().trim());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			}
		}
	}
	
	public void delEnfermedad() {
		try {
			enfermedadService.eliminar(id);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Enfermedad eliminada con éxito.", null));
		} catch (Exception e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
		} finally {
			clearParam();
			try {
				enfermedades = enfermedadService.listar();
			} catch (VacunasUyException e) {
				logger.info(e.getMessage().trim());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			}
		}
	}
	
	private void clearParam() {
		this.id = null;
		this.nombre = null;
		this.enfermedades = null;
	}
	
}
