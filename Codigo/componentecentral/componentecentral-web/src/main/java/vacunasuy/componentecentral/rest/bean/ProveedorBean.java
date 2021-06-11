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
import vacunasuy.componentecentral.business.IPaisService;
import vacunasuy.componentecentral.business.IProveedorService;
import vacunasuy.componentecentral.dto.PaisDTO;
import vacunasuy.componentecentral.dto.ProveedorCrearDTO;
import vacunasuy.componentecentral.dto.ProveedorDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Named("ProveedorBean")
@RequestScoped
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProveedorBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(StockBean.class);

	private Long id;
	private String nombre;
	private Long idPais;
	private List<ProveedorDTO> proveedores;
	private List<PaisDTO> paises;
	
	@EJB
	private IProveedorService proveedorService;
	
	@EJB
	private IPaisService paisService;
	
	@PostConstruct
	public void init() {
		try {
			proveedores = proveedorService.listar();
			paises = paisService.listar();
		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
		}
	}
	
	public void addProveedor() {
		try {
			ProveedorCrearDTO proveedor = ProveedorCrearDTO.builder()
					.nombre(nombre)
					.pais(idPais)
					.build();
			proveedorService.crear(proveedor);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Proveedor " + proveedor.getNombre() + " creado con éxito.", null));
		} catch (VacunasUyException e) {
			logger.error(e.getLocalizedMessage());
		} finally {
			clearParam();
			try {
				proveedores = proveedorService.listar();
				paises = paisService.listar();
			} catch (VacunasUyException e) {
				logger.error(e.getLocalizedMessage());
			}
		}
	}
	
	public void updProveedor() {
		try {
			ProveedorCrearDTO proveedor = ProveedorCrearDTO.builder()
					.nombre(nombre)
					.pais(idPais)
					.build();
			proveedorService.editar(id, proveedor);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Proveedor " + proveedor.getNombre() + " editado con éxito.", null));
		} catch (VacunasUyException e) {
			logger.error(e.getLocalizedMessage());
		} finally {
			clearParam();
			try {
				proveedores = proveedorService.listar();
				paises = paisService.listar();
			} catch (VacunasUyException e) {
				logger.error(e.getLocalizedMessage());
			}
		}
	}
	
	public void delProveedor() {
		try {
			proveedorService.eliminar(id);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Proveedor eliminado con éxito.", null));
		} catch (VacunasUyException e) {
			logger.error(e.getLocalizedMessage());
		} finally {
			clearParam();
			try {
				proveedores = proveedorService.listar();
				paises = paisService.listar();
			} catch (VacunasUyException e) {
				logger.error(e.getLocalizedMessage());
			}
		}
	}
	
	private void clearParam() {
		this.id = null;
		this.nombre = null;
		this.idPais = null;
		this.proveedores = null;
		this.paises = null;
	}
	
}
