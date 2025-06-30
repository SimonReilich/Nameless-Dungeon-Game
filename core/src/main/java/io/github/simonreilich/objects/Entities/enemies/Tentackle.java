package io.github.simonreilich.objects.Entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.graph.RoomNode;
import io.github.simonreilich.screens.MapView;

public class Tentackle extends Enemy {

    public Tentackle(int x, int y, MapView mapView, RoomNode room) {
        super(new Sprite(new Texture("sprites/entities/tentackle.png")), x, y, mapView, room);
    }

    @Override
    public void move() {

    }

    @Override
    public void harm() {

    }

    @Override
    public void attack() {

    }
}
