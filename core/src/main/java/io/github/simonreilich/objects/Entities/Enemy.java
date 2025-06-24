package io.github.simonreilich.objects.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.UpdateType;
import io.github.simonreilich.objects.Entities.enemies.Blob;
import io.github.simonreilich.objects.Entities.enemies.Ghost;
import io.github.simonreilich.objects.Entities.enemies.SlowBlob;
import io.github.simonreilich.screens.MapView;

public abstract class Enemy extends Entity {

    public Enemy(Sprite sprite, int x, int y, MapView mapView) {
        super(sprite, x, y, mapView);
    }

    public static Enemy spawn(int spawnID, int x, int y, MapView mapView) {
        if (Math.random() < 0.5) {
            return new Blob(x, y, mapView);
        } else if (Math.random() < 0.5) {
            return new Ghost(x, y, mapView);
        } else {
            return new SlowBlob(x, y, mapView);
        }
    }

    @Override
    public void update(UpdateType type, float delta) {
        switch (type) {
            case PlayerMove:
                move();
                break;
            case PlayerAttack:
                harm();
                break;
        }
    }

    public abstract void move();

    public abstract void harm();

    public abstract void attack();

    protected MapView mapView() {
        return super.mapView;
    }
}
