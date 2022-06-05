package si2022.p02.albertofernandez;

import java.util.LinkedList;
import java.util.List;

import Acciones.*;
import Antecedentes.*;
import Cerebro.*;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;

public class Agente6 extends AbstractPlayer {

	private Mundo mundo = null;
	private MotorReglas motor = null;
	
	/**
	 * Agente que resuelve el juego 89 de GVGAI (Superman) mediante un
	 * sistema basado en reglas.
	 * 
	 * @param so
	 * @param elapsedTimer
	 */
	public Agente6(StateObservation so, ElapsedCpuTimer elapsedTimer) {
		
		// Creación  del mundo inicial.
		mundo = new Mundo(so);
		
		// Lista de antecedentes para aplicar a las reglas
		Condicion estaCayendo = new estaCayendo(mundo);
		Condicion mochilaLlena = new mochilaLlena();
		Condicion hayMalos = new hayMalos();
		Condicion noHacerNada = new PorDefecto();

		
		// Creación de la lista de antecedentes para aplicar a cada regla.
		
		List<Condicion> buenosCayendoLista = new LinkedList<Condicion>(); buenosCayendoLista.add(estaCayendo);
		List<Condicion> mochilaLlenaLista = new LinkedList<Condicion>(); mochilaLlenaLista.add(mochilaLlena);
		List<Condicion> hayMalosLista = new LinkedList<Condicion>(); 	 hayMalosLista.add(hayMalos);
		List<Condicion> noHacerNadaLista = new LinkedList<Condicion>();	 noHacerNadaLista.add(noHacerNada);
		
		//Lista de acciones que se dispararán si se cumple un antecedente.
		Accion irCarcel = new irACarcel(mundo);
		Accion salvarBueno = new salvarBueno(mundo);
		Accion irCentro = new irAlCentro(mundo);
		Accion capturarMalo = new capturarMalo(mundo);


		// Construcción de las reglas: antecedente + acción
		Regla regla0 = new Regla(buenosCayendoLista,salvarBueno);
		Regla regla1 = new Regla(mochilaLlenaLista,irCarcel);
		Regla regla2 = new Regla(hayMalosLista,capturarMalo);		
		Regla regla3 = new Regla(noHacerNadaLista,irCentro);
		
		
		//Lista de Reglas que se introducirán en el motor de reglas.
		
		List<Regla> ListaReglas = new LinkedList<Regla>();
		ListaReglas.add(regla0); 	// Salvar Bueno
		ListaReglas.add(regla1); 	// Mochila Llena
		ListaReglas.add(regla2);	// Capturar Malo
		ListaReglas.add(regla3);	// Ir Al Centro
		
		// Creación del Motor de Reglas introduciéndole la lista de reglas.
		
		motor = new MotorReglas(ListaReglas);


	}
	/**
	 * Acción que realiza el agente por cada tick del juego.
	 */
	@Override
	public ACTIONS act(StateObservation arg0, ElapsedCpuTimer arg1) {
		
		// Actualización de la visión del mundo.
		mundo.actualizar(arg0);	
		
		// Dispara la regla correspondiente.
		Regla r = motor.disparo(mundo);
		
		// Ejecuta la acción de la regla que se ha disparado.
		return r.getAccion().doAction(mundo);	
	}


}
