package edu.up.cs301.qwirklegame;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

public class PlaceTileAction extends GameAction {

    //TODO:  which tile is being placed??  curr selected tile?
    //TODO: WHERE is it being placed?
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public PlaceTileAction(GamePlayer player) {
        super(player);
    }
}