package edu.up.cs301.qwirklegame;

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
		try {
			Thread.sleep(2000);
			if (!(info instanceof QwirkleState)) {
				return;
			}
			// Just send end turn for now
			QwirkleState gameState = (QwirkleState) info;
			game.sendAction(new EndTurnAction(gameState, this, gameState.getNumPlayers()));


		} catch (InterruptedException e) {
            throw new RuntimeException(e);
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
