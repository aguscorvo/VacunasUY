package vacunasuy.componentecentral.business;

import java.util.List;
import javax.ejb.Local;
import vacunasuy.componentecentral.dto.LoteCrearDTO;
import vacunasuy.componentecentral.dto.LoteDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Local
public interface ILoteService {

	public List<LoteDTO> listar() throws VacunasUyException;
	public LoteDTO listarPorId(Long id) throws VacunasUyException;
	public LoteDTO crear(LoteCrearDTO loteDTO) throws VacunasUyException;
	public LoteDTO editar(Long id, LoteCrearDTO loteDTO) throws VacunasUyException;
	public void eliminar(Long id) throws VacunasUyException;
	
}
