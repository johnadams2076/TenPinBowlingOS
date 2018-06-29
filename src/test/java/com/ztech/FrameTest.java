package com.ztech;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FrameTest {
    Ball ball = null;
    Ball ball1 = null;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {

        ball = new Ball();
        ball.setBallNum(1);
        ball.setScore(1);

        Ball ball1 = new Ball();
        ball1.setBallNum(2);
        ball1.setScore(9);

        Frame f = new Frame();
        f.getBallAray().add(ball);
        f.getBallAray().add(ball1);

        assertEquals(f.calculateCurrScore(), 10);


    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {

        ball = null;
    }

    @org.junit.jupiter.api.Test
    void calculateCurrScore() {


    }
}