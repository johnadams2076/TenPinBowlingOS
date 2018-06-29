package com.ztech;

import java.util.ArrayList;
import java.util.List;

public class Frame {


    public int getFrameScore() {

        return frameScore;
    }

    public void setFrameScore(int frameScore) {
        this.frameScore = frameScore;
    }

    int frameScore = 0;
    boolean strike;

    public boolean isStrike() {
        return strike;
    }

    public void setStrike(boolean strike) {
        this.strike = strike;
    }

    public boolean isSpare() {
        return spare;
    }

    public void setSpare(boolean spare) {
        this.spare = spare;
    }

    boolean spare;

    public int getFrameNum() {
        return frameNum;
    }

    public void setFrameNum(int frameNum) {
        this.frameNum = frameNum;
    }

    int frameNum;
    int[] frameNumAray = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    public int[] getFrameNumAray() {
        return frameNumAray;
    }

    public void setFrameNumAray(int[] frameNumAray) {
        this.frameNumAray = frameNumAray;
    }

    public int getCurrentBallNum() {
        return currentBallNum;
    }

    public void setCurrentBallNum(int currentBallNum) {
        this.currentBallNum = currentBallNum;
    }

    int currentBallNum;

    public List<Ball> getBallAray() {
        return ballAray;
    }

    public void setBallAray(List<Ball> ballAray) {
        this.ballAray = ballAray;
    }

    List<Ball> ballAray = new ArrayList<>();


    public int calculateCurrScore() {

        int sumScore = 0;
        for (Ball b : this.getBallAray()) {
            sumScore += b.getScore();
        }
        return sumScore;

    }
}
