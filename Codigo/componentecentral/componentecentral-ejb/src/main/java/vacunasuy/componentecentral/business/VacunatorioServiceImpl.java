package vacunasuy.componentecentral.business;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import vacunasuy.componentecentral.converter.PuestoConverter;
import vacunasuy.componentecentral.converter.VacunatorioConverter;
import vacunasuy.componentecentral.dao.IActoVacunalDAO;
import vacunasuy.componentecentral.dao.IDepartamentoDAO;
import vacunasuy.componentecentral.dao.IEventoDAO;
import vacunasuy.componentecentral.dao.ILocalidadDAO;
import vacunasuy.componentecentral.dao.IPuestoDAO;
import vacunasuy.componentecentral.dao.IVacunatorioDAO;
import vacunasuy.componentecentral.dto.PuestoCrearDTO;
import vacunasuy.componentecentral.dto.VacunatorioCercanoDTO;
import vacunasuy.componentecentral.dto.VacunatorioCrearDTO;
import vacunasuy.componentecentral.dto.VacunatorioDTO;
import vacunasuy.componentecentral.entity.ActoVacunal;
import vacunasuy.componentecentral.entity.Departamento;
import vacunasuy.componentecentral.entity.Evento;
import vacunasuy.componentecentral.entity.Localidad;
import vacunasuy.componentecentral.entity.Puesto;
import vacunasuy.componentecentral.entity.Vacunatorio;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Stateless
public class VacunatorioServiceImpl implements IVacunatorioService {

	@EJB
	private IVacunatorioDAO vacunatorioDAO;
	
	@EJB
	private ILocalidadDAO localidadDAO;
	
	@EJB
	private IDepartamentoDAO departamentoDAO;
	
	@EJB
	private IPuestoDAO puestoDAO;
	
	@EJB
	private IEventoDAO eventoDAO;
	
	@EJB 
	private IActoVacunalDAO actoVacunalDAO;
	
	@EJB
	private VacunatorioConverter vacunatorioConverter;
	
