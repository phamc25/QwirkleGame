package edu.up.cs301.qwirklegame;

import java.util.ArrayList;

import edu.up.cs301.GameFramework.GameMainActivity;
import edu.up.cs301.GameFramework.infoMessage.GameState;
import edu.up.cs301.GameFramework.players.GamePlayer;
import edu.up.cs301.GameFramework.LocalGame;
import edu.up.cs301.GameFramework.gameConfiguration.*;

/**
 * This contains the QwirkleMainActivity
 *
 * Current status of 11/27/2024 Beta Release: The game is implemented to the Beta release specifications.
 * Tile placements are fully checked to be valid according to game rules, full tile bag amount,
 * player score calculation, discard tiles action, fully functional GUI for all game actions,
 * a smart AI, a dumb AI, network play, and possibility for the game to be played with 2-4
 * players according to Qwirkle rules.
 *
 * Known bugs:
 * If selecting multiple players to play with. A player has to be added and then the
 * "MAKE CONFIGURATION DEFAULT" button has to be clicked, then the game has to be reloaded
 * or reran from the "Run" button on Android Studio. If not done, the number of players is
 * not initialized into the game. This may be a game framework bug.
 *
 * @author Chloe Pham
 * @author Talia Martinez
 * @author Tyler Crosbie
 * @author De'Ante Agleham
 * @author Ryan Murray
 *
 * @version November 27, 2024
 */
public class QwirkleMainActivity extends GameMainActivity {

	// the port number that this game will use when playing over the network
	private static final int PORT_NUMBER = 2234;

	/**
	 * Create the default configuration for this game:
	 * - one human player vs. one computer player
	 * - minimum of 1 player, maximum of 2
	 * - one kind of computer player and one kind of human player available
	 *
	 * @return
	 * 		the new configuration object, representing the default configuration
	 */
	@Override
	public GameConfig createDefaultConfig() {

		// Define the allowed player types
		ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

		// a human player player type (player type 0)
		playerTypes.add(new GamePlayerType("Local Human Player") {
			public GamePlayer createPlayer(String name) {
				return new QwirkleHumanPlayer(name);
			}});

		// a computer player type (player type 1)
		playerTypes.add(new GamePlayerType("Computer Player") {
			public GamePlayer createPlayer(String name) {
				return new QwirkleComputerPlayer1(name);
			}});

		// a computer player type (player type 2)
		playerTypes.add(new GamePlayerType("Computer Player: Smart") {
			public GamePlayer createPlayer(String name) {
				return new QwirkleComputerPlayer2(name);
			}});

		// Create a game configuration class for Counter:
		// - player types as given above
		// - from 1 to 2 players
		// - name of game is "Counter Game"
		// - port number as defined above
		GameConfig defaultConfig = new GameConfig(playerTypes, 2, 4, "Qwirkle Game",
				PORT_NUMBER);

		// Add the default players to the configuration
		defaultConfig.addPlayer("Human", 0); // player 1: a human player
		defaultConfig.addPlayer("Computer", 1); // player 2: a computer player

		// Set the default remote-player setup:
		// - player name: "Remote Player"
		// - IP code: (empty string)
		// - default player type: human player
		defaultConfig.setRemoteData("Remote Player", "", 0);

		// return the configuration
		return defaultConfig;
	}//createDefaultConfig

	/**
	 * create a local game
	 *
	 * @return
	 * 		the local game, a counter game
	 */
	@Override
	public LocalGame createLocalGame(GameState state) {
		int num = config.getNumPlayers();
		if (state == null) state = new QwirkleState(num); //based on context if the state is null, it will use a default constructor to start a new game
		return new QwirkleLocalGame(state);
	}

}
