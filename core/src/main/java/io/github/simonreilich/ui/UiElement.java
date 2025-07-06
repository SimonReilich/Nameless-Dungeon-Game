package io.github.simonreilich.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public interface UiElement {
    public static int size = 32;

    public void draw(SpriteBatch batch);
    public void draw(SpriteBatch batch, float x, float y);
    public void update(Vector2 mouse);
    public float getWidth();
    public float getHeight();
    public void setX(float x);
    public void setY(float y);
}
