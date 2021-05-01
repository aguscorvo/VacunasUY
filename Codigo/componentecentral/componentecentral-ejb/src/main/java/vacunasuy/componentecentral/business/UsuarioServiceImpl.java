package vacunasuy.componentecentral.business;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import at.favre.lib.crypto.bcrypt.BCrypt;
import vacunasuy.componentecentral.converter.UsuarioConverter;
import vacunasuy.componentecentral.dao.IUsuarioDAO;
import vacunasuy.componentecentral.dto.UsuarioCrearDTO;
import vacunasuy.componentecentral.dto.UsuarioDTO;
import vacunasuy.componentecentral.dto.UsuarioLoginBackofficeDTO;
import vacunasuy.componentecentral.entity.Administrador;
import vacunasuy.componentecentral.entity.Autoridad;
import vacunasuy.componentecentral.entity.Usuario;

@Stateless
public class UsuarioServiceImpl implements IUsuarioService {
	
	@EJB
	private IUsuarioDAO usuarioDAO;
	
	@EJB
	private UsuarioConverter usuarioConverter;

	@Override
	public List<Usuario> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario listarPorId(Long id) {
		return usuarioDAO.listarPorId(id);
	}

	@Override
	public Usuario listarPorCorreo(String correo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UsuarioDTO crear(UsuarioCrearDTO usuarioDTO) {
		Usuario usuario = usuarioConverter.fromCrearDTO(usuarioDTO);
		/* Se encripta la contraseña */
		if(usuario instanceof Administrador) {
			((Administrador) usuario).setPassword(BCrypt.withDefaults().hashToString(12, ((Administrador) usuario).getPassword().toCharArray()));
		} else if(usuario instanceof Autoridad) {
			((Autoridad) usuario).setPassword(BCrypt.withDefaults().hashToString(12, ((Autoridad) usuario).getPassword().toCharArray()));
		}
		return usuarioConverter.fromEntity(usuarioDAO.crear(usuario));
	}

	@Override
	public UsuarioDTO loginBackoffice(UsuarioLoginBackofficeDTO usuarioDTO) {
		Usuario usuario = usuarioDAO.listarPorCorreo(usuarioDTO.getCorreo());
		if (usuario != null) {
			/* Se verifica que la contraseña sea válida */
			BCrypt.Result resultado = null;
			if(usuario instanceof Administrador) {
				resultado = BCrypt.verifyer().verify(usuarioDTO.getPassword().toCharArray(), ((Administrador) usuario).getPassword());
			} else if(usuario instanceof Autoridad) {
				resultado = BCrypt.verifyer().verify(usuarioDTO.getPassword().toCharArray(), ((Autoridad) usuario).getPassword());
			}
			if(resultado.verified) {
				return usuarioConverter.fromEntity(usuario);
			} else {
				return null;
			}
			
		} else {
			return null;
		}
	}
	
}
