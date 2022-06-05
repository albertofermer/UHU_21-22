package Estados;

import Acciones.irACarcel;
import Cerebro.Accion;
import Cerebro.Estado;
import si2022.p02.albertofernandez.Mundo;

public class eVaciar extends Estado {

	private Mundo m = null;

	public eVaciar(Mundo m) {
		this.m = m;
	}

	@Override
	public Accion getAccion() {
		return new irACarcel(m);
	}

}
