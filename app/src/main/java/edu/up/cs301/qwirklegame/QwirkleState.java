package edu.up.cs301.qwirklegame;


import static edu.up.cs301.qwirklegame.Board.COLUMNS;
import static edu.up.cs301.qwirklegame.Board.ROWS;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

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
public class QwirkleState extends GameState implements Serializable {

	// to satisfy Serializable interface
	private static final long serialVersionUID = 7737393762469851826L;

	// Instance variables
	private int pointsToAdd;               // At the end of every turn, scores is calculated to be added
	private int currPlayer;                // An integer that represents the current player playing
	private int drawTiles;                 // The # of tiles that need to be drawn at the end of turn
	private int currTile;                  // Represents the current tile index for tilesInHands
	private Board board;                   // Board game object that contains a QwirkleTile[][]
	private int[] playersScore;                           // An array to hold player's scores
	private ArrayList<QwirkleTile> tilesInBag;            // ArrayList of tiles in bag: 72
	private ArrayList<QwirkleTile>[] tilesInHands;        // ArrayList of tiles in each player's hands
	private boolean isFirstMove;						  // First move of the game
    private ArrayList<Integer> currentTilesX = new ArrayList<>();
	private ArrayList<Integer> currentTilesY = new ArrayList<>();

	// Static variables for common values
	public static final int HAND_SIZE = 6;

	/**
	 * default constructor
	 * inits the game to match the starting state
	 */
	public QwirkleState(int numPlayers) {
		this.numPlayers = numPlayers;
		this.pointsToAdd = 0;    // No points added to score yet
		this.currPlayer = 0;    // No current player is decided yet at the beginning
		this.drawTiles = 6;    // Each player needs to draw 6 tiles at the beginning
		this.currTile = -1;    // Current tile selected not initialized yet
		this.board = new Board();
		this.playersScore = new int[this.numPlayers];    // Empty array of all player's scores
		this.tilesInBag = new ArrayList<QwirkleTile>(108); // Initial array of 72 tiles
		this.isFirstMove = true;

        // Iterate through enums and create 2 Qwirkle Tiles of each shape and color
		for (QwirkleTile.Color color : QwirkleTile.Color.values())
			for (QwirkleTile.Shape shape : QwirkleTile.Shape.values())
				for (int i = 0; i < 3; i++) {
					this.tilesInBag.add(new QwirkleTile(shape, color));
				}
		tilesInBag = shuffleTiles(tilesInBag);

		setupTileLists(this.numPlayers);

		// Draw 6 tiles initially
		for (int i = 0; i < numPlayers; i++) {
			drawTiles(i, HAND_SIZE);
		}
	}

	/**
	 * Constructor for unit tests
	 */
	public QwirkleState() {
		this(2);
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
		this.isFirstMove = orig.isFirstMove;

		//copy the currTileInLine vars
		for(Integer num : orig.currentTilesX) {
			this.currentTilesX.add(num);
		}
		for(Integer num : orig.currentTilesY) {
			this.currentTilesY.add(num);
		}

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
		if (isValid(action.getPlacedTile(), action.getX(), action.getY()) && !(board.notEmpty(action.getX(), action.getY()))) {
			ArrayList<QwirkleTile> playerHand = tilesInHands[currPlayer];

			//place the tile now
			board.addToBoard(action.getPlacedTile(), action.getX(), action.getY());

			// Set the tile to null in the hand
			playerHand.set(currTile, null);
		}
		else {
			return false;
		}
		ArrayList<QwirkleTile> playerHand = tilesInHands[currPlayer];
		// Set the tile to null in the hand
		return true;
	}

	/**
	 * Discards tiles that were selected
	 */
	protected boolean discardTiles (DiscardTilesAction action) {
		// Get the player hand
		ArrayList<QwirkleTile> playerHand = tilesInHands[currPlayer];

		// Add the current tile selected to the bag
		tilesInBag.add(playerHand.get(currTile));

		// Set that current tile in the hand to null
		playerHand.set(currTile, null);

		// Shuffle the tiles and return true
		tilesInBag = shuffleTiles(tilesInBag);
		return true;
	}

