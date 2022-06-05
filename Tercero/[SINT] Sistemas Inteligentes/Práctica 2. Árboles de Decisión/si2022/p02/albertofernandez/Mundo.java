package si2022.p02.albertofernandez;

import java.util.ArrayList;
import core.game.Observation;
import core.game.StateObservation;
import tools.Vector2d;

public class Mundo {

	private final int ENEMIGOSIZQ = 0;
	private final int ENEMIGOSDER = 0;
	private final int MAXTICKS = 1800;

	private int numEnemigosD = 0;
	private int numEnemigosI = 0;
	private int Mochila = 0;
	private int nFila, nCol, dimBlock;
	private ArrayList<Observation>[][] mapa = null;
	private ArrayList<Observation> buenos = null;
	private ArrayList<Observation> malos = null;
	private ArrayList<Observation> malosIzq = null;
	private ArrayList<Observation> malosDer = null;
	private ArrayList<Observation> cayendo = null;
	private ArrayList<Observation> cayendoPeligro = null;
	private int[] avatar = new int[2];
	private int[] carcel = new int[2];
	private Observation salvar = null;
	private Observation masCercaDelSuelo = null;
	private Observation capturar = null;
	private int ticks;
	private ArrayList<Observation>[] enemigos;
	private ArrayList<Observation>[] civiles;

	public Mundo(StateObservation so) {

		civiles = so.getMovablePositions();

		buenos = new ArrayList<>();
		malos = new ArrayList<>();
		malosIzq = new ArrayList<>();
		malosDer = new ArrayList<>();
		cayendo = new ArrayList<>();
		cayendoPeligro = new ArrayList<>();
		mapa = so.getObservationGrid();
		nFila = mapa[0].length;
		nCol = mapa.length;
		dimBlock = so.getBlockSize();

		avatar[0] = (int) (so.getAvatarPosition().x / dimBlock);
		avatar[1] = (int) (so.getAvatarPosition().y / dimBlock);

		carcel[0] = (int) so.getImmovablePositions()[3].get(0).position.x / dimBlock;
		carcel[1] = (int) so.getImmovablePositions()[3].get(0).position.y / dimBlock;

		inicializar();
	}

	public int getNUMENEMIGOSIZQ() {
		return ENEMIGOSIZQ;
	}

	public int getNUMENEMIGOSDER() {
		return ENEMIGOSDER;
	}

	public int getMAXTICKS() {
		return MAXTICKS;
	}

	public Observation getMasCercaDelSuelo() {
		return masCercaDelSuelo;
	}

	public Observation getCapturar() {
		return capturar;
	}

	public Observation getSalvar() {
		return salvar;
	}

	public int getDimensionBlock() {
		return dimBlock;
	}

	public boolean mochilaLlena() {
		return Mochila == 8;
	}

	public ArrayList<Observation> getBuenos() {
		return buenos;
	}

	public int getNumEnemDer() {
		return numEnemigosD;
	}

	public int getNumEnemIzq() {
		return numEnemigosI;
	}

	public ArrayList<Observation> getCayendo() {
		return cayendo;
	}

	public void actualizar(StateObservation arg0) {

		if (arg0.getAvatarResources().get(13) != null) {
			Mochila = arg0.getAvatarResources().get(13);
		}

		avatar[0] = (int) (arg0.getAvatarPosition().x / dimBlock);
		avatar[1] = (int) (arg0.getAvatarPosition().y / dimBlock);
		mapa = arg0.getObservationGrid();
		ticks = arg0.getGameTick();

		enemigos = arg0.getNPCPositions();
		civiles = arg0.getMovablePositions();
		// System.out.println("Numero enemigos izquierda" + getNumEnemIzq() + " - " +
		// malosIzq.size());
		inicializar();

	}

