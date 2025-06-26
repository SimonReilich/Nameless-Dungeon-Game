package io.github.simonreilich.objects.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.graph.RoomNode;
import io.github.simonreilich.objects.Entities.Item;
import io.github.simonreilich.screens.MapView;

public class Coin extends Item {
    public Coin(int x, int y, MapView mapView, RoomNode room) {
        super(new Sprite(new Texture("sprites/coin.png")), x, y, mapView, room);
    }

    @Override
    public void consume() {
        mapView.score++;
        mapView.dequeue(this);
        room.removeDrawable(this);
    }
}
