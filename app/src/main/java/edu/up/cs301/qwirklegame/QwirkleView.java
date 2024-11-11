package edu.up.cs301.qwirklegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 *    For CS371 Lab activities
 */

public class QwirkleView extends SurfaceView{

//        private Bitmap tileBlueCirc;
//        private Bitmap tileOrangeCirc;
//        private Bitmap tileRedCirc;
//        private Bitmap tileRed4Star;
//        private Bitmap tileRed8Star;
//        private Bitmap tileRedClo;
//        private Bitmap tileRedDia;
//        private Bitmap tileRedSquare;
        private BoardModel playerModel;

        // Array of bitmaps? 36 is a bit much
        private Bitmap[] tiles;

        // Variables for drawing grid
        private float cellSize, offsetX, offsetY;

        private Paint black;
        private Paint darkGreenPaint;
        private Paint gridPaint;

        private int numColumns = 27;
        private int numRows = 15;

        private Board board;    // Board class

        public QwirkleView(Context context, AttributeSet attrs) {

            super(context, attrs);
            setWillNotDraw(false);

            darkGreenPaint = new Paint();
            darkGreenPaint.setColor(0xFF119722);
            darkGreenPaint.setStyle(Paint.Style.STROKE);

            black = new Paint();
            black.setColor(0xFF000000);
            black.setStyle(Paint.Style.STROKE);

            gridPaint = new Paint();
            gridPaint.setColor(Color.BLACK);
            gridPaint.setStrokeWidth(2);

            // Initialize bitmaps
//            tileBlueCirc = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.tile_blue_circle), 50, 50, false);
//            tileOrangeCirc = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.tile_orange_circle), 50, 50, false);
//            tileRedCirc = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.tile_red_circle), 50, 50, false);
//            tileRed4Star = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.tile_red_4star), 50, 50, false);
//            tileRed8Star = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.tile_red_8star), 50, 50, false);
//            tileRedClo = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.tile_red_clover), 50, 50, false);
//            tileRedDia = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.tile_red_diamond), 50, 50, false);
//            tileRedSquare = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.tile_red_square), 50, 50, false);

            setBackgroundColor(0xFFDDDDDD);

        }

    // returns the initialized drawing model
    public BoardModel getModel() {
        return this.playerModel;
    }

    /**
     * Helper method to draw the grid lines on the Qwirkle board
     * @param canvas
     */
    public void drawBoard(Canvas canvas) {
            // The cell size is the smallest ratio between these to fit the defined row and columns
            // all in the board
            cellSize = Math.min(getWidth() / (float) numColumns, (getHeight() - 20) / (float) numRows);

            // The length for the side of the centered grid
            offsetX = (getWidth() - numColumns * cellSize) / 2;
            offsetY = (getHeight() - numRows * cellSize) / 2;

            // Loop through the rows and draws the lines
            for (int i = 0; i <= numRows; i++) {
                // Y coordinate calculation. i * cellSize = each line for each cell row. + offSetY for centering
                float y = offsetY + (i * cellSize);
                canvas.drawLine(offsetX, y, offsetX + (numColumns * cellSize), y, gridPaint);
            }
            // Loop through the columns and draws its lines
            for (int j = 0; j <= numColumns; j++) {
                // X coordinate calculation. j * cellSize = each line for each cell col. + offSetX for centering
                float x = offsetX + (j * cellSize);
                canvas.drawLine(x, offsetY, x, offsetY + (numRows * cellSize), gridPaint);
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            // Drawing the grid board
            drawBoard(canvas);

//            canvas.drawBitmap(tileBlueCirc, 920, 350, darkGreenPaint);
//            canvas.drawBitmap(tileOrangeCirc, 970, 350, darkGreenPaint);
//            canvas.drawBitmap(tileRedCirc, 1020, 350, darkGreenPaint);
//            canvas.drawBitmap(tileRed4Star, 1020, 300, darkGreenPaint);
//            canvas.drawBitmap(tileRed8Star, 1020, 250, darkGreenPaint);
//            canvas.drawBitmap(tileRedSquare, 1020, 400, darkGreenPaint);
//            canvas.drawBitmap(tileRedDia, 1020, 450, darkGreenPaint);
//            canvas.drawBitmap(tileRedClo, 1020, 500, darkGreenPaint);

            black.setTextSize(35);
            canvas.drawText("Player 2", 30, 55, black);
            canvas.drawText("Score: 0", 30, 90, black);

            canvas.drawText("Player 3", 30, 190, black);
            canvas.drawText("Score: 0", 30, 225, black);

            canvas.drawText("Player 4", 30, 325, black);
            canvas.drawText("Score: 0", 30, 360, black);
        }

        // Setters

    /**
     * Sets the board and then redraws
     * @param board
     */
    public void setBoard(Board board) {
            this.board = board;
            invalidate();
        }
    }
