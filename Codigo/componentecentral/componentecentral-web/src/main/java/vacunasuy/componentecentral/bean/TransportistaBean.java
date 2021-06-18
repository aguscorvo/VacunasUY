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

	static Logger logger = Logger.getLogger(TransportistaBean.class);
	
	private Long id;
	private String nombre;
	String strbuscar; 
	private List<TransportistaDTO> transportistas;
	
	@EJB
	private ITransportistaService transportistaService;
	
	@PostConstruct
	public void init() {
		try {
			transportistas = transportistaService.listar();
		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			transportistas = new ArrayList<TransportistaDTO>();

		}
	}
	
	public void srchTransportista() {

		logger.info("srchTransportista 'strbuscar': " + strbuscar);

		try {
			transportistas = transportistaService.listar();;

		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			transportistas = new ArrayList<TransportistaDTO>();
		}

		if (!strbuscar.equals("")) {
			List<TransportistaDTO> auxtra = new ArrayList<TransportistaDTO>();

			strbuscar = strbuscar.toUpperCase();

			for (TransportistaDTO tdto : transportistas) {
				if (tdto.getNombre().toUpperCase().contains(strbuscar))
					auxtra.add(tdto);
			}
			transportistas = auxtra;
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
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));

		} finally {
			clearParam();
			try {
				transportistas = transportistaService.listar();
			} catch (VacunasUyException e) {
				logger.info(e.getMessage().trim());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
				
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
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			
		} finally {
			clearParam();
			try {
				transportistas = transportistaService.listar();
			} catch (VacunasUyException e) {
				logger.info(e.getMessage().trim());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
				
			}
		}
	}
	
	public void delTransportista() {
		try {
			transportistaService.eliminar(id);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Transportista eliminado con éxito.", null));
		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			
		} finally {
			clearParam();
			try {
				transportistas = transportistaService.listar();
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
		this.transportistas = null;
	}
	
}
