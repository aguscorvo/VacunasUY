package vacunasuy.componentecentral.rest;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import vacunasuy.componentecentral.business.IUsuarioService;
import vacunasuy.componentecentral.dto.UsuarioCrearDTO;
import vacunasuy.componentecentral.dto.UsuarioDTO;
import vacunasuy.componentecentral.dto.UsuarioLoginBackofficeDTO;

@RequestScoped
@Path("/usuarios")
@Consumes("application/json")
@Produces("application/json")
public class UsuarioREST {
	
	@EJB
	IUsuarioService usuarioService;
	
	@POST
	public UsuarioDTO crear(UsuarioCrearDTO request) {
		return usuarioService.crear(request);
	}

	@POST
	@Path("/loginbackoffice")
	public UsuarioDTO loginBackoffice(UsuarioLoginBackofficeDTO request) {
		return usuarioService.loginBackoffice(request);
	}
	
}
