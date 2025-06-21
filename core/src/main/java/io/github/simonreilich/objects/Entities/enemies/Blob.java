package io.github.simonreilich.objects.Entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.objects.Entities.Enemy;

public class Blob extends Enemy {

    private int i = 0;

    public Blob(int x, int y) {
        super(new Sprite(new Texture("sprites/blob/01.png")), x, y);
    }

    @Override
    public void move() {
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

    }

    @Override
    public void attack() {

    }
}
