package Transiciones;

import Cerebro.Condicion;
import si2022.p02.albertofernandez.Mundo;

public class True extends Condicion {

	public True() {
	}
	/**
	 * En caso de que no se cumpla ninguna regla se deber�
	 * activar esta que siempre se cumplir�.
	 */
	@Override
	public boolean seCumple(Mundo m) {
		return true; 

	}

}
