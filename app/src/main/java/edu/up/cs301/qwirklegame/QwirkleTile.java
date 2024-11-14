package edu.up.cs301.qwirklegame;

import static edu.up.cs301.qwirklegame.QwirkleHumanPlayer.TILE_RESOURCES;

/**
 * This contains the class for Qwirkle Tiles
 *
 * @author Talia Martinez
 * @author Chloe Pham
 * @author Tyler Crosbie
 * @author De'ante Agleham
 * @author Ryan Murray
 *
 * @version October 5, 2024
 */

public class QwirkleTile {
    // Declaring for each shape of tile
    public enum Shape {
        CIRCLE, SQUARE, DIAMOND, FOURSTAR, CLOVER, EIGHTSTAR
    }
    // For each color of tile
    public enum Color {
        RED, BLUE, GREEN, YELLOW, PURPLE, ORANGE
    }

    // instance vars
    private Shape shape;
    private Color color;
    private boolean isSelected;

    @Override
    public String toString() {
        return "" + color + " " + shape;
    }

    /**
     * Constructor
     *
     * @param shape shape of tile
     * @param color color of tile
     */
    public QwirkleTile(Shape shape, Color color) {
        this.shape = shape;
        this.color = color;
        this.isSelected = false;
    }

    /**
     * Copy constructor
     */
    public QwirkleTile(QwirkleTile orig) {
        this.shape = orig.shape;
        this.color = orig.color;
        this.isSelected = orig.isSelected;
    }

    // for checking if a tile is a duplicate
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof QwirkleTile) {
            QwirkleTile other = (QwirkleTile)obj;
            if (this.color != other.color) { return false; }
            if (this.shape != other.shape) { return false; }
            return true;
        }
        return false;
    }

    // Getters for tile attributes
    public Shape getShape() {
        return shape;
    }
    public Color getColor() {
        return color;
    }
    public boolean getSelected() { return isSelected; }
    /**
     * Takes a QwirkleTile and matches the image file name to the tile from
     * the TILE_RESOURCES array
     *
     * @param tile
     * @return
     */
    public int getTileImageFile(QwirkleTile tile) {
        // No tile, draw blank tile
        if (tile == null) { return R.drawable.tile_blank; }
        // Return the right tile image with the enum values
        return TILE_RESOURCES[tile.getShape().ordinal()][tile.getColor().ordinal()];
    }

    // Setters for tile variables
    public void setSelected(boolean selected) { this.isSelected = selected; }

    public boolean isValidTile(QwirkleTile tile) {
        return true;
    }
}
