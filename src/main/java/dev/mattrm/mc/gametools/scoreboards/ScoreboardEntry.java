package dev.mattrm.mc.gametools.scoreboards;

public abstract class ScoreboardEntry {
    protected final GameScoreboard scoreboard;

    public ScoreboardEntry(GameScoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    public abstract void render(int pos);

    public int getRowCount() {
        return 1;
    }

    protected void markDirty() {
        this.scoreboard.redraw();
    }
}
