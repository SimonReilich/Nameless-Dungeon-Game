package io.github.simonreilich;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

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
            case End:
                return end(keycode);
        }
        return super.keyDown(keycode);
    }

    private boolean start(int keycode) {
        switch (keycode) {
            case Input.Keys.SPACE:
                model.nextView();
                screen = Screen.Map;
                break;
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
            case Input.Keys.ESCAPE:
                model.nextView();
                screen = Screen.End;
                break;
        }
        return super.keyDown(keycode);
    }

    private boolean menu(int keycode) {
        switch (keycode) {

        }
        return super.keyDown(keycode);
    }

    private boolean end(int keycode) {
        switch (keycode) {
            case Input.Keys.ESCAPE:
                model.nextView();
                break;
        }
        return super.keyDown(keycode);
    }

}

enum Screen {
    Start, Map, Menu, End
}
