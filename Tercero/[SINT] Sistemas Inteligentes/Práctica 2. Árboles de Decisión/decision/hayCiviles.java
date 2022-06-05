package decision;

import Cerebro.Condicion;
import si2022.p02.albertofernandez.Mundo;

public class hayCiviles implements Condicion {

	public hayCiviles() {
	}
	/**
	 * Indica si hay algún civil cayéndose y no tiene nubes debajo.
	 */
	@Override
	public boolean seCumple(Mundo m) {
		
		//Obtiene los civiles que existeno.
		// Si esa lista no es vacía significa que se cumple el antecedente.
		if(!m.getBuenos().isEmpty() || !m.getCayendo().isEmpty()) return true;
		else {
			//System.out.println("NO HAY CIVILES!");
			return false;
		}
		
	}
	
	public String toString() {
		
		return "hayCiviles";
		
		
	}
}
