package vacunasuy.componentecentral.business;

import java.util.List;

import javax.ejb.Local;

import vacunasuy.componentecentral.dto.AgendaCrearDTO;
import vacunasuy.componentecentral.dto.AgendaDTO;
import vacunasuy.componentecentral.dto.AgendaMinDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Local
public interface IAgendaService {

	public List<AgendaDTO> listar() throws VacunasUyException;
	public AgendaDTO listarPorId(Long id) throws VacunasUyException;
	public AgendaMinDTO crear(AgendaCrearDTO agendaDTO) throws VacunasUyException;
	public AgendaDTO editar(Long id, AgendaCrearDTO agendaDTO) throws VacunasUyException;
	public void eliminar(Long id) throws VacunasUyException;
	
}
