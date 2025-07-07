package io.github.simonreilich.objects.Entities.enemies.orcs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.graph.RoomNode;
import io.github.simonreilich.objects.Entities.enemies.Enemy;
import io.github.simonreilich.screens.MapScreen;

public class Goblin extends Enemy {

    public Goblin(int x, int y, MapScreen mapScreen, RoomNode room) {
        super(new Sprite(new Texture("sprites/entities/orcs/goblin.png")), x, y, mapScreen, room);
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
