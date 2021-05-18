package vacunasuy.nodosperifericos.servlet;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import vacunasuy.nodosperifericos.business.IVacunatorioService;
import vacunasuy.nodosperifericos.exception.NodosPerifericosException;

/**
 * Servlet implementation class nodosPerifericosServlet
 */
@WebServlet("/")
public class nodosPerifericosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	@EJB
	private IVacunatorioService vacunatorioService;
	
    public nodosPerifericosServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accion = request.getServletPath();
		try {
			switch (accion) {
				default:
					listarNodosPerifericos(request, response);
					break;
			}
		}catch (NodosPerifericosException e) {
			throw new ServletException(e);
		}
	}
	
	private void listarNodosPerifericos(HttpServletRequest request, HttpServletResponse response) throws NodosPerifericosException, ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        request.setAttribute("vacunatorios", vacunatorioService.listar());
        dispatcher.forward(request, response);
	}
}
