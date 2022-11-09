package com.example.breakout;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.input.UserAction;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.core.math.FXGLMath.randomBoolean;
import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.getInput;
import static com.almasb.fxgl.dsl.FXGL.onCollisionBegin;
import static com.almasb.fxgl.dsl.FXGL.spawn;
import static com.example.breakout.Util.bounceBot;
import static com.example.breakout.Util.bounceTop;
import static com.example.breakout.Util.getBallSize;
import static com.example.breakout.Util.getBrickHeight;
import static com.example.breakout.Util.getBrickWidth;
import static com.example.breakout.Util.getPaddle1;
import static com.example.breakout.Util.getPaddle2;
import static com.example.breakout.Util.getPaddleHeight;
import static com.example.breakout.Util.getPaddleSpeed;
import static com.example.breakout.Util.getPaddleWidth;
import static com.example.breakout.Util.lostBallCheck;
import static com.example.breakout.Util.player1BounceCheck;
import static com.example.breakout.Util.player2BounceCheck;
import static com.example.breakout.Util.setBall;
import static com.example.breakout.Util.setPaddle1;
import static com.example.breakout.Util.setPaddle2;
import static com.example.breakout.Util.updateBall;

public class BreakoutApp extends GameApplication
{
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setTitle("Breakout");
    }

    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("Up 1") {
            @Override
            protected void onAction() {
                getPaddle1().translateY(-getPaddleSpeed());
            }
        }, KeyCode.Z);

        getInput().addAction(new UserAction("Down 1") {
            @Override
            protected void onAction() {
                getPaddle1().translateY(getPaddleSpeed());
            }
        }, KeyCode.S);

        getInput().addAction(new UserAction("Up 2") {
            @Override
            protected void onAction() {
                getPaddle2().translateY(-getPaddleSpeed());
            }
        }, KeyCode.UP);

        getInput().addAction(new UserAction("Down 2") {
            @Override
            protected void onAction() {
                getPaddle2().translateY(getPaddleSpeed());
            }
        }, KeyCode.DOWN);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new BreakoutFactory());

        setPaddle1(spawn("bat", 0, getAppHeight()/2.0 - getPaddleHeight()/2.0));
        setPaddle2(spawn("bat", getAppWidth() - getPaddleWidth(), getAppHeight()/2.0 - getPaddleHeight()/2.0));
        setBall(spawn("ball", getAppWidth()/2.0 - getBallSize()/2.0, getAppHeight()/2.0 - getBallSize()/2.0));

        for (int i = 0; i < 10; i++) {
            spawn("brick", getAppWidth()/2.0 - 200 - getBrickWidth(), 100 + i*(getBrickHeight() + 20));
            spawn("brick", getAppWidth()/2.0 + 200, 100 + i*(getBrickHeight() + 20));
        }
    }

    @Override
    protected void initPhysics() {
        onCollisionBegin(BreakoutType.BALL, BreakoutType.BRICK, (ball, brick) -> {
            brick.removeFromWorld();
            Point2D velocity = ball.getObject("velocity");

            if(randomBoolean())
                ball.setProperty("velocity", new Point2D(-velocity.getX(), velocity.getY()));
            else
                ball.setProperty("velocity", new Point2D(velocity.getX(), -velocity.getY()));
        });
    }

    @Override
    protected void onUpdate(double tpf) {
        updateBall();

        player1BounceCheck();
        player2BounceCheck();

        lostBallCheck();

        bounceTop();
        bounceBot();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
