package decision;

import Cerebro.Condicion;
import si2022.p02.albertofernandez.Mundo;

public class mochilaLlena implements Condicion {

	public mochilaLlena() {
	}
	/**
	 * Esta regla se cumplir� si la mochila est� llena.
	 */
	@Override
	public boolean seCumple(Mundo m) {
		
		return m.mochilaLlena();
	}
	
	public String toString() {
		
		return "mochilaLlena";
		
		
	}

}
