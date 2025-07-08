package io.github.simonreilich.objects.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.rooms.RoomNode;
import io.github.simonreilich.screens.MapScreen;

public class SilverTreasure extends Item {

    public SilverTreasure(int x, int y, MapScreen mapScreen, RoomNode room) {
        super(new Sprite(new Texture("assets/sprites/items/silver_treasure.png")), x, y, mapScreen, room);
    }

    @Override
    public void consume() {
        this.mapScreen.addPoints(5);
        this.mapScreen.dequeue(this);
        this.room.removeDrawable(this);
    }
}
