package decision;

import Cerebro.Condicion;
import si2022.p02.albertofernandez.Mundo;

public class numTicks implements Condicion {

	private int t;
	public numTicks(int tick) {
	
		t = tick;
	
	}
	/**
	 * Indica si hay algún civil cayéndose y no tiene nubes debajo.
	 */
	@Override
	public boolean seCumple(Mundo m) {
		
		//Obtiene los civiles que están cayendo y se encuentran en peligro.
		// Si esa lista no es vacía significa que se cumple el antecedente.
		if(m.getTicks() > t) return true;
		else return false;
		
	}
	
	public String toString() {
		
		return "civiPeligro";
		
		
	}
}