	/**
	 * Ends your turn when action is made
	 */
	protected boolean endTurn (EndTurnAction action) {
		// Update the player's score at the end of the turn
		currentTilesX.clear();
		currentTilesY.clear();
		currTile = -1;
		return true;
	}

	/**
	 * Method that switches the currPlayer variable to the next player
	 */
	public void nextPlayer() {
		this.currPlayer = (currPlayer + 1) % numPlayers;
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
	 * Helper method for isValid
	 * <p>
	 * changes an x,y coordinate by one step in a given dir
	 */
	public int[] takeStep(int x, int y, String dir) {
		int[] cord = new int[2];
		// Take a step in the direction by changing x or y vals
		switch (dir) {
			case "north":
				x--;
				break;
			case "south":
				x++;
				break;
			case "east":
				y++;
				break;
			case "west":
				y--;
				break;
		}
		// Assign cord values with updated x and y and return
		cord[0] = x;
		cord[1] = y;
		return cord;
	}

	// Checks if all tiles from current turn are connected in a straight line
	public boolean currTilesInLine(int candX, int candY) {
		// Stores this move's tiles in an arrayList
		//if previous candX or candY equals current, dont add

		boolean sameCoordX = false;
		boolean sameCoordY = false;
        // Checks if X value of current tile matches an old coordinate
		// and if not it adds the value to the arraylist
		if (this.currentTilesX.size() == 0) {
			this.currentTilesX.add(candX);
			this.currentTilesY.add(candY);
		}
		else {
			for (int i = 0; i < this.currentTilesX.size(); i++) {
				if (this.currentTilesX.get(i) == candX) {
					sameCoordX = true;
					break;
				}
			}
			if (!sameCoordX) {
				this.currentTilesX.add(candX);
			}

			// Checks if Y value of current tile matches an old coordinate
			// and if not it adds the value to the arraylist
			for (int i = 0; i < this.currentTilesY.size(); i++) {
				if (this.currentTilesY.get(i) == candY) {
					sameCoordY = true;
					break;
				}
			}
			if (!sameCoordY) {
				this.currentTilesY.add(candY);
			}
		}

		if (this.currentTilesX.size() > 1 && this.currentTilesY.size() > 1) {
			if (!sameCoordX) {
				this.currentTilesX.remove(this.currentTilesX.size() - 1);
			}
			if (!sameCoordY) {
				this.currentTilesY.remove(this.currentTilesY.size() - 1);
			}
			return false;
		}

		// Int var keeps track of when the loop doesnt find a current tile in a direction
		int fails = 0;
		// Check if candX connects to any of the current X coordinates
		if (currentTilesX.size() > 1) {
			// searches for a matching tile in the same line and in +X direction
			for (int i = 1; i < 6; i++) {
				if (board.notEmpty(candX + i, candY)) {
					if (currentTilesX.indexOf(candX + i) != -1) {
						break;
					}
				}
				else {
					fails++;
					break;
				}
			}
			// searches for a matching tile in the same line and in -X direction
			for (int i = 1; i < 6; i++) {
				if (board.notEmpty(candX - i, candY)) {
					if (currentTilesX.indexOf(candX - i) != -1) {
						break;
					}
				}
				else {
					fails++;
					break;
				}
			}
		}
		// Check if candY connects to any of the current Y coordinates
		if (currentTilesY.size() > 1) {
			// searches for a matching tile in the same line and in +Y direction
			for (int i = 1; i < 6; i++) {
				if (board.notEmpty(candX, candY + i)) {
					if (currentTilesY.indexOf(candY + i) != -1) {
						break;
					}
				}
				else {
					fails++;
					break;
				}
			}
			// searches for a matching tile in the same line and in -Y direction
			for (int i = 1; i < 6; i++) {
				if (board.notEmpty(candX, candY - i)) {
					if (currentTilesY.indexOf(candY - i) != -1) {
						break;
					}
				}
				else {
					fails++;
					break;
				}
			}
		}

		// if the loop cant find a matching tile in the same line in either direction
		// returns false
		if (fails >= 2) {
			return false;
		}
		return true;
	}//currTilesInLine

	/**
	 * isValid
	 *
	 * @return true if the given tile can be legally placed in the given position
	 */
	public boolean isValid(QwirkleTile toPlace, int candX, int candY) {
		// Check if this is the first move
		if (isFirstMove) {
			// Force the player to play in the center of the board
			if (candX != ROWS / 2 || candY != COLUMNS / 2) {
				return false;
			}
			isFirstMove = false;		// Set to false once first move is made
			this.pointsToAdd++;			// Increment points
			currentTilesX.add(candX);	// Add this index to currentTilesX
			currentTilesY.add(candY);	// Add this index to currentTilesY
			return true;
		}

		// If a tile has a neighbor
		boolean hasNeighbor = false;
		// Strings for all directions of a tile
		String[] directions = {"north", "south", "east", "west"};

		// Strings for vertical and horizontal line iteration
		String[] lines = {"vertical", "horizontal"};

		// First check if the position has any adjacent tiles
		for (String dir : directions) {
			// Next position index
			int[] nextPos = takeStep(candX, candY, dir);
			int adjX = nextPos[0];
			int adjY = nextPos[1];

			// Bound checking and see if there is a tile in that direction
			if (adjX >= 0 && adjX < ROWS && adjY >= 0 && adjY < COLUMNS && board.notEmpty(adjX, adjY)) {
				hasNeighbor = true;
				// Stop iterating
				break;
			}
		}

		// If all directions have been iterated and theres nothing, return false
		if (!hasNeighbor) {
			return false;
		}

		// Check both vertical and horizontal dirs
		for (String line : lines) {
			ArrayList<QwirkleTile> tilesInLine = new ArrayList<>();

			// Add the tile player is trying to place
			tilesInLine.add(toPlace);

			// Get the directions to check based on the line
			String[] lineDirections;
			// If its a vertical line, make line directions be north and south
			if (line.equals("vertical")) {
				lineDirections = new String[]{"north", "south"};
				// Else its horizontal, make it east and west
			} else {
				lineDirections = new String[]{"east", "west"};
			}

			// Check both dirs
			for (String dir : lineDirections) {
				int[] pos = takeStep(candX, candY, dir);

				// Keep going in this direction while there are tiles
				while (pos[0] >= 0 && pos[0] < ROWS && pos[1] >= 0 && pos[1] < COLUMNS &&
						board.notEmpty(pos[0], pos[1])) {
					QwirkleTile nextTile = board.getTiles()[pos[0]][pos[1]];
					tilesInLine.add(nextTile);
					pos = takeStep(pos[0], pos[1], dir);
				}
			}

			// If we found tiles in this line
			if (tilesInLine.size() > 1) {
				// Check if this is a color line or shape line based on first two tiles
				QwirkleTile firstTile = tilesInLine.get(0);
				QwirkleTile secondTile = tilesInLine.get(1);

				if ((firstTile == null) || (secondTile == null)) {
					Log.d("oops!", "null tile");
				}

				boolean isColorLine = false;
				try {
					isColorLine = firstTile.getColor() == secondTile.getColor();
				}
				catch (NullPointerException e) {
				}

				// Validate all tiles in the line follow the same rule
				for (int i = 1; i < tilesInLine.size(); i++) {
					QwirkleTile currTile = tilesInLine.get(i);

					if (isColorLine) {
						// In a color line, all colors must match and shapes must be different
						if (currTile.getColor() != firstTile.getColor()) {
							return false;
						}
						// Check for duplicate shapes in color line
						for (int j = 0; j < i; j++) {
							if (currTile.getShape() == tilesInLine.get(j).getShape()) {
								return false;
							}
						}
					} else {
						// In a shape line, all shapes must match and colors must be different
						try {
							if (currTile.getShape() != firstTile.getShape()) {
								return false;
							}
						} catch (NullPointerException e) {
							return false;
						}
						// Check for duplicate colors in shape line
						for (int j = 0; j < i; j++) {
							if (currTile.getColor() == tilesInLine.get(j).getColor()) {
								return false;
							}
						}
					}
				}
			}
		}

		//Checks if this turn's tiles are in one line
		if (!currTilesInLine(candX,candY)) {
			return false;
		}
		// Add point for every tile placed
		this.pointsToAdd = calcScore(candX, candY);
		// no mismatches found, no repeated tiles, connected to another tile
		return true;
	}

	/**
	 * Calculates score for current player's turn
	 * Checks if Qwirkle has been achieved (colors/shapes match up)
	 *
	 * @param candX
	 * @param candY
	 * @return
	 */
	protected int calcScore(int candX, int candY) {
		//whether or not there is a tile adjacent in each direction
		boolean right = true;
		boolean left = true;
		boolean up = true;
		boolean down = true;

		//ret value
		int score = 1;

		//iterator variables
		int yChan = 0;
		int xChan = 0;

		// Iterate in +col direction
		for (int j = 0; j < 5; j++) {
			yChan++;
			if (board.notEmpty(candX + xChan, candY + yChan, false)) {
				score++;
			} else {
				up = false;
				break;
			}
		}
		yChan = 0;
		// Iterate in -col direction
		for (int j = 0; j < 5; j++) {
			yChan--;
			if (board.notEmpty(candX + xChan, candY + yChan, false)) {
				score++;
			} else {
				down = false;
				break;
			}
		}
		yChan = 0;
		// Iterate in +row direction
		for (int j = 0; j < 5; j++) {
			xChan++;
			if (board.notEmpty(candX + xChan, candY + yChan, false)) {
				score++;
			} else {
				right = false;
				break;
			}
		}
		xChan = 0;
		// Iterate in +row direction
		for (int j = 0; j < 5; j++) {
			xChan--;
			if (board.notEmpty(candX + xChan, candY + yChan, false)) {
				score++;
			} else {
				left = false;
				break;
			}
		}
		// If a Qwirkle move -> bonus 6 points
		if ((right||left)||(up||down)) {
			score += 6;
		}
		return score;
	}

	/**
	 * Shuffles tiles placed into the bag
	 *
	 * @param currBag
	 * @return
	 */
	public ArrayList<QwirkleTile> shuffleTiles(ArrayList<QwirkleTile> currBag) {
		ArrayList<QwirkleTile> shuffledBag = new ArrayList<>(currBag); // Create a copy of the tiles in the bag
		for (int i = 0; i < shuffledBag.size(); i++) {
			// Swap the current tile with a random tile
			int randomIndex = (int) (Math.random() * shuffledBag.size());
			QwirkleTile temp = shuffledBag.get(i);
			shuffledBag.set(i, shuffledBag.get(randomIndex));
			shuffledBag.set(randomIndex, temp);
		}
		return shuffledBag;
	}

	/**
	 * toString method that returns the current GameState in a string
	 * @param currState
	 * @return
	 */
	@Override
	public String toString (GameState currState){
		String state = "Current Game State: \n";
		state += "Points to add: " + this.pointsToAdd + "\n";
		state += "Current player: " + this.currPlayer + "\n";
		state += "Tiles to be drawn: " + this.drawTiles + "\n";

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
	public void setPlayersScore(int player, int score) {this.playersScore[player] = score; }
	public void setCurrTile(int curr) {
		this.currTile = curr;
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

/**
 * External Citation
 *
 * Problem: How can we send the action to the local gamestate instead?
 * Source: Professor Nuxoll office hours
 *
 * Date: November 8, 2024
 */

/**
 * External Citation
 *
 * Problem: isValid() method implementation
 * Source: Professor Nuxoll office hours
 *
 * Date: Week of November 11, 2024
 */




