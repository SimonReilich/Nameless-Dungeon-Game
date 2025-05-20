package io.github.simonreilich.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import io.github.simonreilich.Model;
import io.github.simonreilich.UpdateType;
import io.github.simonreilich.graph.LazyMap;
import io.github.simonreilich.graph.RoomGraph;
import io.github.simonreilich.graph.RoomNode;
import io.github.simonreilich.objects.Drawable;
import io.github.simonreilich.objects.Entities.Enemy;
import io.github.simonreilich.objects.Entities.Entity;
import io.github.simonreilich.objects.Entities.Player;

import java.util.ArrayList;
import java.util.List;

public class MapView implements Screen, DrawQueue {

    private Model model;
    private OrthographicCamera camera;
    private Batch batch;
    private List<Drawable> draw;
    public Player player;

    private RoomGraph roomGraph;
    private RoomNode map;
    private OrthogonalTiledMapRenderer renderer;

    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    public void show() {

        camera = new OrthographicCamera();
        camera.position.set(32 * 15, 32 * 10, 0);
        camera.zoom = 0.35f;
        batch = new SpriteBatch();

        draw = new ArrayList<>();

        this.player = new Player(this);

        roomGraph = new RoomGraph();
        LazyMap lazyMap = new LazyMap("maps/testMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(lazyMap.getMap());
        map = new RoomNode(lazyMap);
        roomGraph.addRoom(map);

        Enemy enemy = new Enemy(16, 11);

        player.setPosX((Integer) map.map.getLayers().get(0).getProperties().get("spawnX"));
        player.setPosY((Integer) map.map.getLayers().get(0).getProperties().get("spawnY"));

        this.enqueue(enemy);
        this.enqueue(player);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(camera);
        renderer.render();

        for (Drawable d : draw) {
            d.draw(camera, batch, delta);
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        map.map.dispose();
        renderer.dispose();
        map = null;
        renderer = null;
    }

    public MapProperties getMapProperties(int x, int y) {
        TiledMapTileLayer.Cell cell = ((TiledMapTileLayer) map.map.getLayers().get(0)).getCell(x, y);
        if (cell != null) {
            return cell.getTile().getProperties();
        }
        return new MapProperties();
    }

    public boolean inBounds(int x, int y) {
        if (map == null) return false;
        Object result = getMapProperties(x, y).get("wakable");
        if (result instanceof Boolean) {
            return (Boolean) result;
        } else {
            return false;
        }
    }

    @Override
    public void enqueue(Drawable drawable) {
        draw.add(drawable);
    }

    @Override
    public void dequeue(Drawable drawable) {
        draw.remove(drawable);
    }

    @Override
    public void dequeueAll() {
        draw.clear();
    }

    @Override
    public void dequeueAndDispose(Drawable drawable) {
        dequeue(drawable);
        drawable.dispose();
    }

    @Override
    public void dequeueAndDisposeAll() {
        for (Drawable d : draw) {
            d.dispose();
        }
        draw.clear();
    }

    @Override
    public void prioritize(Drawable drawable) {
        draw.remove(drawable);
        draw.add(drawable);
    }

    @Override
    public void deprioritize(Drawable drawable) {
        draw.remove(drawable);
        draw.add(0, drawable);
    }

    @Override
    public void updateAll(UpdateType type, float delta) {

        switch (type) {
            case PlayerMove:
                Object newMap = getMapProperties(player.getPosX(), player.getPosY()).get("map");
                if (newMap instanceof Integer) {
                    map = map.getNeighbor((Integer) newMap);
                    map.map.init();
                    player.setPosX((Integer) map.map.getLayers().get(0).getProperties().get("spawnX"));
                    player.setPosY((Integer) map.map.getLayers().get(0).getProperties().get("spawnY"));
                }
        }

        for (Drawable d : draw) {
            if (d instanceof Entity) {
                ((Entity) d).update(type, delta);
                if (! (d instanceof Player) && ((Entity) d).getPosX() == player.getPosX() && ((Entity) d).getPosY() == player.getPosY()) {
                    player.interact((Entity) d);
                }
            }
        }
    }

    public void killed() {
        model.killed();
    }
}
