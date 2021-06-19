
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import vacunasuy.componentecentral.business.SectorLaboralServiceImpl;
import vacunasuy.componentecentral.converter.SectorLaboralConverter;
import vacunasuy.componentecentral.dao.SectorLaboralDAOImpl;
import vacunasuy.componentecentral.dto.SectorLaboralDTO;
import vacunasuy.componentecentral.entity.SectorLaboral;
import vacunasuy.componentecentral.exception.VacunasUyException;

@RunWith(MockitoJUnitRunner.class)
public class SectorLaboralServiceTest {

	/* Servicio que se quiere probar */
	@InjectMocks
	private SectorLaboralServiceImpl sectorService;
	
	/* 
	 * Mocks que se desean realizar, serían los @EJB que se necesiten en los métodos a probar
	 * Deben declararse como public en el service 
	*/
	@Mock
	private SectorLaboralDAOImpl sectorLaboralDAO;
	
	@Mock
	private SectorLaboralConverter sectorLaboralConverter;
	
	@Before
	public void init() {
		sectorService = new SectorLaboralServiceImpl();
		sectorService.sectorLaboralDAO = this.sectorLaboralDAO;
		sectorService.sectorLaboralConverter = this.sectorLaboralConverter;
	}
	
	/* Se prueba el método listar */
	@Test
	public void listar() {
		List<SectorLaboral> sectores = new ArrayList<SectorLaboral>();
		sectores.add(new SectorLaboral(1L, "Prueba"));
		List<SectorLaboralDTO> sectoresDTO = new ArrayList<SectorLaboralDTO>();
		sectoresDTO.add(new SectorLaboralDTO(1L, "Prueba"));
		Mockito.when(sectorService.sectorLaboralDAO.listar()).thenReturn(sectores);
		Mockito.when(sectorService.sectorLaboralConverter.fromEntity(Mockito.anyList())).thenReturn(sectoresDTO);
		try {
			List<SectorLaboralDTO> sectoresEsperados = sectorService.listar();
			assertEquals(1, sectoresEsperados.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
}
