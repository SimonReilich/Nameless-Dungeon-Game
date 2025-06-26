package io.github.simonreilich.graph;

import io.github.simonreilich.objects.Drawable;
import io.github.simonreilich.objects.Entities.Enemy;
import io.github.simonreilich.screens.MapView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


//Repräsentiert einen Raum im Spiel als Knoten im Graphen.
public class RoomNode {

    private List<RoomNode> neighbors;
    private List<Drawable> drawables;
    public final LazyMap map;

    public RoomNode(LazyMap map) {
        this.neighbors = new ArrayList<>();
        this.map = map;
    }

    public void addNeighbor(RoomNode neighbor) {
        neighbors.add(neighbor);
    }

    public RoomNode getNeighbor(int index) {
        while (neighbors.size() <= index) {
            neighbors.add(new RoomNode(new LazyMap()));
        }
        return neighbors.get(index);
    }

    public List<RoomNode> getNeighbors() {
        return neighbors;
    }

    public void setNeighbor(RoomNode neighbor, int i) {
        while (neighbors.size() <= i) {
            neighbors.add(new RoomNode(new LazyMap()));
        }
        neighbors.set(i, neighbor);
    }

    public void initDrawables(MapView mapView) {
        if (drawables == null) {
            drawables = new ArrayList<>();

            for (int x = 0; x < 30; x++) {
                for (int y = 0; y < 20; y++) {
                    if (this.map.getMapProperties(x, y).containsKey("spawn")) {
                        drawables.add(Enemy.spawn(this.map.getMapProperties(x, y).get("spawn", Integer.class), x, y, mapView, this));
                    }
                }
            }
        }
    }

    public List<Drawable> getDrawables() {
        return drawables;
    }

    public void removeDrawable(Drawable drawable) {
        drawables.remove(drawable);
    }
}
