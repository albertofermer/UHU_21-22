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
	 * Acci�n que realizar� el agente cuando se cumpla el antecedente de hayMalos.
	 * El agente se dirigir� al enemigo marcado por la posici�n del vector capturar.
	 */
	@Override
	public ACTIONS doAction(Mundo m) {

		//Obtiene el vector posici�n del avatar y del enemigo que ha seleccionado para capturar.
		posAvatar = m.getAvatar();
		capturar = m.getCapturar();
		
		// Si el enemigo se encuentra saliendo de la escalera, ir a la posici�n superior.
		if ((int)(capturar.y/dimBlock) == numFilas - 2) {capturar.y = numFilas - 3;}	
		
		// Dependiendo de la posici�n del enemigo respecto al avatar, 
		// este deber� realizar una acci�n diferente.
		
		if ((int)capturar.y/dimBlock < posAvatar[1]) {return ACTIONS.ACTION_UP;}
		if ((int)capturar.y/dimBlock > posAvatar[1]) {return ACTIONS.ACTION_DOWN;}
		if ((int)capturar.x/dimBlock > posAvatar[0]) {return ACTIONS.ACTION_RIGHT;}
		if ((int)capturar.x/dimBlock < posAvatar[0]) {return ACTIONS.ACTION_LEFT;}
		
		// Como el m�todo debe devolver siempre una acci�n, se devuelve no hacer nada en caso de que algo falle.
		return ACTIONS.ACTION_NIL;
		

	}
}