	private void inicializar() {

		vacio();

		if (civiles != null) {
			for (int i = 0; i < civiles.length; i++) {
				for (int j = 0; j < civiles[i].size(); j++) {
					Observation o = civiles[i].get(j);
					switch (o.itype) {
					case 16:
						buenos.add(o);
						break;
					case 18:
						cayendo.add(o);
						break;
					default:
						break;
					}
				}
			}

		}

		if (enemigos != null) {
			for (int i = 0; i < enemigos.length; i++) {
				for (int j = 0; j < enemigos[i].size(); j++) {
					Observation o = enemigos[i].get(j);
					if (o.itype == 11) {
						malosDer.add(o);
						malos.add(o);
					}
					if (o.itype == 12) {
						malosIzq.add(o);
						malos.add(o);
					}
				}
			}

			numEnemigosD = malosDer.size();
			numEnemigosI = malosIzq.size();

		}

		if (cayendo.isEmpty() && buenos.isEmpty() && !malos.isEmpty()) {
			capturar = malos.get(0);
			double distanciaCapturar = distanciaAlSuperMan(avatar, capturar.position);

			for (int i = 1; i < malos.size(); i++) {
				if (distanciaCapturar > distanciaAlSuperMan(avatar, malos.get(i).position)) {
					distanciaCapturar = distanciaAlSuperMan(avatar, malos.get(i).position);
					capturar = malos.get(i);
				}
			}
		}

		if (!cayendo.isEmpty() && salvar == null) {
			for (int i = 0; i < cayendo.size(); i++) {
				if (!nubeDebajo(cayendo.get(i))) {
					cayendoPeligro.add(cayendo.get(i));
				}
			}

			if (!cayendoPeligro.isEmpty() && salvar == null) {
				salvar = cayendoPeligro.get(0);
				int distanciaSalvarSuelo = (int) salvar.position.y / dimBlock;
				double distanciaSalvarSuperman = distanciaAlSuperMan(avatar, salvar.position);
				for (int i = 1; i < cayendoPeligro.size(); i++) {

					int dSuelo = (int) cayendoPeligro.get(i).position.y / dimBlock;
					double dSuperman = distanciaAlSuperMan(avatar, cayendoPeligro.get(i).position);

					if (distanciaSalvarSuperman > dSuperman) {
						distanciaSalvarSuperman = dSuperman;
						salvar = cayendoPeligro.get(i);

					} else if (distanciaSalvarSuelo < dSuelo) {
						distanciaSalvarSuelo = dSuelo;
						salvar = cayendoPeligro.get(i);
					}
				}
			}
		}
		//System.out.println(malosDer.isEmpty()?"izquierdavacia":"izquierdaNOvacia" + " - " + capturar == null?"null":"capturar" + " - " + (malosDer.size() > ENEMIGOSDER || ticks > MAXTICKS));
		
		if (!malosIzq.isEmpty() && capturar == null && (malosIzq.size() > ENEMIGOSIZQ || ticks > MAXTICKS)) {

			capturar = malosIzq.get(0);
			double distanciaCapturar = distanciaAlSuperMan(avatar, capturar.position);

			for (int i = 1; i < malosIzq.size(); i++) {
				if (distanciaCapturar > distanciaAlSuperMan(avatar, malosIzq.get(i).position)) {
					distanciaCapturar = distanciaAlSuperMan(avatar, malosIzq.get(i).position);
					capturar = malosIzq.get(i);
				}
			}

		} else if (!malosDer.isEmpty() && capturar == null && (malosDer.size() > ENEMIGOSDER || ticks > MAXTICKS)) {

			capturar = malosDer.get(0);
			double distanciaCapturar = distanciaAlSuperMan(avatar, capturar.position);

			for (int i = 1; i < malosDer.size(); i++) {
				if (distanciaCapturar > distanciaAlSuperMan(avatar, malosDer.get(i).position)) {

					distanciaCapturar = distanciaAlSuperMan(avatar, malosDer.get(i).position);
					capturar = malosDer.get(i);
				}
			}
		}

//		if (!buenos.isEmpty() && salvar == null) {
//			masCercaDelSuelo = buenos.get(0);
//			int distanciaAlSuelo = (int) masCercaDelSuelo.position.y / dimBlock;
//
//			for (int i = 1; i < buenos.size(); i++) {
//				if (distanciaAlSuelo < (int) buenos.get(i).position.y / dimBlock) {
//
//					distanciaAlSuelo = (int) buenos.get(i).position.y / dimBlock;
//					masCercaDelSuelo = buenos.get(i);
//				}
//			}
//		}

	}

	private void vacio() {

		if (salvar != null) {
			salvar.position.x = -1;
			salvar.position.y = -1;
			salvar = null;
		}
		numEnemigosD = 0;
		numEnemigosI = 0;
		cayendo.clear();
		buenos.clear();
		malos.clear();
		malosIzq.clear();
		malosDer.clear();

		cayendoPeligro.clear();

		if (capturar != null && avatar[0] == (int) (capturar.position.x / dimBlock)
				&& avatar[1] == (int) (capturar.position.y / dimBlock)) {
			capturar.position.x = -1;
			capturar.position.y = -1;
			capturar = null;
			// System.out.println("Captura");

		}

	}

	public int[] getAvatar() {
		return avatar;
	}

	public int[] getCarcel() {
		return carcel;
	}

	public int getnFila() {
		return nFila;
	}

	public int getnCol() {
		return nCol;
	}

	private boolean nubeDebajo(Observation observation) {
		int y = (int) observation.position.y / dimBlock;
		int x = (int) observation.position.x / dimBlock;
		boolean hayNube = false;
		for (int i = y; i < nFila - 2; i++) {
			if (!mapa[x][i].isEmpty() && mapa[x][i].get(0).itype == 3) {
				hayNube = true;

			}
		}

		return hayNube;
	}

	private double distanciaAlSuperMan(int[] posAvatar2, Vector2d salvar) {
		return (Math.abs(((salvar.x / dimBlock) - avatar[0])) + Math.abs(((salvar.y / dimBlock) - avatar[1])));
	}

	public int getTicks() {
		return ticks;
	}

	public ArrayList<Observation> getMalos() {
		return malos;
	}
}
