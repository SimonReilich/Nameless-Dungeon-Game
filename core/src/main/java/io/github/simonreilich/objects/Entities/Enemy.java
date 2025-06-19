package io.github.simonreilich.objects.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.UpdateType;

public class Enemy extends Entity {
    private int i = -1;

    public Enemy(int x, int y) {
        super(new Sprite(new Texture(randomTexture())), x, y);
    }

    private static String randomTexture() {
        if (Math.random() < 0.7) {
            return "sprites/blob/01.png";
        } else {
            return "sprites/blob/02.png";
        }
    }

    @Override
    public void update(UpdateType type, float delta) {
        switch (type) {
            case PlayerMove:
                i = (i + 1) % 4;
                if (i == 0) {
                    left();
                } else if (i == 1) {
                    down();
                } else if (i == 2) {
                    right();
                } else if (i == 3) {
                    up();
                }
        }
    }
}
