package vacunasuy.componentecentral.business;

import java.util.List;

import javax.ejb.Local;

import vacunasuy.componentecentral.dto.ActoVacunalCrearDTO;
import vacunasuy.componentecentral.dto.ActoVacunalDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Local
public interface IActoVacunalService {
	
	public List<ActoVacunalDTO> listar() throws VacunasUyException;
	public ActoVacunalDTO listarPorId(Long id) throws VacunasUyException;
	public ActoVacunalDTO crear(ActoVacunalCrearDTO actoVacunalDTO) throws VacunasUyException;
	public ActoVacunalDTO editar(Long id, ActoVacunalCrearDTO actoVacunalDTO) throws VacunasUyException;
	public void eliminar(Long id) throws VacunasUyException;

}
