package Transiciones;

import Cerebro.Condicion;
import si2022.p02.albertofernandez.Mundo;

public class mochilaLlena extends Condicion {

	public mochilaLlena() {
	}
	/**
	 * Esta regla se cumplirá si la mochila está llena.
	 */
	@Override
	public boolean seCumple(Mundo m) {
		
		return m.mochilaLlena();
	}
	
	public String toString() {
		
		return "mochilaLlena";
		
		
	}

}
