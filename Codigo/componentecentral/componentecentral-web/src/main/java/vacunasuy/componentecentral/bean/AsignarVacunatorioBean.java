package vacunasuy.componentecentral.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@RequestScoped
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
	List<VacunatorioDTO> atiende;
	List<PuestoMinDTO> selectPuestoOnVacunatorio;

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
			System.out.println(e.getMessage().trim());
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

		try {
			AtiendeCrearDTO atiendeCrearDTO = AtiendeCrearDTO.builder().idUsuario(idUsuario).idPuesto(idPuesto)
					.fecha(fecha).build();

			usuarioService.asignarVacunadorAPuesto(atiendeCrearDTO);

			logger.info("addAsignacion 'id': " + idUsuario);

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
					usuarios.add(vdto);
				}
			}
		}

		return res;
	}

	public List<VacunatorioDTO> getAtiende() {
		List<AtiendeDTO> auxatiende = new ArrayList<AtiendeDTO>();
		List<VacunatorioDTO> ret = new ArrayList<VacunatorioDTO>();

		if (idUsuario != null) {
			try {
				Map<Long, VacunatorioDTO> mapaux = new HashMap<Long, VacunatorioDTO>();

				auxatiende = usuarioService.listarAtiendeVacunador(idUsuario);

				for (AtiendeDTO adto : auxatiende) {
					VacunatorioDTO vac;
					PuestoMinDTO pmdto = PuestoMinDTO.builder().agendas(adto.getPuesto().getAgendas())
							.numero(adto.getPuesto().getNumero()).build();

					Long vacid = adto.getPuesto().getVacunatorio().getId();
					if (mapaux.containsKey(vacid)) {
						vac = mapaux.get(vacid);
					} else {
						vac = VacunatorioDTO.builder().id(adto.getPuesto().getVacunatorio().getId())
								.nombre(adto.getPuesto().getVacunatorio().getNombre())
								.departamento(adto.getPuesto().getVacunatorio().getDepartamento())
								.localidad(adto.getPuesto().getVacunatorio().getLocalidad())
								.direccion(adto.getPuesto().getVacunatorio().getDireccion())
								.puestos(new ArrayList<PuestoMinDTO>()).build();
					}

					vac.getPuestos().add(pmdto);

					mapaux.put(vacid, vac);

				}

				ret = new ArrayList<VacunatorioDTO>(mapaux.values());
			} catch (VacunasUyException e) {
				logger.info(e.getMessage().trim());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			} catch (Exception e) {
				logger.info(e.getMessage().trim());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			}
		}
		return ret;
	}

	public List<PuestoMinDTO> getSelectPuestoOnVacunatorio() {
		List<PuestoMinDTO> puestos = new ArrayList<PuestoMinDTO>();
		
		if (idVacunatorio != null) {
			try {
				VacunatorioDTO vac = vacunatorioService.listarPorId(idVacunatorio);
				puestos = vac.getPuestos();
			} catch (VacunasUyException e) {
				logger.info(e.getMessage().trim());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			} catch (Exception e) {
				logger.info(e.getMessage().trim());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			}
		}

		return puestos;
	}

	private void clearParam() {
		this.idUsuario = null;
		this.idVacunatorio = null;
		this.idPuesto = null;
		this.fecha = null;
	}

}
