package io.github.simonreilich.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import io.github.simonreilich.Consts;

public abstract class Button implements UiElement {

    private float x, y;
    private float width;
    public static final Texture left   = new Texture("interface/left.png");
    public static final Texture right  = new Texture("interface/right.png");
    public static final Texture middle = new Texture("interface/middle.png");
    public final Sprite text;


    public Button(float x, float y, int width, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.text = new Sprite(new Texture("interface/text/" + text + ".png"));
        this.text.scale(1.0f);
    }

    public abstract void clicked();

    public void update(Vector2 mouse) {
        if (x * size < mouse.x && mouse.x < (x + width) * size
        && y * size < mouse.y && mouse.y < (y + 1) * size) {
            clicked();
        }
    }

    public void draw(SpriteBatch batch) {
        draw(batch, x, y);
    }

    public void draw(SpriteBatch batch, float x, float y) {
        batch.begin();
        batch.draw(left, x * size, y * size, size, size);
        batch.end();

        batch.begin();
        for (float xi = x + 1; xi < x + width - 1; xi++) {
            batch.draw(middle, xi * size, y * size, size, size);
        }
        batch.end();

        batch.begin();
        batch.draw(right, (x + width - 1) * size, y * size, size, size);
        batch.end();

        text.setCenter(x * size + 0.5f * size * width, y * size + 0.5f * size);
        batch.begin();
        text.draw(batch);
        batch.end();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return 1.0f;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
