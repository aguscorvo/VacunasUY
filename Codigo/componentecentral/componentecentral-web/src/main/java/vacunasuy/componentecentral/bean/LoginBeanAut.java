package vacunasuy.componentecentral.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Named("LoginBeanAut")
@RequestScoped
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginBeanAut implements Serializable {

	static Logger logger = Logger.getLogger(LoginBeanAut.class);

	private static final long serialVersionUID = 1L;
	private String usernameCookie;
	private String userToken;

	@PostConstruct
	public void init() {

		Cookie cookie = (Cookie) FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap()
				.get("flutter.vacunasUYUser");

		if (cookie != null) {

			JSONObject loginJSON = new JSONObject(cookie.getValue());
			userToken = loginJSON.getString("token");

			boolean esAutoridad = false;

			if (loginJSON.getLong("rol") == 2)
				esAutoridad = true;

			if (esAutoridad) {
				usernameCookie = loginJSON.getString("nombre");

			} else {
				logger.info("ERROR Autoridad loginJSON: " + loginJSON.toString());
				/*
				 * try {
				 * //FacesContext.getCurrentInstance().getExternalContext().redirect(request.
				 * getContextPath());
				 * FacesContext.getCurrentInstance().getExternalContext().redirect(
				 * "https://vacunasuy.web.elasticloud.uy/");
				 * 
				 * } catch (IOException e) { logger.info(e.getMessage().trim()); }
				 */
			}

		} else {
			logger.info("ERROR Autoridad Cookie null");
			/*
			 * 
			 * try { FacesContext.getCurrentInstance().getExternalContext().redirect(
			 * "https://vacunasuy.web.elasticloud.uy/"); } catch (IOException e) {
			 * logger.info(e.getMessage().trim()); }
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
