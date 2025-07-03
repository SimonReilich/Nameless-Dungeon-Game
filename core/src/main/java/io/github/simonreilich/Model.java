package io.github.simonreilich;

import com.badlogic.gdx.Gdx;
import io.github.simonreilich.screens.EndView;
import io.github.simonreilich.screens.MapView;
import io.github.simonreilich.screens.StartView;
import io.github.simonreilich.util.UpdateType;

public class Model {

    private final View view;
    private final Controller controller;
    private final StartView startView;
    private final MapView mapView;
    private final EndView endView;

    public Model(View view) {
        this.view = view;
        this.controller = new Controller(this);
        Gdx.input.setInputProcessor(controller);
        this.startView = new StartView();
        this.mapView = new MapView();
        this.endView = new EndView();
        this.startView.setModel(this);
        this.mapView.setModel(this);
        this.view.setScreen(startView);
    }

    public void update(UpdateType type) {
        switch (type) {
            case Exit:
                nextView();
        }
    }

    public void up() {
        mapView.up();
    }

    public void left() {
        mapView.left();
    }

    public void down() {
        mapView.down();
    }

    public void right() {
        mapView.right();
    }

    public void skip() {
        mapView.skip();
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

    public void clicked(int mouseX, int mouseY) {
        startView.click(mouseX, mouseY);
    }

    public void nextView() {
        if (this.view.getScreen() == startView) {
            this.view.setScreen(mapView);
            controller.setMap();
        } else if (this.view.getScreen() == mapView) {
            this.view.setScreen(endView);
            controller.setEnd();
        } else {
            startView.dispose();
            mapView.dispose();
            endView.dispose();
            view.dispose();
            Gdx.app.exit();
        }
    }

    public void toggleAttack() {
        mapView.toggleAttack();
    }

    public void restart() {
        view.create();
        endView.dispose();
    }

}
