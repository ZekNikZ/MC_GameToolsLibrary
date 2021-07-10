package dev.mattrm.mc.gametools.scoreboards.impl;

import dev.mattrm.mc.gametools.data.SharedReference;
import dev.mattrm.mc.gametools.scoreboards.GameScoreboard;
import dev.mattrm.mc.gametools.scoreboards.ValueEntry;

public class SharedReferenceEntry<T> extends ValueEntry<T> {
    private final SharedReference<T> ref;
    private final int refListenerId;

    public SharedReferenceEntry(GameScoreboard scoreboard, SharedReference<T> ref) {
        super(scoreboard, "", ValuePos.PREFIX, ref.get());
        this.ref = ref;
        this.refListenerId = this.ref.addListener(this::updateValue);
        this.updateValue();
    }

    public SharedReferenceEntry(GameScoreboard scoreboard, String format, ValuePos valuePos, SharedReference<T> ref) {
        super(scoreboard, format, valuePos, ref.get());
        this.ref = ref;
        this.refListenerId = this.ref.addListener(this::updateValue);
        this.updateValue();
    }

    @Override
    public T getValue() {
        return this.ref.get();
    }

    @Override
    public void setValue(T value) {
        if (this.ref != null) {
            this.ref.setAndNotify(value);
        }
    }

    public final void updateValue() {
        super.setValue(this.ref.get());
    }

    @Override
    protected void cleanup() {
        this.ref.removeListener(this.refListenerId);
    }
}
