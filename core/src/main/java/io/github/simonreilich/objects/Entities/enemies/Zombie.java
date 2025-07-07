package io.github.simonreilich.objects.Entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.util.Consts;
import io.github.simonreilich.graph.RoomNode;
import io.github.simonreilich.screens.MapScreen;

public class Zombie extends Enemy {

    public Zombie(int x, int y, MapScreen mapScreen, RoomNode room) {
        super(new Sprite(new Texture("sprites/entities/zombies/zombie.png")), x, y, mapScreen, room);

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
        int relPlayerX = (int) (mapScreen.getHeroDestination().x - getPosX());
        int relPlayerY = (int) (mapScreen.getHeroDestination().y - getPosY());
        if (Math.random() < Consts.zombieActiveProb && Math.sqrt(Math.pow(relPlayerX, 2) + Math.pow(relPlayerY, 2)) <= Consts.zombieRange) {
            if (Math.abs(relPlayerX) > Math.abs(relPlayerY)) {
                if (relPlayerX > 0 && mapScreen().free(getPosX() + 1, getPosY())) {
                    right(1);
                } else if (relPlayerX < 0 && mapScreen().free(getPosX() - 1, getPosY())) {
                    left(1);
                }
            } else {
                if (relPlayerY > 0 && mapScreen().free(getPosX(), getPosY() + 1)) {
                    up(1);
                } else if (relPlayerY < 0 && mapScreen().free(getPosX(), getPosY() - 1)) {
                    down(1);
                }
            }
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
