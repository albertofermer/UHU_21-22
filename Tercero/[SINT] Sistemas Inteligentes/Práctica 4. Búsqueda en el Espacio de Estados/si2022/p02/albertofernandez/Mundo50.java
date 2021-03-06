package si2022.p02.albertofernandez;

import java.util.ArrayList;
import java.util.List;

import ElementosDeBusqueda.Estado;
import ElementosDeBusqueda.Operador;
import Operadores.*;
import core.game.Observation;
import core.game.StateObservation;
import ontology.Types.ACTIONS;
import tools.Vector2d;

public class Mundo50 implements Estado {

	private int nFila, nCol, dimBlock;

	private Estado anterior = null;
	private Operador op = null;
	private String[][] pintar = null;
	private ArrayList<Observation>[][] mapa = null;
	private Vector2d avatar = new Vector2d(0, 0);

	private List<Observation> ListaArboles = null;
	private List<Observation> ListaAgua = null;
	private List<Observation> ListaFlechaIzq = null;
	private List<Observation> ListaFlechaDcha = null;
	private List<Observation> ListaFlechaAbajo = null;
	private List<Observation> ListaFlechaArriba = null;

	private Vector2d posMeta = null;
	private StateObservation so = null;

	private int puntuacion = 0;
	private int heuristica = 0;
	private int distancia = 0;

	public Mundo50(StateObservation so) {

		ListaArboles = new ArrayList<>();
		ListaAgua = new ArrayList<>();
		ListaFlechaAbajo = new ArrayList<>();
		ListaFlechaArriba = new ArrayList<>();
		ListaFlechaIzq = new ArrayList<>();
		ListaFlechaDcha = new ArrayList<>();

		mapa = so.getObservationGrid();
		nFila = mapa[0].length;
		nCol = mapa.length;
		dimBlock = so.getBlockSize();
		pintar = new String[nCol][nFila];

		avatar.x = (int) (so.getAvatarPosition().x / dimBlock);
		avatar.y = (int) (so.getAvatarPosition().y / dimBlock);

		posMeta = so.getPortalsPositions()[0].get(0).position;

		for (int i = 0; i < so.getImmovablePositions().length; i++) {
			switch (so.getImmovablePositions()[i].get(0).itype) {
			case 0: // ?rboles
				ListaArboles.addAll(so.getImmovablePositions()[i]);
				break;
			case 3: // Agua
				ListaAgua.addAll(so.getImmovablePositions()[i]);
				break;
			default:
				break;
			}
		}

		this.so = so;
		inicializar();
		// pintar();
	}

	public Vector2d getPosMeta() {
		return posMeta;
	}

	public List<Observation> getArboles() {
		return ListaArboles;
	}

	public List<Observation> getFlechaIzq() {
		return ListaFlechaIzq;
	}

	public List<Observation> getFlechaDcha() {
		return ListaFlechaDcha;
	}

	public List<Observation> getFlechaAbajo() {
		return ListaFlechaAbajo;
	}

	public List<Observation> getFlechaArriba() {
		return ListaFlechaArriba;
	}

	public int getDimensionBlock() {
		return dimBlock;
	}

	@Override
	public void actualizar(StateObservation arg0) {

		avatar.x = (int) (arg0.getAvatarPosition().x / dimBlock);
		avatar.y = (int) (arg0.getAvatarPosition().y / dimBlock);

		mapa = arg0.getObservationGrid();
		so = arg0;
		inicializar();
		// pintar();

	}

