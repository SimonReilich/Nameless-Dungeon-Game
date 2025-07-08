package io.github.simonreilich.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.simonreilich.objects.Drawable;
import io.github.simonreilich.objects.Entities.Entity;
import io.github.simonreilich.objects.Entities.Hero;
import io.github.simonreilich.objects.Entities.enemies.Enemy;
import io.github.simonreilich.objects.Items.Item;
import io.github.simonreilich.rooms.LazyMap;
import io.github.simonreilich.rooms.RoomNode;
import io.github.simonreilich.util.Consts;
import io.github.simonreilich.util.UpdateType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapScreen implements Screen, DrawQueue {
    // the main-screen for the game itself

    private static final BitmapFont font = new BitmapFont();
    private static final GlyphLayout glyphLayout = new GlyphLayout();
    private Hero hero;
    private int score;
    private Viewport viewport;
    private Batch batch;
    private List<Drawable> draw;
    private RoomNode roomNode;
    private OrthogonalTiledMapRenderer renderer;
    private boolean attack;
    private Texture attackTex;

    @Override
    public void show() {
        this.viewport = new FitViewport(32 * 32, 22 * 32);
        this.batch = new SpriteBatch();
        this.draw = new ArrayList<>();

        this.hero = new Hero(this);

        LazyMap lazyMap = new LazyMap();
        this.renderer = new OrthogonalTiledMapRenderer(lazyMap.getMap());
        this.roomNode = new RoomNode(lazyMap);

        // setting the players spawn location
        this.hero.setPosX((Integer) this.roomNode.map.getLayers().get(0).getProperties().get("spawnX1"));
        this.hero.setPosY((Integer) this.roomNode.map.getLayers().get(0).getProperties().get("spawnY1"));

        // add all drawables to the list
        this.roomNode.initDrawables(this);
        for (Drawable d : this.roomNode.getDrawables()) {
            this.enqueue(d);
        }
        this.enqueue(this.hero);

        this.attack = false;
        this.attackTex = new Texture("interface/attack.png");
        this.score = 0;
    }

    @Override
    public void render(float delta) {

        if (renderer == null) return;

        Gdx.gl.glClearColor(0.11f, 0.07f, 0.09f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.renderer.setView((OrthographicCamera) this.viewport.getCamera());
        this.viewport.apply();
        this.renderer.render(Consts.backgroundLayer);

        for (Drawable d : this.draw) {
            d.draw((OrthographicCamera) this.viewport.getCamera(), this.batch, delta);
        }

        this.renderer.render(Consts.foregroundLayer);

        if (this.attack) {
            this.batch.begin();
            this.batch.draw(this.attackTex, 30 * 32, 32);
            this.batch.end();
        }

        this.batch.begin();
        glyphLayout.setText(font, Integer.toString(this.score), Color.WHITE, 32, Align.center, true);
        font.draw(
            this.batch,
            Integer.toString(this.score),
            32,
            32 + 32 / 2f + glyphLayout.height / 2f,
            32,
            Align.center,
            true);
        this.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height, true);
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
        this.batch.dispose();
        this.roomNode.map.dispose();
        this.renderer.dispose();
        this.roomNode = null;
        this.renderer = null;
    }

    public Set<Item> getItemsPos(int x, int y) {
        Set<Item> items = new HashSet<>();
        for (Drawable d : this.draw) {
            if (d instanceof Item && ((Item) d).getPosX() == x && ((Item) d).getPosY() == y) {
                items.add((Item) d);
            }
        }
        return items;
    }

    public Set<Entity> getEntitiesPos(int x, int y) {
        Set<Entity> entities = new HashSet<>();
        for (Drawable d : this.draw) {
            if (d instanceof Entity && ((Entity) d).getPosX() == x && ((Entity) d).getPosY() == y) {
                entities.add((Entity) d);
            }
        }
        return entities;
    }

    public Set<Entity> getEntitiesAdj(int x, int y) {
        Set<Entity> entities = new HashSet<>();
        for (Drawable d : this.draw) {
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
        if (roomNode == null) return false;
        Object result = this.roomNode.map.getMapProperties(x, y).get("wakable");
        if (result instanceof Boolean) {
            return (Boolean) result;
        } else {
            return false;
        }
    }

    public boolean occupied(int x, int y) {
        return this.draw.stream().anyMatch(d -> d instanceof Entity && ((Entity) d).getPosX() == x && ((Entity) d).getPosY() == y);
    }

    @Override
    public void enqueue(Drawable drawable) {
        this.draw.add(drawable);
    }

    @Override
    public void dequeue(Drawable drawable) {
        this.draw.remove(drawable);
    }

    @Override
    public void dequeueAll() {
        this.draw.clear();
    }

    private void reloadMap(int index) {
        this.renderer.setMap(roomNode.map.getMap());
        dequeueAll();

        this.roomNode.initDrawables(this);
        for (Drawable d : this.roomNode.getDrawables()) {
            this.enqueue(d);
        }
        this.enqueue(this.hero);

        this.hero.setPosX((Integer) this.roomNode.map.getLayers().get(Consts.spawnLayer).getProperties().get("spawnX" + (index + 1)));
        this.hero.setPosY((Integer) this.roomNode.map.getLayers().get(Consts.spawnLayer).getProperties().get("spawnY" + (index + 1)));
    }

    @Override
    public void updateAll(UpdateType type, float delta) {

        switch (type) {
            case PlayerMove:
                // if the player steps on a door, the map changes
                Object newMap = this.roomNode.map.getMapProperties(this.hero.getDestinationX(), this.hero.getDestinationY()).get("map");
                if (newMap instanceof Integer) {
                    RoomNode oldRoom = this.roomNode;
                    this.roomNode = this.roomNode.getNeighbor((Integer) newMap);
                    this.roomNode.map.init();
                    this.roomNode.setNeighbor(oldRoom, (Integer) newMap);
                    this.renderer.setMap(roomNode.map.getMap());
                    reloadMap((Integer) newMap);
                }
        }

        // Player interacts with all Entities, that are adjacent to him
        for (Entity entity : getEntitiesAdj(this.hero.getDestinationX(), this.hero.getDestinationY())) {
            this.hero.interact(entity);
        }

        if (this.hero.isAlive()) {
            // every entity gets updated
            for (Drawable d : this.draw) {
                if (d instanceof Entity) {
                    ((Entity) d).update(type, delta);
                }
            }

            // Player interacts with all Entities, that are adjacent to him
            for (Entity entity : getEntitiesAdj(this.hero.getDestinationX(), this.hero.getDestinationY())) {
                this.hero.interact(entity);
            }

            // Player picks up items
            for (Item item : getItemsPos(this.hero.getDestinationX(), this.hero.getDestinationY())) {
                item.consume();
            }
        }
    }

    public void toggleAttack() {
        if (this.hero.isAlive()) this.attack = !this.attack;
    }

    public void up() {
        this.hero.up();
        this.attack = false;
    }

    public void left() {
        this.hero.left();
        this.attack = false;
    }

    public void right() {
        this.hero.right();
        this.attack = false;
    }

    public void down() {
        this.hero.down();
        this.attack = false;
    }

    public void skip() {
        if (this.hero.isAlive()) {
            updateAll(UpdateType.PlayerMove, Gdx.graphics.getDeltaTime());
            this.hero.decreaseSpeed();
        }
    }

    public void attack() {
        if (this.attack) {
            getEntitiesAdj(this.hero.getDestinationX(), this.hero.getDestinationY()).forEach(entity -> {
                if (entity instanceof Enemy) {
                    entity.update(UpdateType.PlayerAttack, 0.0f);
                    this.score++;
                }
            });
        }
    }

    public boolean free(int x, int y) {
        return !occupied(x, y) && inBounds(x, y) && getItemsPos(x, y).isEmpty();
    }

    public void setHeroSkin(Texture heroSkin) {
        this.hero.set(new Sprite(heroSkin));

        this.hero.setPosX((Integer) this.roomNode.map.getLayers().get(0).getProperties().get("spawnX1"));
        this.hero.setPosY((Integer) this.roomNode.map.getLayers().get(0).getProperties().get("spawnY1"));
    }

    public void increaseHeroSpeed() {
        this.hero.increaseSpeed();
    }

    public void addPoints(int score) {
        this.score += score;
    }

    public Vector2 getHeroDestination() {
        return new Vector2(this.hero.getDestinationX(), this.hero.getDestinationY());
    }
}
