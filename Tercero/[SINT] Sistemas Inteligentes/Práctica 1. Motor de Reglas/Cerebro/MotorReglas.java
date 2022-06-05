package Cerebro;

import java.util.List;

import si2022.p02.albertofernandez.Mundo;

public class MotorReglas implements Motor {
	
	private List<Regla> reglas;
	
	public MotorReglas(List<Regla> reglas) {
		this.reglas = reglas;
	}
	
	/**
	 * Lanza la primera regla que se cumpla del listado de reglas.
	 */
	@Override
	public Regla disparo(Mundo m) {					// Se lanza la primera regla que se cumpla
		for (int i = 0; i < reglas.size(); i++) {
			if (reglas.get(i).seCumple(m)) {
					//System.out.println("Se cumple la regla - " + i);
				return reglas.get(i);			
			}
		}
		
		return null;								// La última regla será no hacer nada y se debe cumplir siempre.
													// Por lo que no llegará nunca a ejecutarse esta línea.

	}
}
