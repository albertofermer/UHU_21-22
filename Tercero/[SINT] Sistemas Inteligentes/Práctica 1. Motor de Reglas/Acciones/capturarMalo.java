package Acciones;


import Cerebro.Accion;
import ontology.Types.ACTIONS;
import si2022.p02.albertofernandez.Mundo;
import tools.Vector2d;

public class capturarMalo implements Accion {

	private int[] posAvatar;
	private int dimBlock;
	private int numFilas;
	private Vector2d capturar;

	public capturarMalo(Mundo m) {
		dimBlock = m.getDimensionBlock();
		numFilas = m.getnFila();
	}
	/**
	 * Acción que realizará el agente cuando se cumpla el antecedente de hayMalos.
	 * El agente se dirigirá al enemigo marcado por la posición del vector capturar.
	 */
	@Override
	public ACTIONS doAction(Mundo m) {

		//Obtiene el vector posición del avatar y del enemigo que ha seleccionado para capturar.
		posAvatar = m.getAvatar();
		capturar = m.getCapturar();
		
		// Si el enemigo se encuentra saliendo de la escalera, ir a la posición superior.
		if ((int)(capturar.y/dimBlock) == numFilas - 2) {capturar.y = numFilas - 3;}	
		
		// Dependiendo de la posición del enemigo respecto al avatar, 
		// este deberá realizar una acción diferente.
		
		if ((int)capturar.y/dimBlock < posAvatar[1]) {return ACTIONS.ACTION_UP;}
		if ((int)capturar.y/dimBlock > posAvatar[1]) {return ACTIONS.ACTION_DOWN;}
		if ((int)capturar.x/dimBlock > posAvatar[0]) {return ACTIONS.ACTION_RIGHT;}
		if ((int)capturar.x/dimBlock < posAvatar[0]) {return ACTIONS.ACTION_LEFT;}
		
		// Como el método debe devolver siempre una acción, se devuelve no hacer nada en caso de que algo falle.
		return ACTIONS.ACTION_NIL;
		

	}
}
