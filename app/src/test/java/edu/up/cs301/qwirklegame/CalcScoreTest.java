package edu.up.cs301.qwirklegame;

import static org.junit.Assert.*;

import org.junit.Test;

public class CalcScoreTest {

    @Test
    public void calcScore() {
        QwirkleState qs = new QwirkleState();
        QwirkleTile purpleCircle = new QwirkleTile(QwirkleTile.Shape.CIRCLE, QwirkleTile.Color.PURPLE);
        QwirkleTile orangeCircle = new QwirkleTile(QwirkleTile.Shape.CIRCLE, QwirkleTile.Color.ORANGE);
        QwirkleTile greenCircle = new QwirkleTile(QwirkleTile.Shape.CIRCLE, QwirkleTile.Color.GREEN);
        QwirkleTile redCircle = new QwirkleTile(QwirkleTile.Shape.CIRCLE, QwirkleTile.Color.RED);
        QwirkleTile yellowCircle = new QwirkleTile(QwirkleTile.Shape.CIRCLE, QwirkleTile.Color.YELLOW);
        QwirkleTile blueCircle = new QwirkleTile(QwirkleTile.Shape.CIRCLE, QwirkleTile.Color.BLUE);
        qs.getBoard().addToBoard(purpleCircle, 5, 11);
        qs.getBoard().addToBoard(orangeCircle, 6, 11);
        qs.getBoard().addToBoard(greenCircle, 7, 11);
        qs.getBoard().addToBoard(redCircle, 8, 11);
        qs.getBoard().addToBoard(yellowCircle, 9, 11);
        qs.getBoard().addToBoard(blueCircle, 10, 11);

        int result =  qs.calcScore(10, 11);
        assertTrue(result == 12);
    }

    // If you add a tile that is a part of another line, it should count those tiles as well
    @Test
    public void multipleLineScore() {
        QwirkleState qs = new QwirkleState();
        QwirkleTile redCircle = new QwirkleTile(QwirkleTile.Shape.CIRCLE, QwirkleTile.Color.RED);
        QwirkleTile redDiamond = new QwirkleTile(QwirkleTile.Shape.DIAMOND, QwirkleTile.Color.RED);
        QwirkleTile redSquare = new QwirkleTile(QwirkleTile.Shape.SQUARE, QwirkleTile.Color.RED);
        QwirkleTile red8Star = new QwirkleTile(QwirkleTile.Shape.EIGHTSTAR, QwirkleTile.Color.RED);
        QwirkleTile blue8Star = new QwirkleTile(QwirkleTile.Shape.EIGHTSTAR, QwirkleTile.Color.BLUE);
        QwirkleTile purple8Star = new QwirkleTile(QwirkleTile.Shape.EIGHTSTAR, QwirkleTile.Color.PURPLE);

        qs.getBoard().addToBoard(redCircle, 5,11);
        qs.getBoard().addToBoard(redDiamond, 6,11);
        qs.getBoard().addToBoard(redSquare, 7,11);
        qs.getBoard().addToBoard(red8Star, 8,11);
        qs.getBoard().addToBoard(blue8Star, 8,12);
        qs.getBoard().addToBoard(purple8Star, 8,13);

        int result =  qs.calcScore(8, 13);
        assertEquals(7, result);
    }
}