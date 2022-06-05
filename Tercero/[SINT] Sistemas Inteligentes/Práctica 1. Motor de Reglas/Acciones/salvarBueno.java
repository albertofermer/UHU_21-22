package Acciones;

import Cerebro.Accion;
import ontology.Types.ACTIONS;
import si2022.p02.albertofernandez.Mundo;
import tools.Vector2d;

public class salvarBueno implements Accion {

	private int[] posAvatar;
	private int dimBlock;
	private Vector2d objetivo;
	

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
		
		// Dependiendo de la posición dle civil respecto a la del avatar, este deberá
		// realizar una acción diferente.
		
		if ((int)objetivo.x/dimBlock > posAvatar[0]) {return ACTIONS.ACTION_RIGHT;}
		if ((int)objetivo.x/dimBlock < posAvatar[0]) {return ACTIONS.ACTION_LEFT;}
		if ((int)objetivo.y/dimBlock > posAvatar[1]) {return ACTIONS.ACTION_DOWN;}
		if ((int)objetivo.y/dimBlock < posAvatar[1]) {return ACTIONS.ACTION_UP;}
		return ACTIONS.ACTION_NIL;

	}
}
