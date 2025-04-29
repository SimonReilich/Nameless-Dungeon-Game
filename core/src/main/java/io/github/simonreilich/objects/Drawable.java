package io.github.simonreilich.objects;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface Drawable {

    void draw(Batch batch);

    void dispose();
}
