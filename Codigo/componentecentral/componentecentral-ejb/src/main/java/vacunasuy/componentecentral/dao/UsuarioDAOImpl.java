package vacunasuy.componentecentral.dao;

import java.util.List;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import vacunasuy.componentecentral.entity.Usuario;

@Singleton
public class UsuarioDAOImpl implements IUsuarioDAO {

	@PersistenceContext(name = "LaboratorioTSE")
	private EntityManager em;
	
	@Override
	public List<Usuario> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario listarPorId(Long id) {
		return em.find(Usuario.class, id);
	}

	@Override
	public Usuario listarPorCorreo(String correo) {
		Query consulta = em.createQuery("SELECT u FROM Usuario u WHERE u.correo = :correo");
		consulta.setParameter("correo", correo);
		return (Usuario) consulta.getSingleResult();
	}

	@Override
	public Usuario crear(Usuario usuario) {
		em.persist(usuario);
		return usuario;
	}

}
