package dev.mattrm.mc.gametools.scoreboards;

import java.util.ArrayList;
import java.util.List;

public abstract class CompositeScoreboardEntry extends ScoreboardEntry {
    public List<ScoreboardEntry> children = new ArrayList<>();

    public CompositeScoreboardEntry(GameScoreboard scoreboard) {
        super(scoreboard);
    }

    public List<ScoreboardEntry> getChildren() {
        return this.children;
    }

    public int getChildCount() {
        return this.children.size();
    }

    public int addChild(ScoreboardEntry entry) {
        int pos = this.getChildCount();
        this.addChild(pos, entry);
        return pos;
    }

    public void addChild(int position, ScoreboardEntry entry) {
        this.children.add(position, entry);
    }

    public ScoreboardEntry removeChild(int position) {
        return this.children.remove(position);
    }

    public int getRowCount() {
        return this.children.stream().mapToInt(ScoreboardEntry::getRowCount).sum();
    }

    @Override
    public void render(int pos) {
        for (ScoreboardEntry entry : this.children) {
            entry.render(pos);
            pos += entry.getRowCount();
        }
    }
}
