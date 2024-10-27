package edu.up.cs301.qwirklegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 *    For CS371 Lab activities
 */

public class QwirkleBitmaps extends SurfaceView{

        private Bitmap tileBlueCirc;
        private Bitmap tileOrangeCirc;
        private Bitmap tileRedCirc;
        private Bitmap tileRed4Star;
        private Bitmap tileRed8Star;
        private Bitmap tileRedClo;
        private Bitmap tileRedDia;
        private Bitmap tileRedSquare;

        private Paint black;
        private Paint darkGreenPaint;

        public QwirkleBitmaps(Context context, AttributeSet attrs) {

            super(context, attrs);
            setWillNotDraw(false);

            darkGreenPaint = new Paint();
            darkGreenPaint.setColor(0xFF119722);
            darkGreenPaint.setStyle(Paint.Style.STROKE);

            black = new Paint();
            black.setColor(0xFF000000);
            black.setStyle(Paint.Style.STROKE);

            tileBlueCirc = BitmapFactory.decodeResource(getResources(), R.drawable.tile_blue_circle);
            tileBlueCirc = Bitmap.createScaledBitmap(tileBlueCirc, 50, 50, false);

            tileOrangeCirc = BitmapFactory.decodeResource(getResources(), R.drawable.tile_orange_circle);
            tileOrangeCirc = Bitmap.createScaledBitmap(tileOrangeCirc, 50, 50, false);

            tileRedCirc = BitmapFactory.decodeResource(getResources(), R.drawable.tile_red_circle);
            tileRedCirc = Bitmap.createScaledBitmap(tileRedCirc, 50, 50, false);

            tileRed4Star = BitmapFactory.decodeResource(getResources(), R.drawable.tile_red_4star);
            tileRed4Star = Bitmap.createScaledBitmap(tileRed4Star, 50, 50, false);

            tileRedClo = BitmapFactory.decodeResource(getResources(), R.drawable.tile_red_clover);
            tileRedClo = Bitmap.createScaledBitmap(tileRedClo, 50, 50, false);

            tileRed8Star = BitmapFactory.decodeResource(getResources(), R.drawable.tile_red_8star);
            tileRed8Star = Bitmap.createScaledBitmap(tileRed8Star, 50, 50, false);

            tileRedDia = BitmapFactory.decodeResource(getResources(), R.drawable.tile_red_diamond);
            tileRedDia = Bitmap.createScaledBitmap(tileRedDia, 50, 50, false);

            tileRedSquare = BitmapFactory.decodeResource(getResources(), R.drawable.tile_red_square);
            tileRedSquare = Bitmap.createScaledBitmap(tileRedSquare, 50, 50, false);

            setBackgroundColor(0xFFDDDDDD);

        }
        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawBitmap(tileBlueCirc, 920, 350, darkGreenPaint);
            canvas.drawBitmap(tileOrangeCirc, 970, 350, darkGreenPaint);
            canvas.drawBitmap(tileRedCirc, 1020, 350, darkGreenPaint);
            canvas.drawBitmap(tileRed4Star, 1020, 300, darkGreenPaint);
            canvas.drawBitmap(tileRed8Star, 1020, 250, darkGreenPaint);
            canvas.drawBitmap(tileRedSquare, 1020, 400, darkGreenPaint);
            canvas.drawBitmap(tileRedDia, 1020, 450, darkGreenPaint);
            canvas.drawBitmap(tileRedClo, 1020, 500, darkGreenPaint);

            black.setTextSize(35);
            canvas.drawText("Player 2", 30, 55, black);
            canvas.drawText("Score: 12", 30, 90, black);

            canvas.drawText("Player 3", 1850, 55, black);
            canvas.drawText("Score: 0", 1850, 90, black);

            canvas.drawText("Player 4", 1850, 715, black);
            canvas.drawText("Score: 0", 1850, 750, black);
        }
    }
