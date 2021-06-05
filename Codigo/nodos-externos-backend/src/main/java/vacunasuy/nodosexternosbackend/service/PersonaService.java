package vacunasuy.nodosexternosbackend.service;

import java.time.LocalDate;
import org.springframework.stereotype.Service;
import vacunasuy.nodosexternosbackend.entity.Persona;

@Service
public class PersonaService {

	public Persona getPersona(String cedula) {
		LocalDate fechaNacimiento = randomDate();		
		String sectorLaboral =  randomSectorLaboral(fechaNacimiento);		
		Persona persona = new Persona (cedula, fechaNacimiento, sectorLaboral);
		System.out.println("Se recibe solicitud - Documento: " + cedula);
		System.out.println("Se asignan datos: " + persona.toString());
		return persona;
	}
	
	// Métodos auxiliares
	
	LocalDate randomDate() {
		//año
		int añoMin=1910;
		int añoMax=2021;
		int año = randomNum(añoMin, añoMax);
		
		//mes
		int mesMin=1;
		int mesMax=12;
		int mes = randomNum(mesMin, mesMax);
		
		//dia
		int diaMin=1;
		int diaMax=30;
		if (mes31dias(mes)){
			diaMax=31;
		}else if((mes==2) && (añoBisiesto(año))) {
			diaMax=29;
		}else if(mes==2) {
			diaMax=28;
		}
		int dia = randomNum(diaMin, diaMax);		
		
		LocalDate fecha = LocalDate.of(año, mes, dia);
		return fecha;
	}
	
	String randomSectorLaboral(LocalDate fechaNacimiento) {
		LocalDate hoy = LocalDate.now();
		String [] sectoresLaborales = {"Bombero", "Militar", "Personal de la Salud", "Policía",
                "Docente", "Otra ocupación", "No tiene", "Jubilado"};
		String sectorLaboral = "";
		if((hoy.getYear() - fechaNacimiento.getYear()) < 16 ) {
			sectorLaboral = "No tiene";
		}else if((hoy.getYear() - fechaNacimiento.getYear()) > 90) {
			sectorLaboral = "Jubilado";
		}else if((hoy.getYear() - fechaNacimiento.getYear()) < 60) {
			sectorLaboral= sectoresLaborales[randomNum(0, sectoresLaborales.length-2)];
		}else{
			sectorLaboral= sectoresLaborales[randomNum(0, sectoresLaborales.length-1)];
		}
		return sectorLaboral;
	}
	
	boolean añoBisiesto(int año) {
		LocalDate aux = LocalDate.of(año, 1, 1);
		return aux.isLeapYear();
	}
	
	boolean mes31dias(int mes) {
		switch(mes) {
			case 4:
				return false;
			case 6:
				return false;
			case 9:
				return false;
			case 11:
				return false;
			default: return true;
		}		
	}
	
	int randomNum(int min, int max) {
	    int random = (int)Math.floor(Math.random()*(max-min+1)+min);
		return random;
	}
	
}
