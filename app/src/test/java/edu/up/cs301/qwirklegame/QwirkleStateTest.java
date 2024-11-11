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

    @Test
    public void getAddPoints() {
    }

    @Test
    public void getCurrPlayer() {
    }

    @Test
    public void setAddPoints() {
    }

    @Test
    public void setCurrPlayer() {
    }

    @Test
    public void setCurrTile() {
    }

    @Test
    public void setPlayersScore() {
    }

    @Test
    public void refillHand() {
    }

    @Test
    public void getPlayerHand() {
    }
}