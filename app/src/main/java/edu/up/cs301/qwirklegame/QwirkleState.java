package edu.up.cs301.qwirklegame;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.infoMessage.GameState;


/**
 * This contains the state for the Counter game. The state consist of simply
 * the value of the counter.
 * 
 * @author Chloe Pham
 * @author Talia Martinez
 * @author Tyler Crosbie
 * @author De'Ante Agleham
 * @author Ryan Murray
 *
 * @version July 2013
 */
public class QwirkleState extends GameState {

	// to satisfy Serializable interface
	private static final long serialVersionUID = 7737393762469851826L;

	// the value of the counter
	private int addPoints;
	private int bagTiles;
	private int tilesPlayed;
	private int tilesDiscarded;
	private int player1Score;
	private int player2Score;
	private int player3Score;
	private int player4Score;
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
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param p4
	 * @param player
	 * @param turn
	 * @param turnCount
	 * @param board
	 * @param draw
	 * @param time
	 */
	public QwirkleState(int points, int bag, int play, int discard, int p1,
						int p2, int p3, int p4, int player, boolean turn,
						int turnCount, int board, int draw, int time) {
		addPoints = points;
		bagTiles = bag;
		tilesPlayed = play;
		tilesDiscarded = discard;
		player1Score = p1;
		player2Score = p2;
		player3Score = p3;
		player4Score = p4;
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
		// set the counter to that of the original
		this.addPoints = orig.addPoints;
		this.bagTiles = orig.bagTiles;
		this.tilesPlayed = orig.tilesPlayed;
		this.tilesDiscarded = orig.tilesDiscarded;
		this.player1Score = orig.player1Score;
		this.player2Score = orig.player2Score;
		this.player3Score = orig.player3Score;
		this.player4Score = orig.player4Score;


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

}
