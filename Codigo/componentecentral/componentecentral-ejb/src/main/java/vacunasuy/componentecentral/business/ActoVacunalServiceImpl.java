package vacunasuy.componentecentral.business;

import java.time.LocalDate;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import vacunasuy.componentecentral.converter.ActoVacunalConverter;
import vacunasuy.componentecentral.dao.IActoVacunalDAO;
import vacunasuy.componentecentral.dao.IEnfermedadDAO;
import vacunasuy.componentecentral.dao.IPlanVacunacionDAO;
import vacunasuy.componentecentral.dao.IUsuarioDAO;
import vacunasuy.componentecentral.dto.ActoVacunalCertificadoDTO;
import vacunasuy.componentecentral.dto.ActoVacunalCrearDTO;
import vacunasuy.componentecentral.dto.ActoVacunalDTO;
import vacunasuy.componentecentral.entity.ActoVacunal;
import vacunasuy.componentecentral.entity.Enfermedad;
import vacunasuy.componentecentral.entity.PlanVacunacion;
import vacunasuy.componentecentral.entity.Usuario;
import vacunasuy.componentecentral.exception.VacunasUyException;


@Stateless
public class ActoVacunalServiceImpl implements IActoVacunalService {

	@EJB
	private IUsuarioService usuarioService;
	
    @EJB
    private IActoVacunalDAO actoVacunalDAO;
    
    @EJB
    private IPlanVacunacionDAO planVacunacionDAO;
    
    @EJB
    private IUsuarioDAO usuarioDAO;
    
    @EJB
    private IEnfermedadDAO enfermedadDAO;
    
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
	public List<ActoVacunalCertificadoDTO> listarActosVacunalesPorUsuarioEnfermedad(Long idUsuario, Long idEnfermedad) throws VacunasUyException {
    	try {
    		/* Se valida que exista el usuario */
        	Usuario usuario = usuarioDAO.listarPorId(idUsuario); 
        	if(usuario == null) throw new VacunasUyException("El usuario indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
        	/* Se valida que exista la enfermedad */
        	Enfermedad enfermedad = enfermedadDAO.listarPorId(idEnfermedad); 
        	if(enfermedad == null) throw new VacunasUyException("La enfermedad indicada no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
        	return actoVacunalDAO.listarActosVacunalesPorUsuarioEnfermedad(idUsuario, idEnfermedad);
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
    }
	
	@Override
	public ActoVacunalDTO crear(ActoVacunalCrearDTO actoVacunalDTO) throws VacunasUyException{
		//se valida que el ciudadano exista
		Usuario ciudadano = usuarioDAO.listarPorId(actoVacunalDTO.getUsuario());
		if(ciudadano==null) throw new VacunasUyException("El ciudadano indicado no existe.",
				VacunasUyException.NO_EXISTE_REGISTRO);
		// se valida que el plan de vacunacion exista
		PlanVacunacion planVacunacion = planVacunacionDAO.listarPorId(actoVacunalDTO.getPlanVacunacion());
		if(planVacunacion==null) throw new VacunasUyException("El plan de vacunación indicado no existe.",
				VacunasUyException.NO_EXISTE_REGISTRO);
		try {
			ActoVacunal actoVacunal = actoVacunalConverter.fromCrearDTO(actoVacunalDTO);
			actoVacunal.setPlanVacunacion(planVacunacion);
			ActoVacunal actoVacunalCreado = actoVacunalDAO.crear(actoVacunal);
			usuarioService.agregarActoVacunal(ciudadano.getId(), actoVacunalCreado.getId());
			return actoVacunalConverter.fromEntity(actoVacunalCreado);
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
