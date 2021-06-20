package vacunasuy.componentecentral.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import vacunasuy.componentecentral.converter.PuestoConverter;
import vacunasuy.componentecentral.dao.IAgendaDAO;
import vacunasuy.componentecentral.dao.IPuestoDAO;
import vacunasuy.componentecentral.dao.IVacunatorioDAO;
import vacunasuy.componentecentral.dto.PuestoCrearDTO;
import vacunasuy.componentecentral.dto.PuestoDTO;
import vacunasuy.componentecentral.dto.PuestoMinDTO;
import vacunasuy.componentecentral.entity.Puesto;
import vacunasuy.componentecentral.entity.Vacunatorio;
import vacunasuy.componentecentral.exception.VacunasUyException;


@Stateless
public class PuestoServiceImpl implements IPuestoService {
	
	@EJB
	public IPuestoDAO puestoDAO;
	
	@EJB
	public IAgendaDAO agendaDAO;
	
	@EJB
	public PuestoConverter puestoConverter;
	
	@EJB
	public IVacunatorioDAO vacunatorioDAO;
	
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
	public PuestoMinDTO crear(PuestoCrearDTO puestoDTO) throws VacunasUyException{
		try {
			Vacunatorio vacunatorio = vacunatorioDAO.listarPorId(puestoDTO.getVacunatorio());
			if(vacunatorio==null) throw new VacunasUyException("El vacunatorio indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
			Puesto puesto = puestoConverter.fromCrearDTO(puestoDTO);
			puesto.setVacunatorio(vacunatorio);
			vacunatorio.getPuestos().add(puesto);
			return puestoConverter.fromEntityToMin(puestoDAO.crear(puesto));
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
