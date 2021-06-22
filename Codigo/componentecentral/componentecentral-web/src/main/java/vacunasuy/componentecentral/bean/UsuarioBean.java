package vacunasuy.componentecentral.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.jboss.logging.Logger;

import vacunasuy.componentecentral.business.IRolService;
import vacunasuy.componentecentral.business.ISectorLaboralService;
import vacunasuy.componentecentral.business.IUsuarioService;
import vacunasuy.componentecentral.dto.RolDTO;
import vacunasuy.componentecentral.dto.SectorLaboralDTO;
import vacunasuy.componentecentral.dto.UsuarioCrearDTO;
import vacunasuy.componentecentral.dto.UsuarioDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Named("beanusuario")
@RequestScoped
public class UsuarioBean implements Serializable {

	static Logger logger = Logger.getLogger(UsuarioBean.class);

	private static final long serialVersionUID = 1L;
	List<UsuarioDTO> usuarios;
	String strbuscar;

	private Long id;
	private String documento;
	private String nombre;
	private String apellido;
	private String correo;
	private String fechaNacimiento;
	private String password;
	private List<Long> rolesID;
	private List<RolDTO> roles;
	private List<SectorLaboralDTO> sectores;
	private Long sectorLaboral;
	private Long rolID;

	@EJB
	IUsuarioService usuarioService;

	@EJB
	IRolService rolService;

	@EJB
	ISectorLaboralService sectorService;
	

	public UsuarioBean() {
	}

	@PostConstruct
	public void init() {
		try {
			usuarios = usuarioService.listar();
			roles = rolService.listar();
			sectores = sectorService.listar();

		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			System.out.println(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			usuarios = new ArrayList<UsuarioDTO>();
		}

	}

	public void srchUsuario() {

		logger.info("srchUsuario 'strbuscar': " + strbuscar);

		try {
			usuarios = usuarioService.listar();

		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			System.out.println(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage().trim(), null));
			usuarios = new ArrayList<UsuarioDTO>();
		}

		if (!strbuscar.equals("")) {
			List<UsuarioDTO> auxusr = new ArrayList<UsuarioDTO>();

			strbuscar = strbuscar.toUpperCase();
			
			for (UsuarioDTO udto : usuarios) {
				if ((udto.getDocumento() != null && udto.getDocumento().contains(strbuscar)) || udto.getCorreo().toUpperCase().contains(strbuscar))
					auxusr.add(udto);
			}
			usuarios = auxusr;
		}

	}

	public void addUsuario() {

		rolesID = new ArrayList<Long>();
		rolesID.add(this.rolID);

		fechaNacimiento = null;
		sectorLaboral = null; //No tiene

		UsuarioCrearDTO usuarioCrearDTO = new UsuarioCrearDTO(documento, nombre, apellido, correo, fechaNacimiento,
				password, rolesID, sectorLaboral);

		logger.info("addUsuario 'documento': " + documento);

		try {
			UsuarioDTO usuario = usuarioService.crear(usuarioCrearDTO);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Usuario " + usuario.getCorreo() + " creado con éxito.", null));

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

	public void updUsuario() {
		// rolesID = new ArrayList<Long>();
		// rolesID.add(this.rolID);
		password = null;
		logger.info(rolesID.size());

		try {
			UsuarioDTO aux = usuarioService.listarPorId(id);
			sectorLaboral = null;

			UsuarioCrearDTO usuarioCrearDTO = new UsuarioCrearDTO(aux.getDocumento(), aux.getNombre(),
					aux.getApellido(), aux.getCorreo(), aux.getFechaNacimiento(), password, rolesID,
					sectorLaboral);

			usuarios = usuarioService.listar();
			
			try {
				UsuarioDTO usuario = usuarioService.editar(id, usuarioCrearDTO);

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Usuario " + usuario.getCorreo() + " modificado con éxito.", null));

			} catch (VacunasUyException e) {
				logger.info(e.getMessage().trim());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));

			} finally {
				clearParam();
			}

		} catch (VacunasUyException e1) {
			logger.info(e1.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e1.getMessage().trim(), null));
		}

	}

	public void delUsuario() {
		try {
			usuarioService.eliminar(id);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario id " + id + " eliminado con éxito.", null));

			usuarios = usuarioService.listar();

		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));

		} finally {
			clearParam();
		}
	}

	private void clearParam() {
		this.id = null;
		this.documento = null;
		this.nombre = null;
		this.apellido = null;
		this.correo = null;
		this.fechaNacimiento = null;
		this.password = null;
		this.sectorLaboral = null;
		this.rolesID = null;
	}

	public void validateModelNombre(FacesContext context, UIComponent comp, Object value) throws ValidatorException {
		String texto = (String) value;

		if (texto.trim().equalsIgnoreCase("")) {
			((UIInput) comp).setValid(false);

			throw new ValidatorException(new FacesMessage("Error: En nombre no puede ser vacío"));
//			context.addMessage(comp.getClientId(context), message);
		}
	}

	public void validateModelApellido(FacesContext context, UIComponent comp, Object value) throws ValidatorException {
		String texto = (String) value;

		if (texto.trim().equalsIgnoreCase("")) {
			((UIInput) comp).setValid(false);

			throw new ValidatorException(new FacesMessage("Error: El apellido no puede ser vacío"));
		}
	}

	public void validateModelCorreo(FacesContext context, UIComponent comp, Object value) throws ValidatorException {
		String texto = (String) value;

		try {
			InternetAddress emailAddr = new InternetAddress(texto);
			emailAddr.validate();
		} catch (AddressException ex) {
			throw new ValidatorException(new FacesMessage("Error: El formato de correo no es correcto"));
		}

	}

	public List<UsuarioDTO> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioDTO> usuarios) {
		this.usuarios = usuarios;
	}

	public String getStrbuscar() {
		return strbuscar;
	}

	public void setStrbuscar(String strbuscar) {
		this.strbuscar = strbuscar;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<RolDTO> getRoles() {
		return roles;
	}

	public void setRoles(List<RolDTO> roles) {
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRolID() {
		return rolID;
	}

	public void setRolID(Long rolID) {
		this.rolID = rolID;
	}

	public Long getSectorLaboral() {
		return sectorLaboral;
	}

	public void setSectorLaboral(Long sectorLaboral) {
		this.sectorLaboral = sectorLaboral;
	}

	public List<SectorLaboralDTO> getSectores() {
		return sectores;
	}

	public void setSectores(List<SectorLaboralDTO> sectores) {
		this.sectores = sectores;
	}

	public List<Long> getRolesID() {
		return rolesID;
	}

	public void setRolesID(List<Long> rolesID) {
		this.rolesID = rolesID;
	}

}
