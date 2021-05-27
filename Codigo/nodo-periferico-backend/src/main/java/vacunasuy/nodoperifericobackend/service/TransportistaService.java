package vacunasuy.nodoperifericobackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
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
		System.out.println("##### Obtener eventos en estado INICIADO #####");
		Client cliente = ClientBuilder.newClient();
		try {
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

	/* Método que obtiene los eventos en estado TRANSITO */
	public void obtenerEventosTransito() {
		Client cliente = ClientBuilder.newClient();
		try {
			System.out.println("##### Obtener eventos en estado TRANSITO #####");
			String URL = "/eventos/listarPorEstado/transito";
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
	
	/* Método que cambia el estado de INICIADO a TRANSITO */
	public void procesarEventosIniciados() {
		System.out.println("##### Procesar eventos en estado INICIADO #####");
		try {
			/* Obtener un transportista aleatorio */
			Transportista transportista = obtenerTransportistaRandom();
			if(transportista == null) {
				System.out.println("No hay transportistas registrados para procesar el evento.");
			} else {
				List<Evento> eventos = eventoDAO.findByEstado("Iniciado");
				if(eventos.size() == 0) {
					System.out.println("No hay eventos inciados que procesar.");
				} else {
					for (Evento evento : eventos) {
						EventoDTO eventoDTO = EventoDTO.builder()
								.detalle("Comienza traslado a vacunatorio " + evento.getIdVacunatorio())
								.cantidad(evento.getCantidad())
								.estado("Transito")
								.idLote(evento.getIdLote())
								.idTransportista(transportista.getId())
								.idVacunatorio(evento.getIdVacunatorio())
								.build();
						/* Se envía al componente central */
						Client cliente = ClientBuilder.newClient();
						ObjectMapper mapper = new ObjectMapper();
						String eventoJSON = mapper.writeValueAsString(eventoDTO);						
						String URL = "/eventos/editar/" + evento.getId();
						System.out.println(baseURL+URL);
						WebTarget target = cliente.target(baseURL+URL);
						Response respuesta = target.request().accept(MediaType.APPLICATION_JSON).put(Entity.json(eventoJSON));
						if(respuesta.getStatus() != 200) {
							System.out.println("Error al procesar evento. Intente más tarde. STATUS: " + respuesta.getStatus());
							System.out.println(respuesta.readEntity(String.class));
						} else {
							/* Se elimina el evento */
							eventoDAO.deleteById(evento.getId());
							System.out.println("Evento procesado con éxito.");
						}
						respuesta.close();
						cliente.close();
					}
				}
			}
		} catch (ProcessingException e) {
			System.out.println("No se pudo conectar al servidor. Intente más tarde.");
			System.out.println(e.getLocalizedMessage());
		} catch (JsonProcessingException e) {
			System.out.println("Error al parsear los datos. Intente más tarde.");
			System.out.println(e.getLocalizedMessage());
		}
	}
	
	/* Método que cambia el estado de TRANSITO a RECIBIDO */
	public void procesarEventosTransito() {
		System.out.println("##### Procesar eventos en estado TRANSITO #####");
		try {	
			List<Evento> eventos = eventoDAO.findByEstado("Transito");
			if(eventos.size() == 0) {
				System.out.println("No hay eventos en tránsito que procesar.");
			} else {
				for (Evento evento : eventos) {
					EventoDTO eventoDTO = EventoDTO.builder()
							.detalle("Se recepcionan vacunas en el vacunatorio " + evento.getIdVacunatorio())
							.cantidad(evento.getCantidad())
							.estado("Recibido")
							.idLote(evento.getIdLote())
							.idTransportista(evento.getIdTransportista())
							.idVacunatorio(evento.getIdVacunatorio())
							.build();
					/* Se envía al componente central */
					Client cliente = ClientBuilder.newClient();
					ObjectMapper mapper = new ObjectMapper();
					String eventoJSON = mapper.writeValueAsString(eventoDTO);						
					String URL = "/eventos/editar/" + evento.getId();
					System.out.println(baseURL+URL);
					WebTarget target = cliente.target(baseURL+URL);
					Response respuesta = target.request().accept(MediaType.APPLICATION_JSON).put(Entity.json(eventoJSON));
					if(respuesta.getStatus() != 200) {
						System.out.println("Error al procesar evento. Intente más tarde. STATUS: " + respuesta.getStatus());
						System.out.println(respuesta.readEntity(String.class));
					} else {
						/* Se elimina el evento */
						eventoDAO.deleteById(evento.getId());
						System.out.println("Evento procesado con éxito.");
					}
					respuesta.close();
					cliente.close();
				}
			}
		} catch (ProcessingException e) {
			System.out.println("No se pudo conectar al servidor. Intente más tarde.");
			System.out.println(e.getLocalizedMessage());
		} catch (JsonProcessingException e) {
			System.out.println("Error al parsear los datos. Intente más tarde.");
			System.out.println(e.getLocalizedMessage());
		}
	}
	
	/* Método que obtiene un transportista aleatorio para asignar al evento */
	public Transportista obtenerTransportistaRandom() {
		List<Transportista> transportistas = transportistaDAO.findAll();
		if(transportistas.size() == 0) {
			return null;
		} else {
			Random random = new Random();
			return transportistas.get(random.nextInt(transportistas.size()));
		}
	}
	
}
