package dev.mattrm.mc.gametools.scoreboards.impl;

import dev.mattrm.mc.gametools.scoreboards.GameScoreboard;
import dev.mattrm.mc.gametools.scoreboards.ScoreboardEntry;

public class StringEntry extends ScoreboardEntry {
    private final String str;

    public StringEntry(GameScoreboard scoreboard, String str) {
        super(scoreboard);

        this.str = str;
    }

    @Override
    public void render(int pos) {
        this.scoreboard.setString(pos, this.str);
    }
}
