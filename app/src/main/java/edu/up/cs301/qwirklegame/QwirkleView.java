package edu.up.cs301.qwirklegame;

import static edu.up.cs301.qwirklegame.Board.COLUMNS;
import static edu.up.cs301.qwirklegame.Board.ROWS;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * This contains the board for the QwirkleHumanPlayer. It takes the current tile that was selected from
 * the QwirkleHumanPlayer and if the player taps the screen in the grid, it draws it inside of the cell
 *
 * This class also draws the board grid and the other player scores on the SurfaceView
 *
 * @author Chloe Pham
 * @author Talia Martinez
 * @author Tyler Crosbie
 * @author De'Ante Agleham
 * @author Ryan Murray
 *
 * @version November 11, 2024
 */

public class QwirkleView extends SurfaceView implements View.OnTouchListener {
    // Custom listener interface for tile touch events
    public interface OnTileTouchListener {
        void onTileTouched(int x, int y); // Pass tile coordinates
    }

    // Current bitmap for the current tile
    private Bitmap currTileBitmap;

    // List to track all touched positions
    private List<BoardModel> placedTiles = new ArrayList<>();

    // Make the interface
    private OnTileTouchListener tileTouchListener;

    // Variables for drawing grid
    private float cellSize, offsetX, offsetY;
    private int selectedTileResource = -1;

    // Paints
    private Paint gridPaint;
    private Paint lightYellow;

    public QwirkleView(Context context, AttributeSet attrs) {

        super(context, attrs);
        setWillNotDraw(false);
        setOnTouchListener(this);

        // Set grid paint
        gridPaint = new Paint();
        gridPaint.setColor(Color.BLACK);
        gridPaint.setStrokeWidth(2);

        // Paint for center grid cell
        lightYellow = new Paint();
        lightYellow.setColor(0xFFFFFFAD);
        lightYellow.setStyle(Paint.Style.FILL);

        // Set background color to light grey
        setBackgroundColor(0xFFDDDDDD);
    }

    /**
     * This method allows QwirkleHumanPlayer to set the selected tile
     * @param imageResource
     */
    public void setSelectedTile(int imageResource) {
        selectedTileResource = imageResource;
        // Load the bitmap from the resource ID
        currTileBitmap = BitmapFactory.decodeResource(getResources(), imageResource);

        // Redraw the view to display the new tile
        invalidate();
    }

    /**
     * To set the custom tile touch listener
     * @param listener
     */
    public void setOnTileTouchListener(OnTileTouchListener listener) {
        this.tileTouchListener = listener;
    }

    /**
     * Helper method to draw the grid lines on the Qwirkle board
     * @param canvas
     */
    public void drawBoard(Canvas canvas) {
        // The cell size is the smallest ratio between these to fit the defined row and columns
        // all in the board
        cellSize = Math.min(getWidth() / (float) COLUMNS, (getHeight() - 20) / (float) ROWS);

        // The length for the side of the centered grid
        offsetX = (getWidth() - COLUMNS * cellSize) / 2;
        offsetY = (getHeight() - ROWS * cellSize) / 2;

        // Loop through the rows and draws the lines
        for (int i = 0; i <= ROWS; i++) {
            // Y coordinate calculation. i * cellSize = each line for each cell row. + offSetY for centering
            float y = offsetY + (i * cellSize);
            canvas.drawLine(offsetX, y, offsetX + (COLUMNS * cellSize), y, gridPaint);
        }
        // Loop through the columns and draws its lines
        for (int j = 0; j <= COLUMNS; j++) {
            // X coordinate calculation. j * cellSize = each line for each cell col. + offSetX for centering
            float x = offsetX + (j * cellSize);
            canvas.drawLine(x, offsetY, x, offsetY + (ROWS * cellSize), gridPaint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Drawing the grid board
        drawBoard(canvas);

        // Draw center placement
        float left = offsetX + 1 + (COLUMNS / 2) * cellSize;
        float top = offsetY + 1 + (ROWS / 2) * cellSize;
        RectF centerCell = new RectF(left, top, left + cellSize - 2, top + cellSize - 2);
        canvas.drawRect(centerCell, lightYellow);

        // Draw all previously placed tiles
        for (BoardModel tile : placedTiles) {
            // Get the position of the tile on the grid
            float x = offsetX + (tile.xLoc * cellSize);
            float y = offsetY + (tile.yLoc * cellSize);

            // Scale the tile to fit within the grid cell
            int newTileSize = (int) (cellSize);
            Bitmap scaledTile = Bitmap.createScaledBitmap(tile.tileBitmap, newTileSize, newTileSize, false);

            // Calculate the centered position for the tile
            float centeredX = x + (cellSize - newTileSize) / 2;
            float centeredY = y + (cellSize - newTileSize) / 2;

            // Draw the scaled tile at the centered position
            canvas.drawBitmap(scaledTile, centeredX, centeredY, null);
        }
    }

    // Method to add a new tile to the grid
    public void addTile(float row, float col, Bitmap currTileBitmap) {
        // Check if there is already a tile at the loc
        for (BoardModel tile : placedTiles) {
            if (tile.xLoc == col && tile.yLoc == row) {
                // Return without adding.
                return;
            }
        }
        if (currTileBitmap != null) {
            BoardModel newTile = new BoardModel(col, row, currTileBitmap);
            placedTiles.add(newTile);
            invalidate(); // Redraw the view
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        // Check if no tile is selected
        if (currTileBitmap == null) {
            return false; // Do nothing if no tile is selected
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Calculate the column and row based on the touch coord
            int col = (int) ((event.getX() - offsetX) / cellSize);
            int row = (int) ((event.getY() - offsetY) / cellSize);

            // Bound checking and then adds tile if its inside
            if (col >= 0 && col < COLUMNS && row >= 0 && row < ROWS) {
                // If the player has touched the screen, call onTileTouched
                if (tileTouchListener != null) {
                    tileTouchListener.onTileTouched(row, col);
                }
            }
            return true; // Event done
        }
        return false;
    }

    /**
     * Updates the board view with the tiles from the current game state.
     * @param board the current board state from QwirkleState.
     */
    public void updateFromGameState(Board board) {
        clearSelectedTile();
        // Clear the current list of placed tiles
        placedTiles.clear();

        // Loop through the board to retrieve and add tiles
        for (int row = 0; row < board.getTiles().length; row++) {
            for (int col = 0; col < board.getTiles()[row].length; col++) {
                QwirkleTile tile = board.getTiles()[row][col];
                if (tile != null) {
                    // Convert QwirkleTile to a BoardModel and add it to placedTiles
                    Bitmap tileBitmap = BitmapFactory.decodeResource(getResources(), tile.getTileImageFile(tile));
                    addTile(row, col, tileBitmap);
                }
            }
        }
        // Redraw the board view
        invalidate();
    }

    /**
     * This method resets the currently selected tile and its bitmap
     */
    private void clearSelectedTile() {
        selectedTileResource = -1;
        currTileBitmap = null;
        invalidate(); // Force a redraw
    }
}
