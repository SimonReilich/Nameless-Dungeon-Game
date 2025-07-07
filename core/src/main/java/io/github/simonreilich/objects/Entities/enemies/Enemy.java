package io.github.simonreilich.objects.Entities.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.screens.MapScreen;
import io.github.simonreilich.util.UpdateType;
import io.github.simonreilich.graph.RoomNode;
import io.github.simonreilich.objects.Entities.Entity;
import io.github.simonreilich.objects.Entities.enemies.elements.*;
import io.github.simonreilich.objects.Entities.enemies.hell.Demon;
import io.github.simonreilich.objects.Entities.enemies.hell.Demonolog;
import io.github.simonreilich.objects.Entities.enemies.hell.SmallDemon;
import io.github.simonreilich.objects.Entities.enemies.orcs.Goblin;
import io.github.simonreilich.objects.Entities.enemies.orcs.Ogre;
import io.github.simonreilich.objects.Entities.enemies.orcs.Orc;

public abstract class Enemy extends Entity {

    protected RoomNode room;

    public Enemy(Sprite sprite, int x, int y, MapScreen mapScreen, RoomNode room) {
        super(sprite, x, y, mapScreen);
        this.room = room;
    }

    public static Enemy spawn(int spawnID, int x, int y, MapScreen mapScreen, RoomNode room) {
        switch (spawnID) {
            case 0:
                return new Bat(x, y, mapScreen, room);
            case 1:
                return new Skelet(x, y, mapScreen, room);
            case 2:
                return new Zombie(x, y, mapScreen, room);
            case 3:
                return new Goblin(x, y, mapScreen, room);
            case 4:
                return new Orc(x, y, mapScreen, room);
            case 5:
                return new Ogre(x, y, mapScreen, room);
            case 6:
                return new Water(x, y, mapScreen, room);
            case 7:
                return new Fire(x, y, mapScreen, room);
            case 8:
                return new Earth(x, y, mapScreen, room);
            case 9:
                return new Air(x, y, mapScreen, room);
            case 10:
                return new Plant(x, y, mapScreen, room);
            case 11:
                return new Goo(x, y, mapScreen, room);
            case 12:
                return new Gold(x, y, mapScreen, room);
            case 13:
                return new SmallDemon(x, y, mapScreen, room);
            case 14:
                return new Demon(x, y, mapScreen, room);
            case 15:
                return new Demonolog(x, y, mapScreen, room);
            case 16:
                return new DarkKnight(x, y, mapScreen, room);
            case 17:
                return new Necromancer(x, y, mapScreen, room);
            case 18:
                return new Tentackle(x, y, mapScreen, room);
            default:
                return spawn((int) (Math.random() * 19), x, y, mapScreen, room);
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

    protected MapScreen mapScreen() {
        return super.mapScreen;
    }
}
