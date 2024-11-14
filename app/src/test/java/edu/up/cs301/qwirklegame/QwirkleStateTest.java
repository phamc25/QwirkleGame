package edu.up.cs301.qwirklegame;

import static org.junit.Assert.*;

import org.junit.Test;

public class QwirkleStateTest {

    @Test
    public void getPlayersScore() {
        QwirkleState qwirkleState = new QwirkleState();
        qwirkleState.setPlayersScore(0, 5);
        int[] playerScore = qwirkleState.getPlayersScore();
        assertTrue(playerScore[0] == 5);

    }

    // Ryan's unit tests
    @Test
    public void getAddPoints() {
        QwirkleState qState = new QwirkleState();
        int expectedPoints = 10;
        qState.setAddPoints(10);
        int actualPoints = qState.getAddPoints();
        assertEquals(expectedPoints, actualPoints);
    }

    @Test
    public void getCurrPlayer() {
        QwirkleState qState = new QwirkleState();
        int expectedPlayer = 1;
        qState.setCurrPlayer(1);
        int actualPlayer = qState.getCurrPlayer();
        assertEquals(expectedPlayer, actualPlayer);
    }

    @Test
    public void getTilesLeft() {
        QwirkleState qState = new QwirkleState();
        int expectedAmount = 108;
        int actualAmount = qState.getTilesLeft();
        assertEquals(expectedAmount, actualAmount);
    }

    // Chloe's unit tests
    @Test
    public void setAddPoints() {
        QwirkleState state = new QwirkleState();    // new state
        state.setAddPoints(10);
        assertEquals(10, state.getAddPoints());
    }

    @Test
    public void setCurrPlayer() {
        QwirkleState state = new QwirkleState();    // new state
        state.setCurrPlayer(2);
        assertEquals(2, state.getCurrPlayer());
    }

    @Test
    public void setCurrTile() {
        QwirkleState state = new QwirkleState();    // new state
        state.setCurrTile(4);   // 4th tile index
        assertEquals(4, state.getCurrTile());
    }

    @Test
    public void setPlayersScore() {
        QwirkleState q1 = new QwirkleState();
        q1.setPlayersScore(0,27);
        assertEquals(27, q1.getPlayersScore()[0]);
    }

    @Test
    public void refillHand() {
        QwirkleState q1 = new QwirkleState();
        q1.refillHand(0);
        int len = q1.getPlayerHand(0).size();
        assertEquals(6, len);
    }

    @Test
    public void getPlayerHand() {
        QwirkleState q1 = new QwirkleState();
        q1.drawTiles(0,3);
        int len = q1.getPlayerHand(0).size();
        assertEquals(3, len);

    }
}