package vacunasuy.componentecentral.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.primefaces.shaded.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Named("LoginBeanAdmin")
@RequestScoped
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginBeanAdmin implements Serializable {

	static Logger logger = Logger.getLogger(LoginBeanAdmin.class);

	private static final long serialVersionUID = 1L;
	private String usernameCookie;
	private String userToken;
	

	@PostConstruct
	public void init() {

		//String cookieValue = "{\"token\":\"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlt7ImlkIjoxLCJub21icmUiOiJBZG1pbmlzdHJhZG9yIn1dLCJpYXQiOjE2MjQ1NTQ2MjMsImV4cCI6MTYyNDU1ODIyM30.e5-4eu_JK7s3pN6Pxp2HjJIYsEOgQ2U4OuLYToJ3XGggLTAPlocbo-sYslaSKjiwJtMLVaIfoQ914dFB3E8VXg\",\"nombre\":\"Administrador \",\"rol\":1}";
		//Cookie prueba = new Cookie("flutter.vacunasUYUser", cookieValue);
		//response.addCookie(prueba);

		
		Cookie cookie = (Cookie) FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap()
				.get("flutter.vacunasUYUser");

		if (cookie != null) {

			JSONObject loginJSON = new JSONObject(cookie.getValue());
			userToken = loginJSON.getString("token");
			
			//logger.info("JSON:" + cookie.getValue());
			
			boolean esAutoridad = false;
			
			if (loginJSON.getLong("rol") == 1)
				esAutoridad = true;

			//logger.info("ROL:" + loginJSON.getLong("rol"));
			
			if (esAutoridad) {
				usernameCookie = loginJSON.getString("nombre");
			} else {
				logger.info("ERROR Administrador loginJSON: " + loginJSON.toString());
				/*
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("https://vacunasuy.web.elasticloud.uy/");
				} catch (IOException e) {
					logger.info(e.getMessage().trim());
				}
				*/
			}

		} else {
				
			logger.info("ERROR Administrador Cookie null");
			/*
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("https://vacunasuy.web.elasticloud.uy/");
			} catch (IOException e) {
				logger.info(e.getMessage().trim());
			}
			*/
		}
	}

	
	public void logout() {
		try {
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();

			Cookie cookie = (Cookie) FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap()
					.get("flutter.vacunasUYUser");

			if (cookie != null) {
				cookie.setMaxAge(0);
				cookie.setValue("");
				response.addCookie(cookie);
				
			}
			logger.info("logout");
			FacesContext.getCurrentInstance().getExternalContext().redirect("https://vacunasuy.web.elasticloud.uy/");
			
		} catch (IOException e) {
			logger.info(e.getMessage().trim());
		}

	}

}
