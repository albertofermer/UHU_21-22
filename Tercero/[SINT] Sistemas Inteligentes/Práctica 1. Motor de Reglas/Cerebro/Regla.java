package Cerebro;

import java.util.List;

import si2022.p02.albertofernandez.Mundo;

public class Regla {

	private List<Condicion> antecedentes;
	private Accion accion;

	public Regla(List<Condicion> ante, Accion a) {
		
		antecedentes = ante;
		accion = a;
		
	}

	public boolean seCumple(Mundo mundo) {
			
		for (int i = 0; i < antecedentes.size(); i++) {
			if (!antecedentes.get(i).seCumple(mundo)) return false; //Si no se cumple alguno de los antecedentes, no se cumple la regla.
		}
		
		return true;

	}

	public List<Condicion> getAntecedentes() {
		return antecedentes;
	}

	public Accion getAccion() {
		return accion;
	}

}
