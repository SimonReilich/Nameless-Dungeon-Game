package io.github.simonreilich.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.simonreilich.UpdateType;
import io.github.simonreilich.objects.Drawable;
import io.github.simonreilich.objects.Entities.Entity;

import java.util.ArrayList;
import java.util.List;

public class GameView implements Screen, DrawQueue {

    private OrthographicCamera camera;
    private Batch batch;
    private List<Drawable> draw;

    @Override
    public void show() {

        camera = new OrthographicCamera();
        camera.position.set(32 * 15, 32 * 10, 0);
        camera.zoom = 0.35f;
        batch = new SpriteBatch();

        draw = new ArrayList<>();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
        for (Drawable d : draw) {
            d.dispose();
        }
        draw.clear();
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
        for (Drawable d : draw) {
            if (d instanceof Entity) {
                ((Entity) d).update(type, delta);
            }
        }
    }
}
