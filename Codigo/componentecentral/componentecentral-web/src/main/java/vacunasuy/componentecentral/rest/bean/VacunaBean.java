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

	static Logger logger = Logger.getLogger(StockBean.class);
	
	private Long id;
	private String nombre;
	private int cant_dosis;
	private int periodo;
	private int inmunidad;
	private Long idEnfermedad;
	private List<VacunaDTO> vacunas;
	private List<EnfermedadDTO> enfermedades;
	
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
		}
	}
	
	public void addTransportista() {
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
			logger.error(e.getLocalizedMessage());
		} finally {
			clearParam();
			try {
				vacunas = vacunaService.listar();
				enfermedades = enfermedadService.listar();
			} catch (VacunasUyException e) {
				logger.error(e.getLocalizedMessage());
			}
		}
	}
	
	public void updTransportista() {
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
			logger.error(e.getLocalizedMessage());
		} finally {
			clearParam();
			try {
				vacunas = vacunaService.listar();
				enfermedades = enfermedadService.listar();
			} catch (VacunasUyException e) {
				logger.error(e.getLocalizedMessage());
			}
		}
	}
	
	public void delTransportista() {
		try {
			vacunaService.eliminar(id);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Vacuna eliminada con éxito.", null));
		} catch (VacunasUyException e) {
			logger.error(e.getLocalizedMessage());
		} finally {
			clearParam();
			try {
				vacunas = vacunaService.listar();
				enfermedades = enfermedadService.listar();
			} catch (VacunasUyException e) {
				logger.error(e.getLocalizedMessage());
			}
		}
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
