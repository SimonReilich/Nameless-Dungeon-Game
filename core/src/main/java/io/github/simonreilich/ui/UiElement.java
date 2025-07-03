package io.github.simonreilich.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface UiElement {
    public static int size = 128;
    public static BitmapFont font = new BitmapFont();

    public void draw(SpriteBatch batch);
    public void draw(SpriteBatch batch, float x, float y);
    public void update(int mouseX, int mouseY);
    public float getWidth();
    public float getHeight();
    public void setX(float x);
    public void setY(float y);
}
