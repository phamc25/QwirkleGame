package edu.up.cs301.qwirklegame;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.shapes.Shape;

import android.graphics.Bitmap;

public class QwirkleTiles {
    // Declaring for each shape of tile
    public enum Shape {
        CLOVER, FOURSTAR, EIGHTSTAR, CIRCLE, SQUARE, DIAMOND,
    }
    private Shape shape;
    private Color color;
    private Bitmap bitMap;

    // For each color of tile
    public enum Color {
        RED, BLUE, GREEN, YELLOW, ORANGE, PURPLE
    }

    // constructor
    public QwirkleTiles (Shape shape, Color color) {
        this.shape = shape;
        this.color = color;
    }

    public Shape getShape() {
        return shape;
    }
    public Color getColor() {
        return color;
    }
}
