package dev.mattrm.mc.gametools.scoreboards.impl;

import dev.mattrm.mc.gametools.scoreboards.GameScoreboard;
import dev.mattrm.mc.gametools.scoreboards.ValueEntry;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerEntry extends ValueEntry<UUID> {
    private final boolean showTeam;

    public PlayerEntry(GameScoreboard scoreboard, UUID value) {
        super(scoreboard, "", ValuePos.PREFIX, value);
        this.showTeam = true;
    }

    public PlayerEntry(GameScoreboard scoreboard, String format, ValuePos valuePos, UUID value) {
        super(scoreboard, format, valuePos, value);
        this.showTeam = true;
    }

    public PlayerEntry(GameScoreboard scoreboard, UUID value, boolean showTeam) {
        this(scoreboard, "", ValuePos.PREFIX, value, showTeam);
    }

    public PlayerEntry(GameScoreboard scoreboard, String format, ValuePos valuePos, UUID value, boolean showTeam) {
        super(scoreboard, format, valuePos, value);
        this.showTeam = showTeam;
    }

    @Override
    public String getValueString() {
        String name;

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(this.getValue());
        if (offlinePlayer != null) {
            name = offlinePlayer.getName();
        } else {
            Player player = Bukkit.getPlayer(this.getValue());
            if (player != null) {
                name = this.showTeam ? player.getDisplayName() : player.getName();
            } else {
                name = "???";
            }
        }

        return name;
    }
}
