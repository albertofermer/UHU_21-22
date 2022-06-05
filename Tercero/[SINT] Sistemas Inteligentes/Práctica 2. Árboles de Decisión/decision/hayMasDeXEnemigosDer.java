package decision;

import Cerebro.Condicion;
import si2022.p02.albertofernandez.Mundo;

public class hayMasDeXEnemigosDer implements Condicion {

	private int num;
	public hayMasDeXEnemigosDer(int num) {
		this.num = num;
	}
	/**
	 * Indica si hay algún civil cayéndose y no tiene nubes debajo.
	 */
	@Override
	public boolean seCumple(Mundo m) {
		
		//Obtiene los civiles que están cayendo y se encuentran en peligro.
		// Si esa lista no es vacía significa que se cumple el antecedente.
		
		//System.out.println(m.getNumEnemDer() + " - " + m.getNUMENEMIGOSDER());

		if(m.getNumEnemDer() > num/*&& m.getNumEnemIzq() <= m.getNumEnemDer()*/) {
			//System.out.println("TRUE DERECHA");
			//System.out.println("Der: " + m.getNumEnemDer() + " - Izq: " + m.getNumEnemIzq());
			//System.out.println(m.getNumEnemDer());
			return true;
		}
		else return false;
		
	}
	
	public String toString() {
		
		return "hayMasdeDosEnemigosDer";
		
		
	}
}
