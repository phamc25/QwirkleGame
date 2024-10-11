package edu.up.cs301.qwirklegame;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

public class EndTurnAction extends GameAction {

    // to satisfy Serializable interface
    private static final long serialVersionUID = 9876543210123456L;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public EndTurnAction(GamePlayer player) {
        super(player);
    }
}
