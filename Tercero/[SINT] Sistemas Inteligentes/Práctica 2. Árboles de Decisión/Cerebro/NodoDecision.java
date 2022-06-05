package Cerebro;

import si2022.p02.albertofernandez.Mundo;

public class NodoDecision extends NodoArbol{
	
	private Condicion pregunta = null;
	
	public NodoDecision(Condicion c) {
		pregunta = c;
		this.izq = null;
		this.der = null;
	}
	
	
	
	public void setIzq(NodoArbol izq) {
		this.izq = izq;
	}

	public void setDer(NodoArbol der) {
		this.der = der;
	}



	public NodoArbol getBranch(Mundo m) {
		if(pregunta.seCumple(m)) return izq;
		else return der;
		
	}
	
	public NodoArbol decide(Mundo m) {
		//System.out.println("\n" + pregunta.toString() + "? " + pregunta.seCumple(m));
		return this.getBranch(m).decide(m);
	}

}
