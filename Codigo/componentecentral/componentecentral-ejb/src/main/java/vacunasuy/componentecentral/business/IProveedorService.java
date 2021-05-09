package vacunasuy.componentecentral.business;

import java.util.List;

import javax.ejb.Local;

import vacunasuy.componentecentral.dto.ProveedorCrearDTO;
import vacunasuy.componentecentral.dto.ProveedorDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Local
public interface IProveedorService {

	public List<ProveedorDTO> listar() throws VacunasUyException;
	public ProveedorDTO listarPorId(Long id) throws VacunasUyException;
	public ProveedorDTO crear(ProveedorCrearDTO proveedorDTO) throws VacunasUyException;
	public ProveedorDTO editar(Long id, ProveedorCrearDTO proveedorDTO) throws VacunasUyException;
	public void eliminar(Long id) throws VacunasUyException;
		
}
