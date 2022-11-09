package com.example.breakout;

import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;

public final class Util
{
    private static final int PADDLE_WIDTH = 30;
    private static final int PADDLE_HEIGHT = 100;
    private static final int BRICK_WIDTH = 50;
    private static final int BRICK_HEIGHT = 25;
    private static final int BALL_SIZE = 20;

    private static final int PADDLE_SPEED = 5;
    private static final int BALL_SPEED = 5;

    private static Entity paddle1;
    private static Entity paddle2;
    private static Entity ball;

    private static Point2D velocity;

    //region getters/setters
    public static int getPaddleWidth() {
        return PADDLE_WIDTH;
    }

    public static int getPaddleHeight() {
        return PADDLE_HEIGHT;
    }

    public static int getBrickWidth() {
        return BRICK_WIDTH;
    }

    public static int getBrickHeight() {
        return BRICK_HEIGHT;
    }

    public static int getBallSize() {
        return BALL_SIZE;
    }

    public static int getPaddleSpeed() {
        return PADDLE_SPEED;
    }

    public static int getBallSpeed() {
        return BALL_SPEED;
    }

    public static Entity getPaddle1() {
        return paddle1;
    }

    public static void setPaddle1(Entity newPaddle1) {
        paddle1 = newPaddle1;
    }

    public static Entity getPaddle2() {
        return paddle2;
    }

    public static void setPaddle2(Entity newPaddle2) {
        paddle2 = newPaddle2;
    }

    public static Entity getBall() {
        return ball;
    }

    public static void setBall(Entity newBall) {
        ball = newBall;
    }
    //endregion

    public static void resetBall() {
        ball.setPosition(getAppWidth()/2.0 - BALL_SIZE/2.0, getAppHeight()/2.0 - BALL_SIZE/2.0);
        ball.setProperty("velocity", new Point2D(BALL_SPEED, BALL_SPEED));
    }

    public static void updateBall() {
        velocity = getBall().getObject("velocity");
        getBall().translate(velocity);
    }

    public static void player1BounceCheck() {
        if (ball.getX() == paddle1.getRightX()
                && ball.getY() < paddle1.getBottomY()
                && ball.getBottomY() > paddle1.getY()) {
            ball.setProperty("velocity", new Point2D(-velocity.getX(), velocity.getY()));
        }
    }

    public static void player2BounceCheck() {
        if (ball.getRightX() == paddle2.getX()
                && ball.getY() < paddle2.getBottomY()
                && ball.getBottomY() > paddle2.getY()) {
            ball.setProperty("velocity", new Point2D(-velocity.getX(), velocity.getY()));
        }
    }

    public static void lostBallCheck() {
        if (ball.getX() <= 0 || ball.getRightX() >= getAppWidth())
            resetBall();
    }

    public static void bounceTop() {
        if (ball.getY() <= 0) {
            ball.setY(0);
            ball.setProperty("velocity", new Point2D(velocity.getX(), -velocity.getY()));
        }
    }

    public static void bounceBot() {
        if (ball.getBottomY() >= getAppHeight()) {
            ball.setY(getAppHeight() - BALL_SIZE);
            ball.setProperty("velocity", new Point2D(velocity.getX(), -velocity.getY()));
        }
    }
}
