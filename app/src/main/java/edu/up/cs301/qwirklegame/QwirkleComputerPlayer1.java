package edu.up.cs301.qwirklegame;

import static edu.up.cs301.qwirklegame.Board.COLUMNS;
import static edu.up.cs301.qwirklegame.Board.ROWS;

import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

import edu.up.cs301.GameFramework.players.GameComputerPlayer;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.GameFramework.utilities.Tickable;

/**
 * A computer-version of a counter-player.  Since this is such a simple game,
 * it just sends "+" and "-" commands with equal probability, at an average
 * rate of one per second.
 *
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @version September 2013
 *
 */
public class QwirkleComputerPlayer1 extends GameComputerPlayer implements Tickable {
	QwirkleTile[][] temp;
	Random rand = new Random();
	/**
	 * Constructor for objects of class QwirkleComputerPlayer1
	 *
	 * @param name
	 * 		the player's name
	 */
	public QwirkleComputerPlayer1(String name) {
		// invoke superclass constructor
		super(name);

		// start the timer, ticking 20 times per second
//        getTimer().setInterval(50);
//        getTimer().start();
	}

	/**
	 * callback method--game's state has changed
	 *
	 * @param info
	 * 		the information (presumably containing the game's state)
	 */
	@Override
	protected void receiveInfo(GameInfo info) {
		// Ensure we are still working with a valid game state
		if (!(info instanceof QwirkleState)) {
			return;
		}
		QwirkleState gameState = (QwirkleState) info;

		//If it's not my turn, do nothing
		if (this.playerNum != gameState.getCurrPlayer()) {
			return;
		}
//
//		Simulate computer is "thinking"
		try {
			Thread.sleep(500);  // TODO:  make this longer?
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
//
//		//Safety check:  Make sure there is at least one tile in my hand
		ArrayList<QwirkleTile> myHand = gameState.getPlayerHand(gameState.getCurrPlayer());
////		int numTiles = myHand.size();
////		for(QwirkleTile t : myHand) {
////			if (t == null) numTiles--;
////		}
////		if (numTiles == 0) { // no tile to place!
////			game.sendAction(new EndTurnAction(gameState, this, gameState.getNumPlayers()));
////			return;
////		}
////
////		int possX;
////		int possY;
////
////		for (int i = 0; i < gameState.getBoard().ROWS; i++) {
////			for (int j = 0; j < gameState.getBoard().COLUMNS; j++) {
////				// if we come across a space that as a tile, save the index
////				if (gameState.getBoard().notEmpty(i, j)) {
////					possX = i;
////					possY = j;
////					for (int k = 0; k < numTiles; k++) {
////						QwirkleTile curr = myHand.get(k);
////						String[] dirs = {"north", "south", "east", "west"};
////						for (String dir : dirs) {
////							// seeing if the adjacent spaces to a tile on the board is a valid move
////							possX = gameState.takeStep(possX, possY, dir)[0];
////							possY = gameState.takeStep(possX, possY, dir)[1];
////							if (gameState.isValid(curr, possX, possY)) {
////								// let the human player think we're thinking
////								try {
////										Thread.sleep(200);
////								} catch (InterruptedException e) {
////										throw new RuntimeException(e);
////								}
////								PlaceTileAction pta = new PlaceTileAction(this, curr, possX, possY,
////										gameState.getCurrTile());
////								if (gameState.placeTile(pta)) {
////									game.sendAction(pta);
////									return;
////								}
////							}
////						}
////					}
////				}
////			}
////		}
//
//		int tileIndex = rand.nextInt(myHand.size());
////		Try N times to find a valid place to play
//		for(int i = 0; i < 1000; ++i) {
//
//			// Select a random tile from the computer player's hand
//			QwirkleTile toPlace = null;
//			while(toPlace == null) {
//				toPlace = myHand.get(tileIndex);
//			}// Example: picking the first tile
//
//			// Randomly pick a valid position on the board
//			int randX = rand.nextInt(ROWS);
//			int randY = rand.nextInt(COLUMNS);
//			if (gameState.isValid(toPlace, randX, randY) && gameState.getBoard().notEmpty(randX, randY)) {
//				// Create a PlaceTileAction and attempt to place the tile on the board
//				PlaceTileAction place = new PlaceTileAction(this, toPlace, randX, randY, tileIndex);
//				if (gameState.placeTile(place)) {
//					// Send the action to the game and update the board
//					game.sendAction(place);
//					return;
//				}
//			}
//		}
//		// otherwise give up: End the turn
//		game.sendAction(new EndTurnAction(gameState, this, gameState.getNumPlayers()));
//
//	}
		// Get the current player's hand
//		ArrayList<QwirkleTile> myHand = gameState.getPlayerHand(gameState.getCurrPlayer());
		// Iterate through the player's hand
		for (int i = 0; i < myHand.size(); i++) {
			QwirkleTile toPlace = myHand.get(i);
			if (toPlace == null) {
				continue;  // Skip null tiles in the hand
			}
			// Iterate through the entire board to find a valid
			for (int x = 0; x < gameState.getBoard().ROWS; x++) {
				for (int y = 0; y < gameState.getBoard().COLUMNS; y++) {
					// Check if the placement is valid and the spot is empty
					if (gameState.isValid(toPlace, x, y) && !gameState.getBoard().notEmpty(x, y)) {
						// Create a PlaceTileAction
						PlaceTileAction place = new PlaceTileAction(this, toPlace, x, y, i);
						// Try to place
						if (gameState.placeTile(place)) {
							// Send the action to the game
							game.sendAction(place);
							return;  // End the method after successfully placing a tile
						}
					}
				}
			}
		}

		// If no valid moves are found, end the turn
		game.sendAction(new EndTurnAction(gameState, this, gameState.getNumPlayers()));
	}


	/**
	 * callback method: the timer ticked
	 */
//	protected void timerTicked() {
//		// 5% of the time, increment or decrement the counter
//		if (Math.random() >= 0.05) return; // do nothing 95% of the time
//
//		// "flip a coin" to determine whether to increment or decrement
//		boolean move = Math.random() >= 0.5;
//
//		// send the move-action to the game
//		game.sendAction(new QwirkleMoveAction(this, move));
//	}
}
