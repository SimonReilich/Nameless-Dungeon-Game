package io.github.simonreilich.objects.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.UpdateType;
import io.github.simonreilich.objects.Entities.enemies.Blob;
import io.github.simonreilich.objects.Entities.enemies.SlowBlob;

public abstract class Enemy extends Entity {

    public Enemy(Sprite sprite, int x, int y) {
        super(sprite, x, y);
    }

    public static Enemy spawn(int spawnID, int x, int y) {
        if (Math.random() < 0.5) {
            return new Blob(x, y);
        } else {
            return new SlowBlob(x, y);
        }
    }

    @Override
    public void update(UpdateType type, float delta) {
        switch (type) {
            case PlayerMove:
                move();
            case PlayerAttack:
                harm();
        }
    }

    public abstract void move();

    public abstract void harm();

    public abstract void attack();
}
