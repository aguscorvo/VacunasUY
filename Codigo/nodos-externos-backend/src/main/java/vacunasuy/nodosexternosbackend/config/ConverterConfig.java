package vacunasuy.nodosexternosbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import vacunasuy.nodosexternosbackend.converter.PersonaConverter;

@Configuration
public class ConverterConfig {
	
	@Bean
	public PersonaConverter getVacunatorioConverter() {
		return new PersonaConverter();
	}
		
}
