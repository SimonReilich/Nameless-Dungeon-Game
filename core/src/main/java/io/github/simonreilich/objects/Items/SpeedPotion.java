package io.github.simonreilich.objects.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.rooms.RoomNode;
import io.github.simonreilich.screens.MapScreen;

public class SpeedPotion extends Item {

    public SpeedPotion(int x, int y, MapScreen mapScreen, RoomNode room) {
        super(new Sprite(new Texture("sprites/items/speed_potion.png")), x, y, mapScreen, room);
    }

    @Override
    public void consume() {
        mapScreen.increaseHeroSpeed();
        mapScreen.dequeue(this);
        room.removeDrawable(this);
    }
}
