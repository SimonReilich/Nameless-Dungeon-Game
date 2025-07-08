package io.github.simonreilich.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Button implements UiElement {
    // a button that executes a function when clicked

    public static final Texture left = new Texture("interface/left.png");
    public static final Texture right = new Texture("interface/right.png");
    public static final Texture middle = new Texture("interface/middle.png");

    private final Sprite text;
    private final Vector2 pos;
    private final float width;

    public Button(float x, float y, int width, String text) {
        this.pos = new Vector2(x, y);
        this.width = width;
        this.text = new Sprite(new Texture("interface/text/" + text + ".png"));
        this.text.scale(1.0f);
    }

    public void draw(SpriteBatch batch) {
        draw(batch, this.pos.x, this.pos.y);
    }

    public void draw(SpriteBatch batch, float x, float y) {
        batch.begin();
        batch.draw(left, x * UiElement.size, y * UiElement.size, UiElement.size, UiElement.size);
        batch.end();

        batch.begin();
        for (float xi = x + 1; xi < x + this.width - 1; xi++) {
            batch.draw(middle, xi * UiElement.size, y * UiElement.size, UiElement.size, UiElement.size);
        }
        batch.end();

        batch.begin();
        batch.draw(right, (x + this.width - 1) * UiElement.size, y * UiElement.size, UiElement.size, UiElement.size);
        batch.end();

        this.text.setCenter(x * UiElement.size + 0.5f * UiElement.size * this.width, y * UiElement.size + 0.5f * UiElement.size);
        batch.begin();
        this.text.draw(batch);
        batch.end();
    }

    public void update(Vector2 mouse) {
        // checks, if the click happened in the boundaries of the button
        // if this is the case, clicked() is executed
        if (this.pos.x * UiElement.size < mouse.x && mouse.x < (this.pos.x + this.width) * UiElement.size
            && this.pos.y * UiElement.size < mouse.y && mouse.y < (this.pos.y + 1) * UiElement.size) {
            clicked();
        }
    }

    // abstract method, intended to be overwritten by anonymous class
    public abstract void clicked();

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return 1.0f;
    }

    public void setX(float x) {
        this.pos.x = x;
    }

    public void setY(float y) {
        this.pos.y = y;
    }
}
