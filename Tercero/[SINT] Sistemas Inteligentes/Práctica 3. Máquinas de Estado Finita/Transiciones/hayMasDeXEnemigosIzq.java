package Transiciones;

import Cerebro.Condicion;
import si2022.p02.albertofernandez.Mundo;

public class hayMasDeXEnemigosIzq extends Condicion {

	private int num = 0;
	public hayMasDeXEnemigosIzq(int num) {
		this.num = num;
	}
	/**
	 * Indica si hay algún civil cayéndose y no tiene nubes debajo.
	 */
	@Override
	public boolean seCumple(Mundo m) {
		
		//Obtiene los civiles que están cayendo y se encuentran en peligro.
		// Si esa lista no es vacía significa que se cumple el antecedente.
		//System.out.println( (int)m.getCapturarDer().x);
		//System.out.println(m.getNumEnemIzq() + " - " + m.getNUMENEMIGOSIZQ());
		if((m.getMalos().size()) > 0 && m.getNumEnemIzq() > num) {
			//System.out.println("TRUE IZQUIERDA");
			//System.out.println("Izq: " + (m.getNumEnemIzq() - 1) + " - Der: " + (m.getNumEnemDer() - 1));
			//System.out.println(m.getNumEnemIzq());
			return true;
		}
		else return false;
		
	}

	public String toString() {
		
		return "hayMasdeDosEnemigosIzq";
		
		
	}
}
