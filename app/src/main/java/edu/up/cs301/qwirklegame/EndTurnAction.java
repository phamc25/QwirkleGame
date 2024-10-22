package edu.up.cs301.qwirklegame;

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

public class EndTurnAction extends GameAction {

    // to satisfy Serializable interface
    private static final long serialVersionUID = 9876543210123456L;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public EndTurnAction(QwirkleState state, GamePlayer player) {
        super(player);
        int playerIndex = state.getCurrPlayer();

        int nextPlayer = (playerIndex + 1) % QwirkleState.MAX_PLAYERS;
        state.setCurrPlayer(nextPlayer);
    }

    // can always skip turn
    public boolean isEndTurn(){
        return true;
    }
}
