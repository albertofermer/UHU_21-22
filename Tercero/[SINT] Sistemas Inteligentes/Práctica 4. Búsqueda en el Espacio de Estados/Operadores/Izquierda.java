package Operadores;

import ElementosDeBusqueda.Operador;
import ontology.Types.ACTIONS;

public class Izquierda extends Operador{

	public Izquierda() {
	}

	@Override
	public ACTIONS getAccion() {
		return ACTIONS.ACTION_LEFT;
	}

}
