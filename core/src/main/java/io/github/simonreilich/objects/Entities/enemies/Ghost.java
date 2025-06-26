package io.github.simonreilich.objects.Entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.graph.RoomNode;
import io.github.simonreilich.objects.Entities.Enemy;
import io.github.simonreilich.screens.MapView;

public class Ghost extends Enemy {

    private int i;

    public Ghost(int x, int y, MapView mapView, RoomNode room) {
        super(new Sprite(new Texture("sprites/enemy_2.png")), x, y, mapView, room);
        i = 0;
    }

    @Override
    public void move() {
        if (i == 0) {
            left(2);
        } else if (i == 1) {
            down(2);
        } else if (i == 2) {
            right(2);
        } else if (i == 3) {
            up(2);
        }

        i = (i + 1) % 4;
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
}
