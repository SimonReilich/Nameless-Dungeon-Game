package io.github.simonreilich.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

import java.util.*;

public class MapView implements Screen, DrawQueue {

    private Model model;
    private OrthographicCamera camera;
    private Batch batch;
    private List<Drawable> draw;
    public Player player;

    private RoomGraph roomGraph;
    private RoomNode mapNode;
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
        LazyMap lazyMap = new LazyMap();
        renderer = new OrthogonalTiledMapRenderer(lazyMap.getMap());
        mapNode = new RoomNode(lazyMap);
        roomGraph.addRoom(mapNode);

        player.setPosX((Integer) mapNode.map.getLayers().get(0).getProperties().get("spawnX"));
        player.setPosY((Integer) mapNode.map.getLayers().get(0).getProperties().get("spawnY"));

        for (int x = 0; x < 30; x++) {
            for (int y = 0; y < 20; y++) {
                if (mapNode.map.getMapProperties(x, y).containsKey("spawn")) {
                    this.enqueue(Enemy.spawn(mapNode.map.getMapProperties(x, y).get("spawn", Integer.class), x, y));
                }
            }
        }
        this.enqueue(player);
    }

    @Override
    public void render(float delta) {
        if (renderer == null) return;

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
        mapNode.map.dispose();
        renderer.dispose();
        mapNode = null;
        renderer = null;
    }

    public Set<Entity> getEntitiesPos(int x, int y) {
        Set<Entity> entities = new HashSet<>();
        for (Drawable d : draw) {
            if (d instanceof Entity && ((Entity) d).getPosX() == x && ((Entity) d).getPosY() == y) {
                entities.add((Entity) d);
            }
        }
        return entities;
    }

    public Set<Entity> getEntitiesAdj(int x, int y) {
        Set<Entity> entities = new HashSet<>();
        for (Drawable d : draw) {
            if (d instanceof Entity && ((Entity) d).getDestinationX() == x - 1 && ((Entity) d).getDestinationY() == y) {
                entities.add((Entity) d);
            } else if (d instanceof Entity && ((Entity) d).getDestinationX() == x + 1 && ((Entity) d).getDestinationY() == y) {
                entities.add((Entity) d);
            } else if (d instanceof Entity && ((Entity) d).getDestinationX() == x && ((Entity) d).getDestinationY() == y - 1) {
                entities.add((Entity) d);
            } else if (d instanceof Entity && ((Entity) d).getDestinationX() == x && ((Entity) d).getDestinationY() == y + 1) {
                entities.add((Entity) d);
            }
        }
        return entities;
    }

    public boolean inBounds(int x, int y) {
        if (mapNode == null) return false;
        Object result = mapNode.map.getMapProperties(x, y).get("wakable");
        if (result instanceof Boolean) {
            return (Boolean) result;
        } else {
            return false;
        }
    }

    public boolean occupied(int x, int y) {
        return draw.stream().anyMatch(d -> d instanceof Entity && ((Entity) d).getPosX() == x && ((Entity) d).getPosY() == y);
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
                // if the player steps on a door, the map changes
                Object newMap = mapNode.map.getMapProperties(player.getPosX(), player.getPosY()).get("map");
                if (newMap instanceof Integer) {
                    mapNode = mapNode.getNeighbor((Integer) newMap);
                    mapNode.map.init();
                    player.setPosX((Integer) mapNode.map.getLayers().get(0).getProperties().get("spawnX"));
                    player.setPosY((Integer) mapNode.map.getLayers().get(0).getProperties().get("spawnY"));
                }
        }

        // every entity gets updated
        for (Drawable d : draw) {
            if (d instanceof Entity) {
                ((Entity) d).update(type, delta);
            }
        }

        // Player interacts with all Entities, that are adjacent to him
        for (Entity entity : getEntitiesAdj(player.getDestinationX(), player.getDestinationY())) {
            player.interact(entity);
        }
    }
}
