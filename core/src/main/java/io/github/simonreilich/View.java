package io.github.simonreilich;

public class View extends com.badlogic.gdx.Game {

    // View manages the window, screens can be applied by calling .setScreen()

    @Override
    public void create() {
        new Model(this);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }
}
