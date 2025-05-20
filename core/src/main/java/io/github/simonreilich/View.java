package io.github.simonreilich;

public class View extends com.badlogic.gdx.Game {

    private Model model;

    @Override
    public void create() {
        model = new Model(this);
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
