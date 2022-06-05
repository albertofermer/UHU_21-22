package Operadores;

import ElementosDeBusqueda.Operador;
import ontology.Types.ACTIONS;

public class Arriba extends Operador{

	public Arriba() {
	}

	@Override
	public ACTIONS getAccion() {
		return ACTIONS.ACTION_UP;
	}

}
