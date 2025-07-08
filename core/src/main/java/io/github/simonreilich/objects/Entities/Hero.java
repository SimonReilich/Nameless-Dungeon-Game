package io.github.simonreilich.objects.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.screens.MapScreen;
import io.github.simonreilich.util.UpdateType;
import io.github.simonreilich.objects.Drawable;
import io.github.simonreilich.objects.Entities.enemies.Enemy;

public class Hero extends Entity implements Drawable {

    public boolean alive;
    private int speed;

    public Hero(MapScreen view) {
        super(new Sprite(new Texture("sprites/heros/basic.png")), 15, 10, view);
        this.alive = true;
        this.speed = 1;
    }

    public void up() {
        if (mapScreen.inBounds(getPosX(), getPosY() + speed) && !mapScreen.occupied(getPosX(), getPosY() + speed) && destinationX == getX() && destinationY == getY() && alive) {
            super.up(speed);
            mapScreen.attack();
            speed = 1;
            mapScreen.updateAll(UpdateType.PlayerMove, Gdx.graphics.getDeltaTime());
        }
    }

    public void left() {
        if (mapScreen.inBounds(getPosX() - speed, getPosY()) && !mapScreen.occupied(getPosX() - speed, getPosY()) && destinationX == getX() && destinationY == getY()  && alive) {
            super.left(speed);
            mapScreen.attack();
            speed = 1;
            mapScreen.updateAll(UpdateType.PlayerMove, Gdx.graphics.getDeltaTime());
        }
    }

    public void down() {
        if (mapScreen.inBounds(getPosX(), getPosY() - speed) && !mapScreen.occupied(getPosX(), getPosY() - speed) && destinationX == getX() && destinationY == getY() && alive) {
            super.down(speed);
            mapScreen.attack();
            speed = 1;
            mapScreen.updateAll(UpdateType.PlayerMove, Gdx.graphics.getDeltaTime());
        }
    }

    public void right() {
        if (mapScreen.inBounds(getPosX() + speed, getPosY()) && !mapScreen.occupied(getPosX() + speed, getPosY()) && destinationX == getX() && destinationY == getY() && alive) {
            super.right(speed);
            mapScreen.attack();
            speed = 1;
            mapScreen.updateAll(UpdateType.PlayerMove, Gdx.graphics.getDeltaTime());
        }
    }

    public void increaseSpeed() {
        speed = 3;
    }

    public void decreaseSpeed() {
        speed = 1;
    }

    public void interact(Entity entity) {
        if (entity instanceof Enemy && alive) {
            alive = false;
            this.setTexture(new Texture("sprites/misc/hero_dead.png"));
        }
    }

    public void update(UpdateType type, float delta) {
        return;
    }
}
