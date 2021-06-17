package vacunasuy.componentecentral.rest.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import org.jboss.logging.Logger;

import vacunasuy.componentecentral.business.IDepartamentoService;
import vacunasuy.componentecentral.business.IPuestoService;
import vacunasuy.componentecentral.business.IVacunatorioService;
import vacunasuy.componentecentral.dto.DepartamentoDTO;
import vacunasuy.componentecentral.dto.LocalidadDTO;
import vacunasuy.componentecentral.dto.PuestoCrearDTO;
import vacunasuy.componentecentral.dto.PuestoDTO;
import vacunasuy.componentecentral.dto.VacunatorioCrearDTO;
import vacunasuy.componentecentral.dto.VacunatorioDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Named("beanvacunatorio")
@SessionScoped
public class VacunatorioBean implements Serializable {

	static Logger logger = Logger.getLogger(VacunatorioBean.class);
	List<VacunatorioDTO> vacunatorios;
	List<DepartamentoDTO> departamentos;
	List<LocalidadDTO> SelectLocalidadOnDepartamento;
	VacunatorioDTO vacunatorio;
	String strbuscar;
	String Lat;
	String Lon;
	Long departamento;
	Long localidad;
	String nombre;
	String direccion;
	Long id = null;
	String updLat;
	String updLon;
	String puestos; 
	
	
	

	@EJB
	IVacunatorioService vacunatorioService;

	@EJB
	IPuestoService puestoService;
	
	@EJB
	IDepartamentoService departamentoService;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VacunatorioBean() {
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
	public void init() {
		try {
			vacunatorios = vacunatorioService.listar();
			departamentos = departamentoService.listar();

		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			vacunatorios = new ArrayList<VacunatorioDTO>();
		}

	}

	public void srchVacunatorio() {

		logger.info("srchvacunatorio 'strbuscar': " + strbuscar);

		try {
			vacunatorios = vacunatorioService.listar();

		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
			vacunatorios = new ArrayList<VacunatorioDTO>();
		}

		if (!strbuscar.equals("")) {
			List<VacunatorioDTO> auxvac = new ArrayList<VacunatorioDTO>();

			strbuscar = strbuscar.toUpperCase();

			for (VacunatorioDTO vdto : vacunatorios) {
				if (vdto.getNombre().toUpperCase().contains(strbuscar)
						|| vdto.getDireccion().toUpperCase().contains(strbuscar)
						|| vdto.getDepartamento().getNombre().toUpperCase().contains(strbuscar)
						|| vdto.getLocalidad().getNombre().toUpperCase().contains(strbuscar))
					auxvac.add(vdto);
			}
			vacunatorios = auxvac;
		}

	}

	public List<LocalidadDTO> getSelectLocalidadOnDepartamento() {
		List<LocalidadDTO> localidades = new ArrayList<LocalidadDTO>();

		if (departamento != null) {
			try {
				localidades = departamentoService.listarPorId(departamento).getLocalidades();
			} catch (VacunasUyException e) {
				logger.info(e.getMessage().trim());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));

			}
		}

