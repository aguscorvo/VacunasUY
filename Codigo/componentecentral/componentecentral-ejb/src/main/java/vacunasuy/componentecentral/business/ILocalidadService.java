package vacunasuy.componentecentral.business;

import java.util.List;

import javax.ejb.Local;

import vacunasuy.componentecentral.dto.LocalidadCrearDTO;
import vacunasuy.componentecentral.dto.LocalidadDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Local
public interface ILocalidadService {

	  public List<LocalidadDTO> listar() throws VacunasUyException;
	  public LocalidadDTO listarPorId(Long id) throws VacunasUyException;
	  public LocalidadDTO crear(LocalidadCrearDTO localidadDTO) throws VacunasUyException;
	  public LocalidadDTO editar(Long id, LocalidadCrearDTO localidadDTO) throws VacunasUyException;
	  public void eliminar (Long id) throws VacunasUyException;

	
}
