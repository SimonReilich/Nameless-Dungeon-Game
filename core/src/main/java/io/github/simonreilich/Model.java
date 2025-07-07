package io.github.simonreilich;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import io.github.simonreilich.screens.MapScreen;
import io.github.simonreilich.screens.MenuScreen;
import io.github.simonreilich.util.Consts;

public class Model {

    private final View view;
    private final Controller controller;
    private final MenuScreen menuScreen;
    private MapScreen mapScreen;

    public Model(View view) {
        this.view = view;

        this.controller = new Controller(this);
        Gdx.input.setInputProcessor(controller);

        // Initializing the screens and applying menu
        this.menuScreen = new MenuScreen();
        this.menuScreen.setModel(this);
        this.mapScreen = new MapScreen();

        this.view.setScreen(menuScreen);
    }

    // switching screens

    public void startGame(Texture heroSkin) {
        if (this.view.getScreen() != menuScreen)
            Gdx.app.error("Invalid Method call", "call to startGame() is only allowed, if the menuScreen is active");
        controller.setMap();
        this.view.setScreen(mapScreen);
        mapScreen.setHeroSkin(heroSkin);
    }

    public void restart() {
        if (this.view.getScreen() != mapScreen)
            Gdx.app.error("Invalid Method call", "call to restart() is only allowed, if the mapScreen is active");
        this.view.getScreen().dispose();
        this.view.setScreen(menuScreen);
        menuScreen.setTime(Consts.animationEnd);
        controller.setMenu();
        mapScreen = new MapScreen();
    }

    // menu input

    public void clicked(Vector2 mouse) {
        if (this.view.getScreen() == menuScreen) menuScreen.click(mouse);
    }

    // map input

    public void up() {
        if (this.view.getScreen() != mapScreen)
            Gdx.app.error("Invalid Method call", "call to up() is only allowed, if the mapScreen is active");
        mapScreen.up();
    }

    public void left() {
        if (this.view.getScreen() != mapScreen)
            Gdx.app.error("Invalid Method call", "call to left() is only allowed, if the mapScreen is active");
        mapScreen.left();
    }

    public void down() {
        if (this.view.getScreen() != mapScreen)
            Gdx.app.error("Invalid Method call", "call to down() is only allowed, if the mapScreen is active");
        mapScreen.down();
    }

    public void right() {
        if (this.view.getScreen() != mapScreen)
            Gdx.app.error("Invalid Method call", "call to right() is only allowed, if the mapScreen is active");
        mapScreen.right();
    }

    public void skip() {
        if (this.view.getScreen() != mapScreen)
            Gdx.app.error("Invalid Method call", "call to skip() is only allowed, if the mapScreen is active");
        mapScreen.skip();
    }

    public void toggleAttack() {
        if (this.view.getScreen() != mapScreen)
            Gdx.app.error("Invalid Method call", "call to toggleAttack() is only allowed, if the mapScreen is active");
        mapScreen.toggleAttack();
    }
}
