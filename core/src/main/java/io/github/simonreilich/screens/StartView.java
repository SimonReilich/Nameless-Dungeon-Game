package io.github.simonreilich.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StartView implements Screen {

    private Batch batch;
    private Sprite lib;
    private Sprite title;
    private BitmapFont font;

    private final float libLogoFadeInStart = 0.0f;
    private final float libLogoFadeInEnd = 1.0f;

    private final float libLogoFadeOutStart = 3.0f;
    private final float libLogoFadeOutEnd = 4.0f;

    private final float gameLogoFadeInStart = 5.0f;
    private final float gameLogoFadeInEnd = 6.0f;

    private float time;

    @Override
    public void show() {

        batch = new SpriteBatch();

        lib = new Sprite(new Texture("interface/libgdx.png"));
        title = new Sprite(new Texture("interface/title.png"));
        font = new BitmapFont();

        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        time = 0.0f;
    }

    @Override
    public void render(float v) {
        time += v;

        Gdx.gl.glClearColor(0.11f, 0.07f, 0.09f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Color c = batch.getColor();

        if (libLogoFadeInStart < time && time < libLogoFadeInEnd) {
            batch.setColor(c.r, c.g, c.b, (time - libLogoFadeInStart) / (libLogoFadeInEnd - libLogoFadeInStart));
            batch.begin();
            batch.draw(lib, lib.getX(), lib.getY(), lib.getWidth(), lib.getHeight());
            batch.end();
        } else if (libLogoFadeInEnd < time && time < libLogoFadeOutStart) {
            batch.setColor(c.r, c.g, c.b, 1.0f);
            batch.begin();
            batch.draw(lib, lib.getX(), lib.getY(), lib.getWidth(), lib.getHeight());
            batch.end();
        } else if (libLogoFadeOutStart < time && time < libLogoFadeOutEnd) {
            batch.setColor(c.r, c.g, c.b, 1.0f - ((time - libLogoFadeOutStart) / (libLogoFadeOutEnd - libLogoFadeOutStart)));
            batch.begin();
            batch.draw(lib, lib.getX(), lib.getY(), lib.getWidth(), lib.getHeight());
            batch.end();
        } else if (gameLogoFadeInStart < time && time < gameLogoFadeInEnd) {
            batch.setColor(c.r, c.g, c.b, (time - gameLogoFadeInStart) / (gameLogoFadeInEnd - gameLogoFadeInStart));
            batch.begin();
            batch.draw(title, title.getX(), title.getY(), title.getWidth(), title.getHeight());
            batch.end();
        } else if (gameLogoFadeInEnd < time) {
            batch.setColor(c.r, c.g, c.b, 1.0f);
            batch.begin();
            batch.draw(title, title.getX(), title.getY(), title.getWidth(), title.getHeight());
            batch.end();

            batch.begin();
            font.draw(batch, "Press [SPACE] to continue", 64, 64);
            batch.end();
        }
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

        while (width <= lib.getWidth() && height <= lib.getHeight()) {
            lib.setBounds(0, 0, lib.getWidth() / 2, lib.getHeight() / 2);
        }

        while (width > 3 * lib.getWidth() && height > 3 * lib.getHeight()) {
            lib.setBounds(0, 0, lib.getWidth() * 2, lib.getHeight() * 2);
        }

        lib.setCenter((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);

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
