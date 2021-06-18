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

	static Logger logger = Logger.getLogger(ProveedorBean.class);

	private Long id;
	private String nombre;
	private Long idPais;
	private List<ProveedorDTO> proveedores;
	private List<PaisDTO> paises;
	String strbuscar;
	
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
	
	public void srchProveedor() {

		logger.info("srchProveedor 'strbuscar': " + strbuscar);

		try {
			proveedores = proveedorService.listar();

		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			proveedores = new ArrayList<ProveedorDTO>();
		}

		if (!strbuscar.equals("")) {
			List<ProveedorDTO> auxvac = new ArrayList<ProveedorDTO>();

			strbuscar = strbuscar.toUpperCase();

			for (ProveedorDTO vdto : proveedores) {
				if (vdto.getNombre().toUpperCase().contains(strbuscar)
						|| vdto.getPais().getNombre().toUpperCase().contains(strbuscar))
					auxvac.add(vdto);
			}
			proveedores = auxvac;
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
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null)); 
		} finally {
			clearParam();
			try {
				proveedores = proveedorService.listar();
				paises = paisService.listar();
			} catch (VacunasUyException e) {
				logger.info(e.getMessage().trim());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
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
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
		} finally {
			clearParam();
			try {
				proveedores = proveedorService.listar();
				paises = paisService.listar();
			} catch (VacunasUyException e) {
				logger.info(e.getMessage().trim());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			}
		}
	}
	
	public void delProveedor() {
		try {
			proveedorService.eliminar(id);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Proveedor eliminado con éxito.", null));
		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
		} finally {
			clearParam();
			try {
				proveedores = proveedorService.listar();
				paises = paisService.listar();
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
		this.idPais = null;
		this.proveedores = null;
		this.paises = null;
	}
	
}
