package edu.up.cs301.qwirklegame;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;
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

public class DiscardTilesAction extends GameAction {


    // to satisfy Serializable interface
    private static final long serialVersionUID = 8927349827349823L;

    // List of tiles to discard
    private ArrayList<QwirkleTile> tilesToDiscard;
    private QwirkleLocalGame localGame;

    public DiscardTilesAction(GamePlayer player, ArrayList<QwirkleTile> tilesToDiscard) {
        super(player);
        this.tilesToDiscard = tilesToDiscard;
    }
    public ArrayList<QwirkleTile> getTilesToDiscard() {
        return tilesToDiscard;
    }

    public void setTilesToDiscard(ArrayList<QwirkleTile> tilesToDiscard) {
        this.tilesToDiscard = new ArrayList<>(tilesToDiscard);
    }

    public boolean hasTilesToDiscard() {
        return !tilesToDiscard.isEmpty();
    }
    public boolean applyAction(QwirkleState state, GamePlayer player) {
        int playerIndex = localGame.getPlayerIndex(player);

        // Remove discarded tiles from the player's hand
        for (QwirkleTile tile : tilesToDiscard) {
            state.getPlayerHand(playerIndex).remove(tile);
        }

        // Draw the same number of tiles from the bag
        state.drawTiles(playerIndex, tilesToDiscard.size());

        return true;
    }
}
