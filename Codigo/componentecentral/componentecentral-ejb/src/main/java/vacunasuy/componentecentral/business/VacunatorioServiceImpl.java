package vacunasuy.componentecentral.business;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import vacunasuy.componentecentral.converter.DepartamentoConverter;
import vacunasuy.componentecentral.converter.LocalidadConverter;
import vacunasuy.componentecentral.converter.PuestoConverter;
import vacunasuy.componentecentral.converter.VacunatorioConverter;
import vacunasuy.componentecentral.dao.IDepartamentoDAO;
import vacunasuy.componentecentral.dao.ILocalidadDAO;
import vacunasuy.componentecentral.dao.IPuestoDAO;
import vacunasuy.componentecentral.dao.IVacunatorioDAO;
import vacunasuy.componentecentral.dto.PuestoDTO;
import vacunasuy.componentecentral.dto.VacunatorioCrearDTO;
import vacunasuy.componentecentral.dto.VacunatorioDTO;
import vacunasuy.componentecentral.entity.Departamento;
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
	private VacunatorioConverter vacunatorioConverter;

	
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
		if (vacunatorio==null) {
			throw new VacunasUyException("El vacunatorio indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		}else {
			try {
				return vacunatorioConverter.fromEntity(vacunatorio);
			}catch(Exception e) {
				throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
			}
			
		}
	}
	
	@Override
	public VacunatorioDTO crear(VacunatorioCrearDTO vacunatorioDTO) throws VacunasUyException{
		Vacunatorio vacunatorio = vacunatorioConverter.fromCrearDTO(vacunatorioDTO);
		//se verifica que la localidad y el departamento existan
		Localidad localidad = localidadDAO.listarPorId(vacunatorioDTO.getLocalidad());
		Departamento departamento = departamentoDAO.listarPorId(vacunatorioDTO.getDepartamento());
		if (localidad == null) {
			throw new VacunasUyException("La localidad indicada no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		}else if(departamento==null) {
			throw new VacunasUyException("El departamento indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		}else {
			//se verifica que los puestos existan
			List<Long> puestos = vacunatorioDTO.getPuestos();
			List<Puesto> puestosAAgregar = new ArrayList<Puesto>();
			for(Long p: puestos) {
				if(puestoDAO.listarPorId(p)==null) {
					throw new VacunasUyException("El puesto indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
				}else {
					puestosAAgregar.add(puestoDAO.listarPorId(p));
				}
			}			
			//se crea el vacunatorio
			try {
				vacunatorio.setLocalidad(localidad);
				vacunatorio.setDepartamento(departamento);
				vacunatorio.setPuestos(puestosAAgregar);
				return vacunatorioConverter.fromEntity(vacunatorioDAO.crear(vacunatorio));
			}catch(Exception e) {
				throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
			}		
			
		}
	}
	
	@Override
	public VacunatorioDTO editar(Long id, VacunatorioCrearDTO vacunatorioDTO) throws VacunasUyException{
		//se verifica que el vacunatorio exista
		Vacunatorio vacunatorio = vacunatorioDAO.listarPorId(id);
		if(vacunatorio==null) {
			throw new VacunasUyException("El vacunatorio indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		}		
		//se verifica que la localidad y departamentos existan
		Localidad localidad = localidadDAO.listarPorId(vacunatorioDTO.getLocalidad());
		Departamento departamento = departamentoDAO.listarPorId(vacunatorioDTO.getDepartamento());
		if (localidad == null) {
			throw new VacunasUyException("La localidad indicada no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		}else if(departamento==null) {
			throw new VacunasUyException("El departamento indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		}
		else {
			//se verifica que los puestos existan
			List<Long> idPuestos = vacunatorioDTO.getPuestos();
			List<Puesto> puestos = new ArrayList<Puesto>();
			for(Long p: idPuestos) {
				if (puestoDAO.listarPorId(p)==null) {
					throw new VacunasUyException("El puesto indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
				}else {
					puestos.add(puestoDAO.listarPorId(p));
				}
			}			
			//se edita el vacunatorio
			try {
				vacunatorio.setNombre(vacunatorioDTO.getNombre());
				vacunatorio.setLatitud(vacunatorioDTO.getLatitud());
				vacunatorio.setLongitud(vacunatorioDTO.getLongitud());
				vacunatorio.setDireccion(vacunatorioDTO.getDireccion());
				vacunatorio.setDepartamento(departamento);
				vacunatorio.setLocalidad(localidad);
				vacunatorio.setPuestos(puestos);
				return vacunatorioConverter.fromEntity(vacunatorioDAO.editar(vacunatorio));
			}catch(Exception e) {
				throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
			}
		}		
	}
	
	@Override
	public void eliminar(Long id) throws VacunasUyException{
		//se valida que el vacunatorio exista
		Vacunatorio vacunatorio = vacunatorioDAO.listarPorId(id);
		if (vacunatorio==null) {
			throw new VacunasUyException("El vacunatorio indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		}else {
			try {
				vacunatorioDAO.eliminar(vacunatorio);
			}catch(Exception e) {
				throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
			}
		}
	}

}
