package edu.up.cs301.qwirklegame;

import android.graphics.Bitmap;

/**
 * This contains the class for Qwirkle Tiles
 *
 * @author Talia Martinez
 * @author Chloe Pham
 *
 * @version October 5, 2024
 */

public class QwirkleTiles {
    // Declaring for each shape of tile
    public enum Shape {
        CLOVER, FOURSTAR, EIGHTSTAR, CIRCLE, SQUARE, DIAMOND,
    }
    // For each color of tile
    public enum Color {
        RED, BLUE, GREEN, YELLOW, ORANGE, PURPLE
    }

    private Shape shape;
    private Color color;
    private boolean isSelected;

    // Constructor
    public QwirkleTiles (Shape shape, Color color) {
        this.shape = shape;
        this.color = color;
        this.isSelected = false;
    }

    // Copy constructor for tiles
    public QwirkleTiles(QwirkleTiles orig) {
        this.shape = orig.shape;
        this.color = orig.color;
        this.isSelected = orig.isSelected;
    }

    // Getters for tile attributes
    public Shape getShape() {
        return shape;
    }
    public Color getColor() {
        return color;
    }
    public boolean getSelected() { return isSelected; }

    // Setters for tile variables
    public void setSelected(boolean selected) { this.isSelected = selected; }

    public boolean isValidTile(QwirkleTiles tile) {
        return true;
    }
}
