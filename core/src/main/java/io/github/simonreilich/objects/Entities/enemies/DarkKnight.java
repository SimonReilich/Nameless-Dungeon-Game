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

        // workingFields is the list of all fields, that still have unvisited neighbours
        // the priority of a field corresponds to the estimated cost of a path over this field
        PrioQueue workingFields = new PrioQueue();
        // completedFields is the Set of all fields, that have been completed
        Set<Vector2> completedFields = new HashSet<>();
        // pred saves the predecessor of a field for the shortest path
        Map<Vector2, Vector2> pred = new HashMap<>();
        // cost saves the total cost of a path up to a field
        Map<Vector2, Float> cost = new HashMap<>();

        // we start working at the origin, this field has a cost of 0 for reaching it
        workingFields.enqueue(new Vector2(getPosX(), getPosY()), 0);
        cost.put(new Vector2(getPosX(), getPosY()), 0.0f);

        // while we have fields to work on, we do
        while (!workingFields.isEmpty()) {
            // we first work on the field, over which the shortest path will likely go
            Vector2 current = workingFields.removeMin();
            if (current.x == heroPos.x && current.y == heroPos.y) {
                // if this field is equal to the destination, we have found the shortest path
                return nextPos(new Vector2(getPosX(), getPosY()), heroPos, pred);
            }
            // we have now completed the work on the current field and move on to its neighbors
            completedFields.add(current);
            expandNode(current, new Vector2(current.x + 1, current.y), heroPos, workingFields, completedFields, pred, cost);
            expandNode(current, new Vector2(current.x - 1, current.y), heroPos, workingFields, completedFields, pred, cost);
            expandNode(current, new Vector2(current.x, current.y + 1), heroPos, workingFields, completedFields, pred, cost);
            expandNode(current, new Vector2(current.x, current.y - 1), heroPos, workingFields, completedFields, pred, cost);
        }
        return new Vector2(getPosX(), getPosY());
    }

    private Vector2 nextPos(Vector2 current, Vector2 heroPos, Map<Vector2, Vector2> pred) {
        // next pos goes back from the destination to the origin and returns the last field before origin
        Vector2 last = heroPos;
        while (!(pred.get(last).x == current.x && pred.get(last).y == current.y)) {
            last = pred.get(last);
        }
        return last;
    }

    private void expandNode(Vector2 current, Vector2 suc, Vector2 dest, PrioQueue openList, Set<Vector2> closedList, Map<Vector2, Vector2> pred, Map<Vector2, Float> cost) {
        if (closedList.contains(suc) || !mapScreen().free((int) suc.x, (int) suc.y)) {
            // if we have already worked on this field, or it is not walkable, we do nothing
            return;
        }

        // the cost of reaching the new field over current is the cost of reaching the current field plus one step
        float gDash = cost.get(current) + 1;
        if (openList.contains(suc) && gDash > openList.getPriority(suc)) {
            // if we already found a better path to this field, we do not override it
            return;
        }

        // if the program reaches this point, the path over current is the shortest path to suc
        // so we save current as the predecessor of suc and save the costs for suc
        pred.put(suc, current);
        cost.put(suc, gDash);
        // based on this cost, we adjust the guess for the cost of the shortest path to dest over suc
        if (openList.contains(suc)) {
            openList.decreasePriority(suc, gDash + dist(suc, dest));
        } else {
            openList.enqueue(suc, gDash + dist(suc, dest));
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
