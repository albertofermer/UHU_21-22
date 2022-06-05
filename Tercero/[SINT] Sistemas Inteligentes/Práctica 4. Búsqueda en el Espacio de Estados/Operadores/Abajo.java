package Operadores;

import ElementosDeBusqueda.Operador;
import ontology.Types.ACTIONS;

public class Abajo extends Operador{

	public Abajo() {
	}

	@Override
	public ACTIONS getAccion() {
		
		return ACTIONS.ACTION_DOWN;
	}

}
