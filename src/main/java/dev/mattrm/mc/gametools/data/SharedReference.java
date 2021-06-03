package dev.mattrm.mc.gametools.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class SharedReference<V> extends AtomicReference<V> {
    private final Map<Integer, Runnable> listeners = new ConcurrentHashMap<>();
    private int nextId = 0;

    public SharedReference(V initialValue) {
        super(initialValue);
    }

    public SharedReference() {
        super();
    }

    public final void setAndNotify(V newValue) {
        this.set(newValue);
        this.notifyAllListeners();
    }

    public final int addListener(Runnable runnable) {
        this.listeners.put(++nextId, runnable);
        return nextId;
    }

    public final void removeListener(int id) {
        this.listeners.remove(id);
    }

    public final void notifyAllListeners() {
        this.listeners.values().forEach(Runnable::run);
    }
}
