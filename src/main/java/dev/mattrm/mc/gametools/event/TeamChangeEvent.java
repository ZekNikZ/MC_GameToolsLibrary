package dev.mattrm.mc.gametools.event;

import dev.mattrm.mc.gametools.teams.GameTeam;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class TeamChangeEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final UUID player;
    private final GameTeam oldTeam;
    private final GameTeam newTeam;

    public TeamChangeEvent(UUID player, GameTeam oldTeam, GameTeam newTeam) {
        this.player = player;
        this.oldTeam = oldTeam;
        this.newTeam = newTeam;
    }

    public UUID getPlayerUUID() {
        return this.player;
    }

    public GameTeam getNewTeam() {
        return newTeam;
    }

    public GameTeam getOldTeam() {
        return oldTeam;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
