package io.github.simonreilich.objects.Entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import io.github.simonreilich.rooms.RoomNode;
import io.github.simonreilich.screens.MapScreen;
import io.github.simonreilich.util.PrioQueue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DarkKnight extends Enemy {

    public DarkKnight(int x, int y, MapScreen mapScreen, RoomNode room) {
        super(new Sprite(new Texture("sprites/entities/dark_knight.png")), x, y, mapScreen, room);
    }

    @Override
    public void move() {
        // DarkKnight will chase the player, using A* for pathfinding
        Vector2 heroPos = mapScreen.getHeroDestination();
        Vector2 step = pathfinding(heroPos);

        if (step.x > this.getPosX()) {
            right(1);
        } else if (step.x < this.getPosX()) {
            left(1);
        } else if (step.y > this.getPosY()) {
            up(1);
        } else if (step.y < this.getPosY()) {
            down(1);
        }
    }

    private Vector2 pathfinding(Vector2 heroPos) {

        // openList is the list of all fields, that still have unvisited neighbours
        // the priority of a field corresponds to the estimated cost of a path over this field
        PrioQueue openList = new PrioQueue();
        // closedList is the Set of all fields, that have been completed
        Set<Vector2> closedList = new HashSet<>();
        // pred saves the predecessor of a field for the shortest path
        Map<Vector2, Vector2> pred = new HashMap<>();
        // cost saves the total cost of a path up to a field
        Map<Vector2, Float> cost = new HashMap<>();

        openList.enqueue(new Vector2(getPosX(), getPosY()), 0);
        cost.put(new Vector2(getPosX(), getPosY()), 0.0f);

        while (!openList.isEmpty()) {
            Vector2 current = openList.removeMin();
            if (current.x == heroPos.x && current.y == heroPos.y) {
                return nextPos(new Vector2(getPosX(), getPosY()), heroPos, pred);
            }
            closedList.add(current);
            expandNode(current, new Vector2(current.x + 1, current.y), heroPos, openList, closedList, pred, cost);
            expandNode(current, new Vector2(current.x - 1, current.y), heroPos, openList, closedList, pred, cost);
            expandNode(current, new Vector2(current.x, current.y + 1), heroPos, openList, closedList, pred, cost);
            expandNode(current, new Vector2(current.x, current.y - 1), heroPos, openList, closedList, pred, cost);
        }
        return new Vector2(getPosX(), getPosY());
    }

    private Vector2 nextPos(Vector2 current, Vector2 heroPos, Map<Vector2, Vector2> pred) {
        Vector2 last = heroPos;
        while (!(pred.get(last).x == current.x && pred.get(last).y == current.y)) {
            last = pred.get(last);
        }
        return last;
    }

    private void expandNode(Vector2 current, Vector2 suc, Vector2 dest, PrioQueue openList, Set<Vector2> closedList, Map<Vector2, Vector2> pred, Map<Vector2, Float> cost) {
        if (closedList.contains(suc) || !mapScreen().free((int) suc.x, (int) suc.y)) {
            return;
        }

        float tentative_g = cost.get(current) + 1;
        if (openList.contains(suc) && tentative_g > openList.getPriority(suc)) {
            return;
        }

        pred.put(suc, current);
        cost.put(suc, tentative_g);
        if (openList.contains(suc)) {
            openList.decreasePriority(suc, tentative_g + dist(suc, dest));
        } else {
            openList.enqueue(suc, tentative_g + dist(suc, dest));
        }
    }

    private float dist(Vector2 p1, Vector2 p2) {
        return (float) Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
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
}
