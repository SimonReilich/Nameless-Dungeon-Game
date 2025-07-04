package io.github.simonreilich;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import io.github.simonreilich.util.Screen;

public class Controller extends InputAdapter {

    private Model model;
    private Screen screen;

    public Controller(Model model) {
        super();
        this.model = model;
        this.screen = Screen.Start;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (screen) {
            case Start:
                return start(keycode);
            case Map:
                return map(keycode);
            case Menu:
                return menu(keycode);
        }
        return super.keyDown(keycode);
    }

    @Override public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        // ignore if its not left mouse button or first touch pointer
        if (button != Input.Buttons.LEFT || pointer > 0) return false;
        if (screen == Screen.Start) {
            model.clicked(screenX, Gdx.graphics.getHeight() - screenY);
        }
        return true;
    }

    private boolean start(int keycode) {
        switch (keycode) {

        }
        return super.keyDown(keycode);
    }

    private boolean map(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                model.up();
                break;
            case Input.Keys.A:
                model.left();
                break;
            case Input.Keys.S:
                model.down();
                break;
            case Input.Keys.D:
                model.right();
                break;
            case Input.Keys.Q:
                model.toggleAttack();
                break;
            case Input.Keys.SPACE:
                model.skip();
                break;
            case Input.Keys.ESCAPE:
                model.restart();
                screen = Screen.Start;
                break;
        }
        return super.keyDown(keycode);
    }

    private boolean menu(int keycode) {
        switch (keycode) {

        }
        return super.keyDown(keycode);
    }

    public void setStart() {
        screen = Screen.Start;
    }

    public void setMap() {
        screen = Screen.Map;
    }

    public void setMenu() {
        screen = Screen.Menu;
    }

}

