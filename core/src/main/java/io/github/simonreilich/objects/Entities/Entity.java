package io.github.simonreilich.objects.Entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import io.github.simonreilich.objects.Drawable;
import io.github.simonreilich.screens.MapScreen;
import io.github.simonreilich.util.UpdateType;

public abstract class Entity extends Sprite implements Drawable {
    private static final float SKIP_ANIMATION_DISTANCE = 0.5f;
    private static final float NO_ORIGIN = 1.0f;
    private static final float MAX_SPEED = 7.0f;
    private static final float START_SPEED = 0.2f;
    private static final float JUMP_HEIGHT = 8.0f;
    private final Texture shadow;
    protected MapScreen mapScreen;
    protected Vector2 start;
    protected Vector2 dest;

    public Entity(Sprite sprite, int x, int y, MapScreen mapScreen) {
        super(sprite);

        this.start = new Vector2(x * 32, y * 32);
        this.dest = new Vector2(x * 32, y * 32);
        setPosX(x);
        setPosY(y);

        this.shadow = new Texture("sprites/misc/shadow.png");

        this.mapScreen = mapScreen;
    }

    public int getPosX() {
        return (int) (getX() / 32);
    }

    public void setPosX(int x) {
        setX(x * 32 + ((32 - this.getWidth()) / 2));
        this.dest.x = getX();
        this.start.x = NO_ORIGIN;
    }

    public int getPosY() {
        return (int) (getY() / 32);
    }

    public void setPosY(int y) {
        setY(y * 32 + 6);
        this.dest.y = getY();
        this.start.y = NO_ORIGIN;
    }

    public int getDestinationX() {
        return (int) (dest.x / 32);
    }

    public int getDestinationY() {
        return (int) (dest.y / 32);
    }

    public void left(int d) {
        this.start.x = getX();
        this.dest.x -= 32 * d;
    }

    public void right(int d) {
        this.start.x = getX();
        this.dest.x += 32 * d;
    }

    public void up(int d) {
        this.start.y = getY();
        this.dest.y += 32 * d;
    }

    public void down(int d) {
        this.start.y = getY();
        this.dest.y -= 32 * d;
    }

    @Override
    public void draw(OrthographicCamera cam, Batch batch, float delta) {
        cam.update();
        batch.setProjectionMatrix(cam.combined);

        float offset = 0.0f;

        if (dest.x != getX() || this.dest.y != getY()) {
            float progressX = 1.0f - (getX() - this.dest.x) / (start.x - this.dest.x);
            float progressY = 1.0f - (getY() - this.dest.y) / (start.y - this.dest.y);

            if (progressX != 1.0f) {
                offset = (float) (1.0 - Math.pow(2.0 * (progressX - 0.5), 2));
            } else if (progressY != 1.0f) {
                offset = (float) (1.0 - Math.pow(2.0 * (progressY - 0.5), 2));
            }

            if (getX() < this.dest.x) {
                setX(getX() + (offset * MAX_SPEED + START_SPEED));
            } else if (getX() > this.dest.x) {
                setX(getX() - (offset * MAX_SPEED + START_SPEED));
            }

            if (getY() < this.dest.y) {
                setY(getY() + (offset * MAX_SPEED + START_SPEED));
            } else if (getY() > this.dest.y) {
                setY(getY() - (offset * MAX_SPEED + START_SPEED));
            }

            if (Math.abs(dest.x - getX()) < SKIP_ANIMATION_DISTANCE) {
                setX(dest.x);
            }
            if (Math.abs(dest.y - getY()) < SKIP_ANIMATION_DISTANCE) {
                setY(dest.y);
            }
        }

        if (Double.isNaN(offset)) {
            offset = 0.0f;
        }

        float yTemp = getY();
        setY(yTemp + (offset * JUMP_HEIGHT));

        Color c = batch.getColor();
        // shadow should be rendered transparent
        batch.setColor(c.r, c.g, c.b, 0.3f);

        batch.begin();
        batch.draw(shadow, getX() + (getWidth() * 0.1f), yTemp - (getHeight() * 0.5f) * 0.33f, getWidth() * 0.8f, getHeight() * 0.5f);
        batch.end();

        batch.setColor(c.r, c.g, c.b, 1.0f);

        batch.begin();
        super.draw(batch);
        batch.end();

        setY(yTemp);
    }

    // different entities react differently to updates
    public abstract void update(UpdateType type, float delta);

    @Override
    public void dispose() {
        this.getTexture().dispose();
        this.shadow.dispose();
    }
}
