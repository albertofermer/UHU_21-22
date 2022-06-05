package si2022.p02.albertofernandez;

import Cerebro.BEspacioEstados;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;

public class Agente6 extends AbstractPlayer {

	private Mundo mundo = null;
	private BEspacioEstados cerebro = null;
	/**
	 * Agente que resuelve el juego 89 de GVGAI (Superman) mediante un
	 * sistema basado en reglas.
	 * 
	 * @param so
	 * @param elapsedTimer
	 */
	public Agente6(StateObservation so, ElapsedCpuTimer elapsedTimer) {
		
		//mundo = new Mundo(so);
		mundo = new Mundo(so);
		cerebro = new BEspacioEstados(mundo);
	}
	/**
	 * Acci�n que realiza el agente por cada tick del juego.
	 */
	@Override
	public ACTIONS act(StateObservation arg0, ElapsedCpuTimer arg1) {
		
		// Actualizaci�n de la visi�n del mundo.
		mundo.actualizar(arg0);	
		
		return cerebro.piensa(mundo);	
	}


}
