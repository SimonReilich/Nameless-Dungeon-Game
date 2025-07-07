package io.github.simonreilich.objects.Entities.enemies.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.util.Consts;
import io.github.simonreilich.rooms.RoomNode;
import io.github.simonreilich.objects.Entities.enemies.Enemy;
import io.github.simonreilich.screens.MapScreen;

public class Goo extends Enemy {

    private int originX, originY;

    public Goo(int x, int y, MapScreen mapScreen, RoomNode room) {
        super(new Sprite(new Texture("sprites/entities/elements/goo/elemental_goo.png")) , x, y, mapScreen, room);

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
        if (direction < 0.25 && mapScreen().free(this.getPosX(), this.getPosY() + 1)) {
            up(1);
        } else if (direction < 0.50 && mapScreen().free(this.getPosX(), this.getPosY() - 1)) {
            down(1);
        } else if (direction < 0.75 && mapScreen().free(this.getPosX() + 1, this.getPosY())) {
            right(1);
        } else if (mapScreen().free(this.getPosX() - 1, this.getPosY())) {
            left(1);
        }
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
