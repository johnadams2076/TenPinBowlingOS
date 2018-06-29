package com.ztech;


import java.util.LinkedList;
import java.util.List;

public class Player {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Frame> getPlayerFrameAray() {
        return playerFrameAray;
    }

    public void setPlayerFrameAray(List<Frame> playerFrameAray) {
        this.playerFrameAray = playerFrameAray;
    }

    private String name;
    private List<Frame> playerFrameAray = new LinkedList<Frame>();

    public int getCurrentFrameNum() {
        return currentFrameNum;
    }

    public void setCurrentFrameNum(int currentFrameNum) {
        this.currentFrameNum = currentFrameNum;
    }

    private int currentFrameNum;


    public void evalScore(int v, int currFrameNum) {


        List<Frame> playerFrameAray = this.getPlayerFrameAray();
        Frame currFrame = playerFrameAray.get(currFrameNum - 1);
        currFrame.setFrameScore(currFrame.calculateCurrScore());
        Frame currFrameMinusOne = null;
        Frame currFrameMinusTwo = null;
        if (currFrameNum == 2) {
            currFrameMinusOne = playerFrameAray.get(currFrameNum - 2);
            if (currFrameMinusOne.isStrike() || currFrameMinusOne.isSpare()) {
                currFrameMinusOne.setFrameScore(currFrameMinusOne.getFrameScore() + currFrame.getFrameScore());
            }
        } else if (currFrameNum >= 3) {
            currFrameMinusOne = playerFrameAray.get(currFrameNum - 2);
            currFrameMinusTwo = playerFrameAray.get(currFrameNum - 3);
            if (currFrameMinusTwo.isStrike()) {
                currFrameMinusTwo.setFrameScore(currFrameMinusTwo.getFrameScore() + currFrame.getFrameScore());
            }

            if (currFrameMinusOne.isSpare() || currFrameMinusOne.isStrike()) {
                currFrameMinusOne.setFrameScore(currFrameMinusOne.getFrameScore() + currFrame.getFrameScore());
            }

        }


    }
}
