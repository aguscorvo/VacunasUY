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
import vacunasuy.componentecentral.business.ILoteService;
import vacunasuy.componentecentral.business.IProveedorService;
import vacunasuy.componentecentral.business.IVacunaService;
import vacunasuy.componentecentral.dto.LoteCrearDTO;
import vacunasuy.componentecentral.dto.LoteDTO;
import vacunasuy.componentecentral.dto.ProveedorDTO;
import vacunasuy.componentecentral.dto.VacunaDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Named("LoteBean")
@RequestScoped
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoteBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(LoteBean.class);
	
	private Long id;
	private Long cantidad;
	private Long cantidadDisponible;
	private Long idProveedor;
	private Long idVacuna;
	private List<LoteDTO> lotes;
	private List<ProveedorDTO> proveedores;
	private List<VacunaDTO> vacunas;
	String strbuscar;
	
	@EJB
	private ILoteService loteService;
	
	@EJB
	private IProveedorService proveedorService;
	
	@EJB
	private IVacunaService vacunaService;
	
	@PostConstruct
	public void init() {
		try {
			lotes = loteService.listar();
			proveedores = proveedorService.listar();
			vacunas = vacunaService.listar();
		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
		}
	}
	
	public void srchLote() {

		logger.info("srchLote 'strbuscar': " + strbuscar);

		try {
			lotes = loteService.listar();

		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			lotes = new ArrayList<LoteDTO>();
		}

		if (!strbuscar.equals("")) {
			List<LoteDTO> auxvac = new ArrayList<LoteDTO>();

			strbuscar = strbuscar.toUpperCase();

			for (LoteDTO vdto : lotes) {
				if (vdto.getId().toString().toUpperCase().contains(strbuscar)
						|| vdto.getProveedor().getNombre().toUpperCase().contains(strbuscar)
						|| vdto.getVacuna().getNombre().toUpperCase().contains(strbuscar))
					auxvac.add(vdto);
			}
			lotes = auxvac;
		}

	}
	
	public void addLote() {
		try {
			LoteCrearDTO lote = LoteCrearDTO.builder()
					.cantidad(cantidad)
					.idProveedor(idProveedor)
					.idVacuna(idVacuna)
					.build();
			loteService.crear(lote);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Lote creado con éxito.", null));
		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
		} finally {
			clearParam();
			try {
				lotes = loteService.listar();
				proveedores = proveedorService.listar();
				vacunas = vacunaService.listar();
			} catch (VacunasUyException e) {
				logger.info(e.getMessage().trim());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			}
		}
	}
	
	public void updLote() {
		try {
			LoteCrearDTO lote = LoteCrearDTO.builder()
					.cantidad(cantidad)
					.idProveedor(idProveedor)
					.idVacuna(idVacuna)
					.build();
			loteService.editar(id, lote);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Lote editado con éxito.", null));
		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
		} finally {
			clearParam();
			try {
				lotes = loteService.listar();
				proveedores = proveedorService.listar();
				vacunas = vacunaService.listar();
			} catch (VacunasUyException e) {
				logger.info(e.getMessage().trim());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			}
		}
	}
	
	public void delLote() {
		try {
			loteService.eliminar(id);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Lote eliminado con éxito.", null));
		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
		} finally {
			clearParam();
			try {
				lotes = loteService.listar();
				proveedores = proveedorService.listar();
				vacunas = vacunaService.listar();
			} catch (VacunasUyException e) {
				logger.info(e.getMessage().trim());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			}
		}
	}
	
	private void clearParam() {
		this.id = null;
		this.cantidad = null;
		this.cantidadDisponible = null;
		this.idProveedor = null;
		this.idVacuna = null;
		this.lotes = null;
		this.proveedores = null;
		this.vacunas = null;
	}

}
