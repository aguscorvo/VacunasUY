package vacunasuy.componentecentral.business;

import java.util.List;
import javax.ejb.Local;
import vacunasuy.componentecentral.dto.UsuarioCrearDTO;
import vacunasuy.componentecentral.dto.UsuarioDTO;
import vacunasuy.componentecentral.dto.UsuarioLoginBackofficeDTO;
import vacunasuy.componentecentral.dto.UsuarioLoginExitosoDTO;
import vacunasuy.componentecentral.entity.Usuario;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Local
public interface IUsuarioService {
	
	public List<UsuarioDTO> listar() throws VacunasUyException;
	public Usuario listarPorId(Long id);
	public Usuario listarPorCorreo(String correo);
	public UsuarioDTO crear(UsuarioCrearDTO usuarioDTO) throws VacunasUyException;
	public UsuarioDTO editar(Long id, UsuarioCrearDTO usuarioDTO) throws VacunasUyException;
	public void eliminar(Long id) throws VacunasUyException;
	public UsuarioLoginExitosoDTO loginBackoffice(UsuarioLoginBackofficeDTO usuarioDTO) throws VacunasUyException;
}
