package io.github.simonreilich.objects.Entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.UpdateType;
import io.github.simonreilich.objects.Drawable;
import io.github.simonreilich.screens.MapView;

public abstract class Entity extends Sprite implements Drawable {

    protected float destinationX;
    protected float destinationY;

    protected float startX;
    protected float startY;

    private final Texture shadow;

    private final float SKIP_ANIMATION_DISTANCE = 0.5f;
    private final float NO_ORIGIN = 1.0f;
    private float MAX_SPEED;
    private float START_SPEED;
    private float JUMP_HEIGHT;

    protected MapView mapView;

    public Entity(Sprite sprite, int x, int y, MapView mapView) {
        super(sprite);
        setPosX(x);
        setPosY(y);

        MAX_SPEED = 7.0f;
        START_SPEED = 0.2f;
        JUMP_HEIGHT = 8.0f;

        shadow = new Texture("sprites/misc/shadow.png");

        this.mapView = mapView;
    }

    public void setPosX(int x) {
        setX(x * 32 + ((32 - this.getWidth()) / 2));
        destinationX = getX();
        startX = NO_ORIGIN;
    }

    public void setPosY(int y) {
        setY(y * 32 + 6);
        destinationY = getY();
        startY = NO_ORIGIN;
    }

    public int getPosX() {
        return (int) (getX() / 32);
    }

    public int getPosY() {
        return (int) (getY() / 32);
    }

    public int getDestinationX() {
        return (int) (destinationX / 32);
    }

    public int getDestinationY() {
        return (int) (destinationY / 32);
    }

    public void left(int d) {
        startX = getX();
        destinationX -= 32 * d;
    }

    public void right(int d) {
        startX = getX();
        destinationX += 32 * d;
    }

    public void up(int d) {
        startY = getY();
        destinationY += 32 * d;
    }

    public void down(int d) {
        startY = getY();
        destinationY -= 32 * d;
    }

    @Override
    public void draw(OrthographicCamera cam, Batch batch, float delta) {
        cam.update();
        batch.setProjectionMatrix(cam.combined);

        float offset = 0.0f;

        if (destinationX != getX() || destinationY != getY()) {
            float progressX = 1.0f - (getX() - destinationX) / (startX - destinationX);
            float progressY = 1.0f - (getY() - destinationY) / (startY - destinationY);

            if (progressX != 1.0f) {
                offset = (float) (1.0 - Math.pow(2.0 * (progressX - 0.5), 2));
            } else if (progressY != 1.0f) {
                offset = (float) (1.0 - Math.pow(2.0 * (progressY - 0.5), 2));
            }

            if (getX() < destinationX) {
                setX(getX() + (offset * MAX_SPEED + START_SPEED));
            } else if (getX() > destinationX) {
                setX(getX() - (offset * MAX_SPEED + START_SPEED));
            }

            if (getY() < destinationY) {
                setY(getY() + (offset * MAX_SPEED + START_SPEED));
            }  else if (getY() > destinationY) {
                setY(getY() - (offset * MAX_SPEED + START_SPEED));
            }

            if (Math.abs(destinationX - getX()) < SKIP_ANIMATION_DISTANCE) {
                setX(destinationX);
            }
            if (Math.abs(destinationY - getY()) < SKIP_ANIMATION_DISTANCE) {
                setY(destinationY);
            }
        }

        if (Double.isNaN(offset)) {
            offset = 0.0f;
        }

        float yTemp = getY();
        setY(yTemp + (offset * JUMP_HEIGHT));

        batch.begin();
        batch.draw(shadow, getX() + (getWidth() * 0.1f), yTemp - (getHeight() * 0.5f) * 0.33f, getWidth() * 0.8f, getHeight() * 0.5f);
        batch.end();

        batch.begin();
        super.draw(batch);
        batch.end();

        setY(yTemp);
    }

    public abstract void update(UpdateType type, float delta);

    @Override
    public void dispose() {
        this.getTexture().dispose();
        shadow.dispose();
    }
}
