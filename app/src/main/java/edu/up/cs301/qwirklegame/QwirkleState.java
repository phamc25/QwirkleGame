package edu.up.cs301.qwirklegame;


import java.lang.reflect.Array;
import java.util.ArrayList;

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
	private int[] playersScore;
	private int currPlayer;
	private boolean isTurn;
	private int turnCounter;
	private int tilesOnBoard;
	private int drawTiles;
	private int timer;
	private int currTile;
	private ArrayList<QwirkleTiles> tilesInBag;		// List of tiles in bag: 108
	private ArrayList<QwirkleTiles>[] tilesInHands;		// List of tiles in each player's hands

	// Static variables for common values
	private static final int BOARD_SIZE = 20;
	private static final int HAND_SIZE = 6;
	private static final int MAX_PLAYERS = 4;

	/**
	 * constructor
	 *
	 * @param points
	 * @param bag
	 * @param play
	 * @param scores
	 * @param player
	 * @param turn
	 * @param turnCount
	 * @param board
	 * @param draw
	 * @param time
	 * @param tileBag
	 * @param numPlayers
	 */

	//TODO:  default ctor should not have any parameters
	public QwirkleState(int points, int bag, int play, int[] scores,
						int player, boolean turn, int turnCount, int board, int draw,
						int time, int cTile, ArrayList<QwirkleTiles> tileBag, int numPlayers) {
		this.addPoints = points;
		this.bagTiles = bag;
		this.tilesPlayed = play;
		this.playersScore = scores;
		this.currPlayer = player;
		this.isTurn = turn;
		this.turnCounter = turnCount;
		this.tilesOnBoard = board;
		this.drawTiles = draw;
		this.timer = time;
		this.currTile = cTile;

		// Array for the tiles in the bag
		this.tilesInBag = new ArrayList<>();
		for (QwirkleTiles tile : tileBag) {  //where does tileBag come from???
			this.tilesInBag.add(new QwirkleTiles(tile));
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
		// Set the variables to original's variables
		this.addPoints = orig.addPoints;
		this.bagTiles = orig.bagTiles;
		this.tilesPlayed = orig.tilesPlayed;
		this.playersScore = orig.playersScore;  //need to make deep copy of this
		this.currPlayer = orig.currPlayer;
		this.isTurn = orig.isTurn;
		this.turnCounter = orig.turnCounter;
		this.tilesOnBoard = orig.tilesOnBoard;
		this.drawTiles = orig.drawTiles;
		this.timer = orig.timer;

		// Array for tiles in bag for deep copy
		this.tilesInBag = new ArrayList<>();
		for (QwirkleTiles tile : orig.tilesInBag) {
			this.tilesInBag.add(new QwirkleTiles(tile));
		}

		// Loop for arraylist (number of players)
		this.tilesInHands = new ArrayList[orig.tilesInHands.length];
		for (int i = 0; i < orig.tilesInHands.length; i++) {
			this.tilesInHands[i] = new ArrayList<>();
			// Loop for tiles in players' hands
			for (QwirkleTiles tile : orig.tilesInHands[i]) {
				this.tilesInHands[i].add(new QwirkleTiles(tile));
			}
		}
	}

	/**
	 * places selected tile onto the board
	 */
	protected boolean placeTile (PlaceTileAction action) {
		if (isTurn) {
			QwirkleTiles tile = tilesInHands[currPlayer].remove(currTile); //need to add tile to board
			//TODO:  if (tile == null)
			tilesOnBoard++;

			//TODO:  put the the tile on the board in the specified location
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 *  Selects the tile
	 */
	protected boolean selectTiles (SelectTilesAction action, QwirkleTiles tile) {
		if (isTurn) {
			tile.setSelected(true);  // Mark the tile as selected
			currTile = tilesInHands[currPlayer].indexOf(tile);	// Set the variable to the current tile
			return true;
		}
		return false;
	}

	/**
	 *  Discards tiles that were selected
	 */
	protected boolean discardTiles (DiscardTilesAction action) {
		if (isTurn) { //This doesn't work.  You see if the current player turn === action's player id
			// Removes the selected tiles from the current player's hand
			tilesInHands[currPlayer].removeAll(getSelectedTiles());

			// No selected tiles, return
			if (getSelectedTiles().isEmpty()) {
				return false;
			}

			// Loops through the current player's hand and replaces the removed tiles
			for(int i = 0; i < getSelectedTiles().size(); i++) {
				if (!(tilesInBag.isEmpty())) {
					tilesInHands[currPlayer].add(tilesInBag.remove(0));
				}
				else break;
			}
			return true;
		}
		return false;
	}

	/**
	 *  Quits the game when this action made
	 */
	protected boolean quitGame (QuitGameAction action) {
		return true;
	}

	/**
	 * This method returns an ArrayList of Qwirkle tile objects
	 * that represent the selected tiles for discarding
	 */
	public ArrayList<QwirkleTiles> getSelectedTiles() {
		// The ArrayList of selected tiles
		ArrayList<QwirkleTiles> selectedTiles = new ArrayList<>();
		for (QwirkleTiles tile : tilesInHands[currPlayer]) {
			if (tile.getSelected()) {
				selectedTiles.add(tile);
			}
		}
		return selectedTiles;
	}

	// Getter methods
	public int getAddPoints() {
		return addPoints;
	}
	public int getCurrPlayer() {
		return currPlayer;
	}
	public int getNumTilesOnBoard() {
		return tilesOnBoard;
	}

	// Setter methods
	public void setAddPoints(int points) { this.addPoints = points; }
	public void setCurrPlayer(int player) { this.currPlayer = player; }


	/**
	 * toString method that describes the state of the game as a string
	 */
	public String toString(GameState currState) {
		String state = "Current Game State: \n";	// not complete yet, a placeholder
		state += "Tiles left in bag: " + bagTiles + "\n";
		state += "Tiles played: " + tilesPlayed + "\n";
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

/**
 * External Citation
 *
 * Source: PigGame code from CS371 Lab
 * Usage: Used as a reference for instance variables and actions
 *
 * Date: October 5, 2024
 */
