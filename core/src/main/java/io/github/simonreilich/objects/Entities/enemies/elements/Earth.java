package io.github.simonreilich.objects.Entities.enemies.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.util.Consts;
import io.github.simonreilich.rooms.RoomNode;
import io.github.simonreilich.objects.Entities.enemies.Enemy;
import io.github.simonreilich.screens.MapScreen;

public class Earth extends Enemy {

    private int i;

    public Earth(int x, int y, MapScreen mapScreen, RoomNode room) {
        super(new Sprite(new Texture("sprites/entities/elements/earth/elemental_earth.png")) , x, y, mapScreen, room);

        if (Math.random() < Consts.smallProb) {
            this.getTexture().dispose();
            this.set(new Sprite(new Texture("sprites/entities/elements/earth/elemental_earth_small.png")));
            this.setPosX(x);
            this.setPosY(y);
        }
        i = -1;
    }

    @Override
    public void move() {
        i = (i + 1) % 16;
        if (i == 0) {
            super.up(1);
        } else if (i == 1) {
            super.up(1);
        } else if (i == 2) {
            super.down(1);
        } else if (i == 3) {
            super.down(1);
        } else if (i == 4) {
            super.right(1);
        }  else if (i == 5) {
            super.right(1);
        }  else if (i == 6) {
            super.left(1);
        }   else if (i == 7) {
            super.left(1);
        }   else if (i == 8) {
            super.down(1);
        }   else if (i == 9) {
            super.down(1);
        }   else if (i == 10) {
            super.up(1);
        }   else if (i == 11) {
            super.up(1);
        }    else if (i == 12) {
            super.left(1);
        }    else if (i == 13) {
            super.left(1);
        } else if (i == 14) {
            super.right(1);
        }   else if (i == 15) {
            super.right(1);
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
