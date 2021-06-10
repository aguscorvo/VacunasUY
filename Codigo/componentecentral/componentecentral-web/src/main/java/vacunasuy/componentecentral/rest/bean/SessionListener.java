package vacunasuy.componentecentral.rest.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.jboss.logging.Logger;

public class SessionListener implements HttpSessionListener {
	
	static Logger logger = Logger.getLogger(SessionListener.class);

	public SessionListener() {
		// TODO Auto-generated constructor stub
	}
	
	public void sessionCreated(HttpSessionEvent event) {
		logger.info("Created session " + event.getSession().getId()); 
		
	} 
	
	public void sessionDestroyed(HttpSessionEvent event) { 
		String lastAccess = (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).format(new Date(event.getSession().getLastAccessedTime())); 
		logger.info("Expired session "+event.getSession().getId()+". Last access = "+ lastAccess); 
		
	}


}
