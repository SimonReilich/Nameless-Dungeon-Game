package io.github.simonreilich.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

    private boolean attack;
    private Texture attackTex;
    private int highscore;
    private BitmapFont font;

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

        player.setPosX((Integer) mapNode.map.getLayers().get(0).getProperties().get("spawnX1"));
        player.setPosY((Integer) mapNode.map.getLayers().get(0).getProperties().get("spawnY1"));

        for (int x = 0; x < 30; x++) {
            for (int y = 0; y < 20; y++) {
                if (mapNode.map.getMapProperties(x, y).containsKey("spawn")) {
                    this.enqueue(Enemy.spawn(mapNode.map.getMapProperties(x, y).get("spawn", Integer.class), x, y, this));
                }
            }
        }
        this.enqueue(player);

        attack = false;
        attackTex = new Texture("sprites/attack.png");
        highscore = 0;
        font = new BitmapFont();
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

        if (attack) {
            batch.begin();
            batch.draw(attackTex, 0, 0);
            batch.end();
        }

        batch.begin();
        font.draw(batch, Integer.toString(highscore), 0, 0);
        batch.end();
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

    private void reloadMap(int index) {
        renderer.setMap(mapNode.map.getMap());
        dequeueAll();

        for (int x = 0; x < 30; x++) {
            for (int y = 0; y < 20; y++) {
                if (mapNode.map.getMapProperties(x, y).containsKey("spawn")) {
                    this.enqueue(Enemy.spawn(mapNode.map.getMapProperties(x, y).get("spawn", Integer.class), x, y, this));
                }
            }
        }
        this.enqueue(player);

        player.setPosX((Integer) mapNode.map.getLayers().get(0).getProperties().get("spawnX" + index));
        player.setPosY((Integer) mapNode.map.getLayers().get(0).getProperties().get("spawnY" + index));
    }

    @Override
    public void updateAll(UpdateType type, float delta) {

        switch (type) {
            case PlayerMove:
                // if the player steps on a door, the map changes
                Object newMap = mapNode.map.getMapProperties(player.getDestinationX(), player.getDestinationY()).get("map");
                if (newMap instanceof Integer) {
                    RoomNode oldRoom = mapNode;
                    mapNode = mapNode.getNeighbor((Integer) newMap);
                    mapNode.map.init();
                    mapNode.setNeighbor(oldRoom, (Integer) newMap);
                    renderer.setMap(mapNode.map.getMap());
                    reloadMap((Integer) newMap);
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

    public void toggleAttack() {
        attack = !attack;
    }

    public void up() {
        player.up();
        attack = false;
    }

    public void left() {
        player.left();
        attack = false;
    }

    public void right() {
        player.right();
        attack = false;
    }

    public void down() {
        player.down();
        attack = false;
    }

    public void attack() {
        if (attack) {
            getEntitiesAdj(player.getDestinationX(), player.getDestinationY()).forEach(entity -> {
                if (entity instanceof Enemy) {
                    entity.update(UpdateType.PlayerAttack, 0.0f);
                    highscore++;
                }
            });
        }
    }
}
