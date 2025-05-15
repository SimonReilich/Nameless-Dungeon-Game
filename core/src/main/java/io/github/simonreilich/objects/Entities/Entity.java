package io.github.simonreilich.objects.Entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.UpdateType;
import io.github.simonreilich.objects.Drawable;

public abstract class Entity extends Sprite implements Drawable {

    public Entity(Sprite sprite, int x, int y) {
        super(sprite);
        setPosX(x);
        setPosY(y);
    }

    public void setPosX(int x) {
        setX(x * 32 + ((32 - this.getWidth()) / 2));
    }

    public void setPosY(int y) {
        setY(y * 32);
    }

    public int getPosX() {
        return (int) (getX() / 32);
    }

    public int getPosY() {
        return (int) (getY() / 32);
    }

    @Override
    public void draw(OrthographicCamera cam, Batch batch, float delta) {
        cam.update();
        batch.setProjectionMatrix(cam.combined);

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
