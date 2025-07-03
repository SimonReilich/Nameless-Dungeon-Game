package io.github.simonreilich.screens;

import io.github.simonreilich.util.UpdateType;
import io.github.simonreilich.objects.Drawable;

public interface DrawQueue {

    public void enqueue(Drawable drawable);
    public void dequeue(Drawable drawable);
    public void dequeueAll();
    public void dequeueAndDispose(Drawable drawable);
    public void dequeueAndDisposeAll();
    public void prioritize(Drawable drawable);
    public void deprioritize(Drawable drawable);

    public void updateAll(UpdateType type, float delta);

}
