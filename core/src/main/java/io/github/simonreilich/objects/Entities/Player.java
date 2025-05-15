package io.github.simonreilich.objects.Entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.UpdateType;
import io.github.simonreilich.objects.Drawable;
import io.github.simonreilich.screens.DrawQueue;
import io.github.simonreilich.screens.GameView;

public class Player extends Entity implements Drawable {

    private GameView gameView;
    private float cooldown;
    private Direction direction;

    public Player(GameView view) {
        super(new Sprite(new Texture("sprites/player.png")), 15, 10);
        cooldown = 1;
        direction = Direction.NONE;
        this.gameView = view;
    }

    public void up() {
        direction = Direction.UP;
    }

    public void down() {
        direction = Direction.DOWN;
    }

    public void left() {
        direction = Direction.LEFT;
    }

    public void right() {
        direction = Direction.RIGHT;
    }

    public void update(UpdateType type, float delta) {
        return;
    }

    @Override
    public void draw(OrthographicCamera cam, Batch batch, float delta) {
        move(delta);

        cam.update();
        batch.setProjectionMatrix(cam.combined);

        batch.begin();
        super.draw(batch);
        batch.end();
    }

    public void move(float delta) {
        if (cooldown < 0) {
            cooldown = Math.min(cooldown + delta, 0);
        } else {
            boolean moved = true;
            switch (direction) {
                case UP:
                    setY(getY() + 32);
                    cooldown = 10.0f;
                    break;
                case DOWN:
                    setY(getY() - 32);
                    cooldown = 10.0f;
                    break;
                case LEFT:
                    setX(getX() - 32);
                    cooldown = 10.0f;
                    System.out.println("Hi");
                    break;
                case RIGHT:
                    setX(getX() + 32);
                    cooldown = 10.0f;
                    break;
                case NONE:
                    moved = false;
                    break;
            }
            if (moved) {
                gameView.updateAll(UpdateType.PlayerMove, delta);
            }
            direction = Direction.NONE;
        }
    }
}

enum Direction {
    UP, DOWN, LEFT, RIGHT, NONE
}
