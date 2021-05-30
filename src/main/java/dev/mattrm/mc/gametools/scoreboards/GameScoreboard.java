package dev.mattrm.mc.gametools.scoreboards;

import dev.mattrm.mc.gametools.scoreboards.impl.SpaceEntry;
import dev.mattrm.mc.gametools.scoreboards.impl.StringEntry;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameScoreboard {
    private final Scoreboard scoreboard;
    private final Objective objective;
    private final List<ScoreboardEntry> entries = new ArrayList<>();
    private final List<String> strings = new ArrayList<>(15);
    private final List<Team> teams = new ArrayList<>(15);
    private int teamCounter = 0;
    private String title;

    public GameScoreboard(String title) {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = this.scoreboard.registerNewObjective("display", "dummy");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.setTitle(title);

        for (int i = 0; i < 15; i++) {
            strings.add(null);
            teams.add(null);
        }

        this.redraw();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.objective.setDisplayName(title);
    }

    public <T extends ScoreboardEntry> T addEntry(T entry) {
        this.entries.add(entry);
        this.redraw();
        return entry;
    }

    public StringEntry addEntry(String entry) {
        StringEntry se = new StringEntry(this, entry);
        this.entries.add(se);
        this.redraw();
        return se;
    }

    public void redraw() {
        int pos = 0;
        for (ScoreboardEntry entry : this.entries) {
            entry.render(pos);
            pos += entry.getRowCount();
        }
    }

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    public void addSpace() {
        this.addEntry(new SpaceEntry(this));
    }

    public void setString(int pos, String str) {
        String existing = this.strings.get(pos);
        if (pos >= 15 || Objects.equals(existing, str)) {
            return;
        }

        if (existing != null) {
            this.scoreboard.resetScores(existing);
        }

        this.strings.set(pos, str);
        this.objective.getScore(str).setScore(15 - pos);

        if (this.teams.get(pos) != null) {
            this.teams.get(pos).addEntry(str);
        }
    }

    public void setTeam(int pos, Team team) {
        if (pos >= 15) {
            return;
        }

        this.teams.set(pos, team);

        if (this.strings.get(pos) != null) {
            team.addEntry(this.strings.get(pos));
        }
    }

    public Team registerNewTeam() {
        return this.scoreboard.registerNewTeam("" + (teamCounter++));
    }

    public void unregisterTeam(Team team) {
        team.unregister();
    }
}
