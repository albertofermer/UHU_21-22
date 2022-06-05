package Operadores;

import ElementosDeBusqueda.Operador;
import ontology.Types.ACTIONS;

public class NIL extends Operador{

	public NIL() {
	}

	@Override
	public ACTIONS getAccion() {
		return ACTIONS.ACTION_NIL;
	}

}
