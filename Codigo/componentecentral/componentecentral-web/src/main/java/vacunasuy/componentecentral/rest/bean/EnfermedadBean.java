package vacunasuy.componentecentral.rest.bean;

import java.io.Serializable;
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
	
	static Logger logger = Logger.getLogger(StockBean.class);
	
	private Long id;
	private String nombre;
	private List<EnfermedadDTO> enfermedades;
	
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
	
	public void addEnfermedad() {
		try {
			EnfermedadCrearDTO enfermedad = EnfermedadCrearDTO.builder()
					.nombre(nombre)
					.build();
			enfermedadService.crear(enfermedad);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Enfermedad " + enfermedad.getNombre() + " creada con éxito.", null));
		} catch (VacunasUyException e) {
			logger.error(e.getLocalizedMessage());
		} finally {
			clearParam();
			try {
				enfermedades = enfermedadService.listar();
			} catch (VacunasUyException e) {
				logger.error(e.getLocalizedMessage());
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
			// TODO: handle exception
		} finally {
			clearParam();
			try {
				enfermedades = enfermedadService.listar();
			} catch (VacunasUyException e) {
				logger.error(e.getLocalizedMessage());
			}
		}
	}
	
	public void delEnfermedad() {
		try {
			enfermedadService.eliminar(id);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Enfermedad eliminada con éxito.", null));
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			clearParam();
			try {
				enfermedades = enfermedadService.listar();
			} catch (VacunasUyException e) {
				logger.error(e.getLocalizedMessage());
			}
		}
	}
	
	private void clearParam() {
		this.id = null;
		this.nombre = null;
		this.enfermedades = null;
	}
	
}
