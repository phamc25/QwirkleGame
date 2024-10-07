package edu.up.cs301.qwirklegame;

import java.io.Serializable;
import java.util.ArrayList;

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
public class QwirkleState extends GameState implements Serializable {

	// to satisfy Serializable interface
	private static final long serialVersionUID = 7737393762469851826L;

	// the value of the counter

	private int addPoints;
	private int bagTiles;
	private int tilesPlayed;
	private int tilesDiscarded;

	private int[] playerScores;
	private List<Tile>[] playerHands;
	private Tile[][] board;
	private List<Tile> drawPile;


	private int currPlayer;
	private boolean isTurn;
	private int turnCounter;
	private int tilesOnBoard;
	private int drawTiles;
	private int timer;

	private static final int BOARD_SIZE = 20;
	private static final int HAND_SIZE = 6;
	private static final int MAX_PLAYERS = 4;

	/**
	 * constructor, initializing the counter value from the parameter
	 * <p>
	 * <p>
	 * the value to which the counter's value should be initialized
	 */
//	public QwirkleState(int points, int bag, int play, int discard, int p1,
//						int p2, int p3, int p4, int player, boolean turn,
//						int turnCount, int board, int draw, int time) {
//		super();
//		this.numPlayers = MAX_PLAYERS;
//
//		addPoints = points;
//		bagTiles = bag;
//		tilesPlayed = play;
//		tilesDiscarded = discard;
//		player1Score = p1;
//		player2Score = p2;
//		player3Score = p3;
//		player4Score = p4;
//		currPlayer = player;
//		isTurn = turn;
//		turnCounter = turnCount;
//		tilesOnBoard = board;
//		drawTiles = draw;
//		timer = time;
//	}

	public QwirkleState() {
		super();
		this.numPlayers = MAX_PLAYERS; // Set the number of players

		// Initialize game variables
		this.bagTiles = 108; // Total number of tiles in Qwirkle
		this.tilesPlayed = 0;
		this.tilesDiscarded = 0;
		this.tilesOnBoard = 0;
		this.timer = 0;

		// Initialize player scores
		this.playerScores = new int[numPlayers];

		// Initialize player hands
		this.playerHands = new List<Tile>[numPlayers];
		for (int i = 0; i < numPlayers; i++) {
			playerHands[i] = new ArrayList<>();
		}

		// Initialize the board
		this.board = new Tile[BOARD_SIZE][BOARD_SIZE];

		// Initialize the draw pile and distribute tiles to players
		initializeDrawPile();
		dealInitialHands();
	}

	/**
	 * copy constructor; makes a copy of the original object
	 *
	 * @param orig the object from which the copy should be made
	 */
	public QwirkleState(QwirkleState orig) {
		// set the counter to that of the original
		super(orig);
		this.bagTiles = orig.bagTiles;
		this.tilesPlayed = orig.tilesPlayed;
		this.tilesDiscarded = orig.tilesDiscarded;
		this.tilesOnBoard = orig.tilesOnBoard;
		this.timer = orig.timer;

		this.playerScores = orig.playerScores.clone();
	}


}
