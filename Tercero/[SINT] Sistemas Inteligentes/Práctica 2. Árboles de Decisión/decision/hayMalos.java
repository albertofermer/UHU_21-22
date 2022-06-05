package decision;

import Cerebro.Condicion;
import si2022.p02.albertofernandez.Mundo;

public class hayMalos implements Condicion {

	public hayMalos() {
	}
	/**
	 * La regla se cumple si existe alg�n enemigo.
	 */
	@Override
	public boolean seCumple(Mundo m) {
		return ((m.getMalos().size()) > 0);
	}

	public String toString() {
		
		return "hayMalos";
		
		
	}
}
