package accion;

import Cerebro.Accion;
import core.game.Observation;
import ontology.Types.ACTIONS;
import si2022.p02.albertofernandez.Mundo;

public class salvarBueno implements Accion {

	private int[] posAvatar;
	private int dimBlock;
	private Observation objetivo;
	

	public salvarBueno(Mundo m) {
		dimBlock = m.getDimensionBlock();
	}
	
	/**
	 * Acción que realiza el agente cuando un civil esté cayendo y no
	 * tenga ninguna nube debajo.
	 */
	@Override
	public ACTIONS doAction(Mundo m) {
		// Obtención de las posiciones del avatar y del civil que se quiere salvar.
		posAvatar = m.getAvatar();
		objetivo  = m.getSalvar();
		
		//System.out.println("Salvar " + (int)objetivo.x/dimBlock + " : " + (int)objetivo.y/dimBlock);
		// Dependiendo de la posición dle civil respecto a la del avatar, este deberá
		// realizar una acción diferente.
		
		if ((int)objetivo.position.x/dimBlock > posAvatar[0]) {return ACTIONS.ACTION_RIGHT;}
		if ((int)objetivo.position.x/dimBlock < posAvatar[0]) {return ACTIONS.ACTION_LEFT;}
		if ((int)objetivo.position.y/dimBlock > posAvatar[1]) {return ACTIONS.ACTION_DOWN;}
		if ((int)objetivo.position.y/dimBlock < posAvatar[1]) {return ACTIONS.ACTION_UP;}
		return ACTIONS.ACTION_NIL;

	}
	
	public String toString() {
		
		return "Salvar Civil";
	}
}
