package vacunasuy.componentecentral.security;

import javax.ws.rs.ext.Provider;
import io.jsonwebtoken.Jwts;
import vacunasuy.componentecentral.rest.RespuestaREST;
import vacunasuy.componentecentral.util.Constantes;
import java.io.IOException;
import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@Provider
@RecursoProtegidoJWT
@Priority(Priorities.AUTHENTICATION)
public class RecursoProtegidoJWTFiltro implements ContainerRequestFilter{

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		/* Obtenemos el token de la cabecera del request */
        String cabeceraAutorizacion = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        /* Verificamos si nos enviaron un token y tiene el formato correcto */
        if (cabeceraAutorizacion == null || !cabeceraAutorizacion.startsWith("Bearer ")) {
        	throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).entity("Token no enviado o con formato inválido.").build());
        }

        // Extraemos el token
        String token = cabeceraAutorizacion.substring("Bearer".length()).trim();

        try {
        	/* Lo validamos con nuestra clave. Si no es válido, lanza una excepción. Si es válido, continúa con el request. */
            Jwts.parser().setSigningKey(Constantes.JWT_KEY).parseClaimsJws(token);
        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Token inválido. No autorizado.").build());
        }
	}
	
}
