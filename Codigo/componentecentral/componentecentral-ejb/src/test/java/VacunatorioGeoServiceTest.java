import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.geolatte.geom.G2D;
import org.geolatte.geom.Geometries;
import org.geolatte.geom.Point;
import org.geolatte.geom.crs.CoordinateReferenceSystems;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import vacunasuy.componentecentral.business.VacunatorioGeoServiceImpl;
import vacunasuy.componentecentral.converter.UbicacionConverter;
import vacunasuy.componentecentral.converter.VacunatorioGeoConverter;
import vacunasuy.componentecentral.dao.VacunatorioDAOImpl;
import vacunasuy.componentecentral.dao.VacunatorioGeoDAOImpl;
import vacunasuy.componentecentral.dto.UbicacionDTO;
import vacunasuy.componentecentral.dto.VacunatorioDTO;
import vacunasuy.componentecentral.dto.VacunatorioGeoDTO;
import vacunasuy.componentecentral.entity.Ubicacion;
import vacunasuy.componentecentral.entity.Vacunatorio;
import vacunasuy.componentecentral.entity.VacunatorioGeo;
import vacunasuy.componentecentral.exception.VacunasUyException;

@RunWith(MockitoJUnitRunner.class)
public class VacunatorioGeoServiceTest {

	@InjectMocks
	private VacunatorioGeoServiceImpl vacunatorioGeoService;
	
	@Mock
	private VacunatorioGeoDAOImpl vacunatorioGeoDAO;
	
	@Mock
	private VacunatorioDAOImpl vacunatorioDAO;
	
	@Mock
	private VacunatorioGeoConverter vacunatorioGeoConverter;
	
	@Mock
	private UbicacionConverter ubicacionConverter;
	
	
	@Before
	public void init() {
		vacunatorioGeoService = new VacunatorioGeoServiceImpl();
		vacunatorioGeoService.vacunatorioGeoDAO = this.vacunatorioGeoDAO;
		vacunatorioGeoService.vacunatorioDAO = this.vacunatorioDAO;
		vacunatorioGeoService.vacunatorioGeoConverter = this.vacunatorioGeoConverter;
		vacunatorioGeoService.ubicacionConverter = this.ubicacionConverter;
	}
	
