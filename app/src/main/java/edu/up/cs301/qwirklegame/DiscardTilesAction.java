package edu.up.cs301.qwirklegame;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Discard Action
 *
 * @author Chloe Pham
 * @author Talia Martinez
 * @author Tyler Crosbie
 * @author De'Ante Agleham
 * @author Ryan Murray
 */

public class DiscardTilesAction extends GameAction implements Serializable {
    // to satisfy Serializable interface
    private static final long serialVersionUID = 8927349827349823L;

    // List of tiles to discard
    private QwirkleTile tileToDiscard;

    public DiscardTilesAction(GamePlayer player, QwirkleTile tileToDiscard) {
        super(player);
        this.tileToDiscard = tileToDiscard;
    }

    public QwirkleTile getSelectedTiles() {
        return tileToDiscard;
    }

    //Sets a new list of tiles to discard
    public void setTilesToDiscard(QwirkleTile tileToDiscard) {
        this.tileToDiscard = tileToDiscard;
    }
}
