package io.github.simonreilich.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import io.github.simonreilich.objects.Drawable;
import io.github.simonreilich.objects.Player;

import java.util.ArrayList;
import java.util.List;

public class GameView implements Screen, DrawQueue {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    private Player player;

    private List<Drawable> draw;

    @Override
    public void show() {
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("maps/testMap.tmx");

        renderer = new OrthogonalTiledMapRenderer(map);

        camera = new OrthographicCamera();
        camera.position.set(32 * 15, 32 * 10, 0);

        player = new Player(new Sprite(new Texture("sprites/player.png")));

        draw = new ArrayList<Drawable>();
        draw.add(player);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(camera);
        renderer.render();

        renderer.getBatch().begin();
        for (Drawable d : draw) {
            d.draw(renderer.getBatch());
        }
        renderer.getBatch().end();
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
        dispose();
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

    @Override
    public void enqueue(Drawable drawable, long id) {

    }

    @Override
    public void dequeue(long id) {

    }

    @Override
    public void dequeueAll() {

    }

    @Override
    public void dequeueAndDispose(long id) {

    }

    @Override
    public void dequeueAndDisposeAll() {

    }

    @Override
    public void prioritize(long id) {

    }

    @Override
    public void deprioritize(long id) {

    }
}
