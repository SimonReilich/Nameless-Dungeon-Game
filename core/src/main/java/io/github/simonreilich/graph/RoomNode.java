package io.github.simonreilich.graph;

import java.util.ArrayList;
import java.util.List;


 //Repräsentiert einen Raum im Spiel als Knoten im Graphen.
public class RoomNode {

    private List<RoomNode> neighbors;
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
}
