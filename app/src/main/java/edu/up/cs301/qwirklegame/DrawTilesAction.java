package edu.up.cs301.qwirklegame;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;
import java.util.ArrayList;

public class DrawTilesAction extends GameAction {

    // to satisfy Serializable interface
    private static final long serialVersionUID = 918273645938471L;

    // Number of tiles the player wants to draw
    private int numTilesToDraw;

    public DrawTilesAction(GamePlayer player, int numTilesToDraw) {
        super(player);
        this.numTilesToDraw = numTilesToDraw;
    }
    public int getNumTilesToDraw() {
        return numTilesToDraw;
    }

    public void setNumTilesToDraw(int numTilesToDraw) {
        this.numTilesToDraw = numTilesToDraw;
    }
    public boolean isValidDraw() {
        return numTilesToDraw > 0;
    }
}