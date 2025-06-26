package io.github.simonreilich.objects.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.UpdateType;
import io.github.simonreilich.graph.RoomNode;
import io.github.simonreilich.objects.Entities.enemies.Blob;
import io.github.simonreilich.objects.Entities.enemies.Ghost;
import io.github.simonreilich.objects.Entities.enemies.SlowBlob;
import io.github.simonreilich.screens.MapView;

public abstract class Enemy extends Entity {

    protected RoomNode room;

    public Enemy(Sprite sprite, int x, int y, MapView mapView, RoomNode room) {
        super(sprite, x, y, mapView);
        this.room = room;
    }

    public static Enemy spawn(int spawnID, int x, int y, MapView mapView, RoomNode room) {
        if (Math.random() < 0.5) {
            return new Blob(x, y, mapView, room);
        } else if (Math.random() < 0.5) {
            return new Ghost(x, y, mapView, room);
        } else {
            return new SlowBlob(x, y, mapView, room);
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
