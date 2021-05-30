package dev.mattrm.mc.gametools.scoreboards.impl;

import dev.mattrm.mc.gametools.scoreboards.GameScoreboard;
import dev.mattrm.mc.gametools.scoreboards.ValueEntry;

public class IntValueEntry extends ValueEntry<Integer> {
    public IntValueEntry(GameScoreboard scoreboard, Integer value) {
        super(scoreboard, "", ValuePos.PREFIX, value);
    }

    public IntValueEntry(GameScoreboard scoreboard, String format, ValuePos valuePos, Integer value) {
        super(scoreboard, format, valuePos, value);
    }

    public void increment() {
        this.setValue(this.getValue() + 1);
    }

    public void decrement() {
        this.setValue(this.getValue() - 1);
    }
}