	private void inicializar() {
		vacio();

		for (int i = 0; i < nFila; i++) {
			for (int j = 0; j < nCol; j++) {
				if (!mapa[j][i].isEmpty()) {
					Observation obj = mapa[j][i].get(0);
					int x = (int) (obj.position.x / dimBlock);
					int y = (int) (obj.position.y / dimBlock);
					switch (obj.itype) {
					case 0:
						pintar[x][y] = "#"; // ?rboles
						// ListaArboles.add(obj);
						break;
					case 3:
						pintar[x][y] = "?"; // Agua
						// ListaAgua.add(obj);
						break;
					case 6:
						pintar[x][y] = "^"; // Flecha hacia arriba
						ListaFlechaArriba.add(obj);
					case 7:
						pintar[x][y] = ">"; // Flecha hacia la derecha
						ListaFlechaDcha.add(obj);
						break;
					case 5:
						pintar[x][y] = "V"; // Flecha hacia abajo
						ListaFlechaAbajo.add(obj);
						break;
					case 8:
						pintar[x][y] = "<"; // Flecha hacia la izquierda
						ListaFlechaIzq.add(obj);
						break;
					case 15:
					case 4:
						pintar[x][y] = "M"; // Meta
						break;
					default:
						pintar[x][y] = "" + mapa[x][y].get(0).itype;
						break;
					}
				} else {
					pintar[j][i] = " ";
				}
			}
		}

		pintar[(int) avatar.x][(int) avatar.y] = "X";
		// pintar();

	}

	private void pintar() {
		System.out.println("Jugador: [" + (int) avatar.x + "," + (int) avatar.y + "]");
		pintar[(int) avatar.x][(int) avatar.y] = "X";
		System.out.println("Meta: [" + (int) posMeta.x / dimBlock + "," + (int) posMeta.y / dimBlock + "]");
		for (int i = 0; i < nFila; i++) {
			for (int j = 0; j < nCol; j++) {
				System.out.print(pintar[j][i]);
			}
			System.out.println("");
		}
	}

	private void vacio() {

		ListaFlechaAbajo.clear();
		ListaFlechaArriba.clear();
		ListaFlechaIzq.clear();
		ListaFlechaDcha.clear();

		for (int i = 0; i < nFila; i++) {
			for (int j = 0; j < nCol; j++) {
				pintar[j][i] = " ";
			}
		}

	}

	public ArrayList<Observation>[][] getMapa() {
		return mapa;
	}

	public Vector2d getAvatar() {
		return avatar;
	}

	public void setAvatar(int[] posAvatar) {

		this.avatar.x = posAvatar[0];
		this.avatar.y = posAvatar[1];

	}

	public int getnFila() {
		return nFila;
	}

	public int getnCol() {
		return nCol;
	}

	@Override
	public Object clone() {

		Estado e = new Mundo(this.so);
		int[] posAvatar = new int[2];
		posAvatar[0] = (int) this.getAvatar().x;
		posAvatar[1] = (int) this.getAvatar().y;
		((Mundo) e).setAvatar(posAvatar);
		((Mundo) e).setAnterior(this);
		((Mundo) e).calcularHeuristica();
		((Mundo) e).calcularCoste();
		((Mundo) e).pintar();

		return e;
	}

