package io.github.simonreilich;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

public class Controller extends InputAdapter {

    // Controller parses all inputs

    private final Model model;
    private boolean map;

    public Controller(Model model) {
        super();
        this.model = model;
        this.map = false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (this.map) return mapInput(keycode);
        return super.keyDown(keycode);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // ignore if its not left mouse button or first touch pointer
        if (button != Input.Buttons.LEFT || pointer > 0) return false;
        this.model.clicked(new Vector2(screenX, screenY));
        return true;
    }

    private boolean mapInput(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                this.model.up();
                break;
            case Input.Keys.A:
                this.model.left();
                break;
            case Input.Keys.S:
                this.model.down();
                break;
            case Input.Keys.D:
                this.model.right();
                break;
            case Input.Keys.Q:
                this.model.toggleAttack();
                break;
            case Input.Keys.SPACE:
                this.model.skip();
                break;
            case Input.Keys.ESCAPE:
                this.model.restart();
                this.map = false;
                break;
        }
        return super.keyDown(keycode);
    }

    public void setMap() {
        this.map = true;
    }

    public void setMenu() {
        this.map = false;
    }
}

