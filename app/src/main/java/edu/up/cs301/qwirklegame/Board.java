package edu.up.cs301.qwirklegame;

/**
 * Board Class
 *
 * @author Chloe Pham
 * @author Talia Martinez
 * @author Tyler Crosbie
 * @author De'Ante Agleham
 * @author Ryan Murray
 *
 */

public class Board {
    private QwirkleTile[][] boardArray; // Takes a double array of Qwirkle tiles

    // Static variables for board dimensions
    public static final int LENGTH = 15;
    public static final int WIDTH = 20;

    /**
     *  Constructor
     */
    public Board() {
        this.boardArray = new QwirkleTile[LENGTH][WIDTH];
    }

    /**
     *  Copy constructor (Deep copy)
     */
    public Board(Board orig) {
        this.boardArray = orig.copyBoard();
    }

    /**
     * Method for deep copying array
     * @return A QwirkleTile double array
     */
    public QwirkleTile[][] copyBoard() {
        // Create a new double array of QwirkleTile for the copied array
        QwirkleTile[][] copy = new QwirkleTile[LENGTH][WIDTH];

        // Loop through the original board to copy into the copy array
        for (int i = 0; i < boardArray.length; i++) {
            for (int j = 0; j < boardArray[i].length; j++) {
                if(this.boardArray[i][j] != null) {
                    copy[i][j] = new QwirkleTile(this.boardArray[i][j]);
                }
            }
        }
        return copy;
    }

    /**
     *  Getters for variables
     */
    public QwirkleTile[][] getBoard() {
        return this.boardArray;
    }

    /**
     * Checks if place on the board is not empty
     */
    public boolean notEmpty(int x, int y){
        if(boardArray[x][y] != null){
            return true;
        }
        return false;
    }

    /**
     * Adds a tile to empty spot on board
     *
     * @param tile tile to be added
     * @param x x position of tile placement
     * @param y y position of tile placement
     */
    public void addToBoard(QwirkleTile tile, int x, int y){
        if(boardArray[x][y] == null) {
            boardArray[x][y] = tile;
        }
    }
}
