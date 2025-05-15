package io.github.simonreilich.objects.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.UpdateType;

public class Enemy extends Entity {
    private int i = 0;

    public Enemy(int x, int y) {
        super(new Sprite(new Texture("sprites/enemy.png")), x, y);
        System.out.println("Help, I am at x = " + getX() + ", y = " + getY());
    }

    @Override
    public void update(UpdateType type, float delta) {
        System.out.println("Updating enemy");
        switch (type) {
            case PlayerMove:
                i = (i + 1) % 4;
                if (i == 0) {
                    setPosX(getPosX() + 1);
                } else if (i == 1) {
                    setPosY(getPosY() + 1);
                } else if (i == 2) {
                    setPosX(getPosX() - 1);
                } else if (i == 3) {
                    setPosY(getPosY() - 1);
                }
        }
    }
}
