package dev.mattrm.mc.gametools.scoreboards.impl;

import dev.mattrm.mc.gametools.scoreboards.GameScoreboard;
import dev.mattrm.mc.gametools.scoreboards.ScoreboardEntry;
import org.apache.commons.lang.StringUtils;

import java.util.stream.Stream;

public class SpaceEntry extends ScoreboardEntry {
    public SpaceEntry(GameScoreboard scoreboard) {
        super(scoreboard);
    }

    @Override
    public void render(int pos) {
        this.scoreboard.setString(pos, StringUtils.repeat(" ", pos));
    }
}
