package vacunasuy.componentecentral.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import vacunasuy.componentecentral.converter.PuestoConverter;
import vacunasuy.componentecentral.dao.IAgendaDAO;
import vacunasuy.componentecentral.dao.IPuestoDAO;
import vacunasuy.componentecentral.dto.PuestoCrearDTO;
import vacunasuy.componentecentral.dto.PuestoDTO;
import vacunasuy.componentecentral.entity.Agenda;
import vacunasuy.componentecentral.entity.Puesto;
import vacunasuy.componentecentral.exception.VacunasUyException;


@Stateless
public class PuestoServiceImpl implements IPuestoService {
	
	@EJB
	private IPuestoDAO puestoDAO;
	
	@EJB
	private IAgendaDAO agendaDAO;
	
	@EJB
	private PuestoConverter puestoConverter;
	
	@Override
	public List<PuestoDTO> listar() throws VacunasUyException{
		try {
			return puestoConverter.fromEntity(puestoDAO.listar());
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
	@Override
	public PuestoDTO listarPorId(Long id) throws VacunasUyException{
		//se valida que exista el puesto
		Puesto puesto = puestoDAO.listarPorId(id);
		if(puesto==null) throw new VacunasUyException("El puesto indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		try {
			return puestoConverter.fromEntity(puesto);
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}				
	}
	
	@Override
	public PuestoDTO listarPorNumero(int numero) throws VacunasUyException{
		//se valida que exista el puesto
		Puesto puesto = puestoDAO.listarPorNumero(numero);
		if(puesto==null) throw new VacunasUyException("El puesto indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		try {
			return puestoConverter.fromEntity(puesto);
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}				
	}
	
	@Override
	public PuestoDTO crear(PuestoCrearDTO puestoDTO) throws VacunasUyException{
		try {
			Puesto puesto = puestoConverter.fromCrearDTO(puestoDTO);
			return puestoConverter.fromEntity(puestoDAO.crear(puesto));
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}	
	
	@Override
	public PuestoDTO editar(Long id, PuestoCrearDTO puestoDTO) throws VacunasUyException{
		//se valida que exista el puesto
		Puesto puesto = puestoDAO.listarPorId(id);
		if(puesto==null) throw new VacunasUyException("El puesto indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		try {
			puesto.setNumero(puestoDTO.getNumero());
			return puestoConverter.fromEntity(puestoDAO.editar(puesto));
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}				
	}
	
	@Override
	public void eliminar(Long id) throws VacunasUyException{
		//se valida que exista el puesto
		Puesto puesto = puestoDAO.listarPorId(id);
		if(puesto==null) throw new VacunasUyException("El puesto indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		try {
			puestoDAO.eliminar(puesto);
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}			
	}
	



}
