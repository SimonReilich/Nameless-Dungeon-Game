package io.github.simonreilich;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import io.github.simonreilich.objects.Player;

public class Controller extends InputAdapter {

    private Model model;

    public Controller(Model model) {
        super();
        this.model = model;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                model.up();
            case Input.Keys.A:
                model.left();
            case Input.Keys.S:
                model.down();
            case Input.Keys.D:
                model.right();
            case Input.Keys.ESCAPE:
                model.exit();
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                //
            case Input.Keys.A:
                //
            case Input.Keys.S:
                //
            case Input.Keys.D:
                //
        }
        return super.keyDown(keycode);
    }

}
