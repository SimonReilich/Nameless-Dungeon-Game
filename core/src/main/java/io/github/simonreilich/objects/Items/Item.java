package io.github.simonreilich.objects.Entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.UpdateType;
import io.github.simonreilich.graph.RoomNode;
import io.github.simonreilich.objects.Drawable;
import io.github.simonreilich.screens.MapView;

public abstract class Item extends Sprite implements Drawable {

    protected float startX;
    protected float startY;

    private final Texture shadow;

    protected MapView mapView;
    protected RoomNode room;

    public Item(Sprite sprite, int x, int y, MapView mapView, RoomNode room) {
        super(sprite);
        setPosX(x);
        setPosY(y);

        this.room = room;

        shadow = new Texture("sprites/shadow.png");

        this.mapView = mapView;
    }

    public void setPosX(int x) {
        setX(x * 32 + ((32 - this.getWidth()) / 2));
    }

    public void setPosY(int y) {
        setY(y * 32 + 6);
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
        batch.draw(shadow, getX(), getY() - (getHeight() * 0.25f) * 0.33f, getWidth(), (getHeight() * 0.25f));
        batch.end();

        batch.begin();
        super.draw(batch);
        batch.end();
    }

    public abstract void consume();

    @Override
    public void dispose() {
        this.getTexture().dispose();
        shadow.dispose();
    }
}
