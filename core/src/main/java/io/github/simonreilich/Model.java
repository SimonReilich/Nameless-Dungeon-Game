package io.github.simonreilich;

import com.badlogic.gdx.Gdx;
import io.github.simonreilich.screens.GameView;

public class Model {

    private View view;
    private Controller controller;
    private boolean isRunning;

    public Model(View view) {
        this.view = view;
        this.controller = new Controller(this);
        Gdx.input.setInputProcessor(controller);
        this.view.setScreen(new GameView());
        this.isRunning = true;
    }

    public void run() {

        if (isRunning) {
            run();
        }
    }

    public void up() {

    }

    public void left() {

    }

    public void down() {

    }

    public void right() {

    }

    public void exit() {
        view.dispose();
        isRunning = false;
    }

}
