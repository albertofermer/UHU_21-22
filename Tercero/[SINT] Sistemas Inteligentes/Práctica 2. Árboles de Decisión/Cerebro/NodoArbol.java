package Cerebro;

import si2022.p02.albertofernandez.Mundo;

public abstract class NodoArbol {

	protected NodoArbol izq,der;	
	abstract public NodoArbol decide(Mundo m);
	

}
