package com.example.breakout;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.example.breakout.Util.getBallSize;
import static com.example.breakout.Util.getBallSpeed;
import static com.example.breakout.Util.getBrickHeight;
import static com.example.breakout.Util.getBrickWidth;
import static com.example.breakout.Util.getPaddleHeight;
import static com.example.breakout.Util.getPaddleWidth;

public final class BreakoutFactory implements EntityFactory
{
    @Spawns("bat")
    public Entity newBat(SpawnData data) {
        return entityBuilder()
                .from(data)
                .viewWithBBox(new Rectangle(getPaddleWidth(), getPaddleHeight()))
                .build();
    }

    @Spawns("ball")
    public Entity newBall(SpawnData data) {
        return entityBuilder()
                .from(data)
                .type(BreakoutType.BALL)
                .viewWithBBox(new Rectangle(getBallSize(), getBallSize(), Color.BLUE))
                .collidable()
                .with("velocity", new Point2D(getBallSpeed(), getBallSpeed()))
                .build();
    }

    @Spawns("brick")
    public Entity newBrick(SpawnData data) {
        return entityBuilder()
                .from(data)
                .type(BreakoutType.BRICK)
                .viewWithBBox(new Rectangle(getBrickWidth(), getBrickHeight(), Color.RED))
                .collidable()
                .build();
    }
}
