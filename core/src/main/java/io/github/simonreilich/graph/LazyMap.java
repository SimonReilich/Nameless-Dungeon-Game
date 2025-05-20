package io.github.simonreilich.graph;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class LazyMap {

    private TiledMap map;
    private String source;

    public LazyMap(String source) {
        this.source = source;
    }

    public LazyMap() {
        // random map
        this.source = "maps/testMap.tmx";
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
}
