package Estados;

import Acciones.irAlCentro;
import Cerebro.Accion;
import Cerebro.Estado;
import si2022.p02.albertofernandez.Mundo;

public class eInicial extends Estado {
	
	private Mundo m = null;
	
	
	public eInicial(Mundo m) {
		this.m = m;
	}

	@Override
	public Accion getAccion() {
		// TODO Auto-generated method stub
		return new irAlCentro(m);
	}

}
