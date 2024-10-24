package edu.up.cs301.qwirklegame;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

/**
 * Quit Game Action
 *
 * @author Chloe Pham
 * @author Talia Martinez
 * @author Tyler Crosbie
 * @author De'Ante Agleham
 * @author Ryan Murray
 *
 */

public class QuitGameAction extends GameAction {

    // to satisfy Serializable interface
    private static final long serialVersionUID = 4567890123456789L;

    public QuitGameAction(GamePlayer player) {
        super(player);
    }

    // can always quit the game
    public boolean isQuitAction() {

        return true;
    }
    public void quitGame() {
        GamePlayer player = this.getPlayer();
    }

}
