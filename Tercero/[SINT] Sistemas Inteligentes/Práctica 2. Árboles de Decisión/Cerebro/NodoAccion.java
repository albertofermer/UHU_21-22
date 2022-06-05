package Cerebro;

import ontology.Types.ACTIONS;
import si2022.p02.albertofernandez.Mundo;

public class NodoAccion extends NodoArbol{

	private Accion a = null;
	public NodoAccion(Accion a) {
		this.a = a;
		this.izq = null;
		this.der = null;
	}
	
	public ACTIONS doAction(Mundo m) {
		//System.out.println(a.toString());
		return a.doAction(m);
		
	}
	
	public NodoArbol decide(Mundo m) {
		return this;
	}
}
