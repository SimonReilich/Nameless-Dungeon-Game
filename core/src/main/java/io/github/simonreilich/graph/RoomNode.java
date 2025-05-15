package io.github.simonreilich.graph;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.simonreilich.objects.Map;

import java.util.ArrayList;
import java.util.List;


 //Repräsentiert einen Raum im Spiel als Knoten im Graphen.
public class RoomNode {

    private List<RoomNode> neighbors;
    private Map map;

    public RoomNode(Map map) {
        this.neighbors = new ArrayList<>();
        this.map = map;
    }

    public void addNeighbor(RoomNode neighbor) {
        neighbors.add(neighbor);
    }

    public List<RoomNode> getNeighbors() {
        return neighbors;
    }
}