	@EJB
	private PuestoConverter puestoConverter;
	
	
	@Override
	public List<VacunatorioDTO> listar() throws VacunasUyException{
		try {
			return vacunatorioConverter.fromEntity(vacunatorioDAO.listar());
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
	@Override
	public VacunatorioDTO listarPorId(Long id) throws VacunasUyException{
		//se valida que el vacunatorio exista
		Vacunatorio vacunatorio = vacunatorioDAO.listarPorId(id);
		if (vacunatorio==null) throw new VacunasUyException("El vacunatorio indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		try {
			return vacunatorioConverter.fromEntity(vacunatorio);
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}	
	}
	
	@Override
	public VacunatorioDTO crear(VacunatorioCrearDTO vacunatorioDTO) throws VacunasUyException{
		Vacunatorio vacunatorio = vacunatorioConverter.fromCrearDTO(vacunatorioDTO);
		//se verifica que la localidad y el departamento existan
		Localidad localidad = localidadDAO.listarPorId(vacunatorioDTO.getLocalidad());
		if (localidad == null) throw new VacunasUyException("La localidad indicada no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		Departamento departamento = departamentoDAO.listarPorId(vacunatorioDTO.getDepartamento());
		if(departamento==null) throw new VacunasUyException("El departamento indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
	
		//se crea el vacunatorio
		try {
			vacunatorio.setLocalidad(localidad);
			vacunatorio.setDepartamento(departamento);
			return vacunatorioConverter.fromEntity(vacunatorioDAO.crear(vacunatorio));
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}	
	}
	
	@Override
	public VacunatorioDTO editar(Long id, VacunatorioCrearDTO vacunatorioDTO) throws VacunasUyException{
		//se verifica que el vacunatorio exista
		Vacunatorio vacunatorio = vacunatorioDAO.listarPorId(id);
		if(vacunatorio==null) throw new VacunasUyException("El vacunatorio indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		//se verifica que la localidad y departamentos existan
		Localidad localidad = localidadDAO.listarPorId(vacunatorioDTO.getLocalidad());
		if (localidad == null) 	throw new VacunasUyException("La localidad indicada no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		Departamento departamento = departamentoDAO.listarPorId(vacunatorioDTO.getDepartamento());
		if(departamento==null) throw new VacunasUyException("El departamento indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);				
		//se edita el vacunatorio
		try {
			vacunatorio.setNombre(vacunatorioDTO.getNombre());
			vacunatorio.setLatitud(vacunatorioDTO.getLatitud());
			vacunatorio.setLongitud(vacunatorioDTO.getLongitud());
			vacunatorio.setDireccion(vacunatorioDTO.getDireccion());
			vacunatorio.setDepartamento(departamento);
			vacunatorio.setLocalidad(localidad);
			return vacunatorioConverter.fromEntity(vacunatorioDAO.editar(vacunatorio));
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}		
		
	@Override
	public void eliminar(Long id) throws VacunasUyException{
		//se valida que el vacunatorio exista
		Vacunatorio vacunatorio = vacunatorioDAO.listarPorId(id);
		if (vacunatorio==null) throw new VacunasUyException("El vacunatorio indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		try {
			vacunatorioDAO.eliminar(vacunatorio);
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}		
	}
	
	@Override
	public List<VacunatorioDTO> listarVacunatoriosCercanos(VacunatorioCercanoDTO vacunatorioDTO) throws VacunasUyException{
		try {
			List<Vacunatorio> vacunatorios = vacunatorioDAO.listarVacunatoriosCercanos(vacunatorioConverter.fromCercanoDTO(vacunatorioDTO));
			return vacunatorioConverter.fromEntity(vacunatorios);
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
	@Override
	public VacunatorioDTO agregarEvento(Long vacunatorio, Long evento) throws VacunasUyException{
		try {
			//se valida que el vacunatorio y el evento existan
			Vacunatorio vacunatorioAux = vacunatorioDAO.listarPorId(vacunatorio);
			if(vacunatorioAux==null) throw new VacunasUyException("El vacunatorio indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
			Evento eventoAux = eventoDAO.listarPorId(evento);
			if(eventoAux==null) throw new VacunasUyException("El evento indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
			//se valida que el evento no esté asociado a vacunatorio
			for(Evento e: vacunatorioAux.getEventos()) {
				if(e.getId()==eventoAux.getId()) throw new VacunasUyException("El evento indicado ya se "
						+ "encuentra asociado al vacunatorio.", VacunasUyException.EXISTE_REGISTRO);
			}
			vacunatorioAux.getEventos().add(eventoAux);
			return vacunatorioConverter.fromEntity(vacunatorioDAO.editar(vacunatorioAux));
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
	@Override
	public VacunatorioDTO agregarActoVacunal(Long vacunatorio, Long actoVacunal) throws VacunasUyException{
		try {
			//se valida que el vacunatorio y el actoVacunal existan
			Vacunatorio vacunatorioAux = vacunatorioDAO.listarPorId(vacunatorio);
			if(vacunatorioAux==null) throw new VacunasUyException("El vacunatorio indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
			ActoVacunal actoVacunalAux = actoVacunalDAO.listarPorId(actoVacunal);
			if(actoVacunalAux==null) throw new VacunasUyException("El acto vacunal indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
			//se valida que el acto vacunal no esté asociado a vacunatorio
			for(ActoVacunal a: vacunatorioAux.getActosVacunales()) {
				if(a.getId()==actoVacunalAux.getId()) throw new VacunasUyException("El acto vacunal indicado ya se "
						+ "encuentra asociado al vacunatorio.", VacunasUyException.EXISTE_REGISTRO); 
			}
			vacunatorioAux.getActosVacunales().add(actoVacunalAux);
			return vacunatorioConverter.fromEntity(vacunatorioDAO.editar(vacunatorioAux));
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
	@Override
	public VacunatorioDTO agregarPuesto(PuestoCrearDTO puestoDTO) throws VacunasUyException{
		try {
			// se valida que el vacunatorio exista
			Vacunatorio vacunatorio = vacunatorioDAO.listarPorId(puestoDTO.getVacunatorio());
			if(vacunatorio==null) throw new VacunasUyException("El vacunatorio indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
			// se crea un puesto y se setea el vacunatorio 
			Puesto puesto = puestoConverter.fromCrearDTO(puestoDTO);
			puesto.setVacunatorio(vacunatorio);
			// se asocia el puesto al vacunatorio
			vacunatorio.getPuestos().add(puesto);
			return vacunatorioConverter.fromEntity(vacunatorioDAO.editar(vacunatorio));
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	

}
