package vacunasuy.componentecentral.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import vacunasuy.componentecentral.dto.UsuarioLoginGubUYDTO;

@ApplicationScoped
@Path("/serviciosExternos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServiciosExternosREST {
	
	@POST
	public void autenticacionGubUy(UsuarioLoginGubUYDTO request) {
		
		/* Datos de conexión */
		String client_id = "890192";
		String client_secret = "457d52f181bf11804a3365b49ae4d29a2e03bbabe74997a2f510b179";
		String redirect_uri = "http://localhost:8080";
		String api_base_url= "https://auth-testing.iduruguay.gub.uy/oidc/v1";
		String access_token_url= "https://auth-testing.iduruguay.gub.uy/oidc/v1/token";
	    String authorize_url= "https://auth-testing.iduruguay.gub.uy/oidc/v1/authorize";
	    
	}
	
	
}
