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
}