package edu.up.cs301.qwirklegame;

import edu.up.cs301.GameFramework.infoMessage.GameState;
import edu.up.cs301.GameFramework.players.GamePlayer;
import edu.up.cs301.GameFramework.LocalGame;
import edu.up.cs301.GameFramework.actionMessage.GameAction;
import android.util.Log;

import java.util.ArrayList;

/**
 * A class that represents the state of a game. In our counter game, the only
 * relevant piece of information is the value of the game's counter. The
 * QwirkleState object is therefore very simple.
 * 
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @version July 2013
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
	 * @param playerIdx
	 * 		the player's player-number (ID)
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
		if (! (state instanceof QwirkleState)) {
			state = new QwirkleState(); //is asking for the state of the game at the start
		}
		this.gameState = (QwirkleState)state;
		super.state = state;
	}

	/**
	 * The only type of GameAction that should be sent is QwirkleMoveAction
	 */
	@Override
	protected boolean makeMove(GameAction action) {
		Log.i("action", action.getClass().toString());
		
		if (action instanceof QwirkleMoveAction) {
		
			// cast so that we Java knows it's a QwirkleMoveAction
			QwirkleMoveAction cma = (QwirkleMoveAction)action;

			// Update the counter values based upon the action
			//int result = gameState.getCounter() + (cma.isPlus() ? 1 : -1);
			//gameState.setCounter(result);
			
			// denote that this was a legal/successful move
			return true;
		}
		else {
			// denote that this was an illegal move
			return false;
		}
	}//makeMove
	
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

		// Get the number of tiles in the player's hand
		int numTiles = this.gameState.getPlayerHand(currPlayer).size();

		// Check if the length of the player's hand has reached 0
		if (numTiles == 0) {
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
			return playerNames[winningPlayer] + "has won.";
		}
		// If no player hand has reached 0, the game is not over
		else {
			return null;
		}

		// get the value of the counter
		//int counterVal = this.gameState.getCounter();
		
		/*if (counterVal >= TARGET_MAGNITUDE) {
			// counter has reached target magnitude, so return message that
			// player 0 has won.
			return playerNames[0]+" has won.";
		}
		else if (counterVal <= -TARGET_MAGNITUDE) {
			// counter has reached negative of target magnitude; if there
			// is a second player, return message that this player has won,
			// otherwise that the first player has lost
			if (playerNames.length >= 2) {
				return playerNames[1]+" has won.";
			}
			else {
				return playerNames[0]+" has lost.";
			}
		}else {
			// game is still between the two limit: return null, as the game
			// is not yet over
			return null;
		}*/

    }
	protected void endTurn(GamePlayer player) {
		int playerIndex = getPlayerIndex(player);

		// Refill the player's hand at the end of the turn
		gameState.refillHand(playerIndex);

		super.endTurn(player);
	}


	public int getPlayerIndex(GamePlayer player) {
		for (int i = 0; i < players.length; i++) {
			if (players[i] == player) {
				return i;
			}
		}
		return -1; // Return -1 if the player is not found
	}




}// class QwirkleLocalGame
