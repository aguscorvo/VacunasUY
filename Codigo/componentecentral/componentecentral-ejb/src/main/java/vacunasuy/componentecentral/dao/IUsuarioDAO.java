package vacunasuy.componentecentral.dao;

import java.util.List;
import java.util.Optional;

import javax.ejb.Local;
import vacunasuy.componentecentral.entity.Usuario;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Local
public interface IUsuarioDAO {
	
	public List<Usuario> listar();
	public Usuario listarPorId(Long id);
	public Usuario listarPorCorreo(String correo) throws VacunasUyException;
	public Usuario crear(Usuario usuario);
	public Usuario editar(Usuario usuario);
	public void eliminar(Usuario usuario);
	
}
