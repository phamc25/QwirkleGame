package edu.up.cs301.qwirklegame;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

import java.io.Serializable;

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

    // Instance variables
    private int selectedTileIndex;  // keeps track of the currently selected tile index

    /**
     * Constructor for discard tiles
     * @param player
     * @param selectedTileIndex
     */
    public DiscardTilesAction(GamePlayer player, int selectedTileIndex) {
        super(player);
        // List of tiles to discard
        this.selectedTileIndex = selectedTileIndex;
    }

    /**
     * Gets the selected tile index
     * @return
     */
    public int getSelectedTileIndex() {
        return selectedTileIndex;
    }
}
