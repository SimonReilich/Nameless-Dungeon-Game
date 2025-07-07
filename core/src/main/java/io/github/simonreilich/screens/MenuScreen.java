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
import io.github.simonreilich.util.Consts;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class MenuScreen implements Screen {

    private float time;
    private Model model;
    private Viewport viewport;
    private Batch batch;
    private Sprite libgdxLogo;
    private Sprite gameLogo;
    private Div buttonContainer;

    private Sprite[] heroSkins;
    private int selHero;
    private Button changeSkin;

    @Override
    public void show() {
        this.viewport = new FitViewport(15 * 32, 10 * 32);
        this.batch = new SpriteBatch();
        this.time = 0;

        // creating the two logo sprites and setting their position
        this.libgdxLogo = new Sprite(new Texture(Gdx.files.internal("interface/libgdx.png")));
        this.libgdxLogo.setCenter(7.5f * 32, 5 * 32);

        this.gameLogo = new Sprite(new Texture(Gdx.files.internal("interface/title.png")));
        this.gameLogo.setY(7 * 32);
        this.gameLogo.setCenterX(7.5f * 32);

        // creating the main button-container
        this.buttonContainer = new Div(0.5f, 0, 8, 6, Align.TOP_RIGHT,
            new Button(0, 0, 5, "start game") {
                @Override
                public void clicked() {
                    model.startGame(heroSkins[selHero].getTexture());
                }
            },
            new Button(0, 0, 5, "game wiki") {
                @Override
                public void clicked() {
                    Gdx.net.openURI("https://github.com/SimonReilich/Nameless-Dungeon-Game");
                }
            },
            new Button(0, 0, 5, "quit") {
                @Override
                public void clicked() {
                    Gdx.app.exit();
                }
            });

        // creating the skin-chooser
        if (this.heroSkins == null || this.heroSkins.length == 0) initHeroSkins();
        this.changeSkin = new Button(9, 1.5f, 3, "change") {

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
            // assets.txt is a list of all available assets
            // all pngs under sprites/heros/ are used as skins, basic.png is the default
            BufferedReader r = new BufferedReader(new FileReader("assets/assets.txt"));
            String[] sources = r.lines().filter(s -> s.startsWith("sprites/heros/") && s.endsWith(".png")).toArray(String[]::new);
            this.heroSkins = new Sprite[sources.length];
            this.selHero = 0;
            for (int i = 0; i < sources.length; i++) {
                this.heroSkins[i] = new Sprite(new Texture(sources[i]));
                this.heroSkins[i].setScale(2.0f);
                this.heroSkins[i].setCenter(10.5f * 32, 4.25f * 32);
                if (sources[i].endsWith("basic.png")) {
                    this.selHero = i;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void click(Vector2 mouse) {
        // projecting from screen coordinates to world coordinates
        Vector2 screen = this.viewport.unproject(mouse);
        // updating the button-container and skin-chooser with the clicks position
        this.buttonContainer.update(screen);
        this.changeSkin.update(screen);
    }

    public void setTime(float time) {
        this.time = time;
    }

    @Override
    public void render(float delta) {
        // clearing the screen and applying the FitViewport
        Gdx.gl.glClearColor(0.11f, 0.07f, 0.09f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.viewport.apply();

        this.viewport.getCamera().update();
        this.batch.setProjectionMatrix(this.viewport.getCamera().combined);

        // tracking the current time for the animation
        if (this.time <= Consts.animationEnd) this.time += delta;

        if (Consts.libLogoFadeInStart < this.time && this.time < Consts.libLogoFadeInEnd) {
            this.libgdxLogo.setAlpha((this.time - Consts.libLogoFadeInStart) / (Consts.libLogoFadeInEnd - Consts.libLogoFadeInStart));
            this.batch.begin();
            this.libgdxLogo.draw(this.batch);
            this.batch.end();
        } else if (Consts.libLogoFadeInEnd < this.time && this.time < Consts.libLogoFadeOutStart) {
            this.libgdxLogo.setAlpha(1.0f);
            this.batch.begin();
            this.libgdxLogo.draw(this.batch);
            this.batch.end();
        } else if (Consts.libLogoFadeOutStart < this.time && this.time < Consts.libLogoFadeOutEnd) {
            this.libgdxLogo.setAlpha(1.0f - ((this.time - Consts.libLogoFadeOutStart) / (Consts.libLogoFadeOutEnd - Consts.libLogoFadeOutStart)));
            this.batch.begin();
            this.libgdxLogo.draw(this.batch);
            this.batch.end();
        } else if (Consts.gameLogoFadeInStart < this.time && this.time < Consts.animationEnd) {
            this.gameLogo.setAlpha((this.time - Consts.gameLogoFadeInStart) / (Consts.animationEnd - Consts.gameLogoFadeInStart));
            this.batch.begin();
            this.gameLogo.draw(this.batch);
            this.batch.end();
        } else if (Consts.animationEnd < this.time) {
            this.gameLogo.setAlpha(1.0f);
            this.batch.begin();
            this.gameLogo.draw(this.batch);
            this.batch.end();

            this.buttonContainer.draw((SpriteBatch) this.batch);
            this.changeSkin.draw((SpriteBatch) this.batch);

            this.batch.begin();
            this.heroSkins[this.selHero].draw(this.batch);
            this.batch.end();
        }
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
        this.libgdxLogo.getTexture().dispose();
        this.gameLogo.getTexture().dispose();
        for (int i = 0; i < this.heroSkins.length; i++) {
            if (i != this.selHero) {
                this.heroSkins[i].getTexture().dispose();
            }
        }
        this.batch.dispose();
    }
}
