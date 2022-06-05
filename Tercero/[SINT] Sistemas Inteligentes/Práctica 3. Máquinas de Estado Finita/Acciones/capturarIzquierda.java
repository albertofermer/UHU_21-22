package Acciones;


import Cerebro.Accion;
import core.game.Observation;
import ontology.Types.ACTIONS;
import si2022.p02.albertofernandez.Mundo;

public class capturarIzquierda extends Accion {

	private int[] posAvatar;
	private int dimBlock;
	private int numFilas;
	private Observation capturar;

	public capturarIzquierda(Mundo m) {
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
		//System.out.println("Dimblock: " + dimBlock);
		//System.out.println("Capturar : " +(int) capturar.position.x /dimBlock + " : " +(int) capturar.position.y/dimBlock);
		//System.out.println("Capturar Izq: " + (int)capturar.x/dimBlock + " : " + (int)capturar.y/dimBlock + " -- Num Enemigos Izq = " + m.getNumEnemIzq());
		// Si el enemigo se encuentra saliendo de la escalera, ir a la posición superior.
		if ((int)(capturar.position.y/dimBlock) == numFilas - 2) {capturar.position.y = (numFilas - 3) * dimBlock;}	
		
		// Dependiendo de la posición del enemigo respecto al avatar, 
		// este deberá realizar una acción diferente.
		
		
		if ((int)capturar.position.y/dimBlock < posAvatar[1]) {return ACTIONS.ACTION_UP;}
		else if ((int)capturar.position.y/dimBlock > posAvatar[1]) {return ACTIONS.ACTION_DOWN;}
		else if ((int)capturar.position.x/dimBlock < posAvatar[0]) {return ACTIONS.ACTION_LEFT;}
		else if ((int)capturar.position.x/dimBlock > posAvatar[0]) {return ACTIONS.ACTION_RIGHT;}
		
		return null;

	}
	public String toString() {
		
		return "Capturar Izquierda";
	}
}
