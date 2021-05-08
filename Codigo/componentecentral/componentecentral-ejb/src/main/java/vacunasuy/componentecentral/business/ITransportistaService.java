package vacunasuy.componentecentral.business;

import java.util.List;

import javax.ejb.Local;

import vacunasuy.componentecentral.dto.TransportistaCrearDTO;
import vacunasuy.componentecentral.dto.TransportistaDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Local
public interface ITransportistaService {

	public List<TransportistaDTO> listar( ) throws VacunasUyException;
	public TransportistaDTO listarPorId(Long id) throws VacunasUyException;
	public TransportistaDTO crear(TransportistaCrearDTO transportistaDTO) throws VacunasUyException;
	public TransportistaDTO editar(Long id, TransportistaCrearDTO transportistaDTO) throws VacunasUyException;
	public void eliminar (Long id) throws VacunasUyException;
		
}
