package vacunasuy.componentecentral.bean;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;

//@WebFilter("/jsf/*")
public class AuthorizationFilter implements Filter {

	static Logger logger = Logger.getLogger(AuthorizationFilter.class);
	
	@Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String testParam = filterConfig.getInitParameter("test-param");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession(true);

        // Get the IP address of client machine.
        String ipAddress = request.getRemoteAddr();

        // Log the IP address and current timestamp.
        String lastAccess = (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).format(new Date());
        logger.info("AuthorizationFilter IP:  "+ ipAddress +", access:  "+ lastAccess);
        
        if (httpServletRequest.getRequestURL().toString().contains("/jsf/")) {
            if (session == null || session.getAttribute("USERLOGIN") == null) {
                httpServletResponse.sendRedirect("https://vacunasuy.web.elasticloud.uy/");
            } else {
            	logger.info("AuthorizationFilter no Session");
            }

        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        //

    }
	/*
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpSession sess = ((HttpServletRequest) request).getSession(true);
		String newCurrentPage = ((HttpServletRequest) request).getServletPath();
		
		if (sess.getAttribute("currentPage") == null) {
			sess.setAttribute("lastPage", newCurrentPage);
			sess.setAttribute("currentPage", newCurrentPage);
		} else {
			String oldCurrentPage = sess.getAttribute("currentPage").toString();
			if (!oldCurrentPage.equals(newCurrentPage)) {
				sess.setAttribute("lastPage", oldCurrentPage);
				sess.setAttribute("currentPage", newCurrentPage);
			}
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}
*/
}
