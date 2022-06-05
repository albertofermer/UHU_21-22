package accion;


import Cerebro.Accion;
import core.game.Observation;
import ontology.Types.ACTIONS;
import si2022.p02.albertofernandez.Mundo;

public class capturarDerecha implements Accion {

	private int[] posAvatar;
	private int dimBlock;
	private int numFilas;
	private Observation capturar;

	public capturarDerecha(Mundo m) {
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
		
		//System.out.println("Capturar Der: " + (int)capturar.x/dimBlock + " : " + (int)capturar.y/dimBlock  + " -- Num Enemigos Der = " + m.getNumEnemDer());
		// Si el enemigo se encuentra saliendo de la escalera, ir a la posición superior.
		if ((int)(capturar.position.y/dimBlock) == numFilas - 2) {capturar.position.y = (numFilas - 3) * dimBlock;}	
		
		// Dependiendo de la posición del enemigo respecto al avatar, 
		// este deberá realizar una acción diferente.
		
		if ((int)capturar.position.y/dimBlock < posAvatar[1]) {return ACTIONS.ACTION_UP;}
		if ((int)capturar.position.y/dimBlock > posAvatar[1]) {return ACTIONS.ACTION_DOWN;}
		if ((int)capturar.position.x/dimBlock > posAvatar[0]) {return ACTIONS.ACTION_RIGHT;}
		if ((int)capturar.position.x/dimBlock < posAvatar[0]) {return ACTIONS.ACTION_LEFT;}
		
		// Como el método debe devolver siempre una acción, se devuelve no hacer nada en caso de que algo falle.
		return ACTIONS.ACTION_NIL;
		

	}
	
	public String toString() {
		
		return "Capturar Derecha";
	}
}
