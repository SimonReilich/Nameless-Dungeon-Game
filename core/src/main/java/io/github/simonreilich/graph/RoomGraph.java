package io.github.simonreilich.graph;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.simonreilich.objects.Map;

import java.util.ArrayList;
import java.util.List;


 //Repräsentiert den Graph aus Raum-Knoten und Verbindungen (Kanten)

public class RoomGraph {
    private List<RoomNode> rooms;

    public RoomGraph() {
        rooms = new ArrayList<>();
        generateGraph(); // Räume + Verbindungen anlegen
    }


     // Erstellt Räume und verbindet sie
    private void generateGraph() {
        RoomNode a = new RoomNode(new Map("maps/testMap.tmx"));
        RoomNode b = new RoomNode(new Map("maps/testMap.tmx"));
        RoomNode c = new RoomNode(new Map("maps/testMap.tmx"));

        a.addNeighbor(b); // A → B
        b.addNeighbor(c); // B → C
        c.addNeighbor(a); // C → A

        rooms.add(a);
        rooms.add(b);
        rooms.add(c);
    }

    public List<RoomNode> getRooms() {
        return rooms;
    }

}
