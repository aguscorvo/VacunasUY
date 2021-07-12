import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;


public class CookieHelper {

	static Logger logger = Logger.getLogger(CookieHelper.class);
	
	public CookieHelper() {
	}
	
	public void setCookie(String name, String value, int expiry) {

		logger.info(value);
	    //FacesContext facesContext = FacesContext.getCurrentInstance();

	    Cookie cookie = (Cookie) FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap().get(name);

	    /*
	    HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
	    
	    Cookie[] userCookies = request.getCookies();
	    if (userCookies != null && userCookies.length > 0 ) {
	        for (int i = 0; i < userCookies.length; i++) {
	            if (userCookies[i].getName().equals(name)) {
	                cookie = userCookies[i];
	                break;
	            }
	        }
	    }
	    */
	    

	    if (cookie != null) {
	        cookie.setValue(value);
	    } else {
	        cookie = new Cookie(name, value);
	        //cookie.setPath(request.getContextPath());
	    }

	    cookie.setMaxAge(expiry);

	    
	    FacesContext.getCurrentInstance()
	    .getExternalContext()
	    .addResponseCookie(name, value, null);
	    
	    //HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
	    //response.addCookie(cookie);
	    
	    logger.info(cookie);
	  }

	  public Cookie getCookie(String name) {
		  
		Cookie cookie = (Cookie) FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap().get(name);
		
		return cookie;

/*
	    FacesContext facesContext = FacesContext.getCurrentInstance();

	    HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
	    Cookie cookie = null;

	    Cookie[] userCookies = request.getCookies();
	    if (userCookies != null && userCookies.length > 0 ) {
	        for (int i = 0; i < userCookies.length; i++) {
	            if (userCookies[i].getName().equals(name)) {
	                cookie = userCookies[i];
	                return cookie;
	            }
	        }
	    }
	    return null;
	    
*/
	  }

		

}
