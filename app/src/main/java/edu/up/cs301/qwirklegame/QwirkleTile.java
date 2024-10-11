package edu.up.cs301.qwirklegame;

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
        CLOVER, FOURSTAR, EIGHTSTAR, CIRCLE, SQUARE, DIAMOND,
    }
    // For each color of tile
    public enum Color {
        RED, BLUE, GREEN, YELLOW, ORANGE, PURPLE
    }

    // instance vars
    private Shape shape;
    private Color color;
    private boolean isSelected;

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

    public boolean isValidTile(QwirkleTile tile) {
        return true;
    }
}
