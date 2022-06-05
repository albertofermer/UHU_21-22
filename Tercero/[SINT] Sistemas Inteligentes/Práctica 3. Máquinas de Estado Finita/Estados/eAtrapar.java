package Estados;

import Acciones.capturarMalo;
import Cerebro.Accion;
import Cerebro.Estado;
import si2022.p02.albertofernandez.Mundo;

public class eAtrapar extends Estado {

	private Mundo m;
	public eAtrapar(Mundo m) {
		this.m = m;
	}

	@Override
	public Accion getAccion() {
		
		return new capturarMalo(m);
	}

}
