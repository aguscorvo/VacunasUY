package vacunasuy.componentecentral.rest.bean;

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

import vacunasuy.componentecentral.business.IUsuarioService;
import vacunasuy.componentecentral.dto.UsuarioDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Named("beanusuario")
@RequestScoped
public class UsuarioBean implements Serializable {

	static Logger logger = Logger.getLogger(UsuarioBean.class);
	
	private static final long serialVersionUID = 1L;
	List<UsuarioDTO> usuarios;
	
	@EJB
	IUsuarioService usuarioService;
	
	public UsuarioBean() {
	}
	@PostConstruct
	public void init() {
		try {
			usuarios = usuarioService.listar();
			
		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			System.out.println(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
				    FacesMessage.SEVERITY_INFO, e.getMessage().trim(), null));
			usuarios = new ArrayList<UsuarioDTO>();
		}
		
	}
	
	public List<UsuarioDTO> getUsuarios() {
		return usuarios;
	}
	
	public void setUsuarios(List<UsuarioDTO> usuarios) {
		this.usuarios = usuarios;
	}
	
	

}
