package io.github.simonreilich.objects.Entities.enemies.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.Consts;
import io.github.simonreilich.graph.RoomNode;
import io.github.simonreilich.objects.Entities.enemies.Enemy;
import io.github.simonreilich.screens.MapView;

public class Goo extends Enemy {

    private int originX, originY;

    public Goo(int x, int y, MapView mapView, RoomNode room) {
        super(new Sprite(new Texture("sprites/entities/elements/goo/elemental_goo.png")) , x, y, mapView, room);

        if (Math.random() < Consts.smallProb) {
            this.getTexture().dispose();
            this.set(new Sprite(new Texture("sprites/entities/elements/goo/elemental_goo_small.png")));
            this.setPosX(x);
            this.setPosY(y);
        }

        originX = x;
        originY = y;
    }

    @Override
    public void move() {
        double direction = Math.random();
        if (direction < 0.25 && mapView().free(this.getPosX(), this.getPosY() + 1)) {
            up(1);
        } else if (direction < 0.50 && mapView().free(this.getPosX(), this.getPosY() - 1)) {
            down(1);
        } else if (direction < 0.75 && mapView().free(this.getPosX() + 1, this.getPosY())) {
            right(1);
        } else if (mapView().free(this.getPosX() - 1, this.getPosY())) {
            left(1);
        }
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
