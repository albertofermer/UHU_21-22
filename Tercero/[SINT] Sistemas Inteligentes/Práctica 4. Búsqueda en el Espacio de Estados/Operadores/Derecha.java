package Operadores;

import ElementosDeBusqueda.Operador;
import ontology.Types.ACTIONS;

public class Derecha extends Operador{

	public Derecha() {
	}

	@Override
	public ACTIONS getAccion() {
		return ACTIONS.ACTION_RIGHT;
	}

}
