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
						int player, boolean turn, int turnCount, int board, int draw, int time) {
		addPoints = points;
		bagTiles = bag;
		tilesPlayed = play;
		tilesDiscarded = discard;
		playersScore = scores;
		currPlayer = player;
		isTurn = turn;
		turnCounter = turnCount;
		tilesOnBoard = board;
		drawTiles = draw;
		timer = time;
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

		return state;
	}
}