	@Override
	public Estado aplicarOperador(Operador o) {

		Estado sucesor = (Estado) this.clone();
		int[] posAvatar = new int[2];

		switch (o.getAccion()) {
		case ACTION_DOWN:
			// Comprobar si es una flecha

			if (sePuedeAplicar(o.getAccion())) {
				posAvatar[0] = (int) this.getAvatar().x;
				posAvatar[1] = (int) this.getAvatar().y + 1;

				if (!mapa[posAvatar[0]][posAvatar[1]].isEmpty() && (mapa[posAvatar[0]][posAvatar[1]].get(0).itype >= 5
						&& mapa[posAvatar[0]][posAvatar[1]].get(0).itype <= 8)) {

					incrementar(posAvatar, mapa[posAvatar[0]][posAvatar[1]].get(0).itype,
							mapa[posAvatar[0]][posAvatar[1]].get(0).itype);

				}
				((Mundo) sucesor).setAvatar(posAvatar);
				System.out.println("ABAJO");
				((Mundo) sucesor).setOperador(o);

			}
			break;
		case ACTION_UP:
			if (sePuedeAplicar(o.getAccion())) {

				posAvatar[0] = (int) this.getAvatar().x;
				posAvatar[1] = (int) this.getAvatar().y - 1;
				if (!mapa[posAvatar[0]][posAvatar[1]].isEmpty() && (mapa[posAvatar[0]][posAvatar[1]].get(0).itype >= 5
						&& mapa[posAvatar[0]][posAvatar[1]].get(0).itype <= 8)) {

					incrementar(posAvatar, mapa[posAvatar[0]][posAvatar[1]].get(0).itype,
							mapa[posAvatar[0]][posAvatar[1]].get(0).itype);

				}
				((Mundo) sucesor).setAvatar(posAvatar);
				System.out.println("ARRIBA");
				((Mundo) sucesor).setOperador(o);

			}
			break;
		case ACTION_LEFT:
			if (sePuedeAplicar(o.getAccion())) {

				posAvatar[0] = (int) this.getAvatar().x - 1;
				posAvatar[1] = (int) this.getAvatar().y;
				if (!mapa[posAvatar[0]][posAvatar[1]].isEmpty() && (mapa[posAvatar[0]][posAvatar[1]].get(0).itype >= 5
						&& mapa[posAvatar[0]][posAvatar[1]].get(0).itype <= 8)) {

					System.out.println("FLECHA");
					incrementar(posAvatar, mapa[posAvatar[0]][posAvatar[1]].get(0).itype,
							mapa[posAvatar[0]][posAvatar[1]].get(0).itype);

				}
				((Mundo) sucesor).setAvatar(posAvatar);
				System.out.println("IZQ");
				((Mundo) sucesor).setOperador(o);

			}

			break;
		case ACTION_RIGHT:
			if (sePuedeAplicar(o.getAccion())) {

				posAvatar[0] = (int) this.getAvatar().x + 1;
				posAvatar[1] = (int) this.getAvatar().y;
				if (!mapa[posAvatar[0]][posAvatar[1]].isEmpty() && (mapa[posAvatar[0]][posAvatar[1]].get(0).itype >= 5
						&& mapa[posAvatar[0]][posAvatar[1]].get(0).itype <= 8)) {

					incrementar(posAvatar, mapa[posAvatar[0]][posAvatar[1]].get(0).itype,
							mapa[posAvatar[0]][posAvatar[1]].get(0).itype); // RIGHT

				}
				((Mundo) sucesor).setAvatar(posAvatar);
				System.out.println("DERECHA");
				((Mundo) sucesor).setOperador(o);

			}
			break;
		default:
			System.out.println("No se puede aplicar ninguno.");
			break;
		}

		return sucesor;
	}

	private void incrementar(int[] posAvatar, int c, int ultimaFlecha) {

		switch (mapa[posAvatar[0]][posAvatar[1]].get(0).itype) {
		case 5:
			posAvatar[1]++;
			break;
		case 6:
			posAvatar[1]--;
			break;
		case 8:
			posAvatar[0]--;
			break;
		case 7:
			posAvatar[0]++;
			break;
		default:
			break;
		}
		// Hacer que pare cuando est? en la meta.
		while (mapa[posAvatar[0]][posAvatar[1]].isEmpty() || (mapa[posAvatar[0]][posAvatar[1]].get(0).itype != 0
				&& mapa[posAvatar[0]][posAvatar[1]].get(0).itype != 15)) {

			if (mapa[posAvatar[0]][posAvatar[1]].isEmpty() || mapa[posAvatar[0]][posAvatar[1]].get(0).itype < 5
					|| mapa[posAvatar[0]][posAvatar[1]].get(0).itype > 8) {

				// Si est? vac?o o es agua

				switch (c) {
				case 5:
					posAvatar[1]++;
					break;
				case 6:
					posAvatar[1]--;
					break;
				case 8:
					posAvatar[0]--;
					break;
				case 7:
					posAvatar[0]++;
					break;
				default:
					break;
				}

			} else {
				// Si hay una flecha habr? que llamar de nuevo a la funci?n incrementar.
				switch (mapa[posAvatar[0]][posAvatar[1]].get(0).itype) {
				case 5: // V
					ultimaFlecha = 5;
					incrementar(posAvatar, 5, ultimaFlecha);
					break;
				case 6: // ^
					ultimaFlecha = 6;
					incrementar(posAvatar, 6, ultimaFlecha);
					break;
				case 7: // >
					ultimaFlecha = 7;
					incrementar(posAvatar, 7, ultimaFlecha);
					break;
				case 8: // <
					ultimaFlecha = 8;
					incrementar(posAvatar, 8, ultimaFlecha);
					break;
				default:
					System.out.println("ERROR. Tipo no encontrado: " + mapa[posAvatar[0]][posAvatar[1]].get(0).itype);
					break;
				}

			}

		}

		// El avatar estar? encima de un ?rbol, por lo que debemos invertir una vez el
		// movimiento.
		if (mapa[posAvatar[0]][posAvatar[1]].get(0).itype != 15) {
			switch (ultimaFlecha) {
			case 5:
				posAvatar[1]--;
				break;
			case 6:
				posAvatar[1]++;
				break;
			case 8:
				posAvatar[0]++;
				break;
			case 7:
				posAvatar[0]--;
				break;
			default:
				break;
			}
		}

	}

