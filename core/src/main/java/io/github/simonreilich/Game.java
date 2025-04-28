package io.github.simonreilich;

import io.github.simonreilich.maps.Map;

public class Game extends com.badlogic.gdx.Game {

    @Override
    public void create() {
        setScreen(new Map());
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
