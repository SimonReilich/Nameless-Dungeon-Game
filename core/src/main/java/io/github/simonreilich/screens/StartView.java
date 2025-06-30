package io.github.simonreilich.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StartView implements Screen {

    private Batch batch;
    private Sprite title;

    @Override
    public void show() {

        batch = new SpriteBatch();

        title = new Sprite(new Texture("interface/title.png"));

        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float v) {
        batch.begin();
        title.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int i, int i1) {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        while (width <= title.getWidth() && height <= title.getHeight()) {
            title.setBounds(0, 0, title.getWidth() / 2, title.getHeight() / 2);
        }

        while (width > 3 * title.getWidth() && height > 3 * title.getHeight()) {
            title.setBounds(0, 0, title.getWidth() * 2, title.getHeight() * 2);
        }

        title.setCenter((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);

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
        title.getTexture().dispose();
    }
}
