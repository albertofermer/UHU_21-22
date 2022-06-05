package Cerebro;

import si2022.p02.albertofernandez.Mundo;

public class Transicion {

	protected Estado eObjetivo;
	protected Condicion condicion;

	public Transicion(Condicion c, Estado e) {
		eObjetivo = e;
		condicion = c;
	}

	public boolean seDispara(Mundo m) {
		return condicion.seCumple(m);
	}

	public Estado siguienteEstado() {
		return eObjetivo;
	}

}
