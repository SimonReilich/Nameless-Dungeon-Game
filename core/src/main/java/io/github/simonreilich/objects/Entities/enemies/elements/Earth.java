package io.github.simonreilich.objects.Entities.enemies.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.Consts;
import io.github.simonreilich.graph.RoomNode;
import io.github.simonreilich.objects.Entities.enemies.Enemy;
import io.github.simonreilich.screens.MapView;

public class Earth extends Enemy {

    public Earth(int x, int y, MapView mapView, RoomNode room) {
        super(new Sprite(new Texture("sprites/entities/elements/earth/elemental_earth.png")) , x, y, mapView, room);

        if (Math.random() < Consts.smallProb) {
            this.getTexture().dispose();
            this.set(new Sprite(new Texture("sprites/entities/elements/earth/elemental_earth_small.png")));
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
