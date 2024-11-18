package edu.up.cs301.qwirklegame;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

/**
 * Place tile Action
 *
 * @author Chloe Pham
 * @author Talia Martinez
 * @author Tyler Crosbie
 * @author De'Ante Agleham
 * @author Ryan Murray
 *
 */

public class PlaceTileAction extends GameAction {

    // to satisfy Serializable interface
    private static final long serialVersionUID = 9876543210123456L;

    // The tile being placed
    private QwirkleTile placedTile;
    private int selectedTileIndex;

    // The position on the board where the tile is being placed
    private int x;
    private int y;

    /**
     * Constructor that sets the variables to the parameters
     * @param player
     * @param tile
     * @param x
     * @param y
     * @param selectedTileIndex
     */
    public PlaceTileAction(GamePlayer player, QwirkleTile tile, int x, int y, int selectedTileIndex) {
        super(player);
        this.placedTile = tile;
        this.x = x;
        this.y = y;
        this.selectedTileIndex = selectedTileIndex;
    }

    // Getter methods
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public QwirkleTile getPlacedTile() {
        return placedTile;
    }
    public int getSelectedTileIndex() { return selectedTileIndex; }
}
