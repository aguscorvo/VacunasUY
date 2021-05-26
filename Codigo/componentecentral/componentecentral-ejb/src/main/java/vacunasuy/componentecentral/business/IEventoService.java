package vacunasuy.componentecentral.business;

import java.util.List;
import javax.ejb.Local;
import vacunasuy.componentecentral.dto.EventoCrearDTO;
import vacunasuy.componentecentral.dto.EventoDTO;
import vacunasuy.componentecentral.dto.EventoPerifericoDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;
import vacunasuy.componentecentral.util.EstadoEvento;

@Local
public interface IEventoService {
	
	public List<EventoDTO> listar() throws VacunasUyException;
	public EventoDTO listarPorId(Long id) throws VacunasUyException;
	public List<EventoPerifericoDTO> listarPorEstado(EstadoEvento estado) throws VacunasUyException;
	public EventoDTO crear(EventoCrearDTO eventoDTO) throws VacunasUyException;
	public EventoDTO editar(Long id, EventoCrearDTO eventoDTO) throws VacunasUyException;
	public void eliminar(Long id) throws VacunasUyException;
	
}
