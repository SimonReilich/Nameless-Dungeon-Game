package io.github.simonreilich.util;

import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map;

public class PrioQueue {
    // implementation of a priority queue for the A*-pathfinding

    private final Map<Vector2, Float> queue;

    public PrioQueue() {
        queue = new HashMap<>();
    }

    public void enqueue(Vector2 element, float priority) {
        if (!contains(element)) {
            queue.put(element, priority);
        }
    }

    public void decreasePriority(Vector2 element, float priority) {
        if (contains(element) && priority < queue.get(element)) {
            queue.remove(element);
        }
        if (!contains(element)) {
            queue.put(element, priority);
        }
    }

    public Vector2 removeMin() {
        Vector2 min = queue.entrySet().stream().min((v1, v2) -> Float.compare(v1.getValue(), v2.getValue())).get().getKey();
        queue.remove(min);
        return min;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public boolean contains(Vector2 element) {
        return queue.containsKey(element);
    }

    public float getPriority(Vector2 element) {
        return queue.get(element);
    }
}
