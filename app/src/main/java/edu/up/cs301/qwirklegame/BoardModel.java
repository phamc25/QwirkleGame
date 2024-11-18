package edu.up.cs301.qwirklegame;

import android.graphics.Bitmap;

/**
 * Board Model: Creates a model for the board class that is created
 * in the QwirkleView
 *
 * @author Chloe Pham
 * @author Talia Martinez
 * @author Tyler Crosbie
 * @author De'Ante Agleham
 * @author Ryan Murray
 *
 */

public class BoardModel {
    // Instance variables
    public float xLoc;
    public float yLoc;
    public Bitmap tileBitmap;   // Tile picture

    /**
     * Constructor
     * @param xLoc
     * @param yLoc
     * @param tileBitmap
     */
    public BoardModel(float xLoc, float yLoc, Bitmap tileBitmap) {
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.tileBitmap = tileBitmap;
    }
}
