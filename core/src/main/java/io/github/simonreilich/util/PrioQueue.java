package io.github.simonreilich.util;

import com.badlogic.gdx.math.Vector2;

import java.util.*;

public class PrioQueue {
    private List<Map.Entry<Vector2, Integer>> queue;

    public PrioQueue() {
        queue = new ArrayList<>();
    }

    public void enqueue(Vector2 element, int priority) {
        if (!contains(element)) {
            queue.add(new AbstractMap.SimpleEntry<>(element, priority));
        }
    }

    public void decreasePriority(Vector2 element, int priority) {
        if (contains(element)) {
            queue.removeIf(p -> p.getKey().equals(element) && p.getValue() > priority);
        }
        if (!contains(element)) {
            queue.add(new AbstractMap.SimpleEntry<>(element, priority));
        }
    }

    public Vector2 removeMin() {
        Map.Entry<Vector2, Integer> element = queue.stream().min(Comparator.comparingInt(Map.Entry::getValue)).orElse(new AbstractMap.SimpleEntry<>(null, 0));
        queue.remove(element);
        return element.getKey();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public boolean contains(Vector2 element) {
        return queue.stream().anyMatch(p -> p.getKey().equals(element));
    }
}
