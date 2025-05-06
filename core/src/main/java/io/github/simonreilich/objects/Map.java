package io.github.simonreilich.objects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Map implements Drawable {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    public Map(String src) {
        map = new TmxMapLoader().load(src);
        renderer = new OrthogonalTiledMapRenderer(map);
    }


    public TiledMap getMap() {
        return map;
    }

    @Override
    public void draw(OrthographicCamera cam, Batch batch) {
        renderer.setView(cam);
        renderer.render();
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        map = null;
        renderer = null;
    }

    public boolean inBounds(int x, int y) {
        if (map == null) return false;
        TiledMapTileLayer.Cell cell = ((TiledMapTileLayer) map.getLayers().get(0)).getCell(x, y);
        if (cell != null) {
            Object result = cell.getTile().getProperties().get("wakable");
            if (result instanceof Boolean) {
                return (Boolean) result;
            } else {
                return false;
            }
        }
        return false;
    }


}
