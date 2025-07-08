package io.github.simonreilich.objects.Items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.objects.Drawable;
import io.github.simonreilich.rooms.RoomNode;
import io.github.simonreilich.screens.MapScreen;

public abstract class Item extends Sprite implements Drawable {

    private static final Texture shadow = new Texture("sprites/misc/shadow.png");

    protected MapScreen mapScreen;
    protected RoomNode room;

    public Item(Sprite sprite, int x, int y, MapScreen mapScreen, RoomNode room) {
        super(sprite);
        setPosX(x);
        setPosY(y);
        this.room = room;
        this.mapScreen = mapScreen;
    }

    public static Item spawn(int spawnID, int x, int y, MapScreen mapScreen, RoomNode room) {
        switch (spawnID) {
            case 0:
                return new Coin(x, y, mapScreen, room);
            case 1:
                return new SilverTreasure(x, y, mapScreen, room);
            case 2:
                return new GoldTreasure(x, y, mapScreen, room);
            case 3:
                return new SpeedPotion(x, y, mapScreen, room);
            default:
                return spawn((int) (Math.random() * 4), x, y, mapScreen, room);
        }
    }

    public int getPosX() {
        return (int) (getX() / 32);
    }

    public void setPosX(int x) {
        setX(x * 32 + ((32 - this.getWidth()) / 2));
    }

    public int getPosY() {
        return (int) (getY() / 32);
    }

    public void setPosY(int y) {
        setY(y * 32 + 6);
    }

    @Override
    public void draw(OrthographicCamera cam, Batch batch, float delta) {
        cam.update();
        batch.setProjectionMatrix(cam.combined);

        Color c = batch.getColor();
        batch.setColor(c.r, c.g, c.b, 0.3f);

        batch.begin();
        batch.draw(shadow, getX(), getY() - (getHeight() * 0.5f) * 0.33f, getWidth(), (getHeight() * 0.5f));
        batch.end();

        batch.setColor(c.r, c.g, c.b, 1.0f);

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
