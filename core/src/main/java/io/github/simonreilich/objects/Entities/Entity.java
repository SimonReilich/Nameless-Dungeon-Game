package io.github.simonreilich.objects.Entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.UpdateType;
import io.github.simonreilich.objects.Drawable;

public abstract class Entity extends Sprite implements Drawable {

    protected float destinationX;
    protected float destinationY;

    private final float JUMP = 0.5f;

    public Entity(Sprite sprite, int x, int y) {
        super(sprite);
        setPosX(x);
        setPosY(y);
    }

    public void setPosX(int x) {
        setX(x * 32 + ((32 - this.getWidth()) / 2));
        destinationX = getX();
    }

    public void setPosY(int y) {
        setY(y * 32);
        destinationY = getY();
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

    public void left() {
        destinationX -= 32;
    }

    public void right() {
        destinationX += 32;
    }

    public void up() {
        destinationY += 32;
    }

    public void down() {
        destinationY -= 32;
    }

    @Override
    public void draw(OrthographicCamera cam, Batch batch, float delta) {
        cam.update();
        batch.setProjectionMatrix(cam.combined);

        if (destinationX != getX() || destinationY != getY()) {
            setX(getX() - (getX() - destinationX) / 4);
            setY(getY() - (getY() - destinationY) / 4);
            if (Math.abs(destinationX - getX()) < JUMP) {
                setX(destinationX);
            }
            if (Math.abs(destinationY - getY()) < JUMP) {
                setY(destinationY);
            }
        }

        batch.begin();
        super.draw(batch);
        batch.end();
    }

    public abstract void update(UpdateType type, float delta);

    @Override
    public void dispose() {
        this.getTexture().dispose();
    }
}
