package com.ztech.ui;

import com.ztech.Ball;
import com.ztech.Frame;
import com.ztech.Player;
import com.ztech.util.Constants;

import java.io.Console;
import java.util.List;

public class DisplayFormatter {


    public static void displayOnConsole(List<Player> playersList) {
        Console console = System.console();


        System.out.print("Frames\t\t");
        for (int i = 0; i < Constants.MAX_FRAME_COUNT; i++) {


            System.out.print((i + 1) + "\t\t");


        }
        System.out.print("\n");
        for (Player p : playersList) {


            System.out.println(p.getName());

            System.out.print("Pinfalls\t");


            boolean isLast = false;
            for (Frame f : p.getPlayerFrameAray()) {
                isLast = f.getFrameNum() == Constants.MAX_FRAME_COUNT;
                int count = 0;
                for (Ball ball : f.getBallAray()) {

                    String str = String.valueOf(ball.getScore());

                    if (f.isStrike()) {
                        if (count == 0 && !isLast) {

                            str = "\tX";
                        } else if (isLast) {
                            str = "X";

                        } else if (count == 1) {
                            str = "X";
                        }

                    } else if (f.isSpare()) {
                        if (count > 0) {

                            str = "\\";
                        }


                    }
                    count++;
                    System.out.print(str + "\t");
                }


            }
            int sum = 0;
            System.out.print("\n" + "Score\t\t");
            for (Frame f : p.getPlayerFrameAray()) {

                sum += f.getFrameScore();
                System.out.print(sum + "\t\t");


            }
            System.out.print("\n");


        }

    }


}
