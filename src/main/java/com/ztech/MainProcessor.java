package com.ztech;

import com.google.common.collect.ListMultimap;
import com.ztech.ui.DisplayFormatter;
import com.ztech.util.Constants;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 *
 * Main Processor.
 */
public class MainProcessor {

    List<Player> playersList = new ArrayList<>();

    public ListMultimap<String, Integer> getBowlingData() {
        return bowlingFile;
    }

    public void setBowlingData(ListMultimap bowlingFile) {
        this.bowlingFile = bowlingFile;
    }

    ListMultimap<String, Integer> bowlingFile;


    public void process() {


        populateObjects();
        evalScore();
        DisplayFormatter.displayOnConsole(playersList);
    }
    /**
      Method to evaluate scores.
    */
    private void evalScore() {

        for (Player p : playersList) {
            List<Frame> frameAray = p.getPlayerFrameAray();

            for (Frame f : frameAray) {


                if (f.getFrameNum() < Constants.MAX_FRAME_COUNT - 2) {

                    Frame currPlusOne = frameAray.get(f.getFrameNum());
                    Frame currPlusTwo = frameAray.get(f.getFrameNum() + 1);

                    if (f.isStrike()) {

                        f.setFrameScore(f.calculateCurrScore() + currPlusOne.calculateCurrScore() + currPlusTwo.calculateCurrScore());


                    } else if (f.isSpare()) {
                        f.setFrameScore(f.calculateCurrScore() + currPlusOne.calculateCurrScore());

                    } else {
                        f.setFrameScore(f.calculateCurrScore());
                    }
                } else if (f.getFrameNum() == Constants.MAX_FRAME_COUNT - 2) {

                    Frame currPlusOne = frameAray.get(f.getFrameNum());
                    Frame currPlusTwo = frameAray.get(f.getFrameNum() + 1);
                    if (f.isStrike()) {

                        f.setFrameScore(f.calculateCurrScore() + currPlusOne.calculateCurrScore() + currPlusTwo.getBallAray().get(0).getScore());


                    } else if (f.isSpare()) {

                        f.setFrameScore(f.calculateCurrScore() + currPlusOne.calculateCurrScore());

                    } else {
                        f.setFrameScore(f.calculateCurrScore());
                    }


                } else if (f.getFrameNum() == Constants.MAX_FRAME_COUNT - 1) {
                    Frame currPlusOne = frameAray.get(f.getFrameNum());

                    if (f.isStrike()) {

                        f.setFrameScore(f.calculateCurrScore() + currPlusOne.getBallAray().get(0).getScore() + currPlusOne.getBallAray().get(1).getScore());


                    } else if (f.isSpare()) {

                        f.setFrameScore(f.calculateCurrScore() + currPlusOne.getBallAray().get(0).getScore());

                    } else {
                        f.setFrameScore(f.calculateCurrScore());
                    }

                } else if (f.getFrameNum() == Constants.MAX_FRAME_COUNT) {


                    f.setFrameScore(f.calculateCurrScore());


                }


            }

        }


    }

    private void populateObjects() {

        for (String k : bowlingFile.keySet()) {

            List<Integer> scoreListTemp = bowlingFile.get(k);

            Supplier<List<Integer>> supplier = () -> new LinkedList<>();
            List<Integer> scoreList = scoreListTemp.stream().filter(s -> (s >= -1) && (s <= 10)).collect(Collectors.toCollection(supplier));

            Ball ball = null;

            Frame f = new Frame();
            f.setFrameNum(1);
            f.setCurrentBallNum(0);

            Player p = new Player();
            p.setName(k);
            p.setCurrentFrameNum(1);
            p.getPlayerFrameAray().add(f);
            playersList.add(p);

            int i = 0;
            int counter = 0;
            for (int v : scoreList) {

                if (++i < Constants.TOTAL_MAX_BALLS) {

                    final int currFrameNum = p.getCurrentFrameNum();
                    List<Frame> frames = p.getPlayerFrameAray();
                    //Collections.binarySearch(frames,currFrame, (Frame c1, Frame c2) ->  c1.getFrameNum() - c2.getFrameNum())
                    Optional<Frame> frame = frames.stream().filter(s -> s.getFrameNum() == currFrameNum).findFirst();

                    Frame currFrame = frame.get();
                    int currBallNum = currFrame.getCurrentBallNum();

                    if (currFrameNum < Constants.MAX_FRAME_COUNT) {

                        if (currFrame.isStrike() || currBallNum == Constants.MAX_BALLS_PER_FRAME) {
                            p.setCurrentFrameNum(currFrameNum + 1);
                            ball = new Ball();
                            ball.setBallNum(1);
                            if (v == -1) {
                                v = 0;
                                ball.setFoul(true);
                            }
                            ball.setScore(v);

                            f = new Frame();

                            if (v == Constants.STRIKE) {
                                f.setStrike(true);

                            }
                            f.setFrameNum(currFrameNum + 1);
                            f.setCurrentBallNum(1);
                            f.getBallAray().add(ball);
                            p.getPlayerFrameAray().add(f);


                        } else {
                            f = currFrame;

                            ball = new Ball();
                            ball.setBallNum(f.getCurrentBallNum() + 1);

                            if (v == -1) {
                                v = 0;
                                ball.setFoul(true);
                            }
                            ball.setScore(v);

                            if (v == Constants.STRIKE) {
                                f.setStrike(true);

                            }

                            f.setCurrentBallNum(f.getCurrentBallNum() + 1);
                            f.getBallAray().add(ball);


                            if (!f.isStrike() && (f.calculateCurrScore() == Constants.SPARE)) {
                                f.setSpare(true);
                            }


                        }


                    } else if (currFrameNum == Constants.MAX_FRAME_COUNT && currBallNum < Constants.MAX_BALLS_LASTFRAME) {
                        counter++;
                        if (currFrame.isStrike() || currFrame.isSpare() || currBallNum < Constants.MAX_BALLS_PER_FRAME) {

                            f = currFrame;

                            ball = new Ball();
                            ball.setBallNum(f.getCurrentBallNum() + 1);

                            if (v == -1) {
                                v = 0;
                                ball.setFoul(true);
                            }

                            ball.setScore(v);

                            if (v == Constants.STRIKE) {
                                f.setStrike(true);
                            }

                            if (!f.isStrike() && (f.calculateCurrScore() == Constants.SPARE)) {
                                f.setSpare(true);
                            }
                            f.setCurrentBallNum(f.getCurrentBallNum() + 1);
                            f.getBallAray().add(ball);


                        }


                    }


                }

            }
        }

    }

}



