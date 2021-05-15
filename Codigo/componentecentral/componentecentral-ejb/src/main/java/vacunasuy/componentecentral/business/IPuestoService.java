package vacunasuy.componentecentral.business;

import java.util.List;

import javax.ejb.Local;

import vacunasuy.componentecentral.dto.PuestoCrearDTO;
import vacunasuy.componentecentral.dto.PuestoDTO;
import vacunasuy.componentecentral.dto.PuestoMinDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Local
public interface IPuestoService {
	
	public List<PuestoDTO> listar() throws VacunasUyException;
	public PuestoDTO listarPorId(Long id) throws VacunasUyException;
	public PuestoDTO listarPorNumero(int numero) throws VacunasUyException;
	public PuestoMinDTO crear(PuestoCrearDTO puestoDTO) throws VacunasUyException;
	public PuestoDTO editar(Long id, PuestoCrearDTO puestoDTO) throws VacunasUyException;
	public void eliminar(Long id) throws VacunasUyException;

}
