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

    // The position on the board where the tile is being placed
    private int x;
    private int y;

    // setting vars to parameters passed
    public PlaceTileAction(GamePlayer player, QwirkleTile tile, int x, int y) {
        super(player);
        this.placedTile = tile;
        this.x = x;
        this.y = y;
    }

    // get the placed tile
    public QwirkleTile getPlacedTile() {
        return placedTile;
    }

    // getter methods
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
