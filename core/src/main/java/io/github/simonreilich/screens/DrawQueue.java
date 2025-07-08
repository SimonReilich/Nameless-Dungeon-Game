package io.github.simonreilich.screens;

import io.github.simonreilich.objects.Drawable;
import io.github.simonreilich.util.UpdateType;

public interface DrawQueue {

    public void enqueue(Drawable drawable);

    public void dequeue(Drawable drawable);

    public void dequeueAll();

    public void updateAll(UpdateType type, float delta);

}
