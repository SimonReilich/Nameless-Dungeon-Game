package io.github.simonreilich.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.simonreilich.util.Align;

public class Div implements UiElement{

    private float x, y;
    private final float width, height;

    private final Align align;
    private final UiElement[] children;
    private static final float MARGIN = 0.5f;

    public Div(int x, int y, float width, float height, Align align, Button... children) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.align = align;
        this.children = children;
    }

    public void update(int mouseX, int mouseY) {
        for (UiElement child : children) {
            child.update(mouseX, mouseY);
        }
    }

    public void draw(SpriteBatch batch) {
        draw(batch, x, y);
    }

    public void draw(SpriteBatch batch, float x, float y) {
        switch (align) {
            case TOP:
                drawTop(batch, x, y);
                break;
            case TOP_LEFT:
                drawTopLeft(batch, x, y);
                break;
            case TOP_RIGHT:
                drawTopRight(batch, x, y);
                break;
            case BOTTOM:
                drawBottom(batch, x, y);
                break;
            case BOTTOM_LEFT:
                drawBottomLeft(batch, x, y);
                break;
            case BOTTOM_RIGHT:
                drawBottomRight(batch, x, y);
                break;
            case CENTER:
                drawCenter(batch, x, y);
                break;
            case LEFT:
                drawLeft(batch, x, y);
                break;
            case RIGHT:
                drawRight(batch, x, y);
                break;
        }
    }

    private void drawTop(SpriteBatch batch, float x, float y) {
        float offset = height - children[0].getHeight() - MARGIN;
        children[0].setX(x + (width - children[0].getWidth()) / 2);
        children[0].setY(y + offset);
        children[0].draw(batch);
        for (int i = 1; i < children.length; i++) {
            offset -= children[i - 1].getHeight();
            offset -= MARGIN;
            children[i].setX(x + (width - children[i].getWidth()) / 2);
            children[i].setY(y + offset);
            children[i].draw(batch);
        }
    }

    private void drawTopLeft(SpriteBatch batch, float x, float y) {
        float offset = height - children[0].getHeight() - MARGIN;
        children[0].draw(batch, x + MARGIN, y + offset);
        for (int i = 1; i < children.length; i++) {
            offset -= children[i - 1].getHeight();
            offset -= MARGIN;
            children[i].draw(batch, x + MARGIN, y + offset);
        }
    }

    private void drawTopRight(SpriteBatch batch, float x, float y) {
        float offset = height - children[0].getHeight() - MARGIN;
        children[0].draw(batch, x + width - children[0].getWidth() - MARGIN, y + offset);
        for (int i = 1; i < children.length; i++) {
            offset -= children[i - 1].getHeight();
            offset -= MARGIN;
            children[i].draw(batch, x + width - children[i].getWidth() - MARGIN, y + offset);
        }
    }

    private void drawBottom(SpriteBatch batch, float x, float y) {
        float offset = MARGIN;
        children[0].draw(batch, x + (float) (width - children[0].getWidth()) / 2, y + offset);
        for (int i = 1; i < children.length; i++) {
            offset += children[i - 1].getHeight();
            offset += MARGIN;
            children[i].draw(batch, x + (float) (width - children[i].getWidth()) / 2, y + offset);
        }
    }

    private void drawBottomLeft(SpriteBatch batch, float x, float y) {
        float offset = MARGIN;
        children[0].draw(batch, x + MARGIN, y + offset);
        for (int i = 1; i < children.length; i++) {
            offset += children[i - 1].getHeight();
            offset += MARGIN;
            children[i].draw(batch, x + MARGIN, y + offset);
        }
    }

    private void drawBottomRight(SpriteBatch batch, float x, float y) {
        float offset = 0;
        children[0].draw(batch, x + width - children[0].getWidth() - MARGIN, y + offset);
        for (int i = 1; i < children.length; i++) {
            offset += children[i - 1].getHeight();
            offset += MARGIN;
            children[i].draw(batch, x + width - children[i].getWidth() - MARGIN, y + offset);
        }
    }

    private void drawCenter(SpriteBatch batch, float x, float y) {
        float effectiveHeight = (children.length - 1) * MARGIN;
        for (UiElement child : children) {
            effectiveHeight += child.getHeight();
        }
        float offset = (height - effectiveHeight) / 2;
        children[0].draw(batch, x + (float) (width - children[0].getWidth()) / 2, y + offset);
        for (int i = 1; i < children.length; i++) {
            offset += children[i - 1].getHeight();
            offset += MARGIN;
            children[i].draw(batch, x + (float) (width - children[i].getWidth()) / 2, y + offset);
        }
    }

    private void drawLeft(SpriteBatch batch, float x, float y) {
        float effectiveHeight = (children.length - 1) * MARGIN;
        for (UiElement child : children) {
            effectiveHeight += child.getHeight();
        }
        float offset = (height - effectiveHeight) / 2;
        children[0].draw(batch, x + MARGIN, y + offset);
        for (int i = 1; i < children.length; i++) {
            offset += children[i - 1].getHeight();
            offset += MARGIN;
            children[i].draw(batch, x + MARGIN, y + offset);
        }
    }

    private void drawRight(SpriteBatch batch, float x, float y) {
        float effectiveHeight = (children.length - 1) * MARGIN;
        for (UiElement child : children) {
            effectiveHeight += child.getHeight();
        }
        float offset = (height - effectiveHeight) / 2;
        children[0].draw(batch, x + width - children[0].getWidth() - MARGIN, y + offset);
        for (int i = 1; i < children.length; i++) {
            offset += children[i - 1].getHeight();
            offset += MARGIN;
            children[i].draw(batch, x + width - children[i].getWidth() - MARGIN, y + offset);
        }
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
