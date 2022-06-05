package Estados;

import Acciones.capturarIzquierda;
import Cerebro.Accion;
import Cerebro.Estado;
import si2022.p02.albertofernandez.Mundo;

public class eAtraparIzq extends Estado {

	private Mundo m = null;

	public eAtraparIzq(Mundo m) {
		this.m = m;
	}

	@Override
	public Accion getAccion() {
		return new capturarIzquierda(m);
	}

}
