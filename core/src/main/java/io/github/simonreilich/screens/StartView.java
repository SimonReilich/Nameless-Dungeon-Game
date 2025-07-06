package io.github.simonreilich.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.simonreilich.Model;
import io.github.simonreilich.ui.Button;
import io.github.simonreilich.ui.Div;
import io.github.simonreilich.util.Align;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class StartView implements Screen {

    private Model model;
    private Viewport viewport;
    private Batch batch;

    private final float libLogoFadeInStart = 0.0f;
    private final float libLogoFadeInEnd = 0.5f;
    private final float libLogoFadeOutStart = 1.5f;
    private final float libLogoFadeOutEnd = 2.0f;
    private final float gameLogoFadeInStart = 2.5f;
    public final float animationEnd = 3.0f;

    public float time;

    private Sprite libgdxLogo;
    private Sprite gameLogo;
    private Div buttonContainer;

    private Sprite[] heroSkins;
    private int selHero;
    private Button changeSkin;

    @Override
    public void show() {
        viewport = new FitViewport(15 * 32, 10 * 32);
        batch = new SpriteBatch();

        time = 0;

        libgdxLogo = new Sprite(new Texture(Gdx.files.internal("interface/libgdx.png")));
        libgdxLogo.setCenter(7.5f * 32, 5 * 32);

        gameLogo = new Sprite(new Texture(Gdx.files.internal("interface/title.png")));
        gameLogo.setY(7 * 32);
        gameLogo.setCenterX(7.5f * 32);

        buttonContainer = new Div(0.5f, 0, 9, 6, Align.TOP_RIGHT,
            new Button(0, 0, 6, "start game") {
                @Override
                public void clicked() {
                    model.startGame(heroSkins[selHero].getTexture());
                    for (int i = 0; i < heroSkins.length; i++) {
                        if (i != selHero) {
                            heroSkins[i].getTexture().dispose();
                        }
                    }
                }
            },
            new Button(0, 0, 6, "game wiki") {
                @Override
                public void clicked() {

                }
            },
            new Button(0, 0, 6, "quit") {
                @Override
                public void clicked() {
                    Gdx.app.exit();
                }
            });

        initHeroSkins();
        changeSkin = new Button(10, 1.5f, 2, "change") {

            @Override
            public void clicked() {
                selHero = (selHero + 1) % heroSkins.length;
            }
        };
    }

    public void setModel(Model model) {
        this.model = model;
    }

    private void initHeroSkins() {
        try {
            BufferedReader r = new BufferedReader(new FileReader("assets/assets.txt"));
            String[] sources = r.lines().filter(s -> s.startsWith("sprites/heros/") && s.endsWith(".png")).toArray(String[]::new);
            heroSkins = new Sprite[sources.length];
            selHero = 0;
            for (int i = 0; i < sources.length; i++) {
                heroSkins[i] = new Sprite(new Texture(sources[i]));
                heroSkins[i].setScale(2.0f);
                heroSkins[i].setCenter(11 * 32, 4.15f * 32);
                if (sources[i].endsWith("basic.png")) {
                    selHero = i;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.11f, 0.07f, 0.09f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();

        viewport.getCamera().update();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        time += delta;

        if (libLogoFadeInStart < time && time < libLogoFadeInEnd) {
            libgdxLogo.setAlpha((time - libLogoFadeInStart) / (libLogoFadeInEnd - libLogoFadeInStart));
            batch.begin();
            libgdxLogo.draw(batch);
            batch.end();
        } else if (libLogoFadeInEnd < time && time < libLogoFadeOutStart) {
            libgdxLogo.setAlpha(1.0f);
            batch.begin();
            libgdxLogo.draw(batch);
            batch.end();
        } else if (libLogoFadeOutStart < time && time < libLogoFadeOutEnd) {
            libgdxLogo.setAlpha(1.0f - ((time - libLogoFadeOutStart) / (libLogoFadeOutEnd - libLogoFadeOutStart)));
            batch.begin();
            libgdxLogo.draw(batch);
            batch.end();
        } else if (gameLogoFadeInStart < time && time < animationEnd) {
            gameLogo.setAlpha((time - gameLogoFadeInStart) / (animationEnd - gameLogoFadeInStart));
            batch.begin();
            gameLogo.draw(batch);
            batch.end();
        } else if (animationEnd < time) {
            gameLogo.setAlpha(1.0f);
            batch.begin();
            gameLogo.draw(batch);
            batch.end();

            buttonContainer.draw((SpriteBatch) batch);
            changeSkin.draw((SpriteBatch) batch);

            batch.begin();
            heroSkins[selHero].draw(batch);
            batch.end();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public void click(Vector2 mouse) {
        Vector2 screen = viewport.unproject(mouse);
        buttonContainer.update(screen);
        changeSkin.update(screen);
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
        libgdxLogo.getTexture().dispose();
        gameLogo.getTexture().dispose();
    }
}
