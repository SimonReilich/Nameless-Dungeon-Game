package io.github.simonreilich.objects.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.UpdateType;
import io.github.simonreilich.objects.Drawable;
import io.github.simonreilich.screens.MapView;

public class Player extends Entity implements Drawable {

    private MapView mapView;
    private boolean alive;

    public Player(MapView view) {
        super(new Sprite(new Texture("sprites/player.png")), 15, 10);
        this.mapView = view;
        this.alive = true;
    }

    public void up() {
        if (mapView.inBounds(getPosX(), getPosY() + 1) && destinationX == getX() && destinationY == getY() && alive) {
            super.up();
            mapView.updateAll(UpdateType.PlayerMove, Gdx.graphics.getDeltaTime());
        }
    }

    public void left() {
        if (mapView.inBounds(getPosX() - 1, getPosY()) && destinationX == getX() && destinationY == getY()  && alive) {
            super.left();
            mapView.updateAll(UpdateType.PlayerMove, Gdx.graphics.getDeltaTime());
        }
    }

    public void down() {
        if (mapView.inBounds(getPosX(), getPosY() - 1) && destinationX == getX() && destinationY == getY() && alive) {
            super.down();
            mapView.updateAll(UpdateType.PlayerMove, Gdx.graphics.getDeltaTime());
        }
    }

    public void right() {
        if (mapView.inBounds(getPosX() + 1, getPosY()) && destinationX == getX() && destinationY == getY() && alive) {
            super.right();
            mapView.updateAll(UpdateType.PlayerMove, Gdx.graphics.getDeltaTime());
        }
    }

    public void interact(Entity entity) {
        if (entity instanceof Enemy && alive) {
            alive = false;
            mapView.killed();
        }
    }

    public void update(UpdateType type, float delta) {
        return;
    }
}
