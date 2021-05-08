package vacunasuy.componentecentral.business;

import java.util.List;

import javax.ejb.Local;

import vacunasuy.componentecentral.dto.UsuarioCrearDTO;
import vacunasuy.componentecentral.dto.UsuarioDTO;
import vacunasuy.componentecentral.dto.VacunaCrearDTO;
import vacunasuy.componentecentral.dto.VacunaDTO;
import vacunasuy.componentecentral.entity.Vacuna;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Local
public interface IVacunaService {
	
	public List<VacunaDTO> listar() throws VacunasUyException;
	public Vacuna listarPorId(Long id);
	public VacunaDTO crear(VacunaCrearDTO vacunaCrearDTO) throws VacunasUyException;
	public VacunaDTO editar(Long id, VacunaCrearDTO vacunaCrearDTO) throws VacunasUyException;
	public void eliminar(Long id) throws VacunasUyException;

}