package vacunasuy.componentecentral.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.jboss.logging.Logger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vacunasuy.componentecentral.business.IPuestoService;
import vacunasuy.componentecentral.business.IUsuarioService;
import vacunasuy.componentecentral.business.IVacunatorioService;
import vacunasuy.componentecentral.dto.AtiendeCrearDTO;
import vacunasuy.componentecentral.dto.AtiendeDTO;
import vacunasuy.componentecentral.dto.PuestoMinDTO;
import vacunasuy.componentecentral.dto.RolDTO;
import vacunasuy.componentecentral.dto.UsuarioDTO;
import vacunasuy.componentecentral.dto.VacunatorioDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Named("AsignarVacunatorioBean")
@SessionScoped
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AsignarVacunatorioBean implements Serializable {

	static Logger logger = Logger.getLogger(AsignarVacunatorioBean.class);

	private static final long serialVersionUID = 1L;

	private Long idUsuario;
	private Long idVacunatorio;
	private Long idPuesto;
	private String fecha;
	List<VacunatorioDTO> vacunatorios;
	List<UsuarioDTO> usuarios;
	String jsonVac;
	String strbuscar;
	
	@EJB
	IUsuarioService usuarioService;

	@EJB
	IVacunatorioService vacunatorioService;

	@EJB
	IPuestoService puestoService;

	@PostConstruct
	public void init() {
		try {
			usuarios = allVacunadores();
			vacunatorios = vacunatorioService.listar();

		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			usuarios = new ArrayList<UsuarioDTO>();
		} catch (Exception e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			usuarios = new ArrayList<UsuarioDTO>();
		}

	}

	public void srchVacunador() {

		logger.info("srchVacunador 'strbuscar': " + strbuscar);

		try {
			usuarios = allVacunadores();

		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			usuarios = new ArrayList<UsuarioDTO>();
		}

		if (!strbuscar.equals("")) {
			List<UsuarioDTO> auxvac = new ArrayList<UsuarioDTO>();

			strbuscar = strbuscar.toUpperCase();

			for (UsuarioDTO udto : usuarios) {
				if (udto.getDocumento().contains(strbuscar) || udto.getCorreo().toUpperCase().contains(strbuscar))
					auxvac.add(udto);
			}
			usuarios = auxvac;
		}

	}

	public void addAsignacion() {
		logger.info("addAsignacion 'id': " + idUsuario + " - " + idPuesto + " - " + fecha);
		try {
			AtiendeCrearDTO atiendeCrearDTO = AtiendeCrearDTO.builder().idUsuario(idUsuario).idPuesto(idPuesto)
					.fecha(fecha).build();

			usuarioService.asignarVacunadorAPuesto(atiendeCrearDTO);

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario asignado con éxito.", null));

		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
		} finally {
			clearParam();
			try {
				usuarios = usuarioService.listar();
			} catch (VacunasUyException e) {
				logger.info(e.getMessage().trim());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			}
		}
	}

	private List<UsuarioDTO> allVacunadores() throws VacunasUyException {
		List<UsuarioDTO> res = new ArrayList<UsuarioDTO>();
		List<UsuarioDTO> auxvac = usuarioService.listar();

		for (UsuarioDTO vdto : auxvac) {
			for (RolDTO rdto : vdto.getRoles()) {
				if (rdto.getNombre().toUpperCase().contains("VACUNADOR")) {
					res.add(vdto);
				}
			}
		}

		return res;
	}

	public String getJsonVac() {
		String ret = "{\"0\":{}";

		for (VacunatorioDTO vac : vacunatorios) {
			ret += ",\"" + vac.getId() + "\":{\"id\": " + vac.getId() + ", \"nombre\":\""
					+ vac.getDepartamento().getNombre() + " - " + vac.getNombre() + "\", \"puestos\": [{}";

			for (PuestoMinDTO p : vac.getPuestos()) {
				ret += ",{\"id\":" + p.getId() + ",\"numero\":" + p.getNumero() + "}";
			}
			ret += "]}";

		}

		ret += "}";
		return ret;

	}

	private void clearParam() {
		this.idUsuario = null;
		this.idVacunatorio = null;
		this.idPuesto = null;
		this.fecha = null;
	}

}
