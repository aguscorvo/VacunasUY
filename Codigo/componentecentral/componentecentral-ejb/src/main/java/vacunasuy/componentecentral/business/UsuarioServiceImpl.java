package vacunasuy.componentecentral.business;

import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import vacunasuy.componentecentral.converter.UsuarioConverter;
import vacunasuy.componentecentral.dao.IUsuarioDAO;
import vacunasuy.componentecentral.dto.UsuarioCrearDTO;
import vacunasuy.componentecentral.dto.UsuarioDTO;
import vacunasuy.componentecentral.dto.UsuarioLoginBackofficeDTO;
import vacunasuy.componentecentral.dto.UsuarioLoginExitosoDTO;
import vacunasuy.componentecentral.entity.Administrador;
import vacunasuy.componentecentral.entity.Autoridad;
import vacunasuy.componentecentral.entity.Usuario;
import vacunasuy.componentecentral.exception.VacunasUyException;
import vacunasuy.componentecentral.util.Constantes;

@Stateless
public class UsuarioServiceImpl implements IUsuarioService {
	
	@EJB
	private IUsuarioDAO usuarioDAO;
	
	@EJB
	private UsuarioConverter usuarioConverter;

	@Override
	public List<UsuarioDTO> listar() throws VacunasUyException {
		try {
			return usuarioConverter.fromEntity(usuarioDAO.listar());
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
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
	
	/* Este método editar no es para cambiar la contraseña, se debe implementar un servicio particular para ello. */
	@Override
	public UsuarioDTO editar(Long id, UsuarioCrearDTO usuarioDTO) throws VacunasUyException {
		/* Se valida que exista el usuario */
		Usuario usuario = usuarioDAO.listarPorId(id);
		if(usuario == null) {
			throw new VacunasUyException("El usuario indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		} else if(!usuario.getCorreo().equalsIgnoreCase(usuarioDTO.getCorreo())) {
			/* Usuario desea actualizar su correo electrónico, se debe verificar que no esté registrado */
			if(usuarioDAO.listarPorCorreo(usuarioDTO.getCorreo()) != null) {
				throw new VacunasUyException("El correo electrónico ya se encuentra en uso.", VacunasUyException.EXISTE_REGISTRO);
			}
		}
		try {
			/* Se editar los atributos */
			usuario.setNombre(usuarioDTO.getNombre());
			usuario.setApellido(usuarioDTO.getApellido());
			usuario.setCorreo(usuarioDTO.getCorreo());
			return usuarioConverter.fromEntity(usuarioDAO.editar(usuario));
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
	@Override
	public void eliminar(Long id) throws VacunasUyException {
		/* Se valida que exista el usuario */
		Usuario usuario = usuarioDAO.listarPorId(id);
		if(usuario == null) {
			throw new VacunasUyException("El usuario indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		} else {
			try {
				usuarioDAO.eliminar(usuario);
			} catch (Exception e) {
				throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
			}
		}
	}
	
	@Override
	public UsuarioLoginExitosoDTO loginBackoffice(UsuarioLoginBackofficeDTO usuarioDTO) throws VacunasUyException {
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
				String token = crearJsonWebToken(usuario);
				return usuarioConverter.fromLogin(usuario, token);
			} else {
				throw new VacunasUyException("Usuario o contraseña incorrectos.", VacunasUyException.DATOS_INCORRECTOS);
			}
		}
	}
	
	/* Función auxiliar para generar un JWT */
	private String crearJsonWebToken(Usuario usuario) {
		Date ahora = new Date();
		/* 1 horas de validez */
		Date expiracion = new Date(ahora.getTime() + (1000*60*60));
		String rol = null;
		if(usuario instanceof Administrador) {
			rol = "Administrador";
		}else if(usuario instanceof Autoridad) {
			rol = "Autoridad";
		}
		return Jwts.builder()
				.setSubject(Long.toString(usuario.getId()))
				.claim("rol", rol)
				.setIssuedAt(ahora)
				.setExpiration(expiracion)
				.signWith(SignatureAlgorithm.HS512, Constantes.JWT_KEY)
				.compact();
	}
	
}
