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
import vacunasuy.componentecentral.business.ITransportistaService;
import vacunasuy.componentecentral.dto.TransportistaCrearDTO;
import vacunasuy.componentecentral.dto.TransportistaDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Named("TransportistaBean")
@RequestScoped
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransportistaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(StockBean.class);
	
	private Long id;
	private String nombre;
	private List<TransportistaDTO> transportistas;
	
	@EJB
	private ITransportistaService transportistaService;
	
	@PostConstruct
	public void init() {
		try {
			transportistas = transportistaService.listar();
		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
		}
	}
	
	public void addTransportista() {
		try {
			TransportistaCrearDTO transportista = TransportistaCrearDTO.builder()
					.nombre(nombre)
					.build();
			transportistaService.crear(transportista);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Transportista " + transportista.getNombre() + " creado con éxito.", null));
		} catch (VacunasUyException e) {
			logger.error(e.getLocalizedMessage());
		} finally {
			clearParam();
			try {
				transportistas = transportistaService.listar();
			} catch (VacunasUyException e) {
				logger.error(e.getLocalizedMessage());
			}
		}
	}
	
	public void updTransportista() {
		try {
			TransportistaCrearDTO transportista = TransportistaCrearDTO.builder()
					.nombre(nombre)
					.build();
			transportistaService.editar(id, transportista);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Transportista " + transportista.getNombre() + " editado con éxito.", null));
		} catch (VacunasUyException e) {
			logger.error(e.getLocalizedMessage());
		} finally {
			clearParam();
			try {
				transportistas = transportistaService.listar();
			} catch (VacunasUyException e) {
				logger.error(e.getLocalizedMessage());
			}
		}
	}
	
	public void delTransportista() {
		try {
			transportistaService.eliminar(id);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Transportista eliminado con éxito.", null));
		} catch (VacunasUyException e) {
			logger.error(e.getLocalizedMessage());
		} finally {
			clearParam();
			try {
				transportistas = transportistaService.listar();
			} catch (VacunasUyException e) {
				logger.error(e.getLocalizedMessage());
			}
		}
	}
	
	private void clearParam() {
		this.id = null;
		this.nombre = null;
		this.transportistas = null;
	}
	
}
