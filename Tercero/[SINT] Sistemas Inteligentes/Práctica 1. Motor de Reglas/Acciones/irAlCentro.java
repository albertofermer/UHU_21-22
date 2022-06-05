package Acciones;

import Cerebro.Accion;
import ontology.Types.ACTIONS;
import si2022.p02.albertofernandez.Mundo;

public class irAlCentro implements Accion {

	private int numFilas;
	private int numColumnas;
	private int[] posAvatar;
	private int[] destino = new int[2];
	public irAlCentro(Mundo m) {
		
		numFilas = m.getnFila();
		numColumnas = m.getnCol();
		// La posición central del mapa es la siguiente:
		
		destino[0] = (int)(numColumnas/2) - 1;
		destino[1] = (int)numFilas - 3;
	}
	

	@Override
	public ACTIONS doAction(Mundo m) {

		
		//Obtengo la posición del avatar
		posAvatar = m.getAvatar();
		
		//Dependiendo de la posición del avatar, este deberá realizar diferentes
		//acciones para llegar al centro.
		
		if (destino[1] < posAvatar[1]) {return ACTIONS.ACTION_UP;}
		if (destino[1] > posAvatar[1]) {return ACTIONS.ACTION_DOWN;}
		
		if (destino[0] > posAvatar[0]) {return ACTIONS.ACTION_RIGHT;}
		if (destino[0] < posAvatar[0]) {return ACTIONS.ACTION_LEFT;}

		
		return ACTIONS.ACTION_NIL;

	}

}
