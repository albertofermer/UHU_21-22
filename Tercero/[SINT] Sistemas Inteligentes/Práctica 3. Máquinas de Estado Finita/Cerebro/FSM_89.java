package Cerebro;

import Estados.*;
import Transiciones.*;
import si2022.p02.albertofernandez.Mundo;

public class FSM_89 extends MaquinaFSM{
	
	
	
	private Estado inicial;
	private Estado vaciar;
	private Estado salvar;
	private Estado capturar;
	private Estado capturarIzq;
	private Estado capturarDch;

	
	public FSM_89(Mundo m) {
		
		
		inicial = new eInicial(m);
		vaciar = new eVaciar(m);
		salvar = new eSalvar(m);
		capturar = new eAtrapar(m);
		
		capturarIzq = new eAtraparIzq(m);
		capturarDch = new eAtraparDcha(m);
		
		this.estadoInicial = inicial;
		this.estadoActual = inicial;
		
		inicial.addTransicion(new Transicion( new estaCayendo(), salvar));
		inicial.addTransicion( new Transicion( new mochilaLlena(), vaciar));
		inicial.addTransicion( new Transicion ( new hayMasDeXEnemigosIzq(m.getNUMENEMIGOSIZQ()), capturarIzq));
		inicial.addTransicion( new Transicion ( new hayMasDeXEnemigosDer(m.getNUMENEMIGOSDER()), capturarDch));
		inicial.addTransicion( new Transicion ( new noHayCiviles(), capturar));
		inicial.addTransicion( new Transicion ( new numTicks(m.getMAXTICKS()), capturar));
		inicial.addTransicion(new Transicion ( new True(), inicial));
		
		salvar.addTransicion( new Transicion( new estaCayendo(), salvar));
		salvar.addTransicion( new Transicion (new True(), inicial));
		
		vaciar.addTransicion( new Transicion( new estaCayendo(), salvar));
		vaciar.addTransicion( new Transicion( new mochilaLlena(), vaciar));
		vaciar.addTransicion( new Transicion ( new hayMasDeXEnemigosIzq(m.getNUMENEMIGOSIZQ()), capturarIzq));
		vaciar.addTransicion( new Transicion ( new hayMasDeXEnemigosDer(m.getNUMENEMIGOSDER()), capturarDch));		
		vaciar.addTransicion( new Transicion( new True(), inicial));
		
		
		capturarIzq.addTransicion( new Transicion( new estaCayendo(), salvar));
		capturarIzq.addTransicion( new Transicion( new mochilaLlena(), vaciar));
		capturarIzq.addTransicion( new Transicion ( new hayMasDeXEnemigosIzq(m.getNUMENEMIGOSIZQ()), capturarIzq));
		capturarIzq.addTransicion( new Transicion ( new hayMasDeXEnemigosDer(m.getNUMENEMIGOSDER()), capturarDch));
		capturarIzq.addTransicion( new Transicion( new True(), inicial));
		
		capturarDch.addTransicion( new Transicion( new estaCayendo(), salvar));
		capturarDch.addTransicion( new Transicion( new mochilaLlena(), vaciar));
		capturarDch.addTransicion( new Transicion ( new hayMasDeXEnemigosDer(m.getNUMENEMIGOSDER()), capturarDch));
		capturarDch.addTransicion( new Transicion ( new hayMasDeXEnemigosIzq(m.getNUMENEMIGOSIZQ()), capturarIzq));
		capturarDch.addTransicion( new Transicion( new True(), inicial));
		
		capturar.addTransicion( new Transicion( new estaCayendo(), salvar));
		capturar.addTransicion( new Transicion( new mochilaLlena(), vaciar));
		capturar.addTransicion( new Transicion ( new hayMalos(), capturar));
		capturar.addTransicion( new Transicion( new True(), inicial));		
		
		
		
	}

	@Override
	public Estado disparo(Mundo m) {
		
		for(Transicion tr : this.estadoActual.transiciones) {
			if (tr.seDispara(m)) {				
				this.estadoActual = tr.siguienteEstado();
				break;
			}
		}
		
		return estadoActual;

	}

}
