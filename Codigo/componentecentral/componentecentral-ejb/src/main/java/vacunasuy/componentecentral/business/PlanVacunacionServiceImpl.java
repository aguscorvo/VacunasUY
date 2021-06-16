package vacunasuy.componentecentral.business;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import vacunasuy.componentecentral.converter.PlanVacunacionConverter;
import vacunasuy.componentecentral.dao.IPlanVacunacionDAO;
import vacunasuy.componentecentral.dao.ISectorLaboralDAO;
import vacunasuy.componentecentral.dao.IVacunaDAO;
import vacunasuy.componentecentral.dto.PlanVacunacionCrearDTO;
import vacunasuy.componentecentral.dto.PlanVacunacionDTO;
import vacunasuy.componentecentral.entity.PlanVacunacion;
import vacunasuy.componentecentral.entity.SectorLaboral;
import vacunasuy.componentecentral.entity.Vacuna;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Stateless
public class PlanVacunacionServiceImpl implements IPlanVacunacionService {

	@EJB
	private IPlanVacunacionDAO planVacunacionDAO;
	
	@EJB
	private ISectorLaboralDAO sectorLaboralDAO;
	
	@EJB
	private IVacunaDAO vacunaDAO;
	
	@EJB
	private PlanVacunacionConverter planVacunacionConverter;
	
	@Override
	public List<PlanVacunacionDTO> listar() throws VacunasUyException {
		try {
			return planVacunacionConverter.fromEntity(planVacunacionDAO.listar());
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
	@Override
	public List<PlanVacunacionDTO> listarPlanesVigentes() throws VacunasUyException {
		try {
			return planVacunacionConverter.fromEntity(planVacunacionDAO.listarPlanesVigentes());
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}

	@Override
	public PlanVacunacionDTO listarPorId(Long id) throws VacunasUyException {
		try {
			PlanVacunacion plan = planVacunacionDAO.listarPorId(id);
			if(plan == null) throw new VacunasUyException("No existe el plan indicado.", VacunasUyException.NO_EXISTE_REGISTRO);
			return planVacunacionConverter.fromEntity(plan);
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}

	@Override
	public PlanVacunacionDTO crear(PlanVacunacionCrearDTO planVacunacionDTO) throws VacunasUyException {
		try {
			PlanVacunacion plan = planVacunacionConverter.fromCrearDTO(planVacunacionDTO);
			/* Se agregan los sectores */
			List<SectorLaboral> sectores = new ArrayList<SectorLaboral>();
			for (Long id : planVacunacionDTO.getSectores()) {
				SectorLaboral sector = sectorLaboralDAO.listarPorId(id);
				if(sector != null) {
					sectores.add(sector);
				}
			}
			plan.setSectores(sectores);
			/* Se agrega la vacuna */
			Vacuna vacuna = vacunaDAO.listarPorId(planVacunacionDTO.getIdVacuna());
			if(vacuna == null) throw new VacunasUyException("No existe la vacuna indicada.", VacunasUyException.NO_EXISTE_REGISTRO);
			plan.setVacuna(vacuna);
			planVacunacionDAO.crear(plan);
			return planVacunacionConverter.fromEntity(plan);
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}

	@Override
	public PlanVacunacionDTO editar(Long id, PlanVacunacionCrearDTO planVacunacionDTO) throws VacunasUyException {
		try {
			PlanVacunacion plan = planVacunacionDAO.listarPorId(id);
			if(plan == null) throw new VacunasUyException("No existe el plan indicado.", VacunasUyException.NO_EXISTE_REGISTRO);
			Vacuna vacuna = vacunaDAO.listarPorId(planVacunacionDTO.getIdVacuna());
			if(vacuna == null) throw new VacunasUyException("No existe la vacuna indicada.", VacunasUyException.NO_EXISTE_REGISTRO);
			plan.setVacuna(vacuna);
			/* Se agregan los sectores */
			List<SectorLaboral> sectores = new ArrayList<SectorLaboral>();
			for (Long idS : planVacunacionDTO.getSectores()) {
				SectorLaboral sector = sectorLaboralDAO.listarPorId(idS);
				if(sector != null) {
					sectores.add(sector);
				}
			}
			plan.setSectores(sectores);
			plan.setEdadMinima(planVacunacionDTO.getEdadMinima());
			plan.setEdadMaxima(planVacunacionDTO.getEdadMaxima());
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			plan.setFechaFin(LocalDateTime.parse(planVacunacionDTO.getFechaFin(), formato));
			plan.setFechaInicio(LocalDateTime.parse(planVacunacionDTO.getFechaInicio(), formato));
			return planVacunacionConverter.fromEntity(planVacunacionDAO.editar(plan));
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}

	@Override
	public void eliminar(Long id) throws VacunasUyException {
		try {
			PlanVacunacion plan = planVacunacionDAO.listarPorId(id);
			if(plan == null) throw new VacunasUyException("No existe el plan indicado.", VacunasUyException.NO_EXISTE_REGISTRO);
			planVacunacionDAO.eliminar(plan);
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}

}