	private void setAnterior(Mundo mundo) {
		this.anterior = mundo;

	}

	public Estado getAnterior() {
		return anterior;
	}

	private boolean sePuedeAplicar(ACTIONS accion) {
		int[] NuevaPos = new int[2];
		switch (accion) {
		case ACTION_DOWN:
			// System.out.print("ABAJO - ");

			NuevaPos[0] = (int) this.getAvatar().x;
			NuevaPos[1] = (int) this.getAvatar().y + 1;

			if (NuevaPos[0] >= 0 && NuevaPos[1] >= 0
					&& (mapa[NuevaPos[0]][NuevaPos[1]].isEmpty() || mapa[NuevaPos[0]][NuevaPos[1]].get(0).itype == 4))
				return true;
			else if (NuevaPos[0] >= 0 && NuevaPos[1] >= 0 && (mapa[NuevaPos[0]][NuevaPos[1]].get(0).itype >= 5
					&& mapa[NuevaPos[0]][NuevaPos[1]].get(0).itype <= 8)) {
				incrementar(NuevaPos, mapa[NuevaPos[0]][NuevaPos[1]].get(0).itype,
						mapa[NuevaPos[0]][NuevaPos[1]].get(0).itype);

				if (NuevaPos[0] >= 0 && NuevaPos[1] >= 0 && (mapa[NuevaPos[0]][NuevaPos[1]].isEmpty()
						|| mapa[NuevaPos[0]][NuevaPos[1]].get(0).itype == 4))
					return true;
			}

			break;
		case ACTION_UP:

			// System.out.print("ARRIBA - ");
			NuevaPos[0] = (int) this.getAvatar().x;
			NuevaPos[1] = (int) this.getAvatar().y - 1;

			if (NuevaPos[0] >= 0 && NuevaPos[1] >= 0
					&& (mapa[NuevaPos[0]][NuevaPos[1]].isEmpty() || mapa[NuevaPos[0]][NuevaPos[1]].get(0).itype == 4))
				return true;
			else if (NuevaPos[0] >= 0 && NuevaPos[1] >= 0 && (mapa[NuevaPos[0]][NuevaPos[1]].get(0).itype >= 5
					&& mapa[NuevaPos[0]][NuevaPos[1]].get(0).itype <= 8)) {
				incrementar(NuevaPos, mapa[NuevaPos[0]][NuevaPos[1]].get(0).itype,
						mapa[NuevaPos[0]][NuevaPos[1]].get(0).itype);

				if (NuevaPos[0] >= 0 && NuevaPos[1] >= 0 && (mapa[NuevaPos[0]][NuevaPos[1]].isEmpty()
						|| mapa[NuevaPos[0]][NuevaPos[1]].get(0).itype == 4))
					return true;
			}

			break;
		case ACTION_LEFT:

			// System.out.print("IZQUIERDA - ");
			NuevaPos[0] = (int) this.getAvatar().x - 1;
			NuevaPos[1] = (int) this.getAvatar().y;
			// Si es > 0 y es empty o es meta -> true
			// Si es > 0 y es una flecha
			// incrementar NuevaPos.
			// Si NuevaPos es empty o es meta
			if (NuevaPos[0] >= 0 && NuevaPos[1] >= 0
					&& (mapa[NuevaPos[0]][NuevaPos[1]].isEmpty() || mapa[NuevaPos[0]][NuevaPos[1]].get(0).itype == 4))
				return true;
			else if (NuevaPos[0] >= 0 && NuevaPos[1] >= 0 && (mapa[NuevaPos[0]][NuevaPos[1]].get(0).itype >= 5
					&& mapa[NuevaPos[0]][NuevaPos[1]].get(0).itype <= 8)) {
				incrementar(NuevaPos, mapa[NuevaPos[0]][NuevaPos[1]].get(0).itype,
						mapa[NuevaPos[0]][NuevaPos[1]].get(0).itype);

				if (NuevaPos[0] >= 0 && NuevaPos[1] >= 0 && (mapa[NuevaPos[0]][NuevaPos[1]].isEmpty()
						|| mapa[NuevaPos[0]][NuevaPos[1]].get(0).itype == 4))
					return true;
			}
			break;
		case ACTION_RIGHT:

			// System.out.print("DERECHA - ");
			NuevaPos[0] = (int) this.getAvatar().x + 1;
			NuevaPos[1] = (int) this.getAvatar().y;
			if (NuevaPos[0] >= 0 && NuevaPos[1] >= 0
					&& (mapa[NuevaPos[0]][NuevaPos[1]].isEmpty() || mapa[NuevaPos[0]][NuevaPos[1]].get(0).itype == 4))
				return true;
			else if (NuevaPos[0] >= 0 && NuevaPos[1] >= 0 && (mapa[NuevaPos[0]][NuevaPos[1]].get(0).itype >= 5
					&& mapa[NuevaPos[0]][NuevaPos[1]].get(0).itype <= 8)) {
				incrementar(NuevaPos, mapa[NuevaPos[0]][NuevaPos[1]].get(0).itype,
						mapa[NuevaPos[0]][NuevaPos[1]].get(0).itype);

				if (NuevaPos[0] >= 0 && NuevaPos[1] >= 0 && (mapa[NuevaPos[0]][NuevaPos[1]].isEmpty()
						|| mapa[NuevaPos[0]][NuevaPos[1]].get(0).itype == 4))
					return true;
			}

			break;
		default:
			System.out.println("ERROR");
		}

		return false;
	}

