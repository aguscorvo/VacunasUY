package vacunasuy.componentecentral.business;

import java.util.List;
import javax.ejb.Local;
import vacunasuy.componentecentral.dto.UsuarioCrearDTO;
import vacunasuy.componentecentral.dto.UsuarioDTO;
import vacunasuy.componentecentral.dto.UsuarioLoginBackofficeDTO;
import vacunasuy.componentecentral.entity.Usuario;

@Local
public interface IUsuarioService {
	
	public List<Usuario> listar();
	public Usuario listarPorId(Long id);
	public Usuario listarPorCorreo(String correo);
	public UsuarioDTO crear(UsuarioCrearDTO usuarioDTO);
	public UsuarioDTO loginBackoffice(UsuarioLoginBackofficeDTO usuarioDTO);
	
}
