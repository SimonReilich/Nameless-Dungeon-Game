package io.github.simonreilich.objects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;

public interface Drawable {

    void draw(OrthographicCamera cam, Batch batch);

    void dispose();
}
