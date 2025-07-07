package io.github.simonreilich.objects.Entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import io.github.simonreilich.graph.RoomNode;
import io.github.simonreilich.screens.MapScreen;

public class DarkKnight extends Enemy {

    public DarkKnight(int x, int y, MapScreen mapScreen, RoomNode room) {
        super(new Sprite(new Texture("sprites/entities/dark_knight.png")), x, y, mapScreen, room);
    }

    @Override
    public void move() {
        Vector2 player = super.mapScreen().getHeroDestination();

        // A* Algorithm
    }

    @Override
    public void harm() {
        super.mapScreen().dequeue(this);
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
