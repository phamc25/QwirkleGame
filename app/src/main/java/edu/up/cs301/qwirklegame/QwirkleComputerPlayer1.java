package edu.up.cs301.qwirklegame;

import static edu.up.cs301.qwirklegame.Board.COLUMNS;
import static edu.up.cs301.qwirklegame.Board.ROWS;

import java.util.ArrayList;

import edu.up.cs301.GameFramework.players.GameComputerPlayer;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;

/**
 * This contains logic for the Qwirkle Computer Player 1, or "Dumb AI".
 * This AI goes through its hand and iterates through the board
 * to find a valid placement. Once 2 tiles have been placed onto the board,
 * the AI ends its turn
 *
 * If no valid tiles are in its hand, it also ends turn.
 *
 * @author Chloe Pham
 * @author Talia Martinez
 * @author Tyler Crosbie
 * @author De'Ante Agleham
 * @author Ryan Murray
 *
 * @version November 17, 2024
 */
public class QwirkleComputerPlayer1 extends GameComputerPlayer {
	/**
	 * Constructor for objects of class QwirkleComputerPlayer1
	 *
	 * @param name the player's name
	 */
	public QwirkleComputerPlayer1(String name) {
		// invoke superclass constructor
		super(name);
	}

	/**
	 * callback method--game's state has changed
	 *
	 * @param info the information (presumably containing the game's state)
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

		// Computer player hand
		ArrayList<QwirkleTile> myHand = gameState.getPlayerHand(gameState.getCurrPlayer());
		// Keep track of the # of placed tiles
		int placedTiles = 0;

		// Define outer loop
		outerLoop:
		for (int i = 0; i < myHand.size() && placedTiles < 1; i++) {
			QwirkleTile toPlace = myHand.get(i);
			if (toPlace == null) {
				continue;  // Skip null tiles in the hand
			}

			// Iterate through the entire board to find a valid position
			for (int x = 0; x < ROWS; x++) {
				for (int y = 0; y < COLUMNS; y++) {
					// Check if the placement is valid and the spot is empty
					if (gameState.isValid(toPlace, x, y) && !gameState.getBoard().notEmpty(x, y)) {
						// Create a PlaceTileAction
						PlaceTileAction place = new PlaceTileAction(this, toPlace, x, y, i);

						// Try to place the tile
						if (gameState.placeTile(place)) {
							// Send the action to the game
							game.sendAction(place);
							placedTiles++;

							// Simulate computer "thinking"
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								throw new RuntimeException(e);
							}

							// Break out of all loops after successfully placing a tile
							continue outerLoop;
						}
					}
				}
			}
		}
		// End turn after placing tiles or if no valid moves are found
		game.sendAction(new EndTurnAction(gameState, this));
	}
}

/**
 * External Citation
 *
 * Problem: Implementing computer player
 * Source: Professor Nuxoll office hours
 *
 * Date: Week of November 11, 2024
 */
