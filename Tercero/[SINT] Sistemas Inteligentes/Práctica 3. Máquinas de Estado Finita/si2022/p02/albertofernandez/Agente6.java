package si2022.p02.albertofernandez;

import Cerebro.*;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;

public class Agente6 extends AbstractPlayer {

	private Mundo mundo = null;
	private FSM_89 cerebro = null;
	
	/**
	 * Agente que resuelve el juego 89 de GVGAI (Superman) mediante un
	 * sistema basado en reglas.
	 * 
	 * @param so
	 * @param elapsedTimer
	 */
	public Agente6(StateObservation so, ElapsedCpuTimer elapsedTimer) {
		
		// Creaci�n  del mundo inicial.
		mundo = new Mundo(so);
		cerebro = new FSM_89(mundo);

	}
	/**
	 * Acci�n que realiza el agente por cada tick del juego.
	 */
	@Override
	public ACTIONS act(StateObservation arg0, ElapsedCpuTimer arg1) {
		
		// Actualizaci�n de la visi�n del mundo.
		mundo.actualizar(arg0);	
		
		// Dispara la regla correspondiente.
		Estado e = cerebro.disparo(mundo);
		
		// Ejecuta la acci�n de la regla que se ha disparado.
		return e.getAccion().doAction(mundo);	
	}


}