	@Override
	public boolean isMeta() {
		return ((int) posMeta.x / dimBlock == (int) avatar.x && (int) posMeta.y / dimBlock == (int) avatar.y);
	}

	@Override
	public boolean equals(Object o) {
		return (((Mundo) o).getAvatar().equals(this.getAvatar()));

	}

	public int compararEstado(Estado o) {
		int resultado = 0;

		if (puntuacion > ((Mundo) o).getPuntuacion()) {
			resultado = 1;
		}
		if (puntuacion < ((Mundo) o).getPuntuacion()) {
			resultado = -1;
		}

		return resultado;
	}

	public int getPuntuacion() {

		return puntuacion;
	}

	public int getHeuristica() {

		return heuristica;
	}

	public void calcularHeuristica() {

		heuristica = Math.abs((int) avatar.x - (int) posMeta.x) + Math.abs((int) avatar.y - (int) posMeta.y);

	}

	public void calcularCoste() {

		if (anterior == null) {
			// System.out.println("Anterior. Null");
			distancia = 0;
		} else {
			// System.out.println("Anterior. " + anterior.toString());
			distancia = 1 + ((Mundo) anterior).getDistancia();
			puntuacion = distancia + heuristica;
		}
	}

	private int getDistancia() {
		return distancia;
	}

	public String toString() {
		return avatar.toString();
	}

	public Operador getOperador() {
		return op;
	}

	public void setOperador(Operador op) {

		this.op = op;
	}
}
