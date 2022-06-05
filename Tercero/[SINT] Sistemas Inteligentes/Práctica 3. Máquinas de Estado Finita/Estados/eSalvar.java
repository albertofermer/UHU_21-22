package Estados;

import Acciones.salvarBueno;
import Cerebro.Accion;
import Cerebro.Estado;
import si2022.p02.albertofernandez.Mundo;

public class eSalvar extends Estado {

	private Mundo m = null;
	public eSalvar(Mundo m) {
		this.m = m;
	}

	@Override
	public Accion getAccion() {
		return new salvarBueno(m);
	}

}
