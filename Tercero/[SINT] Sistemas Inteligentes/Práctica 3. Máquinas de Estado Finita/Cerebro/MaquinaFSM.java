package Cerebro;

import si2022.p02.albertofernandez.Mundo;

public abstract class MaquinaFSM {

	protected Estado estadoInicial;
	protected Estado estadoActual;	
	public abstract Estado disparo(Mundo m);

}
