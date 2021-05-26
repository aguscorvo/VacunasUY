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
import vacunasuy.nodoperifericobackend.converter.EventoConverter;
import vacunasuy.nodoperifericobackend.dao.IEventoDAO;
import vacunasuy.nodoperifericobackend.dao.ITransportistaDAO;
import vacunasuy.nodoperifericobackend.dto.EventoDTO;
import vacunasuy.nodoperifericobackend.entity.Evento;
import vacunasuy.nodoperifericobackend.entity.Transportista;

@Service
public class TransportistaService {

	@Autowired
	private ITransportistaDAO transportistaDAO;

	@Autowired
	private EventoConverter eventoConverter;

	@Autowired
	private IEventoDAO eventoDAO;

	@Value("${componentecentral.url}")
	String baseURL;

	public List<Transportista> listar() {
		return transportistaDAO.findAll();
	}

	public Transportista crear(Transportista transportista) {
		return transportistaDAO.save(transportista);
	}

	/* Método que obtiene los eventos en estado INICIADO */
	public void obtenerEventosIniciados() {
		Client cliente = ClientBuilder.newClient();
		try {
			String fecha = LocalDate.now().toString();
			System.out.println("##### Obtener eventos en estado INICIADO #####");
			String URL = "/eventos/listarPorEstado/iniciado";
			WebTarget target = cliente.target(baseURL + URL);
			Response respuesta = target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();
			if (respuesta.getStatus() != 200) {
				System.out.println("Error al realizar la petición. Intente más tarde.");
			} else {
				ObjectMapper mapper = new ObjectMapper();
				List<EventoDTO> eventos = new ArrayList<EventoDTO>();
				eventos = mapper.readValue(respuesta.readEntity(String.class), new TypeReference<List<EventoDTO>>() {});
				if (eventos.size() == 0) {
					System.out.println("No hay eventos que procesar.");
				} else {
					for (EventoDTO eventoDTO : eventos) {
						Evento evento = eventoConverter.fromDTO(eventoDTO);
						eventoDAO.save(evento);
					}
					System.out.println("Total de eventos registrados: " + eventos.size());
				}
			}
		} catch (ProcessingException e) {
			System.out.println("No se pudo conectar al servidor. Intente más tarde.");
			System.out.println(e.getLocalizedMessage());
		} catch (JsonProcessingException e) {
			System.out.println("Error al parsear los datos. Intente más tarde.");
			System.out.println(e.getLocalizedMessage());
		}
		cliente.close();
	}

}
