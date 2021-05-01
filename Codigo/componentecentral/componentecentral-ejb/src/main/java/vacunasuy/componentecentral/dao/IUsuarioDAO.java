package vacunasuy.componentecentral.dao;

import java.util.List;
import javax.ejb.Local;
import vacunasuy.componentecentral.entity.Usuario;

@Local
public interface IUsuarioDAO {
	
	public List<Usuario> listar();
	public Usuario listarPorId(Long id);
	public Usuario listarPorCorreo(String correo);
	public Usuario crear(Usuario usuario);
	
}
