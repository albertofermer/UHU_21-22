package si2022.p02.albertofernandez;

import java.util.Random;

import tools.Utils;
import tracks.ArcadeMachine;

public class Practica_02_7exe {

    public static void main(String[] args) {
    	int ganadas = 0;
    	int puntos = 0;
    	int tiempo = 0;
    	final int NUM_REPETICIONES = 1;
    	for (int i = 0; i < NUM_REPETICIONES; i++) {
			
		
		String p0 = "si2022.p02.albertofernandez.Agente";
		
		//Load available games
		String spGamesCollection =  "examples/all_games_sp.csv";
		String[][] games = Utils.readGames(spGamesCollection);

		//Game settings
		boolean visuals = true;
		int seed = new Random().nextInt();
		
		// Game and level to play0
		int gameIdx  = 89;
		int levelIdx = 4; // level names from 0 to 4 (game_lvlN.txt).
		
		String gameName = games[gameIdx][1];
		String game = games[gameIdx][0];
		String level1 = game.replace(gameName, gameName + "_lvl" + levelIdx);

		
		// 1. This starts a game, in a level, played by a human.
		//ArcadeMachine.playOneGame(game, level1, null, seed);

		// 2. This plays a game in a level by the controller.
		double[] a = ArcadeMachine.runOneGame(game, level1, visuals, p0, null, seed, 0);
				System.out.println(i);
				if (a[0] == 1.0) {
					ganadas++;
					puntos += a[1];
					tiempo += a[2];
				
				}
    	}
    	System.out.println("Partidas Ganadas : " + (((double)ganadas/NUM_REPETICIONES)*100) + "%");
    	System.out.println("Media Puntos " + ((double)puntos/ganadas));
    	System.out.println("Media Tiempo " + ((double)tiempo/ganadas));
		System.exit(0);

    }
}
