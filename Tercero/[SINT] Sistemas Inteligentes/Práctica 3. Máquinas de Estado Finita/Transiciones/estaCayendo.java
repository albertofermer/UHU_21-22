package Transiciones;

import Cerebro.Condicion;
import si2022.p02.albertofernandez.Mundo;

public class estaCayendo extends Condicion {

	public estaCayendo() {
	}
	/**
	 * Indica si hay algún civil cayéndose y no tiene nubes debajo.
	 */
	@Override
	public boolean seCumple(Mundo m) {
		
		//Obtiene los civiles que están cayendo y se encuentran en peligro.
		// Si esa lista no es vacía significa que se cumple el antecedente.
		if(m.getSalvar() != null && m.getSalvar().position.x != -1) return true;
		else return false;
		
	}
	
	public String toString() {
		
		return "civiPeligro";
		
		
	}
}
