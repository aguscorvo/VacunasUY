package vacunasuy.componentecentral.business;

import java.util.List;

import javax.ejb.Local;

import vacunasuy.componentecentral.dto.DepartamentoCrearDTO;
import vacunasuy.componentecentral.dto.DepartamentoDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Local
public interface IDepartamentoService {

	public List<DepartamentoDTO> listar() throws VacunasUyException;
	public DepartamentoDTO listarPorId(Long id) throws VacunasUyException;
	public DepartamentoDTO crear(DepartamentoCrearDTO departamentoDTO) throws VacunasUyException;
	public DepartamentoDTO editar(Long id, DepartamentoCrearDTO departamentoDTO) throws VacunasUyException;
	public void eliminar(Long id) throws VacunasUyException;
	
}
