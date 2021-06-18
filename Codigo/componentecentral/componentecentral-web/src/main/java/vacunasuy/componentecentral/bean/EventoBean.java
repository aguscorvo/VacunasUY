package vacunasuy.componentecentral.bean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.jboss.logging.Logger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vacunasuy.componentecentral.business.IEventoService;
import vacunasuy.componentecentral.business.ILoteService;
import vacunasuy.componentecentral.business.IVacunatorioService;
import vacunasuy.componentecentral.dto.EventoCrearDTO;
import vacunasuy.componentecentral.dto.EventoDTO;
import vacunasuy.componentecentral.dto.LoteDTO;
import vacunasuy.componentecentral.dto.VacunatorioDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Named("EventoBean")
@RequestScoped
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(EventoBean.class);
	
	private Long id;
	private String fecha;
	private String detalle;
	private Long cantidad;
	private String estado;
	private Long idLote;
	private Long idVacunatorio;
	private List<EventoDTO> eventos;
	private List<VacunatorioDTO> vacunatorios;
	private List<LoteDTO> lotes;
	
	@EJB
	private IEventoService eventoService;
	
	@EJB
	private IVacunatorioService vacunatorioService;
	
	@EJB
	private ILoteService loteService;
	
	@PostConstruct
	public void init() {
		try {
			eventos = eventoService.listar();
			vacunatorios = vacunatorioService.listar();
			lotes = loteService.listar();
		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
		}
	}
	
	public void addEvento() {
		try {
			EventoCrearDTO evento = EventoCrearDTO.builder()
					.fecha(fecha)
					.detalle(detalle)
					.cantidad(cantidad)
					.estado(estado)
					.idLote(idLote)
					.idTransportista(null)
					.idVacunatorio(idVacunatorio)
					.build();
			eventoService.crear(evento);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Evento creado con éxito.", null));
		} catch (VacunasUyException e) {
			logger.error(e.getLocalizedMessage());
		} finally {
			clearParam();
			try {
				eventos = eventoService.listar();
				vacunatorios = vacunatorioService.listar();
				lotes = loteService.listar();
			} catch (VacunasUyException e) {
				logger.error(e.getLocalizedMessage());
			}
		}
	}
	
	public void updEvento() {
		try {
			EventoCrearDTO evento = EventoCrearDTO.builder()
					.fecha(fecha)
					.detalle(detalle)
					.cantidad(cantidad)
					.estado(estado)
					.idLote(idLote)
					.idTransportista(null)
					.idVacunatorio(idVacunatorio)
					.build();
			eventoService.editar(id, evento);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Evento editado con éxito.", null));
		} catch (VacunasUyException e) {
			logger.error(e.getLocalizedMessage());
		} finally {
			clearParam();
			try {
				eventos = eventoService.listar();
				vacunatorios = vacunatorioService.listar();
				lotes = loteService.listar();
			} catch (VacunasUyException e) {
				logger.error(e.getLocalizedMessage());
			}
		}
	}
	
	public void delEvento() {
		try {
			eventoService.eliminar(id);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Evento eliminado con éxito.", null));
		} catch (VacunasUyException e) {
			logger.error(e.getLocalizedMessage());
		} finally {
			clearParam();
			try {
				eventos = eventoService.listar();
				vacunatorios = vacunatorioService.listar();
				lotes = loteService.listar();
			} catch (VacunasUyException e) {
				logger.error(e.getLocalizedMessage());
			}
		}
	}
	
	private void clearParam() {
		this.id = null;
		this.fecha = null;
		this.detalle = null;
		this.cantidad = null;
		this.estado = null;
		this.idLote = null;
		this.idVacunatorio = null;
		this.eventos = null;
		this.vacunatorios = null;
		this.lotes = null;
	}
	
}
