package vacunasuy.componentecentral.business;

import java.util.List;

import javax.ejb.Local;

import vacunasuy.componentecentral.dto.VacunatorioCercanoDTO;
import vacunasuy.componentecentral.dto.VacunatorioCrearDTO;
import vacunasuy.componentecentral.dto.VacunatorioDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Local
public interface IVacunatorioService {
	
	public List<VacunatorioDTO> listar() throws VacunasUyException;
	public VacunatorioDTO listarPorId(Long id) throws VacunasUyException;
	public VacunatorioDTO crear(VacunatorioCrearDTO vacunatorioDTO) throws VacunasUyException;
	public VacunatorioDTO editar(Long id, VacunatorioCrearDTO vacunatorioDTO) throws VacunasUyException;
	public void eliminar(Long id) throws VacunasUyException;
	
	public List<VacunatorioDTO> listarVacunatoriosCercanos(VacunatorioCercanoDTO vacunatorioDTO) throws VacunasUyException;
	
	//agregarEvento
	//agregarActoVacunal
	
}
