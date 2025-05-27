package io.github.simonreilich;

import com.badlogic.gdx.Gdx;
import io.github.simonreilich.screens.EndView;
import io.github.simonreilich.screens.MapView;

public class Model {

    private final View view;
    private final Controller controller;
    private final MapView mapView;
    private final EndView endView;

    public Model(View view) {
        this.view = view;
        this.controller = new Controller(this);
        Gdx.input.setInputProcessor(controller);
        this.mapView = new MapView();
        this.endView = new EndView();
        this.mapView.setModel(this);
        this.view.setScreen(mapView);
    }

    public void update(UpdateType type) {
        switch (type) {
            case Exit:
                exit();
        }
    }

    public void up() {
        mapView.player.up();
    }

    public void left() {
        mapView.player.left();
    }

    public void down() {
        mapView.player.down();
    }

    public void right() {
        mapView.player.right();
    }

    public void killed() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            this.view.setScreen(endView);
            mapView.dispose();
        }
    }

    public void exit() {
        if (!(this.view.getScreen() == endView)) {
            mapView.dispose();
        }
        endView.dispose();
        view.dispose();
        Gdx.app.exit();
    }

}
