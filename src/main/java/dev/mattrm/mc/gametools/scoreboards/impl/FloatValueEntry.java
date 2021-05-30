package dev.mattrm.mc.gametools.scoreboards.impl;

import dev.mattrm.mc.gametools.scoreboards.GameScoreboard;
import dev.mattrm.mc.gametools.scoreboards.ValueEntry;

public class FloatValueEntry extends ValueEntry<Float> {
    public FloatValueEntry(GameScoreboard scoreboard, Float value) {
        super(scoreboard, "", ValuePos.PREFIX, value);
    }

    public FloatValueEntry(GameScoreboard scoreboard, String format, ValuePos valuePos, Float value) {
        super(scoreboard, format, valuePos, value);
    }
}
