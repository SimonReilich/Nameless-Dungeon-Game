package io.github.simonreilich.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player extends Sprite implements Drawable {

    private float cooldown;
    private Direction direction;

    public Player(Sprite sprite) {
        super(sprite);
        cooldown = 1;
        direction = Direction.NONE;
        setPosition(15 * 32 + 4, 10 * 32);
    }

    public void up() {
        direction = Direction.UP;
        System.out.println("Up");
    }

    public void down() {
        direction = Direction.DOWN;
        System.out.println("Down");
    }

    public void left() {
        direction = Direction.LEFT;
        System.out.println("Left");
    }

    public void right() {
        direction = Direction.RIGHT;
        System.out.println("Right");
    }

    @Override
    public void draw(Batch batch) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            up();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            left();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            down();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            right();
        }
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    @Override
    public void dispose() {
        super.getTexture().dispose();
    }

    private void update(float delta) {
        if (cooldown < 0) {
            cooldown = Math.min(cooldown + delta, 0);
        } else {
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
                    break;
            }
            direction = Direction.NONE;
        }
    }
}

enum Direction {
    UP, DOWN, LEFT, RIGHT, NONE
}
