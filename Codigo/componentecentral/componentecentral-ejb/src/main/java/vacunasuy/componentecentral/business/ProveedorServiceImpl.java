package vacunasuy.componentecentral.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import vacunasuy.componentecentral.converter.ProveedorConverter;
import vacunasuy.componentecentral.dao.IPaisDAO;
import vacunasuy.componentecentral.dao.IProveedorDAO;
import vacunasuy.componentecentral.dto.ProveedorCrearDTO;
import vacunasuy.componentecentral.dto.ProveedorDTO;
import vacunasuy.componentecentral.entity.Pais;
import vacunasuy.componentecentral.entity.Proveedor;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Stateless
public class ProveedorServiceImpl implements IProveedorService {

	@EJB
	private IProveedorDAO proveedorDAO;
	
	@EJB
	private IPaisDAO paisDAO;
	
	@EJB
	private ProveedorConverter proveedorConverter;
	
	@Override
	public List<ProveedorDTO> listar() throws VacunasUyException{
		try {
			return proveedorConverter.fromEntity(proveedorDAO.listar());
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
	@Override
	public ProveedorDTO listarPorId(Long id) throws VacunasUyException{
		//se valida que exista el proveedor
		Proveedor proveedor = proveedorDAO.listarPorId(id);
		if(proveedor==null) throw new VacunasUyException("El proveedor indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		try {
			return proveedorConverter.fromEntity(proveedor);
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
	@Override
	public ProveedorDTO crear(ProveedorCrearDTO proveedorDTO) throws VacunasUyException{
		//se valida que el pais exista
		Pais pais = paisDAO.listarPorId(proveedorDTO.getPais());
		if(pais==null) throw new VacunasUyException("El pais indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		Proveedor proveedor = proveedorConverter.fromCrearDTO(proveedorDTO);
		try {
			proveedor.setPais(pais);
			return proveedorConverter.fromEntity(proveedorDAO.crear(proveedor));
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}		
	}
	
	@Override
	public ProveedorDTO editar(Long id, ProveedorCrearDTO proveedorDTO) throws VacunasUyException{
		//se valida que el proveedor y el pais existan
		Proveedor proveedor = proveedorDAO.listarPorId(id);
		if(proveedor==null) throw new VacunasUyException("El proveedor indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		Pais pais = paisDAO.listarPorId(proveedorDTO.getPais());
		if(pais==null) throw new VacunasUyException("El pais indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		try {
			proveedor.setNombre(proveedorDTO.getNombre());
			proveedor.setPais(pais);
			return proveedorConverter.fromEntity(proveedorDAO.editar(proveedor));
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
	@Override
	public void eliminar(Long id) throws VacunasUyException{
		//se valida que el proveedor exista
		Proveedor proveedor = proveedorDAO.listarPorId(id);
		if(proveedor==null) throw new VacunasUyException("El proveedor indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		try {
			proveedorDAO.eliminar(proveedor);
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}				
	}    

}
