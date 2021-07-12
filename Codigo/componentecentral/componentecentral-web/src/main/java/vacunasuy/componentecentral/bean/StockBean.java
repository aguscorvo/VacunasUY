package vacunasuy.componentecentral.bean;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
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
import vacunasuy.componentecentral.business.IEnfermedadService;
import vacunasuy.componentecentral.business.IReporteService;
import vacunasuy.componentecentral.business.ISectorLaboralService;
import vacunasuy.componentecentral.business.IStockService;
import vacunasuy.componentecentral.business.IVacunaService;
import vacunasuy.componentecentral.business.IVacunatorioService;
import vacunasuy.componentecentral.dto.EnfermedadDTO;
import vacunasuy.componentecentral.dto.ReporteActoVacunalDTO;
import vacunasuy.componentecentral.dto.ReporteEvolucionTiempoDTO;
import vacunasuy.componentecentral.dto.ReporteVacunaDTO;
import vacunasuy.componentecentral.dto.SectorLaboralDTO;
import vacunasuy.componentecentral.dto.VacunaDTO;
import vacunasuy.componentecentral.dto.VacunatorioDTO;
import vacunasuy.componentecentral.entity.Enfermedad;
import vacunasuy.componentecentral.entity.Vacuna;
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
	private Long idEnfermedad;
	private Long idSectorLaboral;
	private String nombre;
	private Long cantidad;
	private String FechaInicio;
	private String FechaFin;
	private Integer edadMinima;
	private Integer edadMaxima;
	List<String> fechas;
	List<ReporteVacunaDTO> reporte;
	private Map<String, List<ReporteVacunaDTO>> stockVacunasVacunatorios;
	List<VacunaDTO> vacunas;
	List<VacunatorioDTO> vacunatorios;
	List<EnfermedadDTO> enfermedades;
	List<SectorLaboralDTO> sectores;
	String titulo;
	String SubTitulo;

	@EJB
	IStockService stockService;

	@EJB
	IVacunatorioService vacunatorioService;

	@EJB
	IVacunaService vacunaService;

	@EJB
	IReporteService reporteService;

	@EJB
	IEnfermedadService enfermedadService;

	@EJB
	ISectorLaboralService sectorService;

	@PostConstruct
	public void init() {
		try {
			vacunas = vacunaService.listar();
			vacunatorios = vacunatorioService.listar();
			enfermedades = enfermedadService.listar();
			sectores = sectorService.listar();

		} catch (VacunasUyException e) {
			logger.error("init: " + e.getMessage());
		}
	}

	public void listarStockVacunasDisponiblesParaEnviar() {

		try {
			reporte = stockService.listarStockVacunasDisponiblesParaEnviar();

			List<ReporteVacunaPDF> datos = new ArrayList<ReporteVacunaPDF>();

			for (ReporteVacunaDTO d : reporte) {
				ReporteVacunaPDF rpdf = ReporteVacunaPDF.builder().cantidad(d.getCantidad()).nombre(d.getNombre())
						.build();

				datos.add(rpdf);
			}

			titulo = "StockVacunas_";
			SubTitulo = "Stock de Vacunas";
			imprimir(datos);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("listarStockVacunasDisponiblesParaEnviar: " + e.getMessage());
		}
	}

	public void listarStockVacunaPorVacunatorios() {
		try {
			reporte = stockService.listarStockVacunaPorVacunatorios(idVacuna);
			Vacuna vac = vacunaService.listarPorId(idVacuna);

			List<ReporteVacunaPDF> datos = new ArrayList<ReporteVacunaPDF>();

			for (ReporteVacunaDTO d : reporte) {
				ReporteVacunaPDF rpdf = ReporteVacunaPDF.builder().cantidad(d.getCantidad()).nombre(d.getNombre())
						.build();

				datos.add(rpdf);
			}
			titulo = "StockVacunaporVacunatorios_";
			SubTitulo = "Stock de Vacuna: " + vac.getNombre();
			imprimir(datos);
		} catch (Exception e) {
			logger.error("listarStockVacunaPorVacunatorios: " + e.getMessage());
		}
	}

	public void listarStockVacunasPorVacunatorio() {
		try {
			reporte = stockService.listarStockVacunasPorVacunatorio(idVacunatorio);
			VacunatorioDTO vac = vacunatorioService.listarPorId(idVacunatorio);

			List<ReporteVacunaPDF> datos = new ArrayList<ReporteVacunaPDF>();

			for (ReporteVacunaDTO d : reporte) {
				ReporteVacunaPDF rpdf = ReporteVacunaPDF.builder().cantidad(d.getCantidad()).nombre(d.getNombre())
						.build();

				datos.add(rpdf);
			}
			titulo = "StockVacunasporVacunatorio_";
			SubTitulo = "Stock de Vacunas por Vacunatorio: " + vac.getNombre() + " - "
					+ vac.getDepartamento().getNombre() + " - " + vac.getLocalidad().getNombre();
			imprimir(datos);
		} catch (Exception e) {
			logger.error("listarStockVacunasPorVacunatorio: " + e.getMessage());
		}
	}

	public void evolucionVacuna() {
		try {

			fechas = new ArrayList<String>();
			Date fechaI = new SimpleDateFormat("yyyy-MM-dd").parse(FechaInicio);
			Date fechaF = new SimpleDateFormat("yyyy-MM-dd").parse(FechaFin);

			if (fechaI.compareTo(fechaF) > 0) {
				fechaI = new SimpleDateFormat("yyyy-MM-dd").parse(FechaFin);
				fechaF = new SimpleDateFormat("yyyy-MM-dd").parse(FechaInicio);

			}

			Vacuna vac = vacunaService.listarPorId(idVacuna);

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			titulo = "EvolucionVacuna_";
			SubTitulo = "Evolución en el tiempo por vacuna: " + vac.getNombre() + " - " + dateFormat.format(fechaI)
					+ " - " + dateFormat.format(fechaF);

			List<ReporteEvolucionTiempoDTO> vacf = new ArrayList<ReporteEvolucionTiempoDTO>();

			List<ReporteEvolucionTiempoDTO> aux = reporteService.listarPorEvolucionEnTiempo(dateFormat.format(fechaI),
					dateFormat.format(fechaF), idVacuna);

			while (fechaI.compareTo(fechaF) <= 0) {
				String strDate = dateFormat.format(fechaI);
				fechas.add(strDate);

				Calendar c = Calendar.getInstance();
				c.setTime(fechaI);
				c.add(Calendar.DATE, 1);
				fechaI = c.getTime();
			}

	
			for (String s : fechas) {
				boolean encontre = false;
				for (ReporteEvolucionTiempoDTO rdto : aux) {
					if (rdto.getFecha().equalsIgnoreCase(s)) {
						vacf.add(rdto);
						encontre = true;
						break;
					}
				}
				if (!encontre) {
					ReporteEvolucionTiempoDTO recdto = ReporteEvolucionTiempoDTO.builder().cantidad(0).fecha(s).build();
					vacf.add(recdto);
				}
			}

			imprimirListado(vacf, vac.getNombre());

		} catch (VacunasUyException | ParseException e) {
			logger.error("evolucionVacuna: " + e.getMessage());
		}

	}

	public void evolucionEnfermedadEdad() {
		try {

			fechas = new ArrayList<String>();
			Date fechaI = new SimpleDateFormat("yyyy-MM-dd").parse(FechaInicio);
			Date fechaF = new SimpleDateFormat("yyyy-MM-dd").parse(FechaFin);

			if (fechaI.compareTo(fechaF) > 0) {
				System.out.println("Date1 is after Date2");
				fechaI = new SimpleDateFormat("yyyy-MM-dd").parse(FechaFin);
				fechaF = new SimpleDateFormat("yyyy-MM-dd").parse(FechaInicio);

			}

			Enfermedad vac = enfermedadService.listarPorId(idEnfermedad);

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			titulo = "EvolucionEnfermedadEdad_";
			SubTitulo = "Evolución en el tiempo por rango de edad enfermedad: " + vac.getNombre() + " - "
					+ dateFormat.format(fechaI) + " - " + dateFormat.format(fechaF) + " - Rango Edad: "
					+ edadMinima.toString() + " - " + edadMaxima.toString();

			List<ReporteActoVacunalDTO> aux = reporteService.listarPorEdad(dateFormat.format(fechaI),
					dateFormat.format(fechaF), edadMinima, edadMaxima, idEnfermedad);
			
			imprimirListadoBar(aux);

		} catch (VacunasUyException | ParseException e) {
			logger.error("evolucionEnfermedadEdad: " + e.getMessage());
		}

	}

	public void listarPorSectorLaboral() {
		try {

			fechas = new ArrayList<String>();
			Date fechaI = new SimpleDateFormat("yyyy-MM-dd").parse(FechaInicio);
			Date fechaF = new SimpleDateFormat("yyyy-MM-dd").parse(FechaFin);

			if (fechaI.compareTo(fechaF) > 0) {
				System.out.println("Date1 is after Date2");
				fechaI = new SimpleDateFormat("yyyy-MM-dd").parse(FechaFin);
				fechaF = new SimpleDateFormat("yyyy-MM-dd").parse(FechaInicio);

			}

			Enfermedad vac = enfermedadService.listarPorId(idEnfermedad);

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			titulo = "ListarPorSectorLaboral_";
			SubTitulo = "Evolución en el tiempo por sector laboral vacuna: " + vac.getNombre() + " - "
					+ dateFormat.format(fechaI) + " - " + dateFormat.format(fechaF) + " -\r\t Sector Laboral: "
					+ idSectorLaboral;

			List<ReporteActoVacunalDTO> aux = reporteService.listarPorSectorLaboral(dateFormat.format(fechaI),
					dateFormat.format(fechaF), idSectorLaboral, idEnfermedad);

			imprimirListadoBar(aux);

		} catch (VacunasUyException | ParseException e) {
			logger.error("listarPorSectorLaboral: " + e.getMessage());
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

	private void imprimir(List<ReporteVacunaPDF> datos) {
		logger.error(reporte.size());

		Document document = new Document(PageSize.A4);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Date hoy = new Date();
		try {

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String strDate = dateFormat.format(hoy);

			PdfWriter writer = PdfWriter.getInstance(document, baos);
			document.open();
			Font fuente1 = new Font();
			Font fuente2 = new Font();
			fuente1.setSize(20);
			fuente2.setSize(10);

			document.add(new Paragraph("VacunasUY \n", fuente1));
			document.add(new Paragraph("\n", fuente2));
			document.add(new Paragraph("Fecha: " + strDate + "\n", fuente2));
			document.add(new Paragraph("\n", fuente2));
			document.add(new Paragraph(SubTitulo + "\n", fuente2));
			LineSeparator linea = new LineSeparator();
			linea.setOffset(-8.0f);
			linea.setAlignment(Element.ALIGN_LEFT);
			linea.setPercentage(100.0f);
			document.add(linea);

			Font font = FontFactory.getFont(FontFactory.HELVETICA);
			font.setSize(10);
			font.setColor(CMYKColor.BLACK);

			// define table header cell
			PdfPCell cell = new PdfPCell();
			PdfPCell cell2 = new PdfPCell();
			cell.setBackgroundColor(CMYKColor.GRAY);
			cell.setPadding(5);

			PdfPTable table2 = new PdfPTable(2);
			table2.setWidthPercentage(100.0f);
			table2.setWidths(new float[] { 5.0f, 2.0f });
			table2.setSpacingBefore(10);

			// write table header
			cell.setPhrase(new Phrase("Entidad", font));
			table2.addCell(cell);

			cell.setPhrase(new Phrase("Cantidad", font));
			table2.addCell(cell);

			for (ReporteVacunaPDF f : datos) {
				cell2.setPhrase(new Phrase(f.getNombre(), font));
				table2.addCell(cell2);

				cell2.setPhrase(new Phrase(f.getCantidad().toString(), font));
				table2.addCell(cell2);

			}

			document.add(table2);

			LineSeparator linea2 = new LineSeparator();
			linea2.setOffset(-8.0f);
			linea2.setPercentage(100.0f);
			document.add(linea2);

		} catch (Exception e) {
			logger.error("Error Imprimir: " + e.getMessage());
		}

		document.close();

		DateFormat dateFormatFile = new SimpleDateFormat("yyyyMMdd_HHmm");
		String strDateFile = dateFormatFile.format(hoy);

		FacesContext context = FacesContext.getCurrentInstance();
		Object response = context.getExternalContext().getResponse();
		if (response instanceof HttpServletResponse) {
			HttpServletResponse hsr = (HttpServletResponse) response;
			hsr.setContentType("application/pdf");
			hsr.setHeader("Content-disposition", "attachment;filename=/" + titulo + strDateFile + ".pdf");
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

	private void imprimirListado(List<ReporteEvolucionTiempoDTO> datos, String serieName) {

		Document document = new Document(PageSize.A4);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Date hoy = new Date();
		try {

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String strDate = dateFormat.format(hoy);

			PdfWriter writer = PdfWriter.getInstance(document, baos);
			document.open();
			Font fuente1 = new Font();
			Font fuente2 = new Font();
			fuente1.setSize(20);
			fuente2.setSize(10);

			document.add(new Paragraph("VacunasUY \n", fuente1));
			document.add(new Paragraph("\n", fuente2));
			document.add(new Paragraph("Fecha: " + strDate + "\n", fuente2));
			document.add(new Paragraph("\n", fuente2));
			document.add(new Paragraph(SubTitulo + "\n", fuente2));

			LineSeparator linea = new LineSeparator();
			linea.setOffset(-8.0f);
			linea.setAlignment(Element.ALIGN_LEFT);
			linea.setPercentage(100.0f);
			document.add(linea);

			Font font = FontFactory.getFont(FontFactory.HELVETICA);
			font.setSize(10);
			font.setColor(CMYKColor.BLACK);

			// define table header cell
			PdfPCell cell = new PdfPCell();
			PdfPCell cell2 = new PdfPCell();
			cell.setBackgroundColor(CMYKColor.GRAY);
			cell.setPadding(5);

			PdfPTable table2 = new PdfPTable(2);
			table2.setWidthPercentage(100.0f);
			table2.setWidths(new float[] { 5.0f, 2.0f });
			table2.setSpacingBefore(10);

			// write table header
			cell.setPhrase(new Phrase("Fecha", font));
			table2.addCell(cell);

			cell.setPhrase(new Phrase("Cantidad", font));
			table2.addCell(cell);

			TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();
			TimeSeries series = new TimeSeries(serieName);

			for (ReporteEvolucionTiempoDTO f : datos) {
				Date fechaX = new SimpleDateFormat("yyyy-MM-dd").parse(f.getFecha());
				series.add(new Day(fechaX), Double.valueOf(f.getCantidad()));

				cell2.setPhrase(new Phrase(f.getFecha(), font));
				table2.addCell(cell2);

				cell2.setPhrase(new Phrase(String.valueOf(f.getCantidad()), font));
				table2.addCell(cell2);

			}

			timeSeriesCollection.addSeries(series);

			XYDataset dataset = timeSeriesCollection;

			// Create chart
			JFreeChart chart = ChartFactory.createTimeSeriesChart("", // Chart
					"Fecha", // X-Axis Label
					"Cantidad", // Y-Axis Label
					dataset);

			BufferedImage bufferedImage = chart.createBufferedImage(500, 300);
			ByteArrayOutputStream baosi = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "png", baosi);
			Image iTextImage = Image.getInstance(baosi.toByteArray());

			LineSeparator linea1 = new LineSeparator();
			linea1.setOffset(-8.0f);
			linea1.setPercentage(100.0f);

			Paragraph p = new Paragraph();
			p.add(linea1);
			document.add(p);

			document.add(iTextImage);

			LineSeparator linea2 = new LineSeparator();
			linea2.setOffset(-8.0f);
			linea2.setPercentage(100.0f);
			document.add(linea2);

			document.add(table2);

			LineSeparator linea3 = new LineSeparator();
			linea3.setOffset(-8.0f);
			linea3.setPercentage(100.0f);
			document.add(linea3);

		} catch (Exception e) {
			logger.error("Error imprimirListado: " + e.getMessage());
		}

		document.close();

		DateFormat dateFormatFile = new SimpleDateFormat("yyyyMMdd_HHmm");
		String strDateFile = dateFormatFile.format(hoy);

		FacesContext context = FacesContext.getCurrentInstance();
		Object response = context.getExternalContext().getResponse();
		if (response instanceof HttpServletResponse) {
			HttpServletResponse hsr = (HttpServletResponse) response;
			hsr.setContentType("application/pdf");
			hsr.setHeader("Content-disposition", "attachment;filename=/" + titulo + strDateFile + ".pdf");
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

	private void imprimirListadoBar(List<ReporteActoVacunalDTO> datos) {

		Document document = new Document(PageSize.A4);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Date hoy = new Date();
		try {

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String strDate = dateFormat.format(hoy);

			PdfWriter writer = PdfWriter.getInstance(document, baos);
			document.open();
			Font fuente1 = new Font();
			Font fuente2 = new Font();
			fuente1.setSize(20);
			fuente2.setSize(10);

			document.add(new Paragraph("VacunasUY \n", fuente1));
			document.add(new Paragraph("\n", fuente2));
			document.add(new Paragraph("Fecha: " + strDate + "\n", fuente2));
			document.add(new Paragraph("\n", fuente2));
			document.add(new Paragraph(SubTitulo + "\n", fuente2));

			LineSeparator linea = new LineSeparator();
			linea.setOffset(-8.0f);
			linea.setAlignment(Element.ALIGN_LEFT);
			linea.setPercentage(100.0f);
			document.add(linea);

			Font font = FontFactory.getFont(FontFactory.HELVETICA);
			font.setSize(10);
			font.setColor(CMYKColor.BLACK);

			// define table header cell
			PdfPCell cell = new PdfPCell();
			PdfPCell cell2 = new PdfPCell();
			cell.setBackgroundColor(CMYKColor.GRAY);
			cell.setPadding(5);

			PdfPTable table2 = new PdfPTable(2);
			table2.setWidthPercentage(100.0f);
			table2.setWidths(new float[] { 5.0f, 2.0f });
			table2.setSpacingBefore(10);

			// write table header
			cell.setPhrase(new Phrase("Vacuna", font));
			table2.addCell(cell);

			cell.setPhrase(new Phrase("Cantidad", font));
			table2.addCell(cell);

			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			for (ReporteActoVacunalDTO f : datos) {
				dataset.addValue(Double.valueOf(f.getCantidad()), f.getVacuna(), "Cantidad");

				cell2.setPhrase(new Phrase(f.getVacuna(), font));
				table2.addCell(cell2);

				cell2.setPhrase(new Phrase(String.valueOf(f.getCantidad()), font));
				table2.addCell(cell2);

			}

			// Create chart
			JFreeChart barChart = ChartFactory.createBarChart("", "Vacuna", "Cantidad", dataset,
					PlotOrientation.VERTICAL, true, true, false);

			BufferedImage bufferedImage = barChart.createBufferedImage(500, 300);
			ByteArrayOutputStream baosi = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "png", baosi);
			Image iTextImage = Image.getInstance(baosi.toByteArray());

			LineSeparator linea1 = new LineSeparator();
			linea1.setOffset(-8.0f);
			linea1.setPercentage(100.0f);

			Paragraph p = new Paragraph();
			p.add(linea1);
			document.add(p);

			document.add(iTextImage);

			LineSeparator linea2 = new LineSeparator();
			linea2.setOffset(-8.0f);
			linea2.setPercentage(100.0f);
			document.add(linea2);

			document.add(table2);

			LineSeparator linea3 = new LineSeparator();
			linea3.setOffset(-8.0f);
			linea3.setPercentage(100.0f);
			document.add(linea3);

		} catch (Exception e) {
			logger.error("Error imprimirListadoBar: " + e.getMessage());
		}

		document.close();

		DateFormat dateFormatFile = new SimpleDateFormat("yyyyMMdd_HHmm");
		String strDateFile = dateFormatFile.format(hoy);

		FacesContext context = FacesContext.getCurrentInstance();
		Object response = context.getExternalContext().getResponse();
		if (response instanceof HttpServletResponse) {
			HttpServletResponse hsr = (HttpServletResponse) response;
			hsr.setContentType("application/pdf");
			hsr.setHeader("Content-disposition", "attachment;filename=/" + titulo + strDateFile + ".pdf");
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
