package vacunasuy.componentecentral.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import vacunasuy.componentecentral.converter.UsuarioConverter;
import vacunasuy.componentecentral.dao.IRolDAO;
import vacunasuy.componentecentral.dao.IUsuarioDAO;
import vacunasuy.componentecentral.dto.RespuestaUserInfoDTO;
import vacunasuy.componentecentral.dto.UsuarioCrearDTO;
import vacunasuy.componentecentral.dto.UsuarioDTO;
import vacunasuy.componentecentral.dto.UsuarioLoginBackofficeDTO;
import vacunasuy.componentecentral.dto.UsuarioLoginExitosoDTO;
import vacunasuy.componentecentral.entity.Rol;
import vacunasuy.componentecentral.entity.Usuario;
import vacunasuy.componentecentral.exception.VacunasUyException;
import vacunasuy.componentecentral.util.Constantes;

@Stateless
public class UsuarioServiceImpl implements IUsuarioService {
	
	@EJB
	private IUsuarioDAO usuarioDAO;
	
	@EJB
	private IRolDAO rolDAO;
	
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
				usuario.setPassword(BCrypt.withDefaults().hashToString(12, usuario.getPassword().toCharArray()));
				/* Se agregan los roles */
				List<Rol> roles = new ArrayList<>();
				for (Long idRol : usuarioDTO.getRoles()) {
					Rol rol = rolDAO.listarPorId(idRol);
					if(rol != null) {
						roles.add(rol);
					}
				}
				usuario.setRoles(roles);
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
			resultado = BCrypt.verifyer().verify(usuarioDTO.getPassword().toCharArray(), usuario.getPassword());
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
		return Jwts.builder()
				.setSubject(Long.toString(usuario.getId()))
				.claim("roles", usuario.getRoles())
				.setIssuedAt(ahora)
				.setExpiration(expiracion)
				.signWith(SignatureAlgorithm.HS512, Constantes.JWT_KEY)
				.compact();
	}

	@Override
	public UsuarioLoginExitosoDTO loginGubUy(RespuestaUserInfoDTO usuarioDTO) throws VacunasUyException {
		/* Verifico si el usuario se encuentra registrado */
		Usuario usuario = usuarioDAO.listarPorDocumento(usuarioDTO.getNumero_documento());
		if(usuario == null) {
			/* Debo registrarlo */
			usuario = new Usuario();
			usuario.setNombre(usuarioDTO.getPrimer_nombre());
			usuario.setApellido(usuarioDTO.getPrimer_apellido());
			usuario.setDocumento(usuarioDTO.getNumero_documento());
			usuario.setCorreo(usuarioDTO.getEmail());
			/* Le agrego el rol de ciudadano */
			Rol rol = rolDAO.listarPorId(4L);
			usuario.getRoles().add(rol);
			usuario = usuarioDAO.crear(usuario);
		}else {
			/* Verifico si el usuario tiene rol ciudadano */
			boolean tieneRol = false;
			for (Rol rol : usuario.getRoles()) {
				if(rol.getNombre().equalsIgnoreCase("Ciudadano")) {
					tieneRol = true;
				}
			}
			if(!tieneRol) {
				/* Le agrego el rol de ciudadano */
				Rol rol = rolDAO.listarPorId(4L);
				usuario.getRoles().add(rol);
				usuario = usuarioDAO.editar(usuario);
			}
		}
		/* Creo un nuevo inicio de sesión */
		String token = crearJsonWebToken(usuario);
		return usuarioConverter.fromLogin(usuario, token);
	}
	
}
