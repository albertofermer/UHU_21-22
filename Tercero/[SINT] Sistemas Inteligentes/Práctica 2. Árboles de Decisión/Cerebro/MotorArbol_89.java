package Cerebro;

import accion.*;
import decision.*;
import ontology.Types.ACTIONS;
import si2022.p02.albertofernandez.Mundo;

public class MotorArbol_89 extends Razonador {
	// Raíz
	// private NodoDecision estaCayendo = new NodoDecision(new civilCayendo()); //
	// Se cumple si hay un civil en peligro.
	// Resto de nodos
	private NodoDecision civilPeligro = null;
	private NodoDecision hayMalos = null;// Se cumple si hay algún enemigo en el mapa.
	private NodoDecision mochilaLlena = null;// Se cumple si la mochila del avatar está llena.
	private NodoDecision mochilaLlena2 = null;// Se cumple si la mochila del avatar está llena.
	private NodoDecision existenCiviles = null;
	private NodoDecision hayMasdeDosEnemigosIzq = null;
	private NodoDecision hayMasdeDosEnemigosDer = null;
	private NodoDecision numTicks = null;

	private NodoAccion capturarMalo = null;
	private NodoAccion irACarcel = null;
	private NodoAccion irAlCentro = null;
	private NodoAccion salvarBueno = null;
	private NodoAccion capturarIzq = null;
	private NodoAccion capturarDer = null;

	public MotorArbol_89(Mundo m) {
		// Crear árbol
		civilPeligro = new NodoDecision(new civilPeligro());
		hayMalos = new NodoDecision(new hayMalos()); // Se cumple si hay algún enemigo en el mapa.
		mochilaLlena = new NodoDecision(new mochilaLlena()); // Se cumple si la mochila del avatar está llena.
		mochilaLlena2 = new NodoDecision(new mochilaLlena()); // Se cumple si la mochila del avatar está llena.
		existenCiviles = new NodoDecision(new hayCiviles());
		hayMasdeDosEnemigosIzq = new NodoDecision(new hayMasDeXEnemigosIzq(m.getNUMENEMIGOSIZQ()));
		hayMasdeDosEnemigosDer = new NodoDecision(new hayMasDeXEnemigosDer(m.getNUMENEMIGOSDER()));
		numTicks = new NodoDecision(new numTicks(m.getMAXTICKS()));
		capturarMalo = new NodoAccion(new capturarMalo(m));
		irACarcel = new NodoAccion(new irACarcel(m));
		irAlCentro = new NodoAccion(new irAlCentro(m));
		salvarBueno = new NodoAccion(new salvarBueno(m));
		capturarIzq = new NodoAccion(new capturarIzquierda(m));
		capturarDer = new NodoAccion(new capturarDerecha(m));

		/**
		 * Opcion que gana siempre con 4040 puntos en 989 timesteps
		 */
//		hayMalos.setIzq(civilPeligro);
//		hayMalos.setDer(civilPeligro);
//		
//		civilPeligro.setIzq(salvarBueno);
//		civilPeligro.setDer(mochilaLlena);
//		
//		mochilaLlena.setIzq(irACarcel);
//		mochilaLlena.setDer(capturarMalo);

		/**
		 * Opción que no gana siempre.
		 */
		civilPeligro.setIzq(salvarBueno);
		civilPeligro.setDer(existenCiviles);

		existenCiviles.setIzq(numTicks);
		existenCiviles.setDer(mochilaLlena2);

		numTicks.setIzq(mochilaLlena2);
		numTicks.setDer(mochilaLlena);

		mochilaLlena.setIzq(irACarcel);
		mochilaLlena.setDer(hayMasdeDosEnemigosIzq);
		// mochilaLlena.setDer(hayMalos);

		hayMasdeDosEnemigosIzq.setIzq(capturarIzq);
		hayMasdeDosEnemigosIzq.setDer(hayMasdeDosEnemigosDer);

		hayMasdeDosEnemigosDer.setIzq(capturarDer);
		hayMasdeDosEnemigosDer.setDer(irAlCentro);

		mochilaLlena2.setIzq(irACarcel);
		mochilaLlena2.setDer(hayMalos);

		hayMalos.setIzq(capturarMalo);
		hayMalos.setDer(irAlCentro);

	}

	public ACTIONS piensa(Mundo m) {
		return ((NodoAccion) civilPeligro.decide(m)).doAction(m);
		// return ((NodoAccion)hayMalos.decide(m)).doAction(m);

	}

}
