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
	private QwirkleView qwirkleView;
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
		if (info instanceof QwirkleState) {
			QwirkleState gameState = (QwirkleState) info;
			if (this.playerNum != gameState.getCurrPlayer()) {
				return;
			}

			try {
				Thread.sleep(2000);  // Simulate a delay for thinking time

				// Ensure we are still working with a valid game state
				if (!(info instanceof QwirkleState)) {
					return;
				}

				// Select a random tile from the computer player's hand
				QwirkleTile toPlace = gameState.getPlayerHand(gameState.getCurrPlayer()).get(0);  // Example: picking the first tile

				// Randomly pick a valid position on the board
				int randX = rand.nextInt(ROWS);
				int randY = rand.nextInt(COLUMNS);

				// Create a PlaceTileAction and attempt to place the tile on the board
				PlaceTileAction place = new PlaceTileAction(this, toPlace, randX, randY, 0);
				if (gameState.placeTile(place)) {
					// Send the action to the game and update the board
					game.sendAction(place);
				}
					// End the turn
					game.sendAction(new EndTurnAction(gameState, this, gameState.getNumPlayers()));

			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
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
