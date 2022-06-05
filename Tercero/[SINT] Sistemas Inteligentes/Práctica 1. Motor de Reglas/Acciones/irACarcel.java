package Acciones;


import Cerebro.Accion;
import ontology.Types.ACTIONS;
import si2022.p02.albertofernandez.Mundo;

public class irACarcel implements Accion {

	private int[] posCarcel;
	private int[] posAvatar;

	public irACarcel(Mundo m) {
		posCarcel = m.getCarcel();
	}
	

	@Override
	public ACTIONS doAction(Mundo m) {
		
		posAvatar = m.getAvatar();
		
		if (posCarcel[0] < posAvatar[0]) {return ACTIONS.ACTION_LEFT;}
		if (posCarcel[0] > posAvatar[0]) {return ACTIONS.ACTION_RIGHT;}
		if (posCarcel[1] > posAvatar[1]) {return ACTIONS.ACTION_DOWN;}
		if (posCarcel[1] < posAvatar[1]) {return ACTIONS.ACTION_UP;}
				
		return ACTIONS.ACTION_NIL;

	}

}
