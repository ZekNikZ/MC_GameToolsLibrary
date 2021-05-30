package dev.mattrm.mc.gametools.data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class SharedReference<V> extends AtomicReference<V> {
    private final List<Runnable> listeners = new ArrayList<>();

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

    public final void addListener(Runnable runnable) {
        this.listeners.add(runnable);
    }

    public final void removeListener(Runnable runnable) {
        this.listeners.remove(runnable);
    }

    public final void notifyAllListeners() {
        this.listeners.forEach(Runnable::run);
    }
}
