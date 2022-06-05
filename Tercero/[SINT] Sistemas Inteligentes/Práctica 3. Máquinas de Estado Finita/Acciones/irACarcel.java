package Acciones;


import Cerebro.Accion;
import ontology.Types.ACTIONS;
import si2022.p02.albertofernandez.Mundo;

public class irACarcel extends Accion {

	private int[] posCarcel;
	private int[] posAvatar;

	public irACarcel(Mundo m) {
		posCarcel = m.getCarcel();
	}
	

	@Override
	public ACTIONS doAction(Mundo m) {
		
		posAvatar = m.getAvatar();
		
		if (posCarcel[0] < posAvatar[0]) {return ACTIONS.ACTION_LEFT;}
		else if (posCarcel[0] > posAvatar[0]) {return ACTIONS.ACTION_RIGHT;}
		else if (posCarcel[1] > posAvatar[1]) {return ACTIONS.ACTION_DOWN;}
		else /*(posCarcel[1] < posAvatar[1]) */{return ACTIONS.ACTION_UP;}

	}
	
	public String toString() {
		
		return "Ir a Carcel";
	}

}
