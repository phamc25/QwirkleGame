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
    private ArrayList<QwirkleTile> tilesToDiscard;

    public DiscardTilesAction(GamePlayer player, ArrayList<QwirkleTile> tilesToDiscard) {
        super(player);
        this.tilesToDiscard = new ArrayList<>(tilesToDiscard);
    }

    public ArrayList<QwirkleTile> getSelectedTiles() {
        return tilesToDiscard;
    }

    //Sets a new list of tiles to discard
    public void setTilesToDiscard(ArrayList<QwirkleTile> tilesToDiscard) {
        this.tilesToDiscard = new ArrayList<>(tilesToDiscard);
    }

    // Checks if there are tiles to discard
    public boolean hasTilesToDiscard() {
        return !tilesToDiscard.isEmpty();
    }

}
