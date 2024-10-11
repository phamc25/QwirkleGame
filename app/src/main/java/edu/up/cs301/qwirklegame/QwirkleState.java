package edu.up.cs301.qwirklegame;


import java.util.ArrayList;

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
	private int addPoints;	// At the end of every turn, scores is calculated to be added
	private int[] playersScore;	// An array to hold player's scores
	private int currPlayer;	// An integer that represents the current player playing
	private int drawTiles;	// The # of tiles that need to be drawn at the end of turn
	private int timer;	// 2 minute timer limit for human player moves
	private int currTile;	// Represents the current tile index for tilesInHands
	private ArrayList<QwirkleTiles> tilesInBag;		// List of tiles in bag: 108
	private ArrayList<QwirkleTiles>[] tilesInHands;		// List of tiles in each player's hands
	private Board board;	// Board game object of QwirkleTiles

	// Static variables for common values
	private static final int HAND_SIZE = 6;
	private static final int MAX_PLAYERS = 4;

	/**
	 * default constructor
	 */
	public QwirkleState() {
		this.addPoints = 0;	// No points added to score yet
		this.playersScore = new int[4];	// Empty array of all player's scores
		this.currPlayer = -1;	// No current player is decided yet at the beginning
		this.drawTiles = 6;	// Each player needs to draw 6 tiles at the beginning
		this.timer = -1;	// Timer not initialized yet
		this.currTile = -1;	// Current tile selected not initialized yet
		this.board = new Board();

		this.tilesInBag = new ArrayList<QwirkleTiles>(72);	// Initial array of 108 tiles

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
		this.playersScore = orig.playersScore;  //need to make deep copy of this
		this.currPlayer = orig.currPlayer;
		this.drawTiles = orig.drawTiles;
		this.timer = orig.timer;
		this.currTile = orig.currTile;
		this.board = new Board(orig.board);

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

	//All action methods will assume that player can make a valid move when called
	/**
	 * places selected tile onto the board
	 */
	protected boolean placeTile (PlaceTileAction action) {
		QwirkleTiles tile = tilesInHands[currPlayer].remove(currTile); //need to add tile to board
		if (tile ==  null){
			return false;
		}
		board.addToBoard(tile, action.getX(), action.getY());
		return true;
	}

	/**
	 *  Selects the tile
	 */
	protected boolean selectTiles (SelectTilesAction action, QwirkleTiles tile) {
		tile.setSelected(true);  // Mark the tile as selected
		currTile = tilesInHands[currPlayer].indexOf(tile);	// Set the variable to the current tile
		return true;
	}

	/**
	 *  Discards tiles that were selected
	 */
	protected boolean discardTiles (DiscardTilesAction action) {
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

	// Setter methods
	public void setAddPoints(int points) { this.addPoints = points; }
	public void setCurrPlayer(int player) { this.currPlayer = player; }


	/**
	 * toString method that describes the state of the game as a string
	 */
	@Override
	public String toString(GameState currState) {
		String state = "Current Game State: \n";	// not complete yet, a placeholder
		//state += "Tiles left in bag: " + bagTiles + "\n";
		//state += "Tiles played: " + tilesPlayed + "\n";
		for(int i = 0; i < playersScore.length; i++){
			state += "Player " + i + " score: " +playersScore[i] + "\n";
		}
		state += "Current player: " + currPlayer + "\n";
		state += "Is player " + currPlayer + " turn." + "\n";
		//state += "Turn number: " + turnCounter + "\n";
		//state += "Tiles on board: " + tilesOnBoard + "\n";
		return state;
	}

	public void drawTiles(int playerIndex, int numTiles) {
		for (int i = 0; i < numTiles && !tilesInBag.isEmpty(); i++) {
			int randomIndex = (int)(Math.random() * tilesInBag.size());
			QwirkleTiles drawnTile = tilesInBag.remove(randomIndex);
			tilesInHands[playerIndex].add(drawnTile);
		}
	}
	public void refillHand(int playerIndex) {
		int tilesNeeded = 6 - tilesInHands[playerIndex].size();
		drawTiles(playerIndex, tilesNeeded);
	}
	public ArrayList<QwirkleTiles> getPlayerHand(int playerIndex) {
		if (playerIndex >= 0 && playerIndex < tilesInHands.length) {
			return tilesInHands[playerIndex];
		}
		return null; // Return null if invalid index
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
