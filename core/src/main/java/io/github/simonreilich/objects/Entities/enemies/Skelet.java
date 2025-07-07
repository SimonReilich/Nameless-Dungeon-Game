package io.github.simonreilich.objects.Entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.graph.RoomNode;
import io.github.simonreilich.screens.MapScreen;

public class Skelet extends Enemy {

    private int i = 0;

    public Skelet(int x, int y, MapScreen mapScreen, RoomNode room) {
        super(new Sprite(new Texture("sprites/entities/skelet.png")), x, y, mapScreen, room);
    }

    @Override
    public void move() {
        if (i == 0) {
            left(1);
        } else if (i == 2) {
            down(1);
        } else if (i == 4) {
            right(1);
        } else if (i == 8) {
            up(1);
        }

        i = (i + 1) % 10;
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
}
