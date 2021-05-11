package vacunasuy.componentecentral.business;

import java.time.LocalDate;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import vacunasuy.componentecentral.converter.ActoVacunalConverter;
import vacunasuy.componentecentral.dao.IActoVacunalDAO;
import vacunasuy.componentecentral.dao.IPlanVacunacionDAO;
import vacunasuy.componentecentral.dto.ActoVacunalCrearDTO;
import vacunasuy.componentecentral.dto.ActoVacunalDTO;
import vacunasuy.componentecentral.entity.ActoVacunal;
import vacunasuy.componentecentral.entity.PlanVacunacion;
import vacunasuy.componentecentral.exception.VacunasUyException;


@Stateless
public class ActoVacunalServiceImpl implements IActoVacunalService {

    @EJB
    private IActoVacunalDAO actoVacunalDAO;
    
    @EJB
    private IPlanVacunacionDAO planVacunacionDAO;
    
    @EJB
    private ActoVacunalConverter actoVacunalConverter;
    
    @Override
    public List<ActoVacunalDTO> listar() throws VacunasUyException{
    	try {
    		return actoVacunalConverter.fromEntity(actoVacunalDAO.listar());
    	}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
    }
    
    @Override
	public ActoVacunalDTO listarPorId(Long id) throws VacunasUyException{
		//se valida que exista el acto vacunal
    	ActoVacunal actoVacunal = actoVacunalDAO.listarPorId(id);
    	if(actoVacunal==null) throw new VacunasUyException("El acto vacunal indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
    	try {
			return actoVacunalConverter.fromEntity(actoVacunal);
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}    	
	}
	
	@Override
	public ActoVacunalDTO crear(ActoVacunalCrearDTO actoVacunalDTO) throws VacunasUyException{
		// se valida que el plan de vacunacion exista
		PlanVacunacion planVacunacion = planVacunacionDAO.listarPorId(actoVacunalDTO.getPlanVacunacion());
		if(planVacunacion==null) throw new VacunasUyException("El plan de vacunación indicado no existe.",
				VacunasUyException.NO_EXISTE_REGISTRO);
		try {
			ActoVacunal actoVacunal = actoVacunalConverter.fromCrearDTO(actoVacunalDTO);
			actoVacunal.setPlanVacunacion(planVacunacion);
			return actoVacunalConverter.fromEntity(actoVacunalDAO.crear(actoVacunal));
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
	@Override
	public ActoVacunalDTO editar(Long id, ActoVacunalCrearDTO actoVacunalDTO) throws VacunasUyException{
		//se verifica que el acto vacunal exista
		ActoVacunal actoVacunal = actoVacunalDAO.listarPorId(id);
    	if(actoVacunal==null) throw new VacunasUyException("El acto vacunal indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		// se valida que el plan de vacunacion exista
    	PlanVacunacion planVacunacion = planVacunacionDAO.listarPorId(actoVacunalDTO.getPlanVacunacion());
		if(planVacunacion==null) throw new VacunasUyException("El plan de vacunación indicado no existe.",
				VacunasUyException.NO_EXISTE_REGISTRO);    	
		try {
    		actoVacunal.setFecha(LocalDate.parse(actoVacunalDTO.getFecha()));
			actoVacunal.setPlanVacunacion(planVacunacion);
    		return actoVacunalConverter.fromEntity(actoVacunalDAO.editar(actoVacunal));
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}    	
	}
	
	@Override
	public void eliminar(Long id) throws VacunasUyException{
		//se valida que exista el acto vacunal
		ActoVacunal actoVacunal = actoVacunalDAO.listarPorId(id);
    	if(actoVacunal==null) throw new VacunasUyException("El acto vacunal indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
    	try {
			actoVacunalDAO.eliminar(actoVacunal);
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}    		
	}

}
