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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.simonreilich.Model;
import io.github.simonreilich.UpdateType;
import io.github.simonreilich.graph.LazyMap;
import io.github.simonreilich.graph.RoomGraph;
import io.github.simonreilich.graph.RoomNode;
import io.github.simonreilich.objects.Drawable;
import io.github.simonreilich.objects.Entities.enemies.Enemy;
import io.github.simonreilich.objects.Entities.Entity;
import io.github.simonreilich.objects.Entities.Hero;
import io.github.simonreilich.objects.Items.Item;

import java.util.*;

public class MapView implements Screen, DrawQueue {

    private Model model;
    private Viewport viewport;
    private Batch batch;
    private List<Drawable> draw;
    public Hero hero;

    private RoomGraph roomGraph;
    private RoomNode mapNode;
    private OrthogonalTiledMapRenderer renderer;

    private boolean attack;
    private Texture attackTex;
    public int score;
    private BitmapFont font;

    private final int[] backgroundLayer = new int[]{4,5};
    private final int[] foregroundLayer = new int[]{6};
    private final int doorLayer = 0;

    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    public void show() {

        OrthographicCamera camera = new OrthographicCamera();
        camera.position.set(32 * 15, 32 * 10, 0);
        viewport = new FitViewport(30 * 32, 20 * 32);
        batch = new SpriteBatch();

        draw = new ArrayList<>();

        this.hero = new Hero(this);

        roomGraph = new RoomGraph();
        LazyMap lazyMap = new LazyMap();
        renderer = new OrthogonalTiledMapRenderer(lazyMap.getMap());
        mapNode = new RoomNode(lazyMap);
        roomGraph.addRoom(mapNode);

        hero.setPosX((Integer) mapNode.map.getLayers().get(0).getProperties().get("spawnX1"));
        hero.setPosY((Integer) mapNode.map.getLayers().get(0).getProperties().get("spawnY1"));

        mapNode.initDrawables(this);
        for (Drawable d : mapNode.getDrawables()) {
            this.enqueue(d);
        }

        this.enqueue(hero);

        attack = false;
        attackTex = new Texture("interface/attack.png");
        score = 0;
        font = new BitmapFont();
    }

    @Override
    public void render(float delta) {

        if (renderer == null) return;

        Gdx.gl.glClearColor(0.11f, 0.07f, 0.09f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView((OrthographicCamera) viewport.getCamera());
        viewport.apply();
        renderer.render(backgroundLayer);

        for (Drawable d : draw) {
            d.draw((OrthographicCamera) viewport.getCamera(), batch, delta);
        }

        renderer.render(foregroundLayer);

        if (attack) {
            batch.begin();
            batch.draw(attackTex, 0, 0);
            batch.end();
        }

        batch.begin();
        font.draw(batch, Integer.toString(score), 32, 32);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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

    public Set<Item> getItemsPos(int x, int y) {
        Set<Item> items = new HashSet<>();
        for (Drawable d : draw) {
            if (d instanceof Item && ((Item) d).getPosX() == x && ((Item) d).getPosY() == y) {
                items.add((Item) d);
            }
        }
        return items;
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

        mapNode.initDrawables(this);
        for (Drawable d : mapNode.getDrawables()) {
            this.enqueue(d);
        }
        this.enqueue(hero);

        hero.setPosX((Integer) mapNode.map.getLayers().get(doorLayer).getProperties().get("spawnX" + (index + 1)));
        hero.setPosY((Integer) mapNode.map.getLayers().get(doorLayer).getProperties().get("spawnY" + (index + 1)));
    }

    @Override
    public void updateAll(UpdateType type, float delta) {

        switch (type) {
            case PlayerMove:
                // if the player steps on a door, the map changes
                Object newMap = mapNode.map.getMapProperties(hero.getDestinationX(), hero.getDestinationY()).get("map");
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
        for (Entity entity : getEntitiesAdj(hero.getDestinationX(), hero.getDestinationY())) {
            hero.interact(entity);
        }

        // Player picks up items
        for (Item item : getItemsPos(hero.getDestinationX(), hero.getDestinationY())) {
            item.consume();
        }
    }

    public void toggleAttack() {
        attack = !attack;
    }

    public void up() {
        hero.up();
        attack = false;
    }

    public void left() {
        hero.left();
        attack = false;
    }

    public void right() {
        hero.right();
        attack = false;
    }

    public void down() {
        hero.down();
        attack = false;
    }

    public void attack() {
        if (attack) {
            getEntitiesAdj(hero.getDestinationX(), hero.getDestinationY()).forEach(entity -> {
                if (entity instanceof Enemy) {
                    entity.update(UpdateType.PlayerAttack, 0.0f);
                    score++;
                }
            });
        }
    }
}
