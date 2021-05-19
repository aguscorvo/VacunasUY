package vacunasuy.nodosperifericos.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import vacunasuy.nodosperifericos.business.IVacunadorService;
import vacunasuy.nodosperifericos.business.IVacunatorioService;
import vacunasuy.nodosperifericos.dto.VacunadorDTO;
import vacunasuy.nodosperifericos.dto.VacunatorioDTO;
import vacunasuy.nodosperifericos.exception.NodosPerifericosException;
import vacunasuy.nodosperifericos.util.Constantes;

/**
 * Servlet implementation class nodosPerifericosServlet
 */
@WebServlet("/")
public class nodosPerifericosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	@EJB
	private IVacunatorioService vacunatorioService;
	
	@EJB
	private IVacunadorService vacunadorService;
	
    public nodosPerifericosServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getServletPath();
		String[] pathParts = pathInfo.split("/");
		String accion = pathParts[1];
		
		System.out.println("Accion:" + accion);
		try {
			switch (accion) {
				case "obtenerVacunadores":
					obtenerVacunadores(request, response);
					break;
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
	
	/* Path: /{idVacunatorio} */
	private void obtenerVacunadores(HttpServletRequest request, HttpServletResponse response) throws NodosPerifericosException, ServletException, IOException {
		String pathInfo = request.getServletPath();
		String[] pathParts = pathInfo.split("/");
		String id = pathParts[2];
		VacunatorioDTO vacunatorio = vacunatorioService.listarPorId(Long.parseLong(id));
		String fecha = LocalDate.now().toString();
		String URL = "/vacunatorios/obtenerAsignacionVacunadores/" + id + "/" + vacunatorio.getClave() + "/" + fecha;
		System.out.println(URL);
		Client cliente = ClientBuilder.newClient();
		WebTarget target = cliente.target(Constantes.COMPONENTE_CENTRAL_REST_URL+URL);
		Response response1 = target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();
		if(response1.getStatus() != 200) {
			System.out.println("Error al realizar la petición. Intente más tarde.");
		} else {
			ObjectMapper mapper = new ObjectMapper();
			List<VacunadorDTO> vacunadores = mapper.readValue(response1.readEntity(String.class), new TypeReference<List<VacunadorDTO>>(){}) ;
			if(vacunadores.size() == 0) {
				System.out.println("No hay vacunadores asignados para la fecha.");
			} else {
				vacunadorService.crear(vacunadores, fecha, Long.parseLong(id));
			}
		}
	}
}
