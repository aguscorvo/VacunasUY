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
import vacunasuy.componentecentral.exception.VacunasUyException;

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
	public UsuarioDTO crear(UsuarioCrearDTO usuarioDTO) throws VacunasUyException {
		/* Se valida que el correo sea único */
		Usuario usuario = usuarioConverter.fromCrearDTO(usuarioDTO);
		if(usuarioDAO.listarPorCorreo(usuario.getCorreo()) != null) {
			throw new VacunasUyException("El correo electrónico ya se encuentra en uso.", VacunasUyException.EXISTE_REGISTRO);
		}else {
			try {
				/* Se encripta la contraseña */
				if(usuario instanceof Administrador) {
					((Administrador) usuario).setPassword(BCrypt.withDefaults().hashToString(12, ((Administrador) usuario).getPassword().toCharArray()));
				} else if(usuario instanceof Autoridad) {
					((Autoridad) usuario).setPassword(BCrypt.withDefaults().hashToString(12, ((Autoridad) usuario).getPassword().toCharArray()));
				}
				return usuarioConverter.fromEntity(usuarioDAO.crear(usuario));
			} catch (Exception e) {
				throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
			}
		}
	}

	@Override
	public UsuarioDTO loginBackoffice(UsuarioLoginBackofficeDTO usuarioDTO) throws VacunasUyException {
		/* Se valida que exista el correo electrónico */
		Usuario usuario = usuarioDAO.listarPorCorreo(usuarioDTO.getCorreo());
		if (usuario == null) {
			throw new VacunasUyException("Usuario o contraseña incorrectos.", VacunasUyException.DATOS_INCORRECTOS);
		} else {
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
				throw new VacunasUyException("Usuario o contraseña incorrectos.", VacunasUyException.DATOS_INCORRECTOS);
			}
		}
	}
	
}
