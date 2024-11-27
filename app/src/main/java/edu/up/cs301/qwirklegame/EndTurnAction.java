package edu.up.cs301.qwirklegame;

import java.io.Serializable;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

/**
 * Ending turn Action
 *
 * @author Chloe Pham
 * @author Talia Martinez
 * @author Tyler Crosbie
 * @author De'Ante Agleham
 * @author Ryan Murray
 *
 */
public class EndTurnAction extends GameAction implements Serializable {

    // to satisfy Serializable interface
    private static final long serialVersionUID = 9876543210123456L;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public EndTurnAction(QwirkleState state, GamePlayer player) {
        super(player);
    }
}
