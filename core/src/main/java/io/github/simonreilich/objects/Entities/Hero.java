package io.github.simonreilich.objects.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.util.UpdateType;
import io.github.simonreilich.objects.Drawable;
import io.github.simonreilich.objects.Entities.enemies.Enemy;
import io.github.simonreilich.screens.MapView;

public class Hero extends Entity implements Drawable {

    public boolean alive;

    public Hero(MapView view) {
        super(new Sprite(new Texture("sprites/heros/basic.png")), 15, 10, view);
        this.alive = true;
    }

    public void up() {
        if (mapView.inBounds(getPosX(), getPosY() + 1) && !mapView.occupied(getPosX(), getPosY() + 1) && destinationX == getX() && destinationY == getY() && alive) {
            super.up(1);
            mapView.attack();
            mapView.updateAll(UpdateType.PlayerMove, Gdx.graphics.getDeltaTime());
        }
    }

    public void left() {
        if (mapView.inBounds(getPosX() - 1, getPosY()) && !mapView.occupied(getPosX() - 1, getPosY()) && destinationX == getX() && destinationY == getY()  && alive) {
            super.left(1);
            mapView.attack();
            mapView.updateAll(UpdateType.PlayerMove, Gdx.graphics.getDeltaTime());
        }
    }

    public void down() {
        if (mapView.inBounds(getPosX(), getPosY() - 1) && !mapView.occupied(getPosX(), getPosY() - 1) && destinationX == getX() && destinationY == getY() && alive) {
            super.down(1);
            mapView.attack();
            mapView.updateAll(UpdateType.PlayerMove, Gdx.graphics.getDeltaTime());
        }
    }

    public void right() {
        if (mapView.inBounds(getPosX() + 1, getPosY()) && !mapView.occupied(getPosX() + 1, getPosY()) && destinationX == getX() && destinationY == getY() && alive) {
            super.right(1);
            mapView.attack();
            mapView.updateAll(UpdateType.PlayerMove, Gdx.graphics.getDeltaTime());
        }
    }

    public void interact(Entity entity) {
        if (entity instanceof Enemy && alive) {
            alive = false;
            this.getTexture().dispose();
            this.setTexture(new Texture("sprites/misc/hero_dead.png"));
        }
    }

    public void update(UpdateType type, float delta) {
        return;
    }
}
