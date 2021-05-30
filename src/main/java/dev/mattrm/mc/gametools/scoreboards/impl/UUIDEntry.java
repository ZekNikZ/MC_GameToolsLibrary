package dev.mattrm.mc.gametools.scoreboards.impl;

import dev.mattrm.mc.gametools.scoreboards.GameScoreboard;
import dev.mattrm.mc.gametools.scoreboards.ValueEntry;

import java.util.UUID;

public class UUIDEntry extends ValueEntry<UUID> {
    public UUIDEntry(GameScoreboard scoreboard, UUID value) {
        super(scoreboard, "", ValuePos.PREFIX, value);
    }

    public UUIDEntry(GameScoreboard scoreboard, String format, ValuePos valuePos, UUID value) {
        super(scoreboard, format, valuePos, value);
    }
}
