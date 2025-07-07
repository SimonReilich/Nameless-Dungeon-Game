package io.github.simonreilich.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import io.github.simonreilich.util.Align;
import io.github.simonreilich.util.Consts;

public class Div implements UiElement {

    private final Vector2 dim;
    private final Align align;
    private final UiElement[] children;
    private final Vector2 pos;

    public Div(float x, float y, float width, float height, Align align, Button... children) {
        this.pos = new Vector2(x, y);
        this.dim = new Vector2(width, height);
        this.align = align;
        this.children = children;
    }

    public void draw(SpriteBatch batch) {
        draw(batch, pos.x, pos.y);
    }

    public void draw(SpriteBatch batch, float x, float y) {
        // determines the layout and calls the corresponding draw-method
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
        float offset = dim.y - children[0].getHeight() - Consts.margin;
        children[0].setX(x + (dim.x - children[0].getWidth()) / 2);
        children[0].setY(y + offset);
        children[0].draw(batch);
        for (int i = 1; i < children.length; i++) {
            offset -= children[i - 1].getHeight();
            offset -= Consts.margin;
            children[i].setX(x + (dim.x - children[i].getWidth()) / 2);
            children[i].setY(y + offset);
            children[i].draw(batch);
        }
    }

    private void drawTopLeft(SpriteBatch batch, float x, float y) {
        float offset = dim.y - children[0].getHeight() - Consts.margin;
        children[0].setX(x + Consts.margin);
        children[0].setY(y + offset);
        children[0].draw(batch);
        for (int i = 1; i < children.length; i++) {
            offset -= children[i - 1].getHeight();
            offset -= Consts.margin;
            children[i].setX(x + Consts.margin);
            children[i].setY(y + offset);
            children[i].draw(batch);
        }
    }

    private void drawTopRight(SpriteBatch batch, float x, float y) {
        float offset = dim.y - children[0].getHeight() - Consts.margin;
        children[0].setX(x + dim.x - children[0].getWidth() - Consts.margin);
        children[0].setY(y + offset);
        children[0].draw(batch);
        for (int i = 1; i < children.length; i++) {
            offset -= children[i - 1].getHeight();
            offset -= Consts.margin;
            children[i].setX(x + dim.x - children[i].getWidth() - Consts.margin);
            children[i].setY(y + offset);
            children[i].draw(batch);
        }
    }

    private void drawBottom(SpriteBatch batch, float x, float y) {
        float offset = Consts.margin;
        children[0].draw(batch, x + (dim.x - children[0].getWidth()) / 2, y + offset);
        for (int i = 1; i < children.length; i++) {
            offset += children[i - 1].getHeight();
            offset += Consts.margin;
            children[i].draw(batch, x + (dim.x - children[i].getWidth()) / 2, y + offset);
        }
    }

    private void drawBottomLeft(SpriteBatch batch, float x, float y) {
        float offset = Consts.margin;
        children[0].draw(batch, x + Consts.margin, y + offset);
        for (int i = 1; i < children.length; i++) {
            offset += children[i - 1].getHeight();
            offset += Consts.margin;
            children[i].draw(batch, x + Consts.margin, y + offset);
        }
    }

    private void drawBottomRight(SpriteBatch batch, float x, float y) {
        float offset = 0;
        children[0].draw(batch, x + dim.x - children[0].getWidth() - Consts.margin, y + offset);
        for (int i = 1; i < children.length; i++) {
            offset += children[i - 1].getHeight();
            offset += Consts.margin;
            children[i].draw(batch, x + dim.x - children[i].getWidth() - Consts.margin, y + offset);
        }
    }

    private void drawCenter(SpriteBatch batch, float x, float y) {
        float effectiveHeight = (children.length - 1) * Consts.margin;
        for (UiElement child : children) {
            effectiveHeight += child.getHeight();
        }
        float offset = (dim.y - effectiveHeight) / 2;
        children[0].draw(batch, x + (dim.x - children[0].getWidth()) / 2, y + offset);
        for (int i = 1; i < children.length; i++) {
            offset += children[i - 1].getHeight();
            offset += Consts.margin;
            children[i].draw(batch, x + (dim.x - children[i].getWidth()) / 2, y + offset);
        }
    }

    private void drawLeft(SpriteBatch batch, float x, float y) {
        float effectiveHeight = (children.length - 1) * Consts.margin;
        for (UiElement child : children) {
            effectiveHeight += child.getHeight();
        }
        float offset = (dim.y - effectiveHeight) / 2;
        children[0].draw(batch, x + Consts.margin, y + offset);
        for (int i = 1; i < children.length; i++) {
            offset += children[i - 1].getHeight();
            offset += Consts.margin;
            children[i].draw(batch, x + Consts.margin, y + offset);
        }
    }

    private void drawRight(SpriteBatch batch, float x, float y) {
        float effectiveHeight = (children.length - 1) * Consts.margin;
        for (UiElement child : children) {
            effectiveHeight += child.getHeight();
        }
        float offset = (dim.y - effectiveHeight) / 2;
        children[0].draw(batch, x + dim.x - children[0].getWidth() - Consts.margin, y + offset);
        for (int i = 1; i < children.length; i++) {
            offset += children[i - 1].getHeight();
            offset += Consts.margin;
            children[i].draw(batch, x + dim.x - children[i].getWidth() - Consts.margin, y + offset);
        }
    }

    public void update(Vector2 mouse) {
        // passes the coordinates of the click to every child-element
        for (UiElement child : children) {
            child.update(mouse);
        }
    }

    public float getWidth() {
        return dim.x;
    }

    public float getHeight() {
        return dim.y;
    }

    public void setX(float x) {
        this.pos.x = x;
    }

    public void setY(float y) {
        this.pos.y = y;
    }
}
