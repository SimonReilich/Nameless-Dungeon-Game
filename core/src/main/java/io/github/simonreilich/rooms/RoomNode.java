package io.github.simonreilich.rooms;

import io.github.simonreilich.objects.Drawable;
import io.github.simonreilich.objects.Entities.enemies.Enemy;
import io.github.simonreilich.objects.Items.Item;
import io.github.simonreilich.screens.MapScreen;

import java.util.ArrayList;
import java.util.List;

public class RoomNode {
    // represents a room as a node in a graph with its neighbors
    // and a list of all drawables belonging to the room

    public final LazyMap map;
    private List<RoomNode> neighbors;
    private List<Drawable> drawables;

    public RoomNode(LazyMap map) {
        this.neighbors = new ArrayList<>();
        this.map = map;
    }

    public void addNeighbor(RoomNode neighbor) {
        this.neighbors.add(neighbor);
    }

    public RoomNode getNeighbor(int index) {
        while (neighbors.size() <= index) {
            this.neighbors.add(new RoomNode(new LazyMap()));
        }
        return this.neighbors.get(index);
    }

    public void setNeighbor(RoomNode neighbor, int i) {
        while (neighbors.size() <= i) {
            this.neighbors.add(new RoomNode(new LazyMap()));
        }
        this.neighbors.set(i, neighbor);
    }

    public void initDrawables(MapScreen mapScreen) {
        if (drawables == null) {
            this.drawables = new ArrayList<>();

            for (int x = 0; x < 30; x++) {
                for (int y = 0; y < 20; y++) {
                    if (this.map.getMapProperties(x, y).containsKey("spawn")) {
                        this.drawables.add(Enemy.spawn(this.map.getMapProperties(x, y).get("spawn", Integer.class), x, y, mapScreen, this));
                    }
                    if (this.map.getMapProperties(x, y).containsKey("item")) {
                        this.drawables.add(Item.spawn(this.map.getMapProperties(x, y).get("item", Integer.class), x, y, mapScreen, this));
                    }
                }
            }
        }
    }

    public List<Drawable> getDrawables() {
        return this.drawables;
    }

    public void removeDrawable(Drawable drawable) {
        this.drawables.remove(drawable);
    }
}
