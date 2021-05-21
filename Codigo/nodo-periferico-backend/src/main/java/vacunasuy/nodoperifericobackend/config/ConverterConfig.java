package vacunasuy.nodoperifericobackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import vacunasuy.nodoperifericobackend.converter.AgendaConverter;
import vacunasuy.nodoperifericobackend.converter.VacunadorConverter;
import vacunasuy.nodoperifericobackend.converter.VacunatorioConverter;

@Configuration
public class ConverterConfig {
	
	@Bean
	public VacunatorioConverter getVacunatorioConverter() {
		return new VacunatorioConverter();
	}
	
	@Bean
	public VacunadorConverter getVacunadorConverter() {
		return new VacunadorConverter();
	}
	
	@Bean
	public AgendaConverter getAgendaConverter() {
		return new AgendaConverter();
	}
		
}
