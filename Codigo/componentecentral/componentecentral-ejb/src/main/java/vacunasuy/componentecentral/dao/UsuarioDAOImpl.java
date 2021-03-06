package vacunasuy.componentecentral.dao;

import java.util.List;
import java.util.Optional;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import vacunasuy.componentecentral.entity.Usuario;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Singleton
public class UsuarioDAOImpl implements IUsuarioDAO {

	@PersistenceContext(name = "LaboratorioTSE")
	private EntityManager em;
	
	@Override
	public List<Usuario> listar() {
		Query consulta = em.createQuery("SELECT u FROM Usuario u");
		return consulta.getResultList();
	}

	@Override
	public Usuario listarPorId(Long id) {
		return em.find(Usuario.class, id);
	}

	@Override
	public Usuario listarPorCorreo(String correo) {
		Query consulta = em.createQuery("SELECT u FROM Usuario u WHERE u.correo = :correo");
		consulta.setParameter("correo", correo);
		return (Usuario) consulta.getResultList().stream().findFirst().orElse(null);
	}
	
	@Override
	public Usuario listarPorDocumento(String documento) {
		Query consulta = em.createQuery("SELECT u FROM Usuario u WHERE u.documento = :documento");
		consulta.setParameter("documento", documento);
		return (Usuario) consulta.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public Usuario crear(Usuario usuario) {
		em.persist(usuario);
		return usuario;
	}
	
	@Override
	public Usuario editar(Usuario usuario) {
		em.persist(usuario);
		return usuario;
	}
	
	public void eliminar(Usuario usuario) {
		em.remove(usuario);
	}

}
