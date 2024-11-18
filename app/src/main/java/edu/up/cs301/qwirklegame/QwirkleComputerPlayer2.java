package edu.up.cs301.qwirklegame;

import static edu.up.cs301.qwirklegame.Board.COLUMNS;
import static edu.up.cs301.qwirklegame.Board.ROWS;

import edu.up.cs301.GameFramework.GameMainActivity;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.GameFramework.players.GameComputerPlayer;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * This contains logic for the Qwirkle Computer Player 2, or "Smart AI".
 * This AI goes through its hand and iterates through the board
 * to find a valid placement. All tiles are checked for valid placement.
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
public class QwirkleComputerPlayer2 extends GameComputerPlayer {
	/**
	 * Constructor for objects of class QwirkleComputerPlayer2
	 *
	 * @param name the player's name
	 */
	public QwirkleComputerPlayer2(String name) {
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

		for (int i = 0; i < myHand.size(); i++) {
			QwirkleTile toPlace = myHand.get(i);
			if (toPlace == null) {
				continue;  // Skip null tiles in the hand
			}
			// Iterate through the entire board to find a valid
			for (int x = 0; x < ROWS; x++) {
				for (int y = 0; y < COLUMNS; y++) {
					// Check if the placement is valid and the spot is empty
					if (gameState.isValid(toPlace, x, y) && !gameState.getBoard().notEmpty(x, y)) {
						// Create a PlaceTileAction
						PlaceTileAction place = new PlaceTileAction(this, toPlace, x, y, i);
						// Try to place
						if (gameState.placeTile(place)) {
							// Send the action to the game
							game.sendAction(place);
							// Simulate computer is "thinking"
							try {
								Thread.sleep(500);  // TODO:  make this longer?
							} catch (InterruptedException e) {
								throw new RuntimeException(e);
							}
							return;  // Return method after successfully placing a tile
						}
					}
				}
			}
		}
		// If no valid moves are found, end the turn
		game.sendAction(new EndTurnAction(gameState, this, gameState.getNumPlayers()));
	}
}