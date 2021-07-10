package dev.mattrm.mc.gametools.scoreboards;

import org.bukkit.scoreboard.Team;

public abstract class ValueEntry<T> extends ScoreboardEntry {
    public enum ValuePos {
        PREFIX,
        SUFFIX
    }

    private String format;
    private T value;
    private final ValuePos valuePos;
    private Team team;

    public ValueEntry(GameScoreboard scoreboard, String format, ValuePos valuePos, T value) {
        super(scoreboard);
        this.team = this.scoreboard.registerNewTeam();
        this.valuePos = valuePos;
        this.setFormat(format);
        this.setValue(value);
    }

    protected String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
        this.markDirty();
    }

    public T getValue() {
        return value;
    }

    protected String getValueString() {
        return this.getValue().toString();
    }

    public void setValue(T value) {
        try {
        this.value = value;
            if (this.valuePos == ValuePos.PREFIX) {
                this.team.setPrefix(this.getValueString());
            } else {
                this.team.setSuffix(this.getValueString());
            }
            this.markDirty();
        } catch (IllegalStateException e) {
            Team t = this.scoreboard.registerNewTeam();
            t.setSuffix(this.team.getSuffix());
            t.setPrefix(this.team.getPrefix());
            this.team = t;
            this.setValue(value);
        }
    }

    @Override
    public void render(int pos) {
        this.scoreboard.setString(pos, this.format);
        Team t = this.scoreboard.setTeam(pos, this.team);
        if (t != null) {
            this.team = t;
        }
    }
}
