package io.github.simonreilich.objects.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.graph.RoomNode;
import io.github.simonreilich.screens.MapScreen;

public class Coin extends Item {
    public Coin(int x, int y, MapScreen mapScreen, RoomNode room) {
        super(new Sprite(new Texture("sprites/items/coin.png")), x, y, mapScreen, room);
    }

    @Override
    public void consume() {
        mapScreen.addPoints(1);
        mapScreen.dequeue(this);
        room.removeDrawable(this);
    }
}
