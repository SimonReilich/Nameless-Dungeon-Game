package io.github.simonreilich.graph;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
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
        map = new TmxMapLoader().load(source);
    }

    public TiledMap getMap() {
        if (map == null) {
            this.init();
        }
        return map;
    }

    public MapLayers getLayers() {
        if (map == null) {
            this.init();
        }
        return map.getLayers();
    }

    public void dispose() {
        if (map != null) {
            map.dispose();
        }
    }

    private static String[] initAssets() {
        try {
            BufferedReader r = new BufferedReader(new FileReader("assets/assets.txt"));
            return r.lines().filter(s -> s.startsWith("maps/") && s.endsWith(".tmx")).toArray(String[]::new);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