		return localidades;
	}

	public void addVacunatorio() {

		try {

			Integer auxp =Integer.valueOf(puestos);
			
			VacunatorioCrearDTO nVac = new VacunatorioCrearDTO(nombre, Double.valueOf(Lat), Double.valueOf(Lon),
					direccion, localidad, departamento);
			VacunatorioDTO vacunatorioDTO = vacunatorioService.crear(nVac);

			
			for(Integer v=0; v<auxp; v++) {
				PuestoCrearDTO puestoDTO = new PuestoCrearDTO(v+1, vacunatorioDTO.getId());  
				puestoService.crear(puestoDTO);
			}
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Vacunatorio " + nVac.getNombre() + " creado con éxito.", null));


			vacunatorios = vacunatorioService.listar();

		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
		} finally {
			clearParam();
		}
	}

	public void delVacunatorio() {
		
		try {
			vacunatorioService.eliminar(id);
			vacunatorios = vacunatorioService.listar();
			
		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
		} catch (Exception e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
		} finally {
			clearParam();
		}
	}

	public void updVacunatorio() {
		
		try {
			VacunatorioCrearDTO updVac = new VacunatorioCrearDTO(nombre, Double.valueOf(updLat), Double.valueOf(updLon),
					direccion, localidad, departamento);
			
			vacunatorioService.editar(id, updVac);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Vacunatorio " + updVac.getNombre() + " modificado con éxito.", null));

			vacunatorios = vacunatorioService.listar();

		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
		} finally {
			clearParam();
		}

	}

	public void validateModelNombre(FacesContext context, UIComponent comp, Object value) throws ValidatorException {
		String texto = (String) value;

		if (texto.trim().equalsIgnoreCase("")) {
			((UIInput) comp).setValid(false);

			throw new ValidatorException(new FacesMessage("Error: En nombre no puede ser vacío"));
		}
	}

	public void validateModelDireccion(FacesContext context, UIComponent comp, Object value) throws ValidatorException {
		String texto = (String) value;

		if (texto.trim().equalsIgnoreCase("")) {
			((UIInput) comp).setValid(false);

			throw new ValidatorException(new FacesMessage("Error: La dirección no puede ser vacía"));
		}
	}

	public void validateModelLatitud(FacesContext context, UIComponent comp, Object value) throws ValidatorException {
		String texto = (String) value;

		if (texto.trim().equalsIgnoreCase("")) {
			((UIInput) comp).setValid(false);

			throw new ValidatorException(new FacesMessage("Error: La Latitud no puede ser vacía"));
		}
	}

	public void validateModelLongitud(FacesContext context, UIComponent comp, Object value) throws ValidatorException {
		String texto = (String) value;

		if (texto.trim().equalsIgnoreCase("")) {
			((UIInput) comp).setValid(false);

			throw new ValidatorException(new FacesMessage("Error: La Longitud no puede ser vacía"));
		}
	}

	private void clearParam() {
		// this.vacunatorio = new VacunatorioDTO();
		this.strbuscar = null;
		this.Lat = null;
		this.Lon = null;
		this.departamento = null;
		this.localidad = null;
		this.nombre = null;
		this.direccion = null;
		this.id = null;
		this.updLat = null;
		this.updLon = null;
	}

	public List<VacunatorioDTO> getVacunatorios() {
		return vacunatorios;
	}

	public void setVacunatorios(List<VacunatorioDTO> vacunatorios) {
		this.vacunatorios = vacunatorios;
	}

	public List<DepartamentoDTO> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(List<DepartamentoDTO> departamentos) {
		this.departamentos = departamentos;
	}

	public VacunatorioDTO getVacunatorio() {
		return vacunatorio;
	}

	public void setVacunatorio(VacunatorioDTO vacunatorio) {
		this.vacunatorio = vacunatorio;
	}

	public String getStrbuscar() {
		return strbuscar;
	}

	public void setStrbuscar(String strbuscar) {
		this.strbuscar = strbuscar;
	}

	public void setSelectLocalidadOnDepartamento(List<LocalidadDTO> selectLocalidadOnDepartamento) {
		SelectLocalidadOnDepartamento = selectLocalidadOnDepartamento;
	}

	public String getLat() {
		return Lat;
	}

	public void setLat(String lat) {
		Lat = lat;
	}

	public String getLon() {
		return Lon;
	}

	public void setLon(String lon) {
		Lon = lon;
	}

	public Long getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Long localidad) {
		this.localidad = localidad;
	}

	public Long getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Long departamento) {
		this.departamento = departamento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUpdLat() {
		String Lat = "";
		
		try {
			
			if(id!=null) {
				VacunatorioDTO vdto = vacunatorioService.listarPorId(id);
				Lat = vdto.getLatitud().toString();	
			}
		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
		} 
		
		return Lat;
	}

	public void setUpdLat(String updLat) {
		this.updLat = updLat;
	}

	public String getUpdLon() {
		String Lon = "";
		
		try {
			if(id != null ) {
				VacunatorioDTO vdto = vacunatorioService.listarPorId(id);
				Lon = vdto.getLongitud().toString();	
			}
		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().trim(), null));
		}
		
		return Lon;

	}

	public void setUpdLon(String updLon) {
		this.updLon = updLon;
	}

	public String getPuestos() {
		return puestos;
	}

	public void setPuestos(String puestos) {
		this.puestos = puestos;
	}
	
	

}
