package io.github.simonreilich.objects.Entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.UpdateType;
import io.github.simonreilich.objects.Drawable;

public abstract class Entity extends Sprite implements Drawable {

    protected float destinationX;
    protected float destinationY;

    protected float startX;
    protected float startY;

    private Sprite shadow;

    private final float JUMP = 0.5f;

    public Entity(Sprite sprite, int x, int y) {
        super(sprite);
        setPosX(x);
        setPosY(y);

        shadow = new Sprite(new Texture("sprites/shadow.png"));
    }

    public void setPosX(int x) {
        setX(x * 32 + ((32 - this.getWidth()) / 2));
        destinationX = getX();
        startX = getX();
    }

    public void setPosY(int y) {
        setY(y * 32);
        destinationY = getY();
        startY = getY();
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
                setX(getX() + (offset * 20.0f + 2.0f) / 4);
            } else if (getX() > destinationX) {
                setX(getX() - (offset * 20.0f + 2.0f) / 4);
            }

            if (getY() < destinationY) {
                setY(getY() + (offset * 20.0f + 2.0f) / 4);
            }  else if (getY() > destinationY) {
                setY(getY() - (offset * 20.0f + 2.0f) / 4);
            }

            if (Math.abs(destinationX - getX()) < JUMP) {
                setX(destinationX);
            }
            if (Math.abs(destinationY - getY()) < JUMP) {
                setY(destinationY);
            }
        }

        float yTemp = getY();
        setY(yTemp + (offset * 8.0f));

        batch.begin();
        batch.draw(shadow, getX(), yTemp + (getHeight() * 0.075f), getWidth(), (getHeight() * 0.25f));
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
    }
}
