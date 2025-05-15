package io.github.simonreilich;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.objects.Entities.Enemy;
import io.github.simonreilich.objects.Map;
import io.github.simonreilich.objects.Entities.Player;
import io.github.simonreilich.screens.GameView;

import java.util.Random;

public class Model {

    private View view;
    private Controller controller;
    private boolean isRunning;
    private GameView gameView;
    private Map map;
    private Player player;

    public Model(View view) {
        this.view = view;
        this.controller = new Controller(this);
        Gdx.input.setInputProcessor(controller);
        this.gameView = new GameView();
        this.view.setScreen(gameView);
        this.isRunning = true;
        this.player = new Player(gameView);
        this.map = new Map("maps/testMap.tmx");

        Enemy enemy = new Enemy(15, 11);

        player.setPosX((Integer) map.getMap().getLayers().get(0).getProperties().get("spawnX"));
        player.setPosY((Integer) map.getMap().getLayers().get(0).getProperties().get("spawnY"));

        gameView.enqueue(map);
        gameView.enqueue(player);
        gameView.enqueue(enemy);
    }

    public void update() {
            if (!map.inBounds(player.getPosX(), player.getPosY())) {
                int dir = new Random().nextInt(4);
                if (dir == 0) {
                    up();
                } else if (dir == 1) {
                    down();
                } else if (dir == 2) {
                    left();
                } else if (dir == 3) {
                    right();
                }

        }
    }

    public void up() {
        if (map.inBounds(player.getPosX(), player.getPosY() + 1)) {
            player.up();
        }
    }

    public void left() {
        if (map.inBounds(player.getPosX() - 1, player.getPosY())) {
            player.left();
        }
    }

    public void down() {
        if (map.inBounds(player.getPosX(), player.getPosY() - 1)) {
            player.down();
        }
    }

    public void right() {
        if (map.inBounds(player.getPosX() + 1, player.getPosY())) {
            player.right();
        }
    }

    public void exit() {
        isRunning = false;
        gameView.dispose();
        view.dispose();
        Gdx.app.exit();
    }

}
