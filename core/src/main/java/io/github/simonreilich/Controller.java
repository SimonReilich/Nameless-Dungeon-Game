package io.github.simonreilich;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import io.github.simonreilich.entities.Player;

public class Controller extends InputAdapter {

    private Player player;

    public Controller(Player player) {
        super();
        this.player = player;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                player.up();
            case Input.Keys.A:
                player.left();
            case Input.Keys.S:
                player.down();
            case Input.Keys.D:
                player.right();
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return super.keyUp(keycode);
    }

}
