package io.github.simonreilich.rooms;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class LazyMap {
    // loads a this.map lazily, i.e. at first, only the name of the file is saved
    // and the this.map itself is only then loaded in, when it is needed

    //private static final String[] ASSETS = initAssets();
    private static final String[] ASSETS = new String[]{
        "maps/map01.tmx",
        "maps/map02.tmx",
        "maps/map03.tmx"
    };
    private final String source;
    private TiledMap map;

    public LazyMap() {
        // random this.map
        int i = (int) (Math.random() * ASSETS.length);
        this.source = ASSETS[i];
    }

    private static String[] initAssets() {
        try {
            BufferedReader r = new BufferedReader(new FileReader("assets/assets.txt"));
            return r.lines().filter(s -> !s.contains("template") && s.startsWith("maps/") && s.endsWith(".tmx")).toArray(String[]::new);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void init() {
        if (this.map == null) {
            this.map = new TmxMapLoader().load(this.source);
        }
    }

    public TiledMap getMap() {
        init();
        return this.map;
    }

    public MapLayers getLayers() {
        init();
        return this.map.getLayers();
    }

    public MapProperties getMapProperties(int x, int y) {
        init();
        MapProperties props = new MapProperties();
        for (int i = 0; i < this.map.getLayers().size(); i++) {
            TiledMapTileLayer.Cell cell = ((TiledMapTileLayer) this.map.getLayers().get(i)).getCell(x, y);
            if (cell != null) {
                props.putAll(cell.getTile().getProperties());
            }
        }
        return props;
    }

    public void dispose() {
        if (this.map != null) {
            this.map.dispose();
        }
    }
}
