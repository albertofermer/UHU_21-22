package si2022.p02.albertofernandez;

import Cerebro.*;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;

public class Agente extends AbstractPlayer {

	private Mundo mundo = null;
	private Razonador cerebro = null;
	
	/**
	 * Agente que resuelve el juego 89 de GVGAI (Superman) mediante un
	 * sistema basado en reglas.
	 * 
	 * @param so
	 * @param elapsedTimer
	 */
	public Agente(StateObservation so, ElapsedCpuTimer elapsedTimer) {
		
		// Creación  del mundo inicial.
		mundo = new Mundo(so);
		cerebro = new MotorArbol_89(mundo);
		

	}
	/**
	 * Acción que realiza el agente por cada tick del juego.
	 */
	@Override
	public ACTIONS act(StateObservation arg0, ElapsedCpuTimer arg1) {
		
		// Actualización de la visión del mundo.
		//System.out.println(arg1.elapsedNanos());
		mundo.actualizar(arg0);	
		ACTIONS a = cerebro.piensa(mundo);
		return a;
	}


}
