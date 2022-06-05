package accion;

import Cerebro.Accion;
import ontology.Types.ACTIONS;
import si2022.p02.albertofernandez.Mundo;
import tools.Vector2d;

public class irAlCentro implements Accion {

	private int numFilas;
	private int numColumnas;
	private int dimBlock;
	private int[] posAvatar;
	private Vector2d destino = new Vector2d();
	public irAlCentro(Mundo m) {
		
		dimBlock = m.getDimensionBlock();
		numFilas = m.getnFila();
		numColumnas = m.getnCol();
		// La posición central del mapa es la siguiente:
		
		destino.x = (double)((int)(numColumnas/2)); //[11,11]
		destino.y = (double)((int)numFilas - 3);
		//System.out.println(destino.x + " : " + destino.y);
	}
	

	@Override
	public ACTIONS doAction(Mundo m) {

		//Obtengo la posición del avatar
		posAvatar = m.getAvatar();
		//destino = m.getMasCercaDelSuelo().position;
		destino.x = 11 * dimBlock;
		destino.y = 12 * dimBlock;
		//System.out.println((int)destino.x / dimBlock+ " : " + (int) destino.y / dimBlock );
		//System.out.println(m.getNumEnemDer() + " - " + m.getNumEnemIzq());
		//Dependiendo de la posición del avatar, este deberá realizar diferentes
		//acciones para llegar al centro.
		
		if ((int)destino.x / dimBlock > posAvatar[0]) {return ACTIONS.ACTION_RIGHT;}
		if ((int)destino.x / dimBlock < posAvatar[0]) {return ACTIONS.ACTION_LEFT;}
		if ((int)destino.y / dimBlock < posAvatar[1]) {return ACTIONS.ACTION_UP;}
		if ((int)destino.y / dimBlock > posAvatar[1]) {return ACTIONS.ACTION_DOWN;}

		return ACTIONS.ACTION_NIL;

	}
	
	public String toString() {
		
		return " ";
	}

}
