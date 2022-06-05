package Antecedentes;

import Cerebro.Condicion;
import si2022.p02.albertofernandez.Mundo;

public class hayMalos implements Condicion {

	public hayMalos() {
	}
	/**
	 * La regla se cumple si existe algún enemigo.
	 */
	@Override
	public boolean seCumple(Mundo m) {
		return ((m.getNumEnemDer() + m.getNumEnemIzq()) > 0);
	}

}
