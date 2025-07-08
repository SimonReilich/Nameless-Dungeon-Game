package io.github.simonreilich.objects.Entities.enemies.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.objects.Entities.enemies.Enemy;
import io.github.simonreilich.objects.Items.Coin;
import io.github.simonreilich.rooms.RoomNode;
import io.github.simonreilich.screens.MapScreen;
import io.github.simonreilich.util.Consts;

public class Gold extends Enemy {

    private int i;

    public Gold(int x, int y, MapScreen mapScreen, RoomNode room) {
        super(new Sprite(new Texture("sprites/entities/elements/gold/elemental_gold.png")), x, y, mapScreen, room);

        if (Math.random() < Consts.smallProb) {
            this.getTexture().dispose();
            this.set(new Sprite(new Texture("sprites/entities/elements/gold/elemental_gold_small.png")));
            this.setPosX(x);
            this.setPosY(y);
        }

        i = 0;
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
        Coin coin = new Coin((int) getX(), (int) getY(), mapScreen, room);
        super.room.getDrawables().add(coin);
        super.mapScreen().enqueue(coin);
    }

    @Override
    public void attack() {

    }
}
