package io.github.simonreilich;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.simonreilich.screens.MapView;
import io.github.simonreilich.screens.StartView;

public class Model {

    private final View view;
    private final Controller controller;
    private final StartView startView;
    private final MapView mapView;
    private Sprite playerSkin;

    public Model(View view) {
        this.view = view;
        this.controller = new Controller(this);
        Gdx.input.setInputProcessor(controller);
        this.startView = new StartView();
        this.mapView = new MapView();
        this.startView.setModel(this);
        this.mapView.setModel(this);
        this.view.setScreen(startView);
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
            mapView.dispose();
            restart();
        }
    }

    public void clicked(int mouseX, int mouseY) {
        startView.click(mouseX, mouseY);
    }

    public void toggleAttack() {
        mapView.toggleAttack();
    }

    public void restart() {
        view.create();
        ((StartView) view.getScreen()).time = ((StartView) view.getScreen()).animationEnd;
    }

    public void startGame(Texture heroSkin) {
        controller.setMap();
        view.setScreen(mapView);
        mapView.setHeroSkin(heroSkin);
    }

}
