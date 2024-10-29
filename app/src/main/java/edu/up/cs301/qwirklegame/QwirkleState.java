package edu.up.cs301.qwirklegame;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

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
	private int pointsToAdd;	// At the end of every turn, scores is calculated to be added
	private int currPlayer;	// An integer that represents the current player playing
	private int drawTiles;	// The # of tiles that need to be drawn at the end of turn
	private int currTile;	// Represents the current tile index for tilesInHands
	private Board board;	// Board game object that contains a QwirkleTile[][]
	private int[] playersScore;	// An array to hold player's scores
	private ArrayList<QwirkleTile> tilesInBag;		// ArrayList of tiles in bag: 72
	private ArrayList<QwirkleTile>[] tilesInHands;		// ArrayList of tiles in each player's hands

	// Static variables for common values
	public static final int HAND_SIZE = 6;
	public static final int MAX_PLAYERS = 4;

	/**
	 * default constructor
	 * inits the game to match the starting state
	 */
	public QwirkleState() {
		this.pointsToAdd = 0;	// No points added to score yet
		this.currPlayer = 0;	// No current player is decided yet at the beginning
		this.drawTiles = 6;	// Each player needs to draw 6 tiles at the beginning
		this.currTile = -1;	// Current tile selected not initialized yet
		this.board = new Board();
		this.playersScore = new int[2];	// Empty array of all player's scores
		this.tilesInBag = new ArrayList<QwirkleTile>(13); // Initial array of 72 tiles

		// TODO: figure out how to set numPlayers in a different way that isn't hardcoding
		numPlayers = 2;

		// Iterate through enums and create 2 Qwirkle Tiles of each shape and color
		//TODO: re add after Proj E
//		for (QwirkleTile.Color color : QwirkleTile.Color.values())
//			for (QwirkleTile.Shape shape : QwirkleTile.Shape.values())
//				for (int i = 0; i < 2; i++) {
//					this.tilesInBag.add(new QwirkleTile(shape,color));
//				}
		//TODO: Remove after ProjE
		this.tilesInBag.add(new QwirkleTile(QwirkleTile.Shape.CIRCLE,QwirkleTile.Color.RED));
		this.tilesInBag.add(new QwirkleTile(QwirkleTile.Shape.CIRCLE,QwirkleTile.Color.GREEN));
		this.tilesInBag.add(new QwirkleTile(QwirkleTile.Shape.CLOVER,QwirkleTile.Color.ORANGE));
		this.tilesInBag.add(new QwirkleTile(QwirkleTile.Shape.SQUARE,QwirkleTile.Color.RED));
		this.tilesInBag.add(new QwirkleTile(QwirkleTile.Shape.EIGHTSTAR,QwirkleTile.Color.BLUE));
		this.tilesInBag.add(new QwirkleTile(QwirkleTile.Shape.SQUARE,QwirkleTile.Color.ORANGE));
		this.tilesInBag.add(new QwirkleTile(QwirkleTile.Shape.CIRCLE,QwirkleTile.Color.BLUE));
		this.tilesInBag.add(new QwirkleTile(QwirkleTile.Shape.EIGHTSTAR,QwirkleTile.Color.RED));
		this.tilesInBag.add(new QwirkleTile(QwirkleTile.Shape.CLOVER,QwirkleTile.Color.BLUE));
		this.tilesInBag.add(new QwirkleTile(QwirkleTile.Shape.FOURSTAR,QwirkleTile.Color.ORANGE));
		this.tilesInBag.add(new QwirkleTile(QwirkleTile.Shape.CIRCLE,QwirkleTile.Color.YELLOW));
		this.tilesInBag.add(new QwirkleTile(QwirkleTile.Shape.DIAMOND,QwirkleTile.Color.ORANGE));
		this.tilesInBag.add(new QwirkleTile(QwirkleTile.Shape.EIGHTSTAR,QwirkleTile.Color.YELLOW));
		this.tilesInBag.add(new QwirkleTile(QwirkleTile.Shape.CIRCLE,QwirkleTile.Color.ORANGE));

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
		this.pointsToAdd = orig.pointsToAdd;
		this.currPlayer = orig.currPlayer;
		this.drawTiles = orig.drawTiles;
		this.currTile = orig.currTile;
		this.board = new Board(orig.board);

		// Deep copy for int array
		this.playersScore = Arrays.copyOf(orig.playersScore, orig.playersScore.length);

		// Array for tiles in bag for deep copy
		this.tilesInBag = new ArrayList<>();
		for (QwirkleTile tile : orig.tilesInBag) {
			this.tilesInBag.add(new QwirkleTile(tile));
		}

		// Loop for arraylist (number of players)
		this.tilesInHands = new ArrayList[orig.tilesInHands.length];
		for (int i = 0; i < orig.tilesInHands.length; i++) {
			this.tilesInHands[i] = new ArrayList<>();
			// Loop for tiles in players' hands
			for (QwirkleTile tile : orig.tilesInHands[i]) {
				this.tilesInHands[i] = new ArrayList<>(orig.tilesInHands[i]);
			}
		}
	}

	// All action methods will assume that player can make a valid move when called
	/**
	 * Places selected tile onto the board
	 */
	protected boolean placeTile (PlaceTileAction action) {
		ArrayList<QwirkleTile> playerHand = tilesInHands[currPlayer];
		QwirkleTile tile = playerHand.remove(currTile); //need to add tile to board
		if (tile ==  null || currTile < 0 || currTile >= playerHand.size()) {
			return false; // invalid index or empty hand
		}
		board.addToBoard(tile, action.getX(), action.getY());
		return true;
	}

	/**
	 *  Discards tiles that were selected
	 */
	protected boolean discardTiles (DiscardTilesAction action, ArrayList<QwirkleTile> hand) {
		// Gets the selected tiles from the action
		ArrayList<QwirkleTile> selectedTiles = getSelectedTiles(hand);

		// No selected tiles, return
		if (selectedTiles.isEmpty()) {
			return false;
		}

		// Removes the selected tiles from the current player's hand and into the bag
        tilesInBag.addAll(selectedTiles);
		hand.removeAll(selectedTiles);

		return true;
	}

	/**
	 * This method returns an ArrayList of Qwirkle tile objects
	 * that represent the selected tiles for discarding
	 */
	public ArrayList<QwirkleTile> getSelectedTiles(ArrayList<QwirkleTile> hand) {
		// The ArrayList of selected tiles
		ArrayList<QwirkleTile> selectedTiles = new ArrayList<>();
		for (QwirkleTile tile : hand) {
			if (tile.getSelected()) {
				selectedTiles.add(tile);
			}
		}
		return selectedTiles;
	}

	/**
	 *  Quits the game when this action made
	 */
	protected boolean quitGame (QuitGameAction action) {
		// can quit at any time
		return true;
	}

	/**
	 * Ends your turn when action is made
	 */
	protected boolean endTurn (EndTurnAction action) {
		// can end their turn at any time
		int toRefill = 6 - tilesInHands[currPlayer].size();
		drawTiles(currPlayer, toRefill);

		this.currPlayer = 1-currPlayer;
		return true;
	}

	// Getter methods
	public int[] getPlayersScore() { return playersScore; }
	public int getAddPoints() {
		return pointsToAdd;
	}
	public int getCurrPlayer() {
		return currPlayer;
	}

	// Setter methods
	public void setAddPoints(int points) { this.pointsToAdd = points; }
	public void setCurrPlayer(int player) { this.currPlayer = player; }
	public void setCurrTile(int tile) { this.currTile = tile; }
	public void setPlayersScore(int player, int score) {
		this.playersScore[player] = score;
	}

	public void drawTiles(int playerIndex, int numTiles) {
		for (int i = 0; i < numTiles && !tilesInBag.isEmpty(); i++) {
			//TODO: restore after Proj E
//			int randomIndex = (int)(Math.random() * tilesInBag.size());
//			QwirkleTile drawnTile = tilesInBag.remove(randomIndex);
			QwirkleTile drawnTile = tilesInBag.get(0);
			tilesInBag.remove(0);
			//tilesInHands[] = numPlayers;
			tilesInHands[playerIndex].add(drawnTile);
		}
	}
	public void refillHand(int playerIndex) {
		int tilesNeeded = 6 - tilesInHands[playerIndex].size();
		drawTiles(playerIndex, tilesNeeded);
	}
	public ArrayList<QwirkleTile> getPlayerHand(int playerIndex) {
		if (playerIndex >= 0 && playerIndex < tilesInHands.length) {
			return tilesInHands[playerIndex];
		}
		return null; // Return null if invalid index
	}

	/**
	 * toString method that describes the state of the game as a string
	 */
	@Override
	public String toString(GameState currState) {
		String state = "Current Game State: \n";
		state += "Points to add: " + this.pointsToAdd + "\n";
		state += "Current player: " + this.currPlayer + "\n";
		state += "Tiles to be drawn: " + this.drawTiles + "\n";
		state += "Current tile selected: " + this.currTile + "\n";

		// Loops through the board array and prints the number of QwirkleTiles in the board
		int tiles = 0;
		for (int i = 0; i < board.getBoard().length; i++) {
			for (int j = 0; j < board.getBoard()[i].length; j++) {
				if (board.getBoard()[i][j] != null)
					tiles++;
			}
		}
		state += "Number of tiles on board: " + tiles + "\n";

		// Loops through players' scores and print them
		int topScore = 0;
		int winner = 0;
		for (int i = 0; i < playersScore.length; i++) {
			if (playersScore[i] > topScore) {
				topScore = playersScore[i];
				winner = i;
			}
			state += "Player " + i + " score: " + playersScore[i] + "\n";
		}

		state += "Number of tiles in bag: " + this.tilesInBag.size() + "\n";

		// Loop through the array of arraylists and print out the tiles in each
		// player's hand
		for (int i = 0; i < tilesInHands.length; i++) {
			state += "Player " + i + " tiles: ";	// Prints player
			for (int j = 0; j < tilesInHands[i].size(); j++) {
				QwirkleTile tile = tilesInHands[i].get(j);
				state += tile.getColor() + " " + tile.getShape() + ", ";	// Prints out the hand
			}
			state+= "\n";
		}

		state += "Game winner: Player " + winner + " with " + topScore + " points!" + "\n";
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

/**
 * External Citation
 *
 * Problem: How can we deep copy an array?
 * Source: https://howtodoinjava.com/java/array/java-array-clone-shallow-copy/
 * Usage: Deep copied an int array for playersScore
 *
 * Date: October 11, 2024
 */

/**
 * External Citation
 *
 * Problem: How can we get values from enums?
 * Source: https://www.baeldung.com/java-enum-iteration
 * Usage: Initializing all 72 tiles in the bag
 *
 * Date: October 11, 2024
 */

/**
 * External Citation
 *
 * Problem: How can we use copyOf to copy an array?
 * Source: https://www.geeksforgeeks.org/arrays-copyof-in-java-with-examples/
 * Usage: Deep copying the players score array
 *
 * Date: October 11, 2024
 */


