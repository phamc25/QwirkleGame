package edu.up.cs301.GameFramework.infoMessage;

import static edu.up.cs301.GameFramework.utilities.Saving.SEPARATOR;

/**
 * The state of the game. This class should be subclassed so that it holds
 * all state information for the particular game being implemented. For
 * example, if the game were chess, it would contain the contents of each
 * square on the board, which player's turn it was, etc.
 *
 * @author Steven R. Vegdahl
 * @version July 2013
 */
public abstract class GameState extends GameInfo {
    //Tag for logging
    private static final String TAG = "GameState";
    // to satisfy the Serializable interface
    private static final long serialVersionUID = -5109179064333136954L;

    //How many setup phases we have, initially set to 0
    protected int numSetupTurns = 0;

    //How many setup turns have passed, initially set to 0
    protected int currentSetupTurn = 0;

    protected int numPlayers = 0;
    protected int currentPlayer = 0;


    public GameState() {
        this.numSetupTurns = 0;
        this.currentSetupTurn = 0;
        this.numPlayers = 0;
        this.currentPlayer = 0;
    }

    // Copy constructor
    public GameState(GameState orig) {
        this.numSetupTurns = orig.numSetupTurns;
        this.currentSetupTurn = orig.currentSetupTurn;
        this.numPlayers = orig.numPlayers;
        this.currentPlayer = orig.currentPlayer;
    }
    /**
     * getNumSetupTurns
     *
     * @return Number of Setup turns in game
     */
    public int getNumSetupTurns(){ return numSetupTurns; }

    /**
     * getCurrentSetupTurn
     *
     * @return The Current Setup Turn number
     */
    public int getCurrentSetupTurn(){ return currentSetupTurn; }

    /**
     * incCurrentSetupTurn
     *
     * @return Whether or not the increment was successful
     */
    public boolean incCurrentSetupTurn(){
        this.currentSetupTurn++;
        return true;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void nextPlayer() {
        currentPlayer = (currentPlayer + 1) % numPlayers;
    }

    /**
     * toString
     *
     * @return String representation of this instance
     */
    public String toString() {
        return "GameState{" +
                "numSetupTurns=" + numSetupTurns +
                ", currentSetupTurn=" + currentSetupTurn +
                ", numPlayers=" + numPlayers +
                ", currentPlayer=" + currentPlayer +
                '}';
    }


}
