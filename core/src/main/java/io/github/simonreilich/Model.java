package io.github.simonreilich;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.objects.Map;
import io.github.simonreilich.objects.Player;
import io.github.simonreilich.screens.GameView;

public class Model {

    private View view;
    private Controller controller;
    private boolean isRunning;
    private GameView gameView;
    private Player player;

    public Model(View view) {
        this.view = view;
        this.controller = new Controller(this);
        Gdx.input.setInputProcessor(controller);
        this.gameView = new GameView();
        this.view.setScreen(gameView);
        this.isRunning = true;
        this.player = new Player(new Sprite(new Texture("sprites/player.png")));

        gameView.enqueue(new Map("maps/testMap.tmx"));
        gameView.enqueue(player);

        // run();
    }

    public void run() {
        if (isRunning) {
            run();
        }
    }

    public void up() {
        player.up();
    }

    public void left() {
        player.left();
    }

    public void down() {
        player.down();
    }

    public void right() {
        player.right();
    }

    public void exit() {
        isRunning = false;
        gameView.dispose();
        view.dispose();
        Gdx.app.exit();
    }

}
