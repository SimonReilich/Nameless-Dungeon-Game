package io.github.simonreilich.objects.Entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.graph.RoomNode;
import io.github.simonreilich.screens.MapView;

public class Zombie extends Enemy {

    public Zombie(int x, int y, MapView mapView, RoomNode room) {
        super(new Sprite(new Texture("sprites/entities/zombies/zombie.png")), x, y, mapView, room);

        double variant = Math.random();
        if (variant < 0.2) {
            return;
        } else if (variant < 0.4) {
            this.getTexture().dispose();
            this.set(new Sprite(new Texture("sprites/entities/zombies/zombie_green.png")));
        } else if (variant < 0.6) {
            this.getTexture().dispose();
            this.set(new Sprite(new Texture("sprites/entities/zombies/zombie_short.png")));
        } else if (variant < 0.8) {
            this.getTexture().dispose();
            this.set(new Sprite(new Texture("sprites/entities/zombies/zombie_small.png")));
        } else {
            this.getTexture().dispose();
            this.set(new Sprite(new Texture("sprites/entities/zombies/zombie_tall.png")));
        }
        this.setPosX(x);
        this.setPosY(y);
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
