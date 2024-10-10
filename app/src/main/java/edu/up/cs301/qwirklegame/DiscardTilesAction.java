package edu.up.cs301.qwirklegame;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;
import java.util.ArrayList;

public class DiscardTilesAction extends GameAction {


    // to satisfy Serializable interface
    private static final long serialVersionUID = 8927349827349823L;

    // List of tiles to discard
    private ArrayList<QwirkleTiles> tilesToDiscard;
    private QwirkleLocalGame localGame;

    public DiscardTilesAction(GamePlayer player, ArrayList<QwirkleTiles> tilesToDiscard) {
        super(player);
        this.tilesToDiscard = tilesToDiscard;
    }
    public ArrayList<QwirkleTiles> getTilesToDiscard() {
        return tilesToDiscard;
    }

    public void setTilesToDiscard(ArrayList<QwirkleTiles> tilesToDiscard) {
        this.tilesToDiscard = new ArrayList<>(tilesToDiscard);
    }

    public boolean hasTilesToDiscard() {
        return !tilesToDiscard.isEmpty();
    }
    public boolean applyAction(QwirkleState state, GamePlayer player) {
        int playerIndex = localGame.getPlayerIndex(player);

        // Remove discarded tiles from the player's hand
        for (QwirkleTiles tile : tilesToDiscard) {
            state.getPlayerHand(playerIndex).remove(tile);
        }

        // Draw the same number of tiles from the bag
        state.drawTiles(playerIndex, tilesToDiscard.size());

        return true;
    }
}
