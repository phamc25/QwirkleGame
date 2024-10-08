package edu.up.cs301.qwirklegame;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.infoMessage.GameState;


/**
 * This contains the state for the Qwirkle game.
 * 
 * @author Chloe Pham
 * @author Talia Martinez
 * @author Tyler Crosbie
 * @author De'Ante Agleham
 * @author Ryan Murray
 *
 * @version October 5, 2024
 */
public class QwirkleState extends GameState {

	// to satisfy Serializable interface
	private static final long serialVersionUID = 7737393762469851826L;

	// Instance variables
	private int addPoints;
	private int bagTiles;
	private int tilesPlayed;
	private int tilesDiscarded;
	private int[] playersScore;
	private int currPlayer;
	private boolean isTurn;
	private int turnCounter;
	private int tilesOnBoard;
	private int drawTiles;
	private int timer;
	private ArrayList<QwirkleTiles> tilesInBag;		// List of tiles in bag - 108
	private ArrayList<QwirkleTiles>[] tilesInHands;		// List of tiles in each player's hands

	private static final int BOARD_SIZE = 20;
	private static final int HAND_SIZE = 6;
	private static final int MAX_PLAYERS = 4;

	/**
	 * constructor
	 *
	 * @param points
	 * @param bag
	 * @param play
	 * @param discard
	 * @param scores
	 * @param player
	 * @param turn
	 * @param turnCount
	 * @param board
	 * @param draw
	 * @param time
	 */
	public QwirkleState(int points, int bag, int play, int discard, int[] scores,
						int player, boolean turn, int turnCount, int board, int draw,
						int time, ArrayList<QwirkleTiles> tileBag, int numPlayers) {
		this.addPoints = points;
		this.bagTiles = bag;
		this.tilesPlayed = play;
		this.tilesDiscarded = discard;
		this.playersScore = scores;
		this.currPlayer = player;
		this.isTurn = turn;
		this.turnCounter = turnCount;
		this.tilesOnBoard = board;
		this.drawTiles = draw;
		this.timer = time;

		// Array for the tiles in the bag
		this.tilesInBag = new ArrayList<>();
		for (QwirkleTiles tile : tileBag) {
			this.tilesInBag.add(new QwirkleTiles(tile)); // write copy constructor for tile
		}

		// Array for the tiles in the players' hands
		this.tilesInHands = new ArrayList[numPlayers];
		for (int i = 0; i < numPlayers; i++) {
			this.tilesInHands[i] = new ArrayList<>();
		}
	}

	/**
	 * copy constructor; makes a copy of the original object
	 *
	 * @param orig the object from which the copy should be made
	 */
	public QwirkleState(QwirkleState orig) {
		// set the variables to original's variables
		this.addPoints = orig.addPoints;
		this.bagTiles = orig.bagTiles;
		this.tilesPlayed = orig.tilesPlayed;
		this.tilesDiscarded = orig.tilesDiscarded;
		this.playersScore = orig.playersScore;
		this.currPlayer = orig.currPlayer;
		this.isTurn = orig.isTurn;
		this.turnCounter = orig.turnCounter;
		this.tilesOnBoard = orig.tilesOnBoard;
	}

	protected boolean placeTile (PlaceTileAction action) {
		return false;
	}
	protected boolean drawTiles (DrawTilesAction action) {
		return false;
	}
	protected boolean selectTiles (SelectTilesAction action) {
		return false;
	}
	protected boolean tradeTiles (TradeTilesAction action) {
		return false;
	}
	protected boolean discardTiles (DiscardTilesAction action) {
		return false;
	}
	protected boolean quitGame (QuitGameAction action) {
		return true;
	}

	/**
	 * toString method that describes the state of the game as a string
	 */
	public String toString(GameState currState) {
		String state = "Current Game State: \n";	// not complete yet, a placeholder
		state += "Tiles left in bag: " + bagTiles + "\n";
		state += "Tiles played: " + tilesPlayed + "\n";
		state += "Tiles discarded: " + tilesDiscarded + "\n";
		for(int i = 0; i < playersScore.length; i++){
			state += "Player " + i + " score: " +playersScore[i] + "\n";
		}
		state += "Current player: " + currPlayer + "\n";
		state += "Is player's turn: " + isTurn + "\n";
		state += "Turn number: " + turnCounter + "\n";
		state += "Tiles on board: " + tilesOnBoard + "\n";

		return state;
	}
}
