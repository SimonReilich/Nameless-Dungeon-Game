package io.github.simonreilich.objects.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.rooms.RoomNode;
import io.github.simonreilich.screens.MapScreen;

public class GoldTreasure extends Item {

    public GoldTreasure(int x, int y, MapScreen mapScreen, RoomNode room) {
        super(new Sprite(new Texture("assets/sprites/items/gold_treasure.png")), x, y, mapScreen, room);
    }

    @Override
    public void consume() {
        mapScreen.addPoints(10);
        mapScreen.dequeue(this);
        room.removeDrawable(this);
    }
}
