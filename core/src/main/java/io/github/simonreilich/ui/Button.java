package io.github.simonreilich.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Button implements UiElement {

    private float x, y;
    private float width;
    public static final Texture left   = new Texture("interface/left.png");
    public static final Texture right  = new Texture("interface/right.png");
    public static final Texture middle = new Texture("interface/middle.png");

    public String text;


    public Button(int x, int y, int width, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        font.getData().setScale((float) size / 30);
        font.setColor(1, 1, 1, 1);
        this.text = text;
    }

    public abstract void clicked();

    public void update(int mouseX, int mouseY) {
        System.out.println("Mouse X: " + mouseX + " Mouse Y: " + mouseY + " X: " + x * size + " Y: " + y * size);
        if (x * size < mouseX && mouseX < (x + width) * size
        && y * size < mouseY && mouseY < (y + 1) * size) {
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

        batch.begin();
        font.draw(batch, text, x * size + (size - font.getCapHeight()) / 2.2f, ((y + 1) * size) - (size - font.getCapHeight()) / 2.2f);
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
