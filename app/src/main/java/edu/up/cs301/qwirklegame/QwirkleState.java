package edu.up.cs301.qwirklegame;


import java.util.ArrayList;
import java.util.Arrays;

import edu.up.cs301.GameFramework.gameConfiguration.GameConfig;
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
	private int pointsToAdd;    // At the end of every turn, scores is calculated to be added
	private int currPlayer;    // An integer that represents the current player playing
	private int drawTiles;    // The # of tiles that need to be drawn at the end of turn
	private int currTile;    // Represents the current tile index for tilesInHands
	private Board board;    // Board game object that contains a QwirkleTile[][]
	private int[] playersScore;    // An array to hold player's scores
	private ArrayList<QwirkleTile> tilesInBag;        // ArrayList of tiles in bag: 72
	private ArrayList<QwirkleTile>[] tilesInHands;        // ArrayList of tiles in each player's hands

	// Static variables for common values
	public static final int HAND_SIZE = 6;
	public static final int MAX_PLAYERS = 4;

	/**
	 * default constructor
	 * inits the game to match the starting state
	 */
	public QwirkleState() {
		this.numPlayers = 2;
		this.pointsToAdd = 0;    // No points added to score yet
		this.currPlayer = 0;    // No current player is decided yet at the beginning
		this.drawTiles = 6;    // Each player needs to draw 6 tiles at the beginning
		this.currTile = -1;    // Current tile selected not initialized yet
		this.board = new Board();
		this.playersScore = new int[2];    // Empty array of all player's scores
		this.tilesInBag = new ArrayList<QwirkleTile>(36); // Initial array of 72 tiles


		// Iterate through enums and create 2 Qwirkle Tiles of each shape and color
		for (QwirkleTile.Color color : QwirkleTile.Color.values())
			for (QwirkleTile.Shape shape : QwirkleTile.Shape.values())
				for (int i = 0; i < 1; i++) {
					this.tilesInBag.add(new QwirkleTile(shape, color));
				}

		setupTileLists(this.numPlayers);

		// Draw 6 tiles initially
		for (int i = 0; i < numPlayers; i++) {
			drawTiles(i, HAND_SIZE);
		}
	}

	/**
	 * helper method for ctors to initialize the tiles lists
	 */
	private void setupTileLists(int numPl) {
		// Array for the tiles in the players' hands
		this.tilesInHands = new ArrayList[numPl];
		for (int i = 0; i < numPl; i++) {
			this.tilesInHands[i] = new ArrayList<>();
		}
	}

	/**
	 * Nuxoll's probably bad idea for finding out how many players there are right away
	 */
	public QwirkleState(GameConfig config) {
		this();
		this.numPlayers = config.getNumPlayers();
		if (this.numPlayers != 2) { //not the default
			setupTileLists(this.numPlayers);
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
		// Check if move is valid first before placing tile
		if (isValid(action.getPlacedTile(), action.getX(), action.getY())) {
			ArrayList<QwirkleTile> playerHand = tilesInHands[currPlayer];

			//TODO: confirm that the to-be-placed tile is in the player's hand

			//place the tile now
			board.addToBoard(action.getPlacedTile(), action.getX(), action.getY());

			// Set the tile to null in the hand
			playerHand.set(currTile, null);
		}
		else {

		}
		return true;
	}

	/**
	 * Discards tiles that were selected
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
	 * Quits the game when this action made
	 */
	protected boolean quitGame (QuitGameAction action) {
		// can quit at any time
		return true;
	}

	/**
	 * Ends your turn when action is made
	 */
	protected boolean endTurn (EndTurnAction action) {
		int nullTiles = 0;
		// can end their turn at any time
		for (int i = 0; i < tilesInHands[currPlayer].size(); i++) {
			if (tilesInHands[currPlayer].get(i) == null) {
				nullTiles++;
			}
		}
		drawTiles(currPlayer, nullTiles);

		this.currPlayer = 1 - currPlayer;
		return true;
	}

	// Getter methods
	public int[] getPlayersScore() {
		return playersScore;
	}
	public int getAddPoints() {
		return pointsToAdd;
	}
	public int getCurrPlayer() {
		return currPlayer;
	}
	public int getTilesLeft() { return tilesInBag.size(); }
	public Board getBoard() {
		return board;
	}
	public int getCurrTile() { return currTile; }
	public ArrayList<QwirkleTile> getPlayerHand(int playerIndex) {
		if (playerIndex >= 0 && playerIndex < tilesInHands.length) {
			return tilesInHands[playerIndex];
		}
		return null; // Return null if invalid index
	}

	// Setter methods
	public void setAddPoints(int points) { this.pointsToAdd = points; }
	public void setCurrPlayer(int player) { this.currPlayer = player; }
	public void setPlayersScore(int player, int score) {
		this.playersScore[player] = score;
	}
	public void setCurrTile(int curr) {
		this.currTile = curr;
	}

	/**
	 * Draws tiles to set player's hand
	 * @param playerIndex
	 * @param numTiles
	 */
	public void drawTiles(int playerIndex, int numTiles) {
		for (int i = 0; i < numTiles && !tilesInBag.isEmpty(); i++) {
			int randomIndex = (int)(Math.random() * tilesInBag.size());
			QwirkleTile drawnTile = tilesInBag.remove(randomIndex); // Remove tile only once

			// Find the first null index in the player's hand
			ArrayList<QwirkleTile> playerHand = tilesInHands[playerIndex];
			boolean tileAdded = false;
			for (int j = 0; j < playerHand.size(); j++) {
				if (playerHand.get(j) == null) {
					playerHand.set(j, drawnTile); // Set the tile at the null position
					tileAdded = true;
					break; // Exit the loop after placing the tile
				}
			}

			// If no null spot is found, add the tile at the end
			if (!tileAdded) {
				playerHand.add(drawnTile);
			}
		}
	}

	/**
	 * Refills empty spots of specified player's hand
	 * @param playerIndex
	 */
	public void refillHand(int playerIndex) {
		int tilesNeeded = 6 - tilesInHands[playerIndex].size();
		drawTiles(playerIndex, tilesNeeded);
	}

	/**
	 * helper method for isValid
	 * <p>
	 * changes an x,y coordinate by one step in a given dir
	 * TODO: finish
	 */
	public int[] takeStep(int x, int y, String dir) {
		int[] cord = new int[2];
		switch (dir) {
			case "north":
				y--;
				break;
			case "south":
				y++;
				break;
			case "east":
				x++;
				break;
			case "west":
				x--;
				break;
		}
		cord[0] = x;
		cord[1] = y;
		return cord;
	}


	/**
	 * helper method for isValid
	 *
	 * checks to see if a place on the board is not empty
	 * TODO: finish
	 */
//	public boolean notEmpty(int x, int y) {
//
//		if ( != null) {
//
//		}
//	}

	/**
	 * isValid
	 *
	 * @return true if the given tile can be legally placed in the given position
	 * TODO: finish translating nux's pseudocode
	 */
	public boolean isValid(QwirkleTile toPlace, int candX, int candY) {
		String[] dirs = {"north", "south", "east", "west"};
		QwirkleTile inLineTile = new QwirkleTile(null, null);

		//for each direction
		for (String dir : dirs) {
			int currX = candX;
			int currY = candY;
			QwirkleTile.Color currColor = null;
			QwirkleTile.Shape currShape = null;
			currX = takeStep(currX, currY, dir)[0];
			currY = takeStep(currX, currY, dir)[1];
			ArrayList<QwirkleTile> row = new ArrayList<QwirkleTile>();
			row.add(toPlace);
			while (board.notEmpty(currX, currY)) {
				QwirkleHumanPlayer player = new QwirkleHumanPlayer("temp");

				// Loop through the current player hand and set inLineTile to the current tile
				for (int i = 0; i < tilesInHands[currPlayer].size(); i++) {
					if (i == currTile) {
						inLineTile = tilesInHands[currPlayer].get(currTile);

					}
				}
				PlaceTileAction pta = new PlaceTileAction(player, inLineTile, currX, currY, currTile);
//				inLineTile = pta.getPlacedTile();
				row.add(inLineTile);

				//if both are None then this is the first neighbor,
				//set curr shape and color
				if ((currShape == null) && (currColor == null)) {
					currShape = inLineTile.getShape();
					currColor = inLineTile.getColor();
				}
				//case: mismatching color
				else if ((currColor != null) && (inLineTile.getColor() != currColor)) {
					//Does the shape still match?
					if ((currShape != null) && (inLineTile.getShape() == currShape)) {
						currColor = null;  //ok, enforce the shape and ignore
						//colors from now on
					}
					else {
						return false;
					}
				}

				//case mismatching shape
				else if ((currShape != null) && (inLineTile.getShape() != currShape)) {
					//Does the color still match?
					if ((currColor != null) && (inLineTile.getColor() == currColor)) {
						currColor = null;  //enforce color but not shape
						continue;
					}
					else {
						return false;
					}
				}

				takeStep(currX, currY, dir);

			}//while

			//check for duplicates in the 'row' arraylist
			for (int i = 0; i < row.size(); i++) {
				for (int j = 1; j < row.size(); j++) {
					QwirkleTile t1 = row.get(i);
					QwirkleTile t2 = row.get(j);
					if (t1.equals(t2)) {
						return false;
					}

				}
			}
		}
		//no mismatches found
		return true;
	}

	/**
	 * Checks if Quirkle has been achieved (colors/shapes match up)
	 *
	 * @param toPlace
	 * @param candX
	 * @param candY
	 * @return
	 */
	protected boolean isQwirkle(QwirkleTile toPlace, int candX, int candY) {
		int yChan = 0;
		int xChan = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 6; j++) {
				if (i == 0) {
					yChan = j;
				} else if (i == 1) {
					yChan = -j;
				} else if (i == 2) {
					xChan = j;
				} else if (i == 3) {
					xChan = -j;
				}
				if (!board.notEmpty(candX + xChan, candY + yChan)) {
					return false;

				}
				yChan = 0;
				xChan = 0;
			}
		}
			return true;

	}


		/**
		 * toString method that describes the state of the game as a string
		 */
		@Override
		public String toString (GameState currState){
			String state = "Current Game State: \n";
			// TODO: Commented out for Proj #E, manual hard-code for faux game
//		state += "Points to add: " + this.pointsToAdd + "\n";
			state += "Current player: " + this.currPlayer + "\n";
//		state += "Tiles to be drawn: " + this.drawTiles + "\n";

			// Loops through the board array and prints the number of QwirkleTiles in the board
			int tiles = 0;
			for (int i = 0; i < board.getTiles().length; i++) {
				for (int j = 0; j < board.getTiles()[i].length; j++) {
					if (board.getTiles()[i][j] != null)
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
				state += "Player " + i + " tiles: ";    // Prints player
				for (int j = 0; j < tilesInHands[i].size(); j++) {
					QwirkleTile tile = tilesInHands[i].get(j);
					state += tile.getColor() + " " + tile.getShape() + ", ";    // Prints out the hand
				}
				state += "\n";
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
/**
 * External Citation
 *
 * Problem: How can we check if the player made a valid move?
 * Source: Professor Nuxoll office hours
 *
 * Date: November 4, 2024
 */



