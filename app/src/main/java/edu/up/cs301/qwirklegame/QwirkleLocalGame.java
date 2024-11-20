package edu.up.cs301.qwirklegame;

import static edu.up.cs301.qwirklegame.QwirkleState.HAND_SIZE;

import edu.up.cs301.GameFramework.infoMessage.GameState;
import edu.up.cs301.GameFramework.players.GamePlayer;
import edu.up.cs301.GameFramework.LocalGame;
import edu.up.cs301.GameFramework.actionMessage.GameAction;
import android.util.Log;

import java.util.ArrayList;

/**
 * This contains the Qwirkle Local Game. Any actions made by any players
 * get sent here to be sent to the actual Game State
 *
 * @author Chloe Pham
 * @author Talia Martinez
 * @author Tyler Crosbie
 * @author De'Ante Agleham
 * @author Ryan Murray
 *
 * @version November 17, 2024
 */
public class QwirkleLocalGame extends LocalGame {

	// The first player to get to 0 tiles in their hand, gets 6 points added to their score.
	// This ends the game
	public static final int FINAL_ADD_POINTS = 6;

	// the game's state
	private QwirkleState gameState;

	/**
	 * Check if player can move
	 *
	 * @param playerIdx the player's player-number (ID)
	 */
	@Override
	protected boolean canMove(int playerIdx) {
		return playerIdx == gameState.getCurrPlayer();
	}

	/**
	 * This ctor should be called when a new counter game is started
	 */
	public QwirkleLocalGame(GameState state) {
		// initialize the game state, with the counter value starting at 0
		if (!(state instanceof QwirkleState)) {
			state = new QwirkleState(); //is asking for the state of the game at the start
		}
		this.gameState = (QwirkleState) state;
		super.state = state;
	}

	/**
	 * The only type of GameAction that should be sent is QwirkleMoveAction
	 */
	@Override
	protected boolean makeMove(GameAction action) {
		Log.i("action", action.getClass().toString());

		// If action is an EndTurnAction
		if (action instanceof EndTurnAction) {
			return endTurn((EndTurnAction) action);
		}
		// Else if it is a PlaceTileAction
		else if (action instanceof PlaceTileAction) {
			return placeTile((PlaceTileAction) action);
		} else {
			// denote that this was an illegal move
			return false;
		}
	}//makeMove

	protected boolean endTurn(EndTurnAction action) {
		// Cast so that we Java knows it's a EndTurnAction
		int nullTiles = 0;
		// Can end their turn at any time
		for (int i = 0; i < gameState.getPlayerHand(gameState.getCurrPlayer()).size(); i++) {
			if (gameState.getPlayerHand(gameState.getCurrPlayer()).get(i) == null) {
				nullTiles++;
			}
		}
		// Draw tiles to fill hand back to 6 tiles
		gameState.drawTiles(gameState.getCurrPlayer(), nullTiles);

		// Score of the current player
		int playerScore = gameState.getPlayersScore()[gameState.getCurrPlayer()];
		// Set the score
		gameState.setPlayersScore(gameState.getCurrPlayer(), gameState.getAddPoints() + playerScore);
		gameState.setAddPoints(0);

		// Send state to the player
		sendUpdatedStateTo(action.getPlayer());
		gameState.nextPlayer();
		return this.gameState.endTurn(action);
	}

	/**
	 * Helper method for makeMove
	 * @param action
	 * @return
	 */
	protected boolean placeTile(PlaceTileAction action) {
		// Get the current tile index
		gameState.setCurrTile(action.getSelectedTileIndex());

		// Send state to the player and return
		sendUpdatedStateTo(action.getPlayer());
		return this.gameState.placeTile(action);
	}
	
	/**
	 * send the updated state to a given player
	 */
	@Override
	protected void sendUpdatedStateTo(GamePlayer p) {
		// this is a perfect-information game, so we'll make a
		// complete copy of the state to send to the player
		p.sendInfo(new QwirkleState(this.gameState));
		
	}//sendUpdatedSate
	
	/**
	 * Check if the game is over. It is over, return a string that tells
	 * who the winner(s), if any, are. If the game is not over, return null;
	 * 
	 * @return
	 * 		a message that tells who has won the game, or null if the
	 * 		game is not over
	 */
	@Override
	protected String checkIfGameOver() {
		int currPlayer = gameState.getCurrPlayer();	// Current player
		int nullTiles = 0;
		if (this.gameState.getTilesLeft() == 0) {
			for (int i = 0; i < HAND_SIZE; i++) {
				if (this.gameState.getPlayerHand(currPlayer).get(i) == null) {
					nullTiles++;
				}
			}
		}
		if (nullTiles == 6) {
			// Add 6 points to their score
			int[] playersScore = gameState.getPlayersScore();
			gameState.setPlayersScore(currPlayer, playersScore[currPlayer] + FINAL_ADD_POINTS);
			int winningPlayer = 0;

			// Loop through the array and find the player with the biggest score
			for (int i = 1; i < playersScore.length; i++) {
				if (playersScore[i] > playersScore[winningPlayer]) {
					winningPlayer = i;
				}
			}
			// Return the winning player
			return playerNames[winningPlayer] + " has won with a score of " + playersScore[winningPlayer] + "! ";
		}
		// If no player hand has reached 0, the game is not over
		else {
			return null;
		}
    }
}// class QwirkleLocalGame


