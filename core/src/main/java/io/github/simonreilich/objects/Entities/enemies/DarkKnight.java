package io.github.simonreilich.objects.Entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import io.github.simonreilich.rooms.RoomNode;
import io.github.simonreilich.screens.MapScreen;
import io.github.simonreilich.util.PrioQueue;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class DarkKnight extends Enemy {

    public DarkKnight(int x, int y, MapScreen mapScreen, RoomNode room) {
        super(new Sprite(new Texture("sprites/entities/dark_knight.png")), x, y, mapScreen, room);
    }

    @Override
    public void move() {
        // DarkKnight will chase the player, using A* for pathfinding
        Vector2 heroPos = mapScreen.getHeroDestination();
        pathfinding(heroPos);
    }

    private void pathfinding(Vector2 heroPos) {
        PrioQueue openList = new PrioQueue();
        Set<Vector2> closedList = new HashSet<>();

        openList.enqueue(new Vector2(getPosX(), getPosY()), 0);

        while (!openList.isEmpty()) {
            Vector2 current = openList.removeMin();
            if (current.x == heroPos.x && current.y == heroPos.y) {
                System.out.println("Path found");
                return;
            }
            closedList.add(current);
            expandNode(current, openList, closedList);
        }
        System.out.println("No Path found");
    }

    private void expandNode(Vector2 current, PrioQueue openList, Set<Vector2> closedList) {
        Vector2 suc;
        // top
        suc = new Vector2(current.x, current.y + 1);
        if (mapScreen().inBounds((int) suc.x, (int) suc.y) && !closedList.contains(suc)) {

        }
        // right
        suc = new Vector2(current.x + 1, current.y);
        if (mapScreen().inBounds((int) suc.x, (int) suc.y) && !closedList.contains(suc)) {

        }
        // bottom
        suc = new Vector2(current.x, current.y - 1);
        if (mapScreen().inBounds((int) suc.x, (int) suc.y) && !closedList.contains(suc)) {

        }
        // left
        suc = new Vector2(current.x - 1, current.y);
        if (mapScreen().inBounds((int) suc.x, (int) suc.y) && !closedList.contains(suc)) {

        }
    }

    @Override
    public void harm() {
        super.mapScreen().dequeue(this);
        super.room.removeDrawable(this);
        this.dispose();
    }

    @Override
    public void attack() {

    }

    private double metric(int x, int y, int playerX, int playerY) {
        return Math.sqrt(Math.pow(x - playerX, 2) + Math.pow(y - playerY, 2));
    }
}
