package si2022.p02.albertofernandez;

import java.util.Random;

import tools.Utils;
import tracks.ArcadeMachine;

public class Practica_XX_exe6 {

    public static void main(String[] args) {

		String p0 = "si2022.p02.albertofernandez.Agente6";
		
		//Load available games
		String spGamesCollection =  "examples/all_games_sp.csv";
		String[][] games = Utils.readGames(spGamesCollection);

		//Game settings
		boolean visuals = true;
		int seed = new Random().nextInt();
		
		// Game and level to play
		int gameIdx  = 89;
		int levelIdx = 0; // level names from 0 to 4 (game_lvlN.txt).
		
		String gameName = games[gameIdx][1];
		String game = games[gameIdx][0];
		String level1 = game.replace(gameName, gameName + "_lvl" + levelIdx);

		
		// 1. This starts a game, in a level, played by a human.
		//ArcadeMachine.playOneGame(game, level1, null, seed);

		// 2. This plays a game in a level by the controller.
		ArcadeMachine.runOneGame(game, level1, visuals, p0, null, seed, 0);
				

		System.exit(0);

    }
}
