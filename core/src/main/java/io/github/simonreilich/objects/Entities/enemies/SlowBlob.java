package io.github.simonreilich.objects.Entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import io.github.simonreilich.screens.MapView;

public class SlowBlob extends Blob {

    boolean move;

    public SlowBlob(int x, int y, MapView mapView) {
        super(x, y, mapView);
        this.setTexture(new Texture("sprites/blob/02.png"));
        move = false;
    }

    @Override
    public void move() {
        move = !move;
        if (move) {
            super.move();
        }
    }
}
