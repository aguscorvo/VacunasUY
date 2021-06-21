package vacunasuy.componentecentral.bean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vacunasuy.componentecentral.business.IStockService;
import vacunasuy.componentecentral.business.IVacunaService;
import vacunasuy.componentecentral.business.IVacunatorioService;
import vacunasuy.componentecentral.dto.ReporteVacunaDTO;
import vacunasuy.componentecentral.dto.VacunaDTO;
import vacunasuy.componentecentral.dto.VacunatorioDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Named("StockBean")
@SessionScoped
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockBean implements Serializable {

	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(StockBean.class);
	
	private Long idVacuna;
	private Long idVacunatorio;
	private String nombre;
	private Long cantidad;
	private String FechaInicio;
	private String FechaFin;
	private List<ReporteVacunaDTO> reporte;
	private List<VacunaDTO> vacunas;
	private List<VacunatorioDTO> vacunatorios;
	private String titulo;
	
	@EJB
	private IStockService stockService;
	
	@EJB
	private IVacunatorioService vacunatorioService;
	
	@EJB
	private IVacunaService vacunaService;
	
	@PostConstruct
	public void init() {
		try {
			vacunas = vacunaService.listar();
			vacunatorios = vacunatorioService.listar();
			
		} catch (VacunasUyException e) {
			logger.info(e.getMessage().trim());
		}
	}
	
	public void listarStockVacunasDisponiblesParaEnviar() {
		try {
			reporte = stockService.listarStockVacunasDisponiblesParaEnviar();
			logger.info(reporte.size());
			imprimir();
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
	}
	 
	public void listarStockVacunaPorVacunatorios() {
		try {
			reporte = stockService.listarStockVacunaPorVacunatorios(idVacuna);
			imprimir();
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
	}
	
	public void listarStockVacunasPorVacunatorio() {
		try {
			reporte = stockService.listarStockVacunasPorVacunatorio(idVacunatorio);
			imprimir();
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
	}
	
	private void clearParam() {
		this.idVacuna = null;
		this.idVacunatorio = null;
		this.nombre = null;
		this.nombre = null;
		this.cantidad = null;
		this.reporte = null;
		this.vacunas = null;
		this.vacunatorios = null;
	}
	
	
private void imprimir() {
	logger.error(reporte.size());
	
		Document document = new Document(PageSize.A4);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			
			Date hoy = new Date();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String strDate = dateFormat.format(hoy);
			
			PdfWriter writer= PdfWriter.getInstance(document, baos);
			document.open();
			Font fuente1 = new Font();
			Font fuente2 = new Font();
			fuente1.setSize(20);
			fuente2.setSize(10);

			document.add(new Paragraph("VacunasUY \n",fuente1));
			document.add(new Paragraph("\n",fuente2));
			document.add(new Paragraph("Fecha: "+strDate+"\n",fuente2));
			LineSeparator linea = new LineSeparator();
			linea.setOffset(-8.0f);
			linea.setAlignment(Element.ALIGN_LEFT);
			linea.setPercentage(70.0f);
			document.add(linea);
			//Image imagen = Image.getInstance(getClass().getResource("/componentecentral-web/src/main/webapp/resources/img/IconMapVac.png"));
			//imagen.setAbsolutePosition(400f, 650f);
			//document.add(imagen);
			
			
			Font font = FontFactory.getFont(FontFactory.HELVETICA);
			font.setSize(10);
			font.setColor(CMYKColor.BLACK);
			
			// define table header cell
			PdfPCell cell = new PdfPCell();
			PdfPCell cell2 = new PdfPCell();
			cell.setBackgroundColor(CMYKColor.DARK_GRAY);
			cell.setPadding(5);
			
			PdfPTable table2 = new PdfPTable(2);
			table2.setWidthPercentage(100.0f);
			table2.setWidths(new float[] {5.0f,2.0f});
			table2.setSpacingBefore(10);
			
			// write table header 
			cell.setPhrase(new Phrase("Entidad", font));
			table2.addCell(cell);
			
			cell.setPhrase(new Phrase("Cantidad", font));
			table2.addCell(cell);
			
			for(ReporteVacunaDTO f : reporte) {
				cell2.setPhrase(new Phrase(f.getNombre(),font));
				table2.addCell(cell2);
				
				cell2.setPhrase(new Phrase(f.getCantidad().toString(),font));
				table2.addCell(cell2);
				
			}

			document.add(table2);
			
			LineSeparator linea2 = new LineSeparator();
			linea2.setOffset(-8.0f);
			linea2.setPercentage(100.0f);
			document.add(linea2);
				
		}catch (Exception e) {
			logger.error("Error: " + e.getMessage());
        }
		
		document.close();
		FacesContext context = FacesContext.getCurrentInstance();
		Object response = context.getExternalContext().getResponse();
		if (response instanceof HttpServletResponse) {
            HttpServletResponse hsr = (HttpServletResponse) response;
            hsr.setContentType("application/pdf");
            hsr.setHeader("Content-disposition", "attachment;filename=/factura.pdf");
            hsr.setContentLength(baos.size());
            try {
                  ServletOutputStream out = hsr.getOutputStream();
                  baos.writeTo(out);
                  out.flush();
            } catch (IOException ex) {
                  System.out.println("Error:  " + ex.getMessage());
            }
            context.responseComplete();
      }
		
		
		
	} 
	
	
}
