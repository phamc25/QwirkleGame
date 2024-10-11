package edu.up.cs301.qwirklegame;

public class Board {
    private QwirkleTiles[][] boardArray; // Takes a double array of Qwirkle tiles

    // Static variables for board dimensions
    public static final int length = 15;
    public static final int width = 20;

    /**
     *  Constructor
     */
    public Board() {
        this.boardArray = new QwirkleTiles[length][width];
    }

    /**
     *  Copy constructor (Deep copy)
     */
    public Board(Board orig) {
        this.boardArray = copyBoard();
    }

    /**
     * Method for deep copying array
     * @return A QwirkleTiles double array
     */
    public QwirkleTiles[][] copyBoard() {
        // Create a new double array of QwirkleTiles for the copied array
        QwirkleTiles[][] copy = new QwirkleTiles[length][width];

        // Loop through the original board to copy into the copy array
        for (int i = 0; i < boardArray.length; i++) {
            //not needed?  copy[i] = new QwirkleTiles[this.boardArray[i].length];
            for (int j = 0; j < boardArray[i].length; j++) {
                //TODO skip null cells
                copy[i][j] = new QwirkleTiles(this.boardArray[i][j]);
            }
        }
        return copy;
    }

    /**
     *  Getters for variables
     */
    public QwirkleTiles[][] getBoard() {
        return this.boardArray;
    }
}
