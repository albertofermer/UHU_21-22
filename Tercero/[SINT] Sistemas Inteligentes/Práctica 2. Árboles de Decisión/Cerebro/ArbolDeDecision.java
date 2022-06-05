package Cerebro;

import ontology.Types.ACTIONS;
import si2022.p02.albertofernandez.Mundo;

public abstract class ArbolDeDecision extends Razonador{
	
	private NodoArbol raiz = null;
	
	
	public ArbolDeDecision(NodoArbol r) {
		raiz = r;
		 
	}
	
	public ACTIONS piensa(Mundo m) {
		return ((NodoAccion)raiz.decide(m)).doAction(m);
		
	}
}
