package edu.up.cs301.qwirklegame;

import edu.up.cs301.GameFramework.players.GamePlayer;
import edu.up.cs301.GameFramework.actionMessage.GameAction;

/**
 * A QwirkleMoveAction is an action that is a "move" the game: either increasing
 * or decreasing the counter value.
 * 
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @version September 2012
 */
public class QwirkleMoveAction extends GameAction {
	
	// to satisfy the serializable interface
	private static final long serialVersionUID = 28062013L;

	//whether this move is a plus (true) or minus (false)
	private boolean isPlus;
	
	/**
	 * Constructor for the QwirkleMoveAction class.
	 * 
	 * @param player
	 *            the player making the move
	 * @param isPlus
	 *            value to initialize this.isPlus
	 */
	public QwirkleMoveAction(GamePlayer player, boolean isPlus) {
		super(player);
		this.isPlus = isPlus;
	}
	
	/**
	 * getter method, to tell whether the move is a "plus"
	 * 
	 * @return
	 * 		a boolean that tells whether this move is a "plus"
	 */
	public boolean isPlus() {
		return isPlus;
		
	}
}//class QwirkleMoveAction
