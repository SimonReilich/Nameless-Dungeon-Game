package io.github.simonreilich.objects.Entities.enemies.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.util.Consts;
import io.github.simonreilich.graph.RoomNode;
import io.github.simonreilich.objects.Entities.enemies.Enemy;
import io.github.simonreilich.screens.MapScreen;

public class Fire extends Enemy {

    private boolean up;

    public Fire(int x, int y, MapScreen mapScreen, RoomNode room) {
        super(new Sprite(new Texture("sprites/entities/elements/fire/elemental_fire.png")) , x, y, mapScreen, room);

        if (Math.random() < Consts.smallProb) {
            this.getTexture().dispose();
            this.set(new Sprite(new Texture("sprites/entities/elements/fire/elemental_fire_small.png")));
            this.setPosX(x);
            this.setPosY(y);
        }

        up = true;
    }

    @Override
    public void move() {
        if (up) {
            up(1);
            up = mapScreen.free(this.getDestinationX(), this.getDestinationY() + 1);
        } else {
            down(1);
            up = !mapScreen.free(this.getDestinationX(), this.getDestinationY() - 1);
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
