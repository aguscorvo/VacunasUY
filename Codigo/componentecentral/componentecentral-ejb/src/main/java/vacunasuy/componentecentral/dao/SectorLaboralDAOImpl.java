package vacunasuy.componentecentral.dao;

import java.util.List;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import vacunasuy.componentecentral.entity.SectorLaboral;

@Singleton
public class SectorLaboralDAOImpl implements ISectorLaboralDAO {

	@PersistenceContext(name = "LaboratorioTSE")
	private EntityManager em;
	
	@Override
	public List<SectorLaboral> listar() {
		Query consulta = em.createQuery("SELECT s FROM SectorLaboral s");
		return consulta.getResultList();
	}

	@Override
	public SectorLaboral listarPorId(Long id) {
		return em.find(SectorLaboral.class, id);
	}

	@Override
	public SectorLaboral listarRandom() {
		Query consulta = em.createNativeQuery("SELECT * FROM sectores_laborales OFFSET floor(random() * (SELECT COUNT(*) FROM sectores_laborales)) LIMIT 1", SectorLaboral.class);
		return (SectorLaboral) consulta.getResultList().stream().findFirst().orElse(null);
	}

}
