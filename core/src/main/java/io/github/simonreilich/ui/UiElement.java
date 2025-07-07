package io.github.simonreilich.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public interface UiElement {
    int size = 32;

    void draw(SpriteBatch batch);

    void draw(SpriteBatch batch, float x, float y);

    void update(Vector2 mouse);

    float getWidth();

    float getHeight();

    void setX(float x);

    void setY(float y);
}
