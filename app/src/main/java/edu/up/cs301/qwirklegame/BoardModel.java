package edu.up.cs301.qwirklegame;

import android.graphics.Bitmap;

public class BoardModel {
    public float xLoc;
    public float yLoc;
    public Bitmap tileBitmap;

    public BoardModel(float xLoc, float yLoc, Bitmap tileBitmap) {
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.tileBitmap = tileBitmap;
    }
}
