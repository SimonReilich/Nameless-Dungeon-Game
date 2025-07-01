package io.github.simonreilich.objects.Entities.enemies.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.Consts;
import io.github.simonreilich.graph.RoomNode;
import io.github.simonreilich.objects.Entities.enemies.Enemy;
import io.github.simonreilich.screens.MapView;

public class Water extends Enemy {

    private boolean left;

    public Water(int x, int y, MapView mapView, RoomNode room) {
        super(new Sprite(new Texture("sprites/entities/elements/water/elemental_water.png")) , x, y, mapView, room);

        if (Math.random() < Consts.smallProb) {
            this.getTexture().dispose();
            this.set(new Sprite(new Texture("sprites/entities/elements/water/elemental_water_small.png")));
            this.setPosX(x);
            this.setPosY(y);
        }

        left = true;
    }

    @Override
    public void move() {
        if (left) {
            left(1);
            left = mapView.free(this.getDestinationX() - 1, this.getDestinationY());
        } else {
            right(1);
            left = !mapView.free(this.getDestinationX() + 1, this.getDestinationY());
        }
    }

    @Override
    public void harm() {

    }

    @Override
    public void attack() {

    }
}
