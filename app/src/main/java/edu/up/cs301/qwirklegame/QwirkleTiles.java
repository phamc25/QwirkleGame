package edu.up.cs301.qwirklegame;

import android.graphics.Bitmap;

public class QwirkleTiles {
    // Declaring for each shape of tile
    public enum Shape {
        CLOVER, FOURSTAR, EIGHTSTAR, CIRCLE, SQUARE, DIAMOND,
    }

    // For each color of tile
    public enum Color {
        RED, BLUE, GREEN, YELLOW, ORANGE, PURPLE
    }

    // Instance variables for tile
    private Shape shape;
    private Color color;
    private Bitmap tileBitmap;
}
