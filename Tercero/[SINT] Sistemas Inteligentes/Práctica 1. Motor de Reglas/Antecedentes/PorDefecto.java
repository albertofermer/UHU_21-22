package Antecedentes;

import Cerebro.Condicion;
import si2022.p02.albertofernandez.Mundo;

public class PorDefecto implements Condicion {

	public PorDefecto() {
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
