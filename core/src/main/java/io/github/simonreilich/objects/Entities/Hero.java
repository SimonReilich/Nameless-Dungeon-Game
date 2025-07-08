package io.github.simonreilich.objects.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.objects.Drawable;
import io.github.simonreilich.objects.Entities.enemies.Enemy;
import io.github.simonreilich.screens.MapScreen;
import io.github.simonreilich.util.UpdateType;

public class Hero extends Entity implements Drawable {

    private boolean alive;
    private int speed;

    public Hero(MapScreen view) {
        super(new Sprite(new Texture("sprites/heros/basic.png")), 15, 10, view);
        this.alive = true;
        this.speed = 1;
    }

    public void interact(Entity entity) {
        if (entity instanceof Enemy && this.alive) {
            this.alive = false;
            this.setTexture(new Texture("sprites/misc/hero_dead.png"));
        }
    }

    public void update(UpdateType type, float delta) {
        return;
    }

    public void up() {
        if (mapScreen.inBounds(getPosX(), getPosY() + this.speed) && !mapScreen.occupied(getPosX(), getPosY() + this.speed) && dest.x == getX() && dest.y == getY() && this.alive) {
            super.up(this.speed);
            mapScreen.attack();
            this.speed = 1;
            mapScreen.updateAll(UpdateType.PlayerMove, Gdx.graphics.getDeltaTime());
        }
    }

    public void left() {
        if (mapScreen.inBounds(getPosX() - this.speed, getPosY()) && !mapScreen.occupied(getPosX() - this.speed, getPosY()) && dest.x == getX() && dest.y == getY() && this.alive) {
            super.left(this.speed);
            mapScreen.attack();
            this.speed = 1;
            mapScreen.updateAll(UpdateType.PlayerMove, Gdx.graphics.getDeltaTime());
        }
    }

    public void down() {
        if (mapScreen.inBounds(getPosX(), getPosY() - this.speed) && !mapScreen.occupied(getPosX(), getPosY() - this.speed) && dest.x == getX() && dest.y == getY() && this.alive) {
            super.down(this.speed);
            mapScreen.attack();
            this.speed = 1;
            mapScreen.updateAll(UpdateType.PlayerMove, Gdx.graphics.getDeltaTime());
        }
    }

    public void right() {
        if (mapScreen.inBounds(getPosX() + this.speed, getPosY()) && !mapScreen.occupied(getPosX() + this.speed, getPosY()) && dest.x == getX() && dest.y == getY() && this.alive) {
            super.right(this.speed);
            mapScreen.attack();
            this.speed = 1;
            mapScreen.updateAll(UpdateType.PlayerMove, Gdx.graphics.getDeltaTime());
        }
    }

    public void increaseSpeed() {
        this.speed = 3;
    }

    public void decreaseSpeed() {
        this.speed = 1;
    }

    public boolean isAlive() {
        return this.alive;
    }
}
