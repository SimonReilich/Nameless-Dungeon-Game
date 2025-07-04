package io.github.simonreilich.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.simonreilich.Model;
import io.github.simonreilich.ui.Button;
import io.github.simonreilich.ui.Div;
import io.github.simonreilich.ui.UiElement;
import io.github.simonreilich.util.Align;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class StartView implements Screen {

    private Batch batch;
    private Sprite lib;
    private Sprite title;

    private final float libLogoFadeInStart = 0.0f;
    private final float libLogoFadeInEnd = 0.5f;

    private final float libLogoFadeOutStart = 1.5f;
    private final float libLogoFadeOutEnd = 2.0f;

    private final float gameLogoFadeInStart = 2.5f;
    public final float animationEnd = 3.0f;

    public float time;

    private Div div;

    private Model model;

    private Sprite[] heroSkins;
    private int selectedHero;

    @Override
    public void show() {

        batch = new SpriteBatch();

        lib = new Sprite(new Texture("interface/libgdx.png"));
        title = new Sprite(new Texture("interface/title.png"));

        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        time = 0.0f;

        div  = new Div(0, 0, (float) Gdx.graphics.getWidth() / (UiElement.size * 2), (float) Gdx.graphics.getHeight() / UiElement.size / 2, Align.TOP_RIGHT,
            new Button(0, 0, 5, "Play") {
                @Override
                public void clicked() {
                    model.startGame(heroSkins[selectedHero].getTexture());
                    for (int i = 0; i < heroSkins.length; i++) {
                        if (i != selectedHero) {
                            heroSkins[i].getTexture().dispose();
                        }
                    }
                }
            },
            new Button(0, 0, 5, "Change Skin") {
                @Override
                public void clicked() {
                    selectedHero = (selectedHero + 1) % heroSkins.length;
                }
            },
            new Button(0, 0, 5, "Close") {
                @Override
                public void clicked() {
                    Gdx.app.exit();
                }
            });

        initHeroSkins();
    }

    public void setModel(Model model) {
        this.model = model;
    }

    private void initHeroSkins() {
        try {
            BufferedReader r = new BufferedReader(new FileReader("assets/assets.txt"));
            String[] sources = r.lines().filter(s -> s.startsWith("sprites/heros/") && s.endsWith(".png")).toArray(String[]::new);
            heroSkins = new Sprite[sources.length];
            selectedHero = 0;
            for (int i = 0; i < sources.length; i++) {
                heroSkins[i] = new Sprite(new Texture(sources[i]));
                if (sources[i].endsWith("basic.png")) {
                    selectedHero = i;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
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
        } else if (gameLogoFadeInStart < time && time < animationEnd) {
            batch.setColor(c.r, c.g, c.b, (time - gameLogoFadeInStart) / (animationEnd - gameLogoFadeInStart));
            batch.begin();
            batch.draw(title, title.getX(), title.getY(), title.getWidth(), title.getHeight());
            batch.end();
        } else if (animationEnd < time) {
            batch.setColor(c.r, c.g, c.b, 1.0f);
            batch.begin();
            batch.draw(title, title.getX(), title.getY(), title.getWidth(), title.getHeight());
            batch.end();

            div.draw((SpriteBatch) batch);

            batch.begin();
            batch.draw(heroSkins[selectedHero], (float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2 - heroSkins[selectedHero].getHeight() * 10, heroSkins[selectedHero].getWidth() * 10, heroSkins[selectedHero].getHeight() * 10);
            batch.end();
        }
    }

    @Override
    public void resize(int i, int i1) {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        while (width <= title.getWidth() && height / 2 <= title.getHeight()) {
            title.setBounds(0, 0, title.getWidth() / 2, title.getHeight() / 2);
        }

        while (width > 3 * title.getWidth() && height / 2 > 3 * title.getHeight()) {
            title.setBounds(0, 0, title.getWidth() * 2, title.getHeight() * 2);
        }

        title.setCenter((float) Gdx.graphics.getWidth() / 2, (float) (3 * Gdx.graphics.getHeight()) / 4);

        while (width <= lib.getWidth() && height <= lib.getHeight()) {
            lib.setBounds(0, 0, lib.getWidth() / 2, lib.getHeight() / 2);
        }

        while (width > 3 * lib.getWidth() && height > 3 * lib.getHeight()) {
            lib.setBounds(0, 0, lib.getWidth() * 2, lib.getHeight() * 2);
        }

        lib.setCenter((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);

    }

    public void click(int mouseX, int mouseY) {
        div.update(mouseX, mouseY);
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
