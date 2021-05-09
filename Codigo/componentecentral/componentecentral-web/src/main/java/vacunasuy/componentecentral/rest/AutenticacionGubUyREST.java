package vacunasuy.componentecentral.rest;

import java.net.URI;
import java.util.UUID;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import vacunasuy.componentecentral.business.IUsuarioService;
import vacunasuy.componentecentral.dto.RespuestaAccessTokenDTO;
import vacunasuy.componentecentral.dto.RespuestaUserInfoDTO;
import vacunasuy.componentecentral.dto.UsuarioLoginExitosoDTO;
import vacunasuy.componentecentral.util.Constantes;

@ApplicationScoped
@Path("/autenticaciongubuy")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AutenticacionGubUyREST {
	
	final Logger log = Logger.getLogger(AutenticacionGubUyREST.class.getName());
	
	@EJB
	private IUsuarioService usuarioService;
	
	@GET
	public Response redireccionarAutenticacion() {
		/* Se genera un string random para establecer el estado */
		String uuid = UUID.randomUUID().toString();
		String state = uuid.replace("-", "");
		UriBuilder uri = UriBuilder.fromPath(Constantes.AUTHORIZATION_URL);
		uri.queryParam("response_type", "code");
		uri.queryParam("client_id", Constantes.CLIENT_ID);
		uri.queryParam("redirect_uri", Constantes.REDIRECT_URI);
		uri.queryParam("scope", "openid document personal_info email auth_info");
		uri.queryParam("state", state);
		URI u = uri.build();
		return Response.temporaryRedirect(u).build();
	}
	
	@GET
	@Path("/procesarTokens")
	public Response procesarTokensGubUy(@QueryParam("code") String code, @QueryParam("state") String state) {
		RespuestaREST<UsuarioLoginExitosoDTO> respuesta = null;
		try {
			Client cliente = ClientBuilder.newClient();
			WebTarget target = cliente.target(Constantes.ACCESSTOKEN_URL);
			
			Form formulario = new Form();
			formulario.param("grant_type", "authorization_code");
			formulario.param("redirect_uri", Constantes.REDIRECT_URI);
			formulario.param("client_id", Constantes.CLIENT_ID);
			formulario.param("client_secret", Constantes.CLIENT_SECRET);
			formulario.param("code", code);
			
			RespuestaAccessTokenDTO respuestaAccessToken = new RespuestaAccessTokenDTO();
			Response response = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.form(formulario));
			respuestaAccessToken = response.readEntity(RespuestaAccessTokenDTO.class);			
			RespuestaUserInfoDTO infoUsuario = obtenerInformacionUsuario(respuestaAccessToken.getAccess_token());
			UsuarioLoginExitosoDTO usuario = usuarioService.loginGubUy(infoUsuario);
			respuesta = new RespuestaREST<UsuarioLoginExitosoDTO>(true, "Usuario logueado con éxito.", usuario);
			return Response.ok(respuesta).build();
		} catch (Exception e) {
			respuesta = new RespuestaREST<UsuarioLoginExitosoDTO>(false, e.getLocalizedMessage());
			return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
		}
		
	}
	
	private RespuestaUserInfoDTO obtenerInformacionUsuario(String accessToken) {
		Client cliente = ClientBuilder.newClient();
		WebTarget target = cliente.target(Constantes.USERINFO_URL);
		RespuestaUserInfoDTO respuestaUserInfo = new RespuestaUserInfoDTO();
		Response response = target.request().accept(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken).get();
		respuestaUserInfo = response.readEntity(RespuestaUserInfoDTO.class);
		return respuestaUserInfo;
	}

}
