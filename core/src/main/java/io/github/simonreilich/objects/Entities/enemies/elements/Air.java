package io.github.simonreilich.objects.Entities.enemies.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.util.Consts;
import io.github.simonreilich.graph.RoomNode;
import io.github.simonreilich.objects.Entities.enemies.Enemy;
import io.github.simonreilich.screens.MapScreen;

public class Air extends Enemy {

    public Air(int x, int y, MapScreen mapScreen, RoomNode room) {
        super(new Sprite(new Texture("sprites/entities/elements/air/elemental_air.png")) , x, y, mapScreen, room);

        if (Math.random() < Consts.smallProb) {
            this.getTexture().dispose();
            this.set(new Sprite(new Texture("sprites/entities/elements/air/elemental_air_small.png")));
            this.setPosX(x);
            this.setPosY(y);
        }
    }

    @Override
    public void move() {

    }

    @Override
    public void harm() {

    }

    @Override
    public void attack() {

    }
}
