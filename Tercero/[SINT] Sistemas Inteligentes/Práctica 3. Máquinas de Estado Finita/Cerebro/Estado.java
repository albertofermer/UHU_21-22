package Cerebro;

import java.util.LinkedList;

public abstract class Estado {

	protected LinkedList<Transicion> transiciones = new LinkedList<Transicion>();
	
	public abstract Accion getAccion();
	
	public void addTransicion(Transicion tr) {	
		transiciones.add(tr);
	}
}
