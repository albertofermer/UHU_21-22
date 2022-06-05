package Estados;

import Acciones.capturarDerecha;
import Cerebro.Accion;
import Cerebro.Estado;
import si2022.p02.albertofernandez.Mundo;

public class eAtraparDcha extends Estado {

	private Mundo m = null;

	public eAtraparDcha(Mundo m) {
		this.m = m;
	}

	@Override
	public Accion getAccion() {
		return new capturarDerecha(m);
	}

}