	@Test
	public void listar() {
		List<VacunatorioGeo> vacunatorios = new ArrayList<VacunatorioGeo>();
		VacunatorioGeo vacunatorio = new VacunatorioGeo(1L, null, null);
		vacunatorios.add(vacunatorio);
		Mockito.when(vacunatorioGeoService.vacunatorioGeoDAO.listar()).thenReturn(vacunatorios);
		List<VacunatorioGeoDTO> vacunatoriosDTO = new ArrayList<VacunatorioGeoDTO>();
		VacunatorioGeoDTO vacunatorioDTO = new VacunatorioGeoDTO(1L, null);
		vacunatoriosDTO.add(vacunatorioDTO);
		Mockito.when(vacunatorioGeoService.vacunatorioGeoConverter.fromEntity(vacunatorios)).thenReturn(vacunatoriosDTO);
		try {
			List<VacunatorioGeoDTO> vacunatoriosDTO2 = vacunatorioGeoService.listar();
			assertEquals(vacunatoriosDTO, vacunatoriosDTO2);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void listarPorId() {
		VacunatorioGeo vacunatorio = new VacunatorioGeo(1L, null, null);
		VacunatorioGeoDTO vacunatorioDTO = new VacunatorioGeoDTO(1L, null);
		Mockito.when(vacunatorioGeoService.vacunatorioGeoDAO.listarPorId(1L)).thenReturn(vacunatorio);
		Mockito.when(vacunatorioGeoService.vacunatorioGeoConverter.fromEntity(vacunatorio)).thenReturn(vacunatorioDTO);
		try {
			VacunatorioGeoDTO vacunatorioDTO2 = vacunatorioGeoService.listarPorId(1L);
			assertEquals(vacunatorioDTO, vacunatorioDTO2);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test (expected = VacunasUyException.class)
	public void listarPorIdVacunatorioNull() throws VacunasUyException {
		Mockito.when(vacunatorioGeoService.vacunatorioGeoDAO.listarPorId(1L)).thenReturn(null);
		VacunatorioGeoDTO vacunatorioDTO = vacunatorioGeoService.listarPorId(1L);
		assertNull(vacunatorioDTO);		
	}
	
	@Ignore
	@Test
	public void crear() {	
		VacunatorioDTO vacunatorioDTO = new VacunatorioDTO(1L, "Antel Arena", -34.86297, -56.15348, null, null, null, null, null);
		Vacunatorio vacunatorio = new Vacunatorio(1L, "Antel Arena", -34.86297, -56.15348, null, null, null, null, null, null, null);
		@SuppressWarnings("rawtypes")
		Point point = Geometries.mkPoint(new G2D(vacunatorioDTO.getLongitud(), vacunatorioDTO.getLatitud()), CoordinateReferenceSystems.WGS84); 
		VacunatorioGeoDTO vacunatorioGeoDTO = new VacunatorioGeoDTO ();
		VacunatorioGeo vacunatorioGeoNuevo = new VacunatorioGeo();
		vacunatorioGeoNuevo.setGeom(point);
		vacunatorioGeoNuevo.setVacunatorio(vacunatorio);
		VacunatorioGeoDTO vacunatorioGeoDTONuevo = new VacunatorioGeoDTO (1L, vacunatorioDTO);
		Mockito.when(vacunatorioGeoService.vacunatorioDAO.listarPorId(vacunatorioDTO.getId())).thenReturn(vacunatorio);
		Mockito.when(vacunatorioGeoService.vacunatorioGeoConverter.fromDTO(vacunatorioGeoDTO)).thenReturn(vacunatorioGeoNuevo);
		Mockito.when(vacunatorioGeoService.vacunatorioDAO.listarPorId(vacunatorioDTO.getId())).thenReturn(vacunatorio);
		Mockito.when(vacunatorioGeoService.vacunatorioGeoDAO.crear(vacunatorioGeoNuevo)).thenReturn(vacunatorioGeoNuevo);
		Mockito.when(vacunatorioGeoService.vacunatorioGeoConverter.fromEntity(vacunatorioGeoNuevo)).thenReturn(vacunatorioGeoDTONuevo);
		try {
			VacunatorioGeoDTO vacunatorioGeoDTOResultado = vacunatorioGeoService.crear(vacunatorioDTO);
			assertEquals(vacunatorioGeoDTOResultado, vacunatorioGeoDTONuevo);
		}catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test (expected = VacunasUyException.class)
	public void crearVacunatorioNull() throws VacunasUyException {
		Mockito.when(vacunatorioGeoService.vacunatorioDAO.listarPorId(1L)).thenReturn(null);
		VacunatorioDTO vacunatorioDTO = new VacunatorioDTO(1L, "Antel Arena", -34.86297, -56.15348, null, null, null, null, null);
		@SuppressWarnings("unused")
		VacunatorioGeoDTO vacunatorioGeoDTO = vacunatorioGeoService.crear(vacunatorioDTO);
	}
		
	@Test
	public void listarCercanos() throws VacunasUyException {
		UbicacionDTO ubicacionDTO = new UbicacionDTO(-34.869996, -56.10902, 10.0);
		Ubicacion ubicacion = new Ubicacion(-34.869996, -56.10902, 10.0);
		ubicacionDTO.setDistancia(ubicacionDTO.getDistancia()/111);
		@SuppressWarnings("rawtypes")
		Point point = Geometries.mkPoint(new G2D(-56.15348, -34.86297), CoordinateReferenceSystems.WGS84); 
		VacunatorioGeo vacunatorioGeo = new VacunatorioGeo();
		vacunatorioGeo.setGeom(point);
		vacunatorioGeo.setId(1L);
		List<VacunatorioGeo> vacunatoriosGeo = new ArrayList<VacunatorioGeo>();
		vacunatoriosGeo.add(vacunatorioGeo);
		List<Long> vacunatoriosGeoId = new ArrayList<Long>();
		for(VacunatorioGeo v: vacunatoriosGeo) {
			vacunatoriosGeoId.add(v.getId());
		}
		Mockito.when(vacunatorioGeoService.ubicacionConverter.fromDTO(ubicacionDTO)).thenReturn(ubicacion);
		Mockito.when(vacunatorioGeoService.vacunatorioGeoDAO.listarCercanos(ubicacion)).thenReturn(vacunatoriosGeoId);
		try {
			List<Long> vacunatoriosGeoIdResultado = vacunatorioGeoService.listarCercanos(ubicacionDTO);
			assertEquals(vacunatoriosGeoId, vacunatoriosGeoIdResultado);
		}catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	

}
