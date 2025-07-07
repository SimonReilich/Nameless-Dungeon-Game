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

    private TiledMap map;
    private final String source;
    static final String[] ASSETS = initAssets();

    public LazyMap(String source) {
        this.source = source;
    }

    public LazyMap() {
        // random map
        int i = (int) (Math.random() * ASSETS.length);
        this.source = ASSETS[i];
    }

    public void init() {
        if (map == null) {
            map = new TmxMapLoader().load(source);
        }
    }

    public TiledMap getMap() {
        init();
        return map;
    }

    public MapLayers getLayers() {
        init();
        return map.getLayers();
    }

    public MapProperties getMapProperties(int x, int y) {
        init();
        MapProperties props = new MapProperties();
        for (int i = 0; i < map.getLayers().size(); i++) {
            TiledMapTileLayer.Cell cell = ((TiledMapTileLayer) map.getLayers().get(i)).getCell(x, y);
            if (cell != null) {
                props.putAll(cell.getTile().getProperties());
            }
        }
        return props;
    }

    public void dispose() {
        if (map != null) {
            map.dispose();
        }
    }

    private static String[] initAssets() {
        try {
            BufferedReader r = new BufferedReader(new FileReader("assets/assets.txt"));
            return r.lines().filter(s -> !s.contains("template") && s.startsWith("maps/") && s.endsWith(".tmx")).toArray(String[]::new);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
