package vacunasuy.nodoperifericobackend.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import vacunasuy.nodoperifericobackend.converter.AgendaConverter;
import vacunasuy.nodoperifericobackend.converter.VacunadorConverter;
import vacunasuy.nodoperifericobackend.dao.IAgendaDAO;
import vacunasuy.nodoperifericobackend.dao.IVacunadorDAO;
import vacunasuy.nodoperifericobackend.dao.IVacunatorioDAO;
import vacunasuy.nodoperifericobackend.dto.AgendaDTO;
import vacunasuy.nodoperifericobackend.dto.VacunadorDTO;
import vacunasuy.nodoperifericobackend.entity.Agenda;
import vacunasuy.nodoperifericobackend.entity.Vacunador;
import vacunasuy.nodoperifericobackend.entity.Vacunador.CompositeKey;
import vacunasuy.nodoperifericobackend.entity.Vacunatorio;

@Service
public class VacunatorioService {
	
	@Autowired
	private IVacunatorioDAO vacunatorioDAO;
	
	@Autowired
	private IVacunadorDAO vacunadorDAO;
	
	@Autowired
	private IAgendaDAO agendaDAO;
	
	@Autowired
	private VacunadorConverter vacunadorConverter;
	
	@Autowired
	private AgendaConverter agendaConverter;
	
	@Value("${componentecentral.url}")
	String baseURL;
	
	public List<Vacunatorio> listar(){
		return vacunatorioDAO.findAll();
	}
	
	public Vacunatorio crear(Vacunatorio vacunatorio) {
		return vacunatorioDAO.save(vacunatorio);
	}
	
	/* Método que obtiene los vacunadores asignados para cada vacunatorio */
	public void obtenerAsignacionVacunadores() {
		Client cliente = ClientBuilder.newClient();
		String fecha = LocalDate.now().toString();
		for (Vacunatorio v : vacunatorioDAO.findAll()) {
			try {
				System.out.println("Consultando vacunadores para el vacunatorio con ID: " + v.getId());
				String URL = "/vacunatorios/obtenerAsignacionVacunadores/" + v.getId() + "/" + v.getClave() + "/" + fecha;
				WebTarget target = cliente.target(baseURL+URL);
				Response respuesta = target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();
				if(respuesta.getStatus() != 200) {
					System.out.println("Error al realizar la petición. Intente más tarde.");
				} else {
					ObjectMapper mapper = new ObjectMapper();
					List<VacunadorDTO> vacunadores = new ArrayList<VacunadorDTO>();
					vacunadores = mapper.readValue(respuesta.readEntity(String.class), new TypeReference<List<VacunadorDTO>>(){});
					if(vacunadores.size() == 0) {
						System.out.println("No hay vacunadores asignados para la fecha.");
					} else {
						for (VacunadorDTO vacunadorDTO : vacunadores) {
							Vacunador vacunador = vacunadorConverter.fromDTO(vacunadorDTO);
							CompositeKey id = new CompositeKey(v.getId(), LocalDate.parse(fecha), v.getId());
							vacunador.setId(id);
							vacunadorDAO.save(vacunador);
						}
						System.out.println("Total de vacunadores registrados: " + vacunadores.size());
					}
				}
			} catch (ProcessingException  e) {
				System.out.println("No se pudo conectar al servidor. Intente más tarde.");
				System.out.println(e.getLocalizedMessage());
			} catch (JsonProcessingException e) {
				System.out.println("Error al parsear los datos. Intente más tarde.");
				System.out.println(e.getLocalizedMessage());
			}
		}
	}
	
	/* Método que obtiene las agendas para cada vacunatorio */
	public void obtenerAgendasPorVacunatorio() {
		Client cliente = ClientBuilder.newClient();
		String fecha = LocalDate.now().toString();
		for (Vacunatorio v : vacunatorioDAO.findAll()) {
			try {
				System.out.println("Consultando agendas para el vacunatorio con ID: " + v.getId());
				String URL = "/vacunatorios/listarAgendasPorVacunatorio/" + v.getId() + "/" + fecha;
				WebTarget target = cliente.target(baseURL+URL);
				Response respuesta = target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();
				if(respuesta.getStatus() != 200) {
					System.out.println("Error al realizar la petición. Intente más tarde.");
				} else {
					ObjectMapper mapper = new ObjectMapper();
					List<AgendaDTO> agendas = new ArrayList<AgendaDTO>();
					agendas = mapper.readValue(respuesta.readEntity(String.class), new TypeReference<List<AgendaDTO>>(){});
					if(agendas.size() == 0) {
						System.out.println("No hay agendas asignados para la fecha.");
					} else {
						for (AgendaDTO agendaDTO : agendas) {
							Agenda agenda = agendaConverter.fromDTO(agendaDTO);
							agenda.setVacunado(false);
							agendaDAO.save(agenda);
						}
						System.out.println("Total de agendas registrados: " + agendas.size());
					}
				}
			} catch (ProcessingException  e) {
				System.out.println("No se pudo conectar al servidor. Intente más tarde.");
				System.out.println(e.getLocalizedMessage());
			} catch (JsonProcessingException e) {
				System.out.println("Error al parsear los datos. Intente más tarde.");
				System.out.println(e.getLocalizedMessage());
			}
		}
	}
	
}
