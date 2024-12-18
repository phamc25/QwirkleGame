package edu.up.cs301.qwirklegame;

import java.io.Serializable;

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

public class Board  implements Serializable {
    private QwirkleTile[][] boardArray; // Takes a double array of Qwirkle tiles

    // Static variables for board dimensions
    public static final int ROWS = 13;
    public static final int COLUMNS = 27;

    /**
     *  Constructor
     */
    public Board() {
        this.boardArray = new QwirkleTile[ROWS][COLUMNS];
    }

    /**
     *  Copy constructor (Deep copy)
     */
    public Board(Board orig) {
        this.boardArray = orig.copyBoard();
    }

    /**
     * Method for deep copying array
     *
     * @return A QwirkleTile double array
     */
    public QwirkleTile[][] copyBoard() {
        // Create a new double array of QwirkleTile for the copied array
        QwirkleTile[][] copy = new QwirkleTile[ROWS][COLUMNS];

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

    /**
     * Check if spot on board is empty
     *
     * @param x
     * @param y
     * @return true if there is tile, false if not
     */
    public boolean notEmpty(int x, int y, boolean offBoardRetVal) {
        if ((x < 0) || (x >= ROWS) || (y < 0) || (y >= COLUMNS)) {
            return offBoardRetVal;  //better to throw an exception here?
        }

        return boardArray[x][y] != null;
    }

    public boolean notEmpty(int x, int y) {
        return notEmpty(x, y, true);
    }



    /**
     *  Getters for variables
     */
    public QwirkleTile[][] getTiles() {
        return this.boardArray;
    }
}


