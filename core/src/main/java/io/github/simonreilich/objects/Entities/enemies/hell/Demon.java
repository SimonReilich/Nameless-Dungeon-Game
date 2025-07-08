package io.github.simonreilich.objects.Entities.enemies.hell;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.objects.Entities.enemies.Enemy;
import io.github.simonreilich.rooms.RoomNode;
import io.github.simonreilich.screens.MapScreen;

public class Demon extends Enemy {

    private int i;

    public Demon(int x, int y, MapScreen mapScreen, RoomNode room) {
        super(new Sprite(new Texture("sprites/entities/hell/demon.png")), x, y, mapScreen, room);
        i = 0;
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void move() {
        // WIP
        if (i == 0) {
            left(1);
        } else if (i == 1) {
            down(1);
        } else if (i == 2) {
            right(1);
        } else if (i == 3) {
            up(1);
        }

        i = (i + 1) % 4;
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
