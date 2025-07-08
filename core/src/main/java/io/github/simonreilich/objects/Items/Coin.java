package io.github.simonreilich.objects.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.rooms.RoomNode;
import io.github.simonreilich.screens.MapScreen;

public class Coin extends Item {
    public Coin(int x, int y, MapScreen mapScreen, RoomNode room) {
        super(new Sprite(new Texture("sprites/items/coin.png")), x, y, mapScreen, room);
    }

    @Override
    public void consume() {
        this.mapScreen.addPoints(1);
        this.mapScreen.dequeue(this);
        this.room.removeDrawable(this);
    }
}
