package io.github.simonreilich.objects.Entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.graph.RoomNode;
import io.github.simonreilich.screens.MapView;

public class DarkKnight extends Enemy {

    public DarkKnight(int x, int y, MapView mapView, RoomNode room) {
        super(new Sprite(new Texture("sprites/entities/dark_knight.png")), x, y, mapView, room);
    }

    @Override
    public void move() {
        int playerX = mapView.hero.getDestinationX();
        int playerY = mapView.hero.getDestinationY();

        // A* Algorithm
    }

    @Override
    public void harm() {
        super.mapView().dequeue(this);
        super.room.removeDrawable(this);
        this.dispose();
    }

    @Override
    public void attack() {

    }

    private double metric(int x, int y, int playerX, int playerY) {
        return Math.sqrt(Math.pow(x - playerX, 2) + Math.pow(y - playerY, 2));
    }
}
