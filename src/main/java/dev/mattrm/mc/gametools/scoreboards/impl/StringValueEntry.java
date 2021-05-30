package dev.mattrm.mc.gametools.scoreboards.impl;

import dev.mattrm.mc.gametools.scoreboards.GameScoreboard;
import dev.mattrm.mc.gametools.scoreboards.ValueEntry;

public class StringValueEntry extends ValueEntry<String> {
    public StringValueEntry(GameScoreboard scoreboard, String value) {
        super(scoreboard, "", ValuePos.PREFIX, value);
    }

    public StringValueEntry(GameScoreboard scoreboard, String format, ValuePos valuePos, String value) {
        super(scoreboard, format, valuePos, value);
    }
}
